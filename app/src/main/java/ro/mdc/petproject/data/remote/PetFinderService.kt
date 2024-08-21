package ro.mdc.petproject.data.remote

import io.reactivex.rxjava3.core.Single
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query
import ro.mdc.petproject.data.local.LocalRepository
import ro.mdc.petproject.data.model.AnimalsListModel
import ro.mdc.petproject.data.model.AuthTokenModel

interface PetFinderService {

    @POST("oauth2/token")
    @FormUrlEncoded
    fun getAccessToken(
        @Field("grant_type") grantType: String = "client_credentials",
        @Field("client_id") clientId: String = "8FvB92COL3loJkRHBozGPLOVKZTG4CgXal6Dou6EjsH5lj2SXB",
        @Field("client_secret") clientSecret: String = "zcYSA3CrhG6yW1dc539o8rAVgj7ecwLUaYHTSe3s"
    ): Call<AuthTokenModel>

    @GET("animals")
    fun getAnimals(
        @Header("Authorization") authorization: String = LocalRepository.token,
        @Query("page") page: Int,
        @Query("limit") limit: Int = 30
    ): Single<AnimalsListModel>
}