package clean.architecture.omdb.ui.search.bar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import clean.architecture.domain.search.usecase.SaveSearchUseCase
import clean.architecture.omdb.ui.search.bar.mapper.toUiState
import clean.architecture.omdb.ui.search.bar.model.SearchBarUiState
import clean.architecture.omdb.ui.search.bar.view.SearchBar
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for the [SearchBar] component.
 */
@HiltViewModel
class SearchBarViewModel @Inject constructor(
    private val saveSearchUseCase: SaveSearchUseCase,
    private val dispatcherIO: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private var job: Job? = null
    private val _uiState = MutableStateFlow<SearchBarUiState>(SearchBarUiState.Default)

    /**
     * Represents the state of the search bar.
     */
    val uiState: StateFlow<SearchBarUiState> = _uiState.asStateFlow()

    /**
     * Saves the current search to the local search history.
     *
     * @param query The search query.
     */
    fun saveSearch(query: String) {
        if (isSaveSearchInProgress()) return
        job = viewModelScope.launch(dispatcherIO) {
            _uiState.value = saveSearchUseCase(query).toUiState()
        }
    }

    /**
     * Checks if a save search operation is in progress.
     */
    fun isSaveSearchInProgress(): Boolean = job?.isActive == true
}
