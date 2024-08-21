package ro.mdc.petproject.ui.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import ro.mdc.petproject.data.model.AnimalModel
import ro.mdc.petproject.data.model.BreedsModel
import ro.mdc.petproject.data.model.PhotoModel
import ro.mdc.petproject.ui.theme.PetProjectTheme
import ro.mdc.petproject.ui.utils.ErrorScreen
import ro.mdc.petproject.ui.utils.PetPreview
import ro.mdc.petproject.ui.utils.TopAppBar

@Composable
fun DetailsRoute(
    modifier: Modifier = Modifier,
    animalModel: AnimalModel?,
    onBackClick: () -> Unit,
) {

    Column {
        TopAppBar(
            modifier = Modifier,
            title = "Details",
            onBackClick = onBackClick,
        )

        if (animalModel != null) {
            DetailsScreen(
                modifier = modifier,
                animalModel = animalModel,
            )
        } else {
            ErrorScreen(
                modifier = modifier
                    .fillMaxSize(),
            )
        }
    }
}

@Composable
fun DetailsScreen(
    modifier: Modifier,
    animalModel: AnimalModel,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(state = rememberScrollState()),
    ) {
        animalModel.photos.firstOrNull()?.let {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth(),
                model = it.medium,
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
            )
        }

        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = "Name: ${animalModel.name.orEmpty()}",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground,
        )

        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = "Breed: ${animalModel.breeds.primary.orEmpty()}",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onBackground,
        )

        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = "Size: ${animalModel.size ?: "Unknown"}",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onBackground,
        )

        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = "Gender: ${animalModel.gender ?: "Unknown"}",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onBackground,
        )

        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = "Status: ${animalModel.status ?: "Unknown"}",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onBackground,
        )
    }
}

@Composable
@PetPreview
fun DetailsScreenPreview() {
    PetProjectTheme {
        DetailsScreen(
            modifier = Modifier,
            animalModel = AnimalModel(
                id = 123,
                organizationId = "456",
                type = "Dog",
                url = "https://example.com",
                name = "Max",
                description = "A playful dog",
                tags = listOf("friendly", "playful"),
                coat = "Soft",
                size = "Medium",
                gender = "Male",
                age = "Young",
                publishedAt = "2022-01-01T00:00:00Z",
                breeds = BreedsModel(
                    primary = "Labrador Retriever",
                    secondary = null,
                    mixed = false,
                    unknown = false,
                ),
                photos = listOf(
                    PhotoModel(
                        small = "https://example.com/small.jpg",
                        medium = "https://example.com/medium.jpg",
                        large = "https://example.com/large.jpg",
                    )
                ),
                species = "Dog",
                status = "adobtable",
            ),
        )
    }
}

@Composable
@PetPreview
fun DetailsRouteErrorPreview() {
    PetProjectTheme {
        DetailsRoute(
            modifier = Modifier,
            animalModel = null,
            onBackClick = {},
        )
    }
}