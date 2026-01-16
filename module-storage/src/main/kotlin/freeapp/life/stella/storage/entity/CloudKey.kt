package freeapp.life.stella.storage.entity

import freeapp.life.stella.storage.entity.type.CloudProvider
import jakarta.persistence.*
import java.time.LocalDateTime


@Entity
@Table(name = "cloud_key")
class CloudKey(
    user: User,
    provider: CloudProvider,
    region: String,
    bucket: String,
    accessKey: String,
    secretKey: String,
    endpoint: String = "",
) : BaseEntity() {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user = user

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "provider")
    var provider = provider

    @Column(nullable = false, name = "region")
    var region = region

    @Column(nullable = false, name = "bucket")
    var bucket = bucket

    @Column(nullable = false, name = "access_key", length = 256)
    val accessKey = accessKey

    @Column(nullable = false, length = 256, name = "secret_key")
    var secretKey = secretKey

    @Column(nullable = true, name = "endpoint")
    var endPoint = endpoint

    @Column(name = "deleted_at", nullable = true)
    var deletedAt: LocalDateTime? = null

    fun disconnect(time: LocalDateTime = LocalDateTime.now()) {
        this.deletedAt = time
    }

}
