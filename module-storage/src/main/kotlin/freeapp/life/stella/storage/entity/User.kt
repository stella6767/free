package freeapp.life.stella.storage.entity


import freeapp.life.stella.storage.entity.type.SignType
import jakarta.persistence.*
import org.hibernate.annotations.Comment
import java.time.LocalDateTime

/**
 * Add profile url, default profile url
 * social id as username
 *
 */


@Entity
@Table(name = "users")
class User(
    socialId: String? = null,
    status: Status,
    username: String,
    email: String?,
    password: String,
    deleteReason: String = "",
    rawData: String,
    profileImg: String = "",
    signType: SignType,
    lastLoginDate: LocalDateTime? = null,
    role: Role = Role.USER,
) : BaseEntity() {

    @Column(name = "social_id")
    var socialId: String? = socialId

    @Column(nullable = false)
    var username = username

    @Column(nullable = true, length = 100)
    val email = email

    @Column(nullable = false, length = 100)
    var password = password

    @Column(name = "raw_data", length = 5000)
    val rawData = rawData

    @Column(name = "profile_img", length = 500)
    var profileImg = profileImg

    @Enumerated(EnumType.STRING)
    val role = role

    @Column(name = "sign_type")
    @Enumerated(EnumType.STRING)
    val signType = signType

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    @Comment("PENDING(\"화원가입 진행 중\"), ACTIVATED(\"활성\"), DELETED(\"탈퇴\"), DEACTIVATED(\"비활성\")")
    var status = status

    @Column(name = "last_login_date")
    var lastLoginDate = lastLoginDate

    @Column(name = "delete_reason", length = 500)
    var deleteReason = deleteReason


    fun updateLastLoginDate(time: LocalDateTime = LocalDateTime.now()) {
        this.lastLoginDate = time
    }


    fun delete(reason: String) {
        this.status = Status.DELETED
        this.deleteReason = reason
    }

    fun update(username: String, encPassword: String, profileUrl: String) {
        this.username = username
        if (profileUrl.isNotBlank()) {
            this.profileImg = profileUrl
        }
        if (encPassword.isNotBlank()) {
            this.password = encPassword
        }
    }


    enum class Role(
        val value: String
    ) {

        USER("ROLE_USER"),
        ADMIN("ROLE_ADMIN"),
    }


    enum class Status(
        val displayName: String
    ) {
        PENDING("화원가입 진행 중"),
        ACTIVATED("활성"),
        DEACTIVATED("비활성"),
        DELETED("탈퇴"),
    }


}