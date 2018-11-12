package me.alfredobejarano.safetymetrocdmx.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import me.alfredobejarano.safetymetrocdmx.data.Station
import me.alfredobejarano.safetymetrocdmx.data.StationRepository

/**
 *
 * ViewModel class that handles the search of a station.
 *
 * @author Alfredo Bejarano
 * @since November 12, 2018 - 00:07
 * @version 1.0
 **/
class StationSearchViewModel(private val repo: StationRepository) : ViewModel() {
    /**
     * Defines the origin station chose by the user.
     */
    private val originStation = MutableLiveData<Station>()
    /**
     * Defines the destination station chose by the user.
     */
    private val destinationStation = MutableLiveData<Station>()

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
    fun searchStation(name: String) = repo.searchStations(query = name)

    /**
     * Factory class that tells a lifecycle owner how to create
     * an instance of the [StationSearchViewModel] class.
     */
    class Factory(private val repo: StationRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T =
            StationSearchViewModel(repo) as T
    }
}