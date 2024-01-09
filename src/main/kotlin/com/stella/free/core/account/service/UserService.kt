package com.stella.free.core.account.service

import com.stella.free.global.config.security.UserPrincipal
import com.stella.free.core.account.repo.UserRepository
import com.stella.free.global.util.logger
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

    //todo session clustering 도입

    override fun loadUserByUsername(username: String): UserPrincipal? {

        log.debug("/login 이 호출되면 실행되어 username 이 DB에 있는지 확인한다.");

        val user =
            userRepository.findByUsername(username) ?: throw EntityNotFoundException()

        return UserPrincipal(user)
    }



}