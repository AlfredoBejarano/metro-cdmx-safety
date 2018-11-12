package me.alfredobejarano.safetymetrocdmx.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import me.alfredobejarano.safetymetrocdmx.utilities.runOnIOThread
import javax.inject.Inject

/**
 *
 * Repository class that serves as the source of truth for the [Crime] entity.
 *
 * @author Alfredo Bejarano
 * @since November 12, 2018 - 15:27
 * @version 1.0
 **/
class CrimesRepository
@Inject constructor(private val dao: CrimeDao) {

    /**
     * Reads a list of crimes from the database committed in a selected
     * station.
     *
     * **Note:** This function **is not THREAD SAFE**.
     * **Note 2:** A LiveData object is returned, a transformation is needed.
     *
     * @param stationName The name of the station.
     * @param crimeType The type of crime to find.
     */
    fun findCrimesInStationByType(stationName: String, crimeType: String): LiveData<List<Crime>> {
        val results = MediatorLiveData<List<Crime>>()
        runOnIOThread {
            results.postValue(dao.findByStationNameAndCrimeType(stationName, crimeType))
        }
        return results
    }

    /**
     * Saves a list of crimes in the database.
     * **Note:** This function **is not THREAD SAFE**.
     * @param crimes The crimes to be saved
     */
    fun saveCrimes(crimes: List<Crime>) = crimes.forEach {
        dao.insert(it)
    }

    /**
     * Retrieves all the crimes from, the db.
     * **Note:** this function is **NOT THREAD SAFE**.
     */
    fun getAllCrimes() = dao.read()
}