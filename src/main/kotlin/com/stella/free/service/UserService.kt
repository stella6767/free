package com.stella.free.service

import com.stella.free.config.security.UserPrincipal
import com.stella.free.repository.UserRepository
import com.stella.free.util.logger
import jakarta.persistence.EntityNotFoundException
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserService(
    private val userRepository: UserRepository,
) : UserDetailsService {

    private val log = logger()

    //todo spring session 도입
    //todo 댓글 라이브러리

    override fun loadUserByUsername(username: String): UserPrincipal? {

        log.debug("/login 이 호출되면 실행되어 username 이 DB에 있는지 확인한다.");

        val user =
            userRepository.findByUsername(username) ?: throw EntityNotFoundException()

        return UserPrincipal(user)
    }



}