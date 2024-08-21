package ro.mdc.petproject.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import coil.compose.AsyncImage
import ro.mdc.petproject.data.model.AnimalModel
import ro.mdc.petproject.ui.theme.PetProjectTheme
import ro.mdc.petproject.ui.utils.ErrorScreen
import ro.mdc.petproject.ui.utils.LoadingScreen
import ro.mdc.petproject.ui.utils.PetPreview
import timber.log.Timber
import kotlin.reflect.jvm.internal.impl.types.error.ErrorScope

@Composable
fun ListRoute(
    modifier: Modifier = Modifier,
    viewModel: ListViewModel = hiltViewModel(),
) {
    ListScreen(
        modifier = modifier,
        uiState = viewModel.uiState,
        onNextPageLoad = viewModel::loadNextPage
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
                    Text(
                        text = "Animals",
                        style = MaterialTheme.typography.titleLarge,
                    )
                }
                items(uiState.animals) {
                    AnimalCard(animalModel = it)
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
fun AnimalCard(animalModel: AnimalModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        animalModel.photos.firstOrNull()?.let {
            AsyncImage(
                modifier = Modifier.fillMaxWidth(),
                model = it.medium,
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
            )
        }
        Text(
            text = animalModel.name.orEmpty(),
            style = MaterialTheme.typography.titleLarge,
        )
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Type: ${animalModel.type}",
        )
    }
}

@Composable
@PetPreview
fun ListScreenPreview() {
    PetProjectTheme {
        ListScreen(
            modifier = Modifier,
            uiState = ListUiState(),
            onNextPageLoad = {}
        )
    }
}