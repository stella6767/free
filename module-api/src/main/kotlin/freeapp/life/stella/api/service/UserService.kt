package freeapp.life.stella.api.service


import freeapp.life.stella.api.config.security.UserPrincipal
import freeapp.life.stella.storage.repository.UserRepository
import jakarta.persistence.EntityNotFoundException
import mu.KotlinLogging
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserService(
    private val userRepository: UserRepository,
) : UserDetailsService {

    private val log = KotlinLogging.logger {  }


    override fun loadUserByUsername(username: String): UserPrincipal? {

        log.debug("/login 이 호출되면 실행되어 username 이 DB에 있는지 확인한다.");

        val user =
            userRepository.findByUsername(username) ?: throw EntityNotFoundException()

        return UserPrincipal(user)
    }



}