package com.stella.free.core.account.repo

import com.stella.free.core.account.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long>, UserCustomRepository {

}