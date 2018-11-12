package me.alfredobejarano.safetymetrocdmx.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import me.alfredobejarano.safetymetrocdmx.utilities.runOnIOThread
import javax.inject.Inject

/**
 *
 * Repository class that provides access to Station data to ViewModel classes.
 *
 * @author Alfredo Bejarano
 * @since November 12, 2018 - 00:10
 * @version 1.0
 **/
class StationRepository
@Inject constructor(private val dao: StationDao) {
    companion object {
        private const val QUERY_LENGTH_THRESHOLD = 4
    }

    /**
     * Fetches all the stations of a given line
     * sorted by their order in said line.
     *
     * **Note:** This function is not **THREAD SAFE**, it must be executed in a worker thread.
     * @see runOnIOThread
     *
     * @param line The line to fetch the stations from.
     */
    fun getLine(line: String) = dao.findByLine(line)

    /**
     * Saves a list of stations in the database.
     * **Note:** This function **is not THREAD SAFE**.
     * @param stations The crimes to be saved
     */
    fun saveStations(stations: List<Station>) = stations.forEach {
        dao.insert(it)
    }

    /**
     * Searches a station by its name.
     * This function in **thread safe**.
     * @param query The name to search for.
     */
    fun searchStations(query: String): LiveData<List<Station>> {
        // Create a LiveData containing the results.
        val results = MutableLiveData<List<Station>>()
        runOnIOThread {
            results.postValue(
                when {
                    // Get all the stations when the query is empty.
                    query.isEmpty() -> dao.read()
                    // Search the stations when the query has at least 4 characters.
                    query.length > QUERY_LENGTH_THRESHOLD -> dao.searchStationByName(query)
                    // Return an empty list if the query doesn't match any of the other search criteria.
                    else -> null
                }
            )
        }
        // Return the results as LiveData
        return results
    }

    /**
     * Retrieves all the stations.
     * **Note:** this function is **NOT THREAD SAFE**.
     */
    fun getStations() = dao.read()
}