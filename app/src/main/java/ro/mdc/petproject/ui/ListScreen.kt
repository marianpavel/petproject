package ro.mdc.petproject.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import coil.compose.AsyncImage
import ro.mdc.petproject.data.model.AnimalModel
import ro.mdc.petproject.ui.theme.PetProjectTheme

@Composable
fun ListRoute(
    modifier: Modifier = Modifier,
    viewModel: ListViewModel = hiltViewModel(),
) {
    ListScreen(
        modifier = modifier,
        uiState = viewModel.uiState,
    )

    LifecycleEventEffect(Lifecycle.Event.ON_STOP) {
        viewModel.cleanUp()
    }
}

@Composable
fun ListScreen(
    modifier: Modifier,
    uiState: ListUiState,
) {
    LazyRow(
        modifier = modifier,
    ) {
        items(uiState.animals) {
            AnimalCard(animalModel = it)
        }
    }
}

@Composable
fun AnimalCard(animalModel: AnimalModel) {
    Column {
        AsyncImage(
            model = animalModel.photos.first(),
            contentDescription = null,
        )
        Text(
            text = animalModel.name,
            style = MaterialTheme.typography.titleLarge,
        )
    }
}

@Preview
@Composable
fun ListScreenPreview() {
    PetProjectTheme {
        ListScreen(
            modifier = Modifier,
            uiState = ListUiState()
        )
    }
}