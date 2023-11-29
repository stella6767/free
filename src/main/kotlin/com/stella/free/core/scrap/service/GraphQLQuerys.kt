package com.stella.free.core.scrap.service


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