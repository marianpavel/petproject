package ro.mdc.petproject.ui

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
import ro.mdc.petproject.data.model.AnimalsListModel
import ro.mdc.petproject.data.model.PaginationModel
import ro.mdc.petproject.data.remote.PetFinderService
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
    private val petFinderService: PetFinderService,
) : ViewModel() {

    var uiState by mutableStateOf(ListUiState())
        private set

    private val compositeDisposable = CompositeDisposable()

    init {
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
        val disposable = petFinderService.getAnimals(page = page)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { uiState = ListUiState.Loading }
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

        disposable.addTo(compositeDisposable)
    }

    fun cleanUp() {
        compositeDisposable.dispose()
    }
}