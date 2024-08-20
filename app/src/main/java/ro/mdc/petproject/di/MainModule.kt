package ro.mdc.petproject.di

import dagger.Lazy
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import ro.mdc.petproject.data.Authenticator
import ro.mdc.petproject.data.remote.PetFinderService

@Module
@InstallIn(SingletonComponent::class)
class MainModule {
    companion object {
        const val API_URL = "https://api.petfinder.com/v2/"
    }

    @Provides
    fun provideRetrofit(petFinderService: Lazy<PetFinderService>): Retrofit {
        val client = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .authenticator(Authenticator(petFinderService))
            .build()

        return Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(client)
            .build()
    }

    @Provides
    fun providePetFinderService(retrofit: Retrofit): PetFinderService {
        return retrofit.create(PetFinderService::class.java)
    }

}