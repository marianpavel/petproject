package ro.mdc.petproject.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AnimalsListModel(
    val animals: List<AnimalModel>,
    val pagination: PaginationModel,
)

@JsonClass(generateAdapter = true)
data class AnimalModel(
    val id: Int,
    @Json(name = "organization_id")
    val organizationId: String,
    val url: String?,
    val type: String?,
    val species: String?,
    val photos: List<PhotoModel>,
    val breeds: BreedsModel,
    val age: String?,
    val gender: String?,
    val size: String?,
    val coat: String?,
    val tags: List<String> = emptyList(),
    val name: String?,
    val description: String?,
    @Json(name = "published_at")
    val publishedAt: String?,
)

@JsonClass(generateAdapter = true)
data class BreedsModel(
    val primary: String?,
    val secondary: String?,
    val mixed: Boolean,
    val unknown: Boolean,
)

@JsonClass(generateAdapter = true)
data class PhotoModel(
    val small: String,
    val medium: String,
    val large: String,
)
