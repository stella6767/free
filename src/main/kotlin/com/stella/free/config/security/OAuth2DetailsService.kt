package com.stella.free.config.security

import com.stella.free.entity.User
import com.stella.free.entity.type.SignType
import com.stella.free.exception.UnSupportedSocialException
import com.stella.free.repository.UserRepository
import com.stella.free.util.logger
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service
import java.util.*


@Service
class OAuth2DetailsService(
    private val userRepository: UserRepository,
    private val bCryptPasswordEncoder: BCryptPasswordEncoder,
    private val mapper: ObjectMapper,
) : DefaultOAuth2UserService() {

    private val log = logger()

    override fun loadUser(userRequest: OAuth2UserRequest): OAuth2User {

        log.info("OAuth 로그인 진행중................ ${userRequest.accessToken.tokenValue}")

        val oAuth2User =
            super.loadUser(userRequest)

        log.info("""
            ${oAuth2User.attributes}
        """.trimIndent())

        return processOAuth2User(userRequest, oAuth2User)
    }


    private fun processOAuth2User(userRequest: OAuth2UserRequest, oAuth2User: OAuth2User): OAuth2User {

        val oAuth2UserInfo =
            getOAuth2UserInfo(userRequest.clientRegistration.clientName, oAuth2User)

        var userEntity =
            userRepository.findByUsername(oAuth2UserInfo.getUsername())

        val uuid =
            UUID.randomUUID()

        val encPassword =
            bCryptPasswordEncoder.encode(uuid.toString())

        if (userEntity == null) {

            log.info("최초 사용자입니다. 자동 회원가입을 진행합니다.")

            val newUser =
                User.createInstanceByOauth(oAuth2UserInfo, encPassword, mapper.writeValueAsString(oAuth2UserInfo.attributes))

            return UserPrincipal(userRepository.save(newUser))

        } else {
            //이미 회원가입이 완료됐다는 뜻(원래는 구글 정보가 변경될 수 있기 떄문에 update 해야되는데 지금은 안하겠음)
            log.info("회원정보가 있습니다. 바로 로그인합니다.")
            return UserPrincipal(userEntity)
        }

    }

    private fun getOAuth2UserInfo(
        clientName: String,
        oAuth2User: OAuth2User
    ): OAuth2UserInfo {

        log.info("머로 로그인 됐지? $clientName")

        return when (clientName) {
            SignType.GOOGLE.clientName -> {
                GoogleAuth2UserInfo(oAuth2User.attributes)
            }
            SignType.FACEBOOK.clientName -> {
                FaceBookAuth2UserInfo(oAuth2User.attributes)
            }
            SignType.GITHUB.clientName -> {
                GithubAuth2UserInfo(oAuth2User.attributes)
            }
            else -> {
                throw UnSupportedSocialException("Unknown client name: $clientName")
            }
        }

    }


}