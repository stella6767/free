package freeapp.life.stella.storage.entity



import freeapp.life.stella.storage.entity.type.SignType
import jakarta.persistence.*

/**
 * Add profile url, default profile url
 * social id as username
 *
 */


@Entity
@Table(name = "user")
class User(
    username: String,
    email: String?,
    password: String,
    rawData: String,
    signType: SignType,
    role: Role = Role.USER,
) : BaseEntity() {

    @Column(nullable = false)
    val username = username

    @Column(nullable = true, length = 100)
    val email = email

    @Column(nullable = false, length = 100)
    val password = password

    @Column(length = 5000)
    val rawData = rawData

    @Enumerated(EnumType.STRING)
    private val role = role

    @Enumerated(EnumType.STRING)
    private val signType = signType

    enum class Role {
        USER, ADMIN
    }


}