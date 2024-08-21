package ro.mdc.petproject.ui.list

import androidx.annotation.VisibleForTesting
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import ro.mdc.petproject.data.model.AnimalModel
import ro.mdc.petproject.data.model.PaginationModel
import ro.mdc.petproject.data.remote.PetFinderService
import ro.mdc.petproject.domain.AnimalRepository
import javax.inject.Inject

open class ListUiState {
    object Loading : ListUiState()
    object Error : ListUiState()
    data class Success(
        val animals: List<AnimalModel>,
        val pagination: PaginationModel?
    ) : ListUiState()
}

@HiltViewModel
class ListViewModel @Inject constructor(
    private val animalRepository: AnimalRepository,
) : ViewModel() {

    var uiState by mutableStateOf(ListUiState())
        private set

    @VisibleForTesting
    var compositeDisposable = CompositeDisposable()

    init {
        uiState = ListUiState.Loading
        loadAnimals()
    }

    fun loadNextPage() {
        if (uiState is ListUiState.Success) {
            (uiState as ListUiState.Success).pagination?.let { pagination ->
                (pagination.currentPage + 1).takeIf {
                    pagination.currentPage < pagination.totalPages
                }?.run {
                    loadAnimals(page = this)
                }
            }
        }
    }

    private fun loadAnimals(page: Int = 1) {
        val disposable = animalRepository.getAnimals(page = page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { response, error ->
                when {
                    response != null -> {
                        val animals = if (uiState is ListUiState.Success) {
                            (uiState as ListUiState.Success).animals + response.animals
                        } else {
                            response.animals
                        }
                        uiState = ListUiState.Success(
                            animals = animals,
                            pagination = response.pagination
                        )
                    }

                    error != null -> {
                        uiState = ListUiState.Error
                    }

                    else -> {
                        uiState = ListUiState.Loading
                    }
                }
            }

        if (compositeDisposable.isDisposed) {
            compositeDisposable = CompositeDisposable()
        }
        disposable.addTo(compositeDisposable)
    }

    fun cleanUp() {
        compositeDisposable.dispose()
    }
}