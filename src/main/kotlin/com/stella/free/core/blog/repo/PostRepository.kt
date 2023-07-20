package com.stella.free.core.blog.repo

import com.stella.free.core.blog.entity.Post
import org.springframework.data.jpa.repository.JpaRepository

interface PostRepository : JpaRepository<Post, Long>, PostCustomRepository {

}