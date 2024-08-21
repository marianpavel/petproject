package ro.mdc.petproject.ui.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import ro.mdc.petproject.data.model.AnimalModel
import ro.mdc.petproject.data.model.BreedsModel
import ro.mdc.petproject.data.model.PhotoModel
import ro.mdc.petproject.ui.theme.PetProjectTheme
import ro.mdc.petproject.ui.utils.PetPreview

@Composable
fun AnimalCard(
    animalModel: AnimalModel,
    onCardClick: (AnimalModel) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 8.dp)
            .background(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = RoundedCornerShape(8.dp)
            )
            .clickable {
                onCardClick(animalModel)
            }
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentAlignment = Alignment.Center,
        ) {
            Text(text = "Placeholder image")

            animalModel.photos.firstOrNull()?.let {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)),
                    model = it.medium,
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth,
                )
            }
        }

        Text(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .padding(vertical = 4.dp),
            text = animalModel.name.orEmpty(),
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.titleLarge,
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .padding(bottom = 8.dp),
            color = MaterialTheme.colorScheme.secondary,
            text = "Type: ${animalModel.type}",
        )
    }
}

@Composable
@PetPreview
fun AnimalCardPreview() {
    PetProjectTheme {
        AnimalCard(
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
                status = "adoptable",
            ),
            onCardClick = {},
        )
    }
}