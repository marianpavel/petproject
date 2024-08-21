package ro.mdc.petproject.ui.utils

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import ro.mdc.petproject.ui.details.addDetailsDirections
import ro.mdc.petproject.ui.details.getDetailsRoute
import ro.mdc.petproject.ui.list.addListDirections

internal fun NavGraphBuilder.createNavGraph(
    navController: NavController,
) {
    addListDirections(
        onCardClick = {
            navController.navigate(getDetailsRoute(it))
        }
    )

    addDetailsDirections(
        onBackClick = {
            navController.navigateUp()
        }
    )
}