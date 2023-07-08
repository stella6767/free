package com.stella.free.service

import com.stella.free.config.security.UserPrincipal
import com.stella.free.entity.Todo
import com.stella.free.repository.PostRepository
import com.stella.free.util.logger
import org.springframework.stereotype.Service

@Service
class PostService(
    private val postRepository: PostRepository,
) {

    private val log = logger()


    fun findPostsByPage(){


    }

    fun save(todo: String, principal: UserPrincipal) {




    }


    fun findById(id: Long) {

    }


    fun deleteById(id: Long) {

    }


}