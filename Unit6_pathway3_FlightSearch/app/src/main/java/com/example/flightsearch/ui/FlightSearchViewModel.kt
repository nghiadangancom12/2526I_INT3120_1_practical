package com.example.flightsearch.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flightsearch.data.FlightRepository
import com.example.flightsearch.data.Airport
import com.example.flightsearch.data.Favorite
import com.example.flightsearch.datastore.UserPreferences
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class UiState(
    val search: String = "",
    val suggestions: List<Airport> = emptyList(),
    val selectedDeparture: Airport? = null,
    val destinations: List<Airport> = emptyList(),
    val favorites: List<Favorite> = emptyList()
)

class FlightSearchViewModel(
    private val repo: FlightRepository,
    private val prefs: UserPreferences
) : ViewModel() {

    private val searchQuery = MutableStateFlow("")
    private val selectedDepartureCode = MutableStateFlow<String?>(null)

    val uiState: StateFlow<UiState> =
        combine(
            searchQuery,
            selectedDepartureCode.flatMapLatest { code ->
                code?.let { repo.destinationsForDeparture(it) } ?: flowOf(emptyList())
            },
            searchQuery.flatMapLatest { q -> repo.autocomplete(q) },
            repo.favorites(),
            selectedDepartureCode.flatMapLatest { code ->
                code?.let { repo.getAirportByCode(it) } ?: flowOf(null)
            },
            ::combineToUiState
        )
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), UiState())

    init {
        // Khởi tạo search từ DataStore
        viewModelScope.launch {
            prefs.searchQuery.collect { q -> setSearch(q) }
        }
    }

    private fun combineToUiState(
        search: String,
        destinations: List<Airport>,
        suggestions: List<Airport>,
        favorites: List<Favorite>,
        selectedDeparture: Airport?
    ) = UiState(
        search = search,
        destinations = destinations,
        suggestions = if (search.isBlank()) emptyList() else suggestions,
        favorites = favorites,
        selectedDeparture = selectedDeparture
    )

    fun setSearch(q: String) {
        viewModelScope.launch { prefs.setSearchQuery(q) }
        searchQuery.value = q
        if (q.isBlank()) {
            // Xoá lựa chọn sân bay khi trống
            selectedDepartureCode.value = null
        }
    }

    fun selectDeparture(airport: Airport) {
        selectedDepartureCode.value = airport.iata_code
        // Giữ nguyên nội dung text để hiển thị kết quả,
        // hoặc tuỳ bạn: setSearch(airport.iata_code)
    }

    fun toggleFavorite(destination: Airport) {
        val dep = selectedDepartureCode.value ?: return
        viewModelScope.launch {
            repo.addOrRemoveFavorite(dep, destination.iata_code)
        }
    }
}
