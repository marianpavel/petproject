package ro.mdc.petproject.domain

import ro.mdc.petproject.data.remote.PetFinderService
import javax.inject.Inject

class AnimalRepository @Inject constructor(
    private val petFinderService: PetFinderService,
) {

    fun getAnimals(page: Int) = petFinderService.getAnimals(page = page)
}