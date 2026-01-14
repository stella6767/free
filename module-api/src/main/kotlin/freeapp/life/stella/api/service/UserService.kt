package freeapp.life.stella.api.service


import freeapp.life.stella.api.util.clearSecurityContext
import freeapp.life.stella.api.web.dto.UpdateProfileDto
import freeapp.life.stella.api.web.dto.UserDeleteRequestDto
import freeapp.life.stella.api.web.dto.UserResponseDto
import freeapp.life.stella.storage.entity.User
import freeapp.life.stella.storage.entity.type.SignType
import freeapp.life.stella.storage.repository.UserRepository
import jakarta.persistence.EntityNotFoundException
import mu.KotlinLogging
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

@Service
class UserService(
    private val userRepository: UserRepository,
    private val encoder: PasswordEncoder,
) {

    private val log = KotlinLogging.logger { }



    @Transactional
    fun createDummyUser(): User {

        val users = userRepository.findAll()

        return  if (users.isEmpty()){
            val rootUser = User(
                email = "dummy@example.com",
                password = encoder.encode("1234"),
                role = User.Role.ADMIN,
                rawData = "",
                username = "stella6767",
                status = User.Status.ACTIVATED,
                signType = SignType.EMAIL,
            )
            userRepository.save(rootUser)
        }else{
            userRepository.findByIdOrNull(1) ?: throw EntityNotFoundException("user with id 1 not found")
        }

    }


    @Transactional(readOnly = true)
    fun findUserById(userId: Long): UserResponseDto {

        val user = (userRepository.findByIdOrNull(userId)
            ?: throw EntityNotFoundException("user with id $userId not found"))

        return UserResponseDto.fromEntity(user)
    }


    @Transactional
    fun deleteUser(
        userId: Long,
        deleteRequestDto: UserDeleteRequestDto,
    ) {

        val user =
            userRepository.findByIdOrNull(userId) ?: throw EntityNotFoundException("can not find user with id $userId")

        user.delete(deleteRequestDto.reason)

        clearSecurityContext()
    }


    @Transactional
    fun updateUser(
        userId: Long,
        profileDto: UpdateProfileDto,
        file: MultipartFile?
    ): UserResponseDto {


        log.debug { "Update user $profileDto ${file?.originalFilename}" }

        val user =
            userRepository.findByIdOrNull(userId)
                ?: throw EntityNotFoundException("can not find user with id ${userId}")

        val encPassword = if (profileDto.password.isNotBlank()) {
            encoder.encode(profileDto.password)
        } else ""

        user.update(profileDto.username, encPassword)

        //principal.user = user //세션동기화
        return UserResponseDto.fromEntity(user)
    }


}