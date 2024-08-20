package ro.mdc.petproject.data

import dagger.Lazy
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import ro.mdc.petproject.data.local.LocalRepository
import ro.mdc.petproject.data.remote.PetFinderService

class Authenticator(
    private val petFinderService: Lazy<PetFinderService>,
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        val token = petFinderService.get().getAccessToken().execute()
        LocalRepository.token = "${token.body()?.tokenType} ${token.body()?.accessToken}"

        return response.request.newBuilder()
            .header("Authorization", LocalRepository.token)
            .build()
    }
}