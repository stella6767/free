package freeapp.life.stella.api.service

import freeapp.life.stella.api.service.file.S3Service
import freeapp.life.stella.api.web.dto.DownloadDto
import freeapp.life.stella.api.web.dto.PaginatedS3Objects
import freeapp.life.stella.api.web.dto.PresignedPartRequestDto
import freeapp.life.stella.api.web.dto.S3ConnectionRequestDto
import freeapp.life.stella.api.web.dto.S3ObjectInfo
import freeapp.life.stella.api.web.dto.S3UploadAbortDto
import freeapp.life.stella.api.web.dto.S3UploadCompleteDto
import freeapp.life.stella.api.web.dto.S3UploadResultDto
import freeapp.life.stella.api.web.dto.UploadInitiateResponseDto
import freeapp.life.stella.api.web.dto.UploadType
import freeapp.life.stella.storage.entity.CloudKey
import freeapp.life.stella.storage.entity.User
import freeapp.life.stella.storage.repository.CloudKeyRepository
import freeapp.life.stella.storage.repository.CloudObjectRepository
import jakarta.persistence.EntityNotFoundException
import mu.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.GetObjectRequest
import software.amazon.awssdk.services.s3.model.HeadBucketRequest
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest
import java.io.File
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.time.Duration
import java.time.ZoneId


