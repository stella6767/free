package com.stella.free.core.scrap.service

import com.stella.free.core.scrap.dto.VelogUserTagDto
import org.springframework.graphql.client.HttpGraphQlClient
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient


@Component
class StaticCrawler(
    private val velogClient:WebClient

) {

    private val graphQlClient =
        HttpGraphQlClient.builder(velogClient).build()

    fun getPost(url:String){

        graphQlClient
            .document("")
            //.variable()


    }



    fun getPosts(username:String): MutableList<Any>? {

        val execute = graphQlClient
            .document(userTagsQuery)
            .operationName("UserTags")
            .variable("username", username)
            .execute()

        val block = execute.block()

        println(block.toString())


        val userTags = graphQlClient
            .document(userTagsQuery)
            .operationName("UserTags")
            .variable("username", username)
            //.variable("cursor", 1)
            .retrieve("userTags")
            .toEntityList(Any::class.java)
            .block()

        println(userTags)

        return userTags
    }


    fun getImage(){


    }



}