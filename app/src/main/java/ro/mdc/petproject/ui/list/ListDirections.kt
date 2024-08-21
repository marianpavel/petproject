package ro.mdc.petproject.ui.list

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import ro.mdc.petproject.data.model.AnimalModel

object ListDirections {
    const val root = "root"
    const val home = "$root/home"
}

fun NavGraphBuilder.addListDirections(
    onCardClick: (animal: AnimalModel) -> Unit,
) {

    navigation(
        route = ListDirections.root,
        startDestination = ListDirections.home
    ) {
        composable(
            route = ListDirections.home,
        ) {
            ListRoute(
                modifier = Modifier,
                onCardClick = onCardClick,
            )
        }
    }
}