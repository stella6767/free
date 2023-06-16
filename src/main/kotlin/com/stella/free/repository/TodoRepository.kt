package com.stella.free.repository

import com.stella.free.entity.Todo
import org.springframework.data.jpa.repository.JpaRepository

interface TodoRepository : JpaRepository<Todo, Long>, TodoCustomRepository {

}