package freeapp.life.stella.api.web.dto

//data class PublicApiCategoriesDto(
//    val count: Int,
//    val entries: List<CategoryEntry>,
//)

//data class CategoryEntry(
//    val name: String,
//    val slug: String,
//)


data class Entry(
    val API: String = "",
    val Auth: String = "",
    val Category: String = "",
    val Cors: String = "",
    val Description: String = "",
    val HTTPS: Boolean = false,
    val Link: String = ""
)


data class EntryListDto(
    val count: Int,
    val filedNames: List<String> = listOf(),
    val entries: List<Entry> = listOf()
)
