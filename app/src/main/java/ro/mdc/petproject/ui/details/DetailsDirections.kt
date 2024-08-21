package ro.mdc.petproject.ui.details

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.squareup.moshi.Moshi
import ro.mdc.petproject.data.model.AnimalModel
import ro.mdc.petproject.ui.details.DetailsDirections.animalArg
import ro.mdc.petproject.ui.details.DetailsDirections.animalParam
import java.net.URLDecoder
import java.net.URLEncoder

object DetailsDirections {
    const val root = "details"
    const val details = "details/{animal}"
    const val animalParam = "animal"
    const val animalArg = "{$animalParam}"
}

/**
 * This can be replaced with other logic
 * Just for demonstration
 * Ex: Since Navigation 2.8.0, we can pass complex data
 * using typesafe navigation and kotlinx serialization
 *
 * Other solution involve passing only the animal id and fetching it again
 * But that is just wasted resources
 */
fun getDetailsRoute(animal: AnimalModel): String {
    val moshi = Moshi.Builder().build()
    val adapter = moshi.adapter(AnimalModel::class.java).lenient()

    return DetailsDirections.details.replace(animalArg,
        URLEncoder.encode(adapter.toJson(animal), "utf-8")
    )
}

fun NavGraphBuilder.addDetailsDirections(
    onBackClick: () -> Unit,
) {

    navigation(
        route = DetailsDirections.root,
        startDestination = DetailsDirections.details
    ) {
        composable(
            route = DetailsDirections.details,
            arguments = listOf(navArgument(animalParam) {
                type = NavType.StringType }
            )
        ) { backStackEntry ->
            val json = backStackEntry.arguments?.getString(animalParam)
            val moshi = Moshi.Builder().build()
            val adapter = moshi.adapter(AnimalModel::class.java).lenient()

            DetailsRoute(
                modifier = Modifier,
                animalModel = json?.let { adapter.fromJson(
                    URLDecoder.decode(it, "utf-8")
                )},
                onBackClick = onBackClick,
            )
        }
    }
}