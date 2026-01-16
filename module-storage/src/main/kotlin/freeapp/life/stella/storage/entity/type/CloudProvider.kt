package freeapp.life.stella.storage.entity.type

enum class CloudProvider(
    val storage: String
) {
    AWS("S3"),
    CloudFlare("R2")
}
