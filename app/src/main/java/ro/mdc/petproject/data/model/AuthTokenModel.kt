package ro.mdc.petproject.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AuthTokenModel(
    @Json(name = "token_type")
    val tokenType: String,
    @Json(name = "expires_in")
    val expiration: Long,
    @Json(name = "access_token")
    val accessToken: String,
)
