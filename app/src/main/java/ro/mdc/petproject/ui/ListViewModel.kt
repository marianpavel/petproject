package ro.mdc.petproject.ui

import android.view.View
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.launch
import ro.mdc.petproject.data.model.AnimalModel
import ro.mdc.petproject.data.remote.PetFinderService
import timber.log.Timber
import javax.inject.Inject

data class ListUiState(
    val animals: List<AnimalModel> = emptyList(),
)

@HiltViewModel
class ListViewModel @Inject constructor(
    petFinderService: PetFinderService
) : ViewModel() {

    var uiState by mutableStateOf(ListUiState())
        private set

    private val compositeDisposable = CompositeDisposable()

    init {
        val disposable = petFinderService.getAnimals(page = 1)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { response, _ ->
                if (response != null) {
                    uiState = uiState.copy(animals = response.animals)
                }
            }

        disposable.addTo(compositeDisposable)
    }

    fun cleanUp() {
        compositeDisposable.dispose()
    }
}