@Service
class CloudUploaderService(
    private val s3Service: S3Service,
    private val s3KeyRepository: CloudKeyRepository,
    private val s3ObjectRepository: CloudObjectRepository,

    ) {

    private val log = KotlinLogging.logger { }

    private val UPLOAD_THRESHOLD = 100 * 1024 * 1024 // 100MB


    fun testConnection(req: S3ConnectionRequestDto) {

        val s3Client =
            s3Service.createS3Client(
                req.accessKey,
                req.secretKey,
                req.region
            )

        s3Client.headBucket(HeadBucketRequest.builder().bucket(req.bucket).build())
        s3Client.close()
    }


    @Transactional
    fun saveCloudKey(
        user: User,
        s3ConnectionRequestDto: S3ConnectionRequestDto
    ): CloudKey {
        val s3Key =
            s3ConnectionRequestDto.toEntity(user)
        return s3KeyRepository.save(s3Key)
    }


    @Transactional(readOnly = true)
    fun getObjectsByCloudKey(
        cloudKey: CloudKey,
        prefix: String,
        size: Int,
        continuationToken: String,
    ): PaginatedS3Objects {

        val s3Client = s3Service.createS3Client(
            cloudKey.accessKey,
            cloudKey.secretKey,
            cloudKey.region
        )

        val s3Objects =
            getObjectsBySize(s3Client, cloudKey.bucket, prefix, size, continuationToken)

        s3Client.close()

        return s3Objects
    }


    @Transactional(readOnly = true)
    fun findCloudKeyByUser(user: User): CloudKey? {

        val s3Key =
            s3KeyRepository.findKeyByUser(user)

        return s3Key
    }


    fun getPresignedPartUrl(
        user: User,
        dto: PresignedPartRequestDto,
    ): String {

        val s3Key =
            findCloudKeyByUser(user = user)
                ?: throw EntityNotFoundException("s3Key not found")

        return s3Service.getPresignedPartUrl(s3Key.bucket, dto.fileKey, dto.uploadId, dto.partNumber)
    }

    fun completeUpload(
        user: User,
        req: S3UploadCompleteDto,
    ): S3UploadResultDto {

        val s3Key =
            findCloudKeyByUser(user = user)
                ?: throw EntityNotFoundException("s3Key not found")

        return S3UploadResultDto(
            fileKey = s3Service.completeUpload(s3Key.bucket, req.uploadId, req.fileKey, req.parts),
        )
    }


    fun abortMultipartUpload(
        bucket: String,
        s3UploadAbortDto: S3UploadAbortDto,
    ) {

        return s3Service.abortMultipartUpload(
            bucket = bucket,
            filename = s3UploadAbortDto.filename,
            uploadId = s3UploadAbortDto.uploadId
        )
    }


    @Transactional
    fun disconnectCloudKeyByUser(user: User) {

        val s3Key =
            s3KeyRepository.findKeyByUser(user) ?: throw EntityNotFoundException("s3key not found")

        s3Key.disconnect()
    }


    fun buildBreadcrumbs(
        prefix: String,
        objects: MutableList<S3ObjectInfo>
    ): List<S3ObjectInfo> {

        if (prefix.isEmpty()) return objects

        val parts =
            prefix.split("/").dropLast(1)

        val directoryDto =
            S3ObjectInfo.toDirectoryDto("", "")

        objects.add(0, directoryDto)

        var currentPath = ""
        for ((index, part) in parts.withIndex()) {
            currentPath += "$part/"
            objects.add(index + 1, S3ObjectInfo.toDirectoryDto(currentPath, part))
        }

        return objects
    }


    fun initiateUpload(
        bucket: String,
        targetObjectDir: String,
        filename: String,
        contentType: String,
        fileSize: Long,
    ): UploadInitiateResponseDto {

        val fileKey =
            targetObjectDir + File.separator + filename

        if (fileSize <= UPLOAD_THRESHOLD) {
            return UploadInitiateResponseDto(
                uploadType = UploadType.SINGLE,
                fileKey = fileKey,
                presignedUrl = s3Service.getSingleUploadUrl(bucket, fileKey, contentType)
            )
        }
        return UploadInitiateResponseDto(
            uploadType = UploadType.MULTIPART,
            fileKey = fileKey,
            uploadId = s3Service.initiateMultipartUpload(bucket, fileKey, contentType)
        )
    }


    fun getDownloadPresignedUrl(
        user: User,
        fileKey: String,
    ): DownloadDto {

        val s3Key =
            findCloudKeyByUser(user = user)
                ?: throw EntityNotFoundException("s3Key not found")

        return s3Service.getDownloadPresignedUrl(s3Key.bucket, fileKey)
    }



    fun getObjectsBySize(
        s3Client: S3Client,
        bucket: String,
        prefix: String,
        size: Int,
        token: String
    ): PaginatedS3Objects {

        val objects =
            mutableListOf<S3ObjectInfo>()

        val request = ListObjectsV2Request.builder()
            .bucket(bucket)
            .prefix(prefix)
            .delimiter("/") // 폴더 구조 유지
            .maxKeys(size)
            .apply {
                if (!token.isNullOrBlank()) {
                    continuationToken(token)
                }
            }
            .build()

        val response = s3Client.listObjectsV2(request)

        // 폴더들 (CommonPrefixes) 추가
        response.commonPrefixes().forEach { commonPrefix ->
            val folderKey = commonPrefix.prefix()
            val folderName =
                folderKey.removeSuffix("/").substringAfterLast("/")
            if (folderName.isNotEmpty()) {
                objects.add(
                    S3ObjectInfo.toDirectoryDto(folderKey, folderName)
                )
            }
        }

        // 파일들 추가
        response.contents().forEach { s3Object ->
            val key = s3Object.key()

            // 현재 레벨의 객체만 포함 (중첩된 폴더 내부 파일 제외)
            if (key != prefix && !key.removePrefix(prefix).contains("/")) {
                val name = key.substringAfterLast("/")
                val extension = if (name.contains(".")) name.substringAfterLast(".") else ""

                objects.add(
                    S3ObjectInfo(
                        key = key,
                        name = name,
                        isDirectory = false,
                        size = s3Object.size(),
                        lastModified = s3Object.lastModified().atZone(ZoneId.systemDefault()).toLocalDateTime(),
                        extension = extension
                    )
                )
            }
        }


        return PaginatedS3Objects(
            objects,
            response.nextContinuationToken() ?: token,
            !response.isTruncated
        )
    }


}