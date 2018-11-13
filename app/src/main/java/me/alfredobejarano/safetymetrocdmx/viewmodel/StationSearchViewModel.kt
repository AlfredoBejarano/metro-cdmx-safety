package me.alfredobejarano.safetymetrocdmx.viewmodel

import androidx.lifecycle.*
import me.alfredobejarano.safetymetrocdmx.data.Station
import me.alfredobejarano.safetymetrocdmx.data.StationRepository
import me.alfredobejarano.safetymetrocdmx.utilities.runOnIOThread
import javax.inject.Inject

/**
 *
 * ViewModel class that handles the search of a station.
 *
 * @author Alfredo Bejarano
 * @since November 12, 2018 - 00:07
 * @version 1.0
 **/
class StationSearchViewModel
@Inject constructor(private val repo: StationRepository) : ViewModel() {
    /**
     * Defines the origin station chose by the user.
     */
    private val originStation = MediatorLiveData<Station>()
    /**
     * Defines the destination station chose by the user.
     */
    private val destinationStation = MediatorLiveData<Station>()
    /**
     * Defines the results of a station search.
     */
    private var searchResults: LiveData<List<Station>> = MediatorLiveData()

    /**
     * Defines the value for the origin station.
     */
    fun setOriginStation(station: Station) {
        originStation.value = station
    }

    /**
     * Defines the value for the origin station.
     */
    fun setDestinationStation(station: Station) {
        destinationStation.value = station
    }

    /**
     * Provides observation to the UI for the chosen origin station
     * without the observer being able to modify its value.
     */
    fun getOriginStation(): LiveData<Station> = originStation

    /**
     * Provides observation to the UI for the chosen destination station
     * without the observer being able to modify its value.
     */
    fun getDestinationStation(): LiveData<Station> = destinationStation

    /**
     * Performs a query to the database to retrieve all
     * the stations that contains the given name.
     * @param name The query to search the station by name.
     */
    fun searchStation(name: String) {
        searchResults = Transformations.map(repo.searchStations(query = name)) { it }
    }

    /**
     * Provides immutable observation to the UI for a search results.
     */
    fun getSearchResults() = searchResults

    /**
     * Factory class that tells a lifecycle owner how to create
     * an instance of the [StationSearchViewModel] class.
     */
    class Factory
    @Inject constructor(private val repo: StationRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T =
            StationSearchViewModel(repo) as T
    }
}