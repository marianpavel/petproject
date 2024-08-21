package ro.mdc.petproject.ui.list

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import ro.mdc.petproject.data.model.AnimalModel
import ro.mdc.petproject.ui.theme.PetProjectTheme
import ro.mdc.petproject.ui.utils.ErrorScreen
import ro.mdc.petproject.ui.utils.LoadingScreen
import ro.mdc.petproject.ui.utils.PetPreview
import ro.mdc.petproject.ui.utils.TopAppBar

@Composable
fun ListRoute(
    modifier: Modifier = Modifier,
    onCardClick: (AnimalModel) -> Unit,
    viewModel: ListViewModel = hiltViewModel(),
) {
    ListScreen(
        modifier = modifier,
        uiState = viewModel.uiState,
        onNextPageLoad = viewModel::loadNextPage,
        onCardClick = onCardClick,
    )

    LifecycleEventEffect(Lifecycle.Event.ON_STOP) {
        viewModel.cleanUp()
    }
}

@Composable
fun ListScreen(
    modifier: Modifier,
    uiState: ListUiState,
    onNextPageLoad: () -> Unit,
    onCardClick: (AnimalModel) -> Unit,
) {
    val state = rememberLazyListState()

    when (uiState) {
        is ListUiState.Loading -> {
            LoadingScreen()
        }
        is ListUiState.Success -> {
            LazyColumn(
                modifier = modifier.fillMaxSize(),
                state = state,
            ) {
                item {
                    TopAppBar(title = "Home")
                }
                items(uiState.animals) {
                    AnimalCard(
                        animalModel = it,
                        onCardClick = onCardClick,
                    )
                }
            }
        }

        is ListUiState.Error -> {
            ErrorScreen()
        }
    }

    LaunchedEffect(state.canScrollForward) {
        if (!state.canScrollForward) {
            onNextPageLoad()
        }
    }
}

@Composable
@PetPreview
fun ListScreenPreview() {
    PetProjectTheme {
        ListScreen(
            modifier = Modifier,
            uiState = ListUiState(),
            onNextPageLoad = {},
            onCardClick = {},
        )
    }
}