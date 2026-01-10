package freeapp.life.stella.storage.entity

import jakarta.persistence.*
import org.hibernate.annotations.Comment
import java.time.LocalDateTime

@Entity
@Table(name = "user_verify")
class UserVerify(
    user: User,
    code: String,
    verifyToken: String,
    expiredAt: LocalDateTime? = null,
    verifiedAt: LocalDateTime? = null,
) : BaseEntity() {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user = user

    @Column(name = "code")
    @Comment("이메일 발송용 인증용 code")
    var code = code

    @Column(name = "verify_token")
    @Comment("프론트용 임시 인증용 token")
    var verifyToken = verifyToken

    @Column(name = "expired_at")
    @Comment("인증 유효만료기간")
    var expiredAt: LocalDateTime? = expiredAt

    @Column(name = "verified_at")
    @Comment("인증 완료시간")
    var verifiedAt: LocalDateTime? = verifiedAt


    fun verify(verifiedAt: LocalDateTime = LocalDateTime.now()) {
        this.verifiedAt = verifiedAt
    }

}
