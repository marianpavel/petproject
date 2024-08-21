package ro.mdc.petproject

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.every
import io.mockk.mockk
import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.schedulers.TestScheduler
import junit.framework.TestCase.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import ro.mdc.petproject.data.model.AnimalModel
import ro.mdc.petproject.data.model.AnimalsListModel
import ro.mdc.petproject.data.model.BreedsModel
import ro.mdc.petproject.data.model.PaginationModel
import ro.mdc.petproject.data.model.PhotoModel
import ro.mdc.petproject.domain.AnimalRepository
import ro.mdc.petproject.ui.list.ListUiState
import ro.mdc.petproject.ui.list.ListViewModel

class ListViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: ListViewModel
    private lateinit var testScheduler: TestScheduler

    private val animalRepository = mockk<AnimalRepository>()

    @Before
    fun setUp() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setMainThreadSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setInitIoSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setNewThreadSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setSingleSchedulerHandler { Schedulers.trampoline() }
        testScheduler = TestScheduler()
    }

    @After
    fun tearDown() {
        RxAndroidPlugins.reset()
        RxJavaPlugins.reset()
    }

    @Test
    fun `when loadAnimals is called, uiState should be updated to Success`() {
        every { animalRepository.getAnimals(1) } returns Single.just(
            AnimalsListModel(
                animals = emptyList(),
                pagination = PaginationModel(
                    countPerPage = 10,
                    currentPage = 0,
                    totalCount = 0,
                    totalPages = 0,
                )
            )
        )

        viewModel = ListViewModel(animalRepository)
        val uiState = viewModel.uiState
        assert(uiState is ListUiState.Success)
    }

    @Test
    fun `when loadNextPage is called and pagination exists, it should load the next page`() {
        every { animalRepository.getAnimals(any()) } returns Single.just(
            AnimalsListModel(
                animals = listOf(testAnimal),
                pagination = PaginationModel(
                    countPerPage = 1,
                    currentPage = 1,
                    totalCount = 2,
                    totalPages = 2,
                )
            )
        )

        viewModel = ListViewModel(animalRepository)
        val uiState = viewModel.uiState
        assert(uiState is ListUiState.Success)

        viewModel.loadNextPage()
        assert(uiState is ListUiState.Success)
        assertEquals(2, (uiState as ListUiState.Success).animals.size)
    }

    @Test
    fun `when cleanUp is called, compositeDisposable should be disposed`() {
        every { animalRepository.getAnimals(any()) } returns Single.just(
            AnimalsListModel(
                animals = listOf(testAnimal),
                pagination = PaginationModel(
                    countPerPage = 1,
                    currentPage = 1,
                    totalCount = 2,
                    totalPages = 2,
                )
            )
        )

        viewModel = ListViewModel(animalRepository)
        viewModel.cleanUp()
        assert(viewModel.compositeDisposable.isDisposed)
    }

    private val testAnimal = AnimalModel(
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
    )
}