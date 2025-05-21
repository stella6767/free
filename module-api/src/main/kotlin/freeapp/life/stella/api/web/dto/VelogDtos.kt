package freeapp.life.stella.api.web.dto

import jakarta.validation.constraints.NotEmpty
import java.util.*

data class VelogTagDto(
    val description: Any?,
    val id: String,
    val name: String,
    val posts_count: Int,
    val thumbnail: Any?
)

data class VelogUserTagDto(
    val tags: List<VelogTagDto>,
    val posts_count: Int,
    val __typename: String,
)


data class VelogPostDto(
    val __typename: String?,
    val comments_count: Int?,
    val id: String?,
    val is_private: Any?,
    val likes: Int?,
    val released_at: String?,
    val short_description: String?,
    val tags: List<String?> = mutableListOf(),
    val thumbnail: String?,
    val title: String?,
    val updated_at: String?,
    val url_slug: String?,
    val user: VelogUser?
)

data class VelogUser(
    val __typename: String?,
    val id: String?,
    val profile: VelogProfile?,
    val username: String?
)


data class VelogProfile(
    val __typename: String?,
    val id: String?,
    val thumbnail: String?
)


data class VelogReadPostDto(
    val __typename: String?,
    val body: String,
    val comments: List<Any>?,
    val comments_count: Int?,
    val id: String?,
    val is_markdown: Boolean?,
    val is_private: Boolean?,
    val is_temp: Boolean?,
    val liked: Boolean?,
    val likes: Int?,
    val linked_posts: LinkedPosts?,
    val released_at: String?,
    val series: Series?,
    val short_description: String?,
    val tags: List<String>?,
    val thumbnail: Any?,
    val title: String?,
    val updated_at: String?,
    val url_slug: String?,
    val user: UserXXX
){

    fun createFileName(): String {
        return this.title + "--" + UUID.randomUUID().toString().substring(0, 5) + ".md"
    }

    fun createInfoBody(): String {

        val newBody = """
            
        title: ${this.title}
        description: ${this.short_description}
        date: ${this.released_at}
        tags: ${this.tags.toString()}           
            
            
            ${this.body}
        """.trimIndent()

        return newBody

    }


}


data class LinkedPosts(
    val __typename: String?,
    val next: Next?,
    val previous: Previous?
)

data class Next(
    val __typename: String?,
    val id: String?,
    val title: String?,
    val url_slug: String?,
    val user: User
)


data class Post(
    val __typename: String?,
    val id: String?,
    val title: String?,
    val url_slug: String?,
    val user: User
)


data class Previous(
    val __typename: String?,
    val id: String?,
    val title: String?,
    val url_slug: String?,
    val user: User?
)


data class Profile(
    val __typename: String?,
    val display_name: String?,
    val id: String?,
    val profile_links: ProfileLinks?,
    val short_bio: String?,
    val thumbnail: String?
)


data class ProfileLinks(
    val github: String?,
    val url: String?
)


data class Series(
    val __typename: String?,
    val id: String?,
    val name: String?,
    val series_posts: List<SeriesPost> = mutableListOf(),
    val url_slug: String?
)


data class SeriesPost(
    val __typename: String?,
    val id: String?,
    val post: Post?
)


data class User(
    val __typename: String?,
    val id: String?,
    val username: String?
)


data class UserXXX(
    val __typename: String?,
    val id: String?,
    val profile: Profile?,
    val username: String?,
    val velog_config: VelogConfig?
)

data class VelogConfig(
    val __typename: String?,
    val title: Any?
)


data class VelogCrawlerReqDto(

    @NotEmpty
    val username:String,
    //val type: AsyncType,

//        @field:Max(1000000)
//        val size: Int
)


val `velogPostsQuery` =

    """
    query Posts(${'$'}cursor: ID, ${'$'}username: String, ${'$'}temp_only: Boolean, ${'$'}tag: String, ${'$'}limit: Int) {
        posts(cursor: ${'$'}cursor, username: ${'$'}username, temp_only: ${'$'}temp_only, tag: ${'$'}tag, limit: ${'$'}limit) {
            id
            title
            short_description
            thumbnail
            user {
                id
                username
                profile {
                    id
                    thumbnail
                    __typename
                }
                __typename
            }
            url_slug
            released_at
            updated_at
            comments_count
            tags
            is_private
            likes
            __typename
        }
    }       
    """.trimIndent()



val velogPostQuery = """
    query ReadPost(${'$'}username: String, ${'$'}url_slug: String) {
    post(username: ${'$'}username, url_slug: ${'$'}url_slug) {
      id
      title
      released_at
      updated_at
      tags
      body
      short_description
      is_markdown
      is_private
      is_temp
      thumbnail
      comments_count
      url_slug
      likes
      liked
      user {
        id
        username
        profile {
          id
          display_name
          thumbnail
          short_bio
          profile_links
          __typename
        }
        velog_config {
          title
          __typename
        }
        __typename
      }
      comments {
        id
        user {
          id
          username
          profile {
            id
            thumbnail
            __typename
          }
          __typename
        }
        text
        replies_count
        level
        created_at
        level
        deleted
        __typename
      }
      series {
        id
        name
        url_slug
        series_posts {
          id
          post {
            id
            title
            url_slug
            user {
              id
              username
              __typename
            }
            __typename
          }
          __typename
        }
        __typename
      }
      linked_posts {
        previous {
          id
          title
          url_slug
          user {
            id
            username
            __typename
          }
          __typename
        }
        next {
          id
          title
          url_slug
          user {
            id
            username
            __typename
          }
          __typename
        }
        __typename
      }
      __typename
    }
  }       
""".trimIndent()


val userTagsQuery = """
    query UserTags(${'$'}username: String) {
        userTags(username: ${'$'}username) {
            tags {       
                id      
                name
                description
                posts_count      
                thumbnail                        
            }    
            posts_count
            __typename  
    }                      
   }
      
""".trimIndent()
