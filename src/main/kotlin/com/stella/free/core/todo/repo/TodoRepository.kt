package com.stella.free.core.todo.repo

import com.stella.free.core.todo.entity.Todo

import org.springframework.data.jpa.repository.JpaRepository

interface TodoRepository : JpaRepository<Todo, Long>, TodoCustomRepository {

}