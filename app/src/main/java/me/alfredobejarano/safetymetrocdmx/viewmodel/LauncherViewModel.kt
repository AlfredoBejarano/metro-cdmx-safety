package me.alfredobejarano.safetymetrocdmx.viewmodel

import android.app.Application
import androidx.annotation.RawRes
import androidx.lifecycle.*
import com.google.gson.Gson
import me.alfredobejarano.safetymetrocdmx.R
import me.alfredobejarano.safetymetrocdmx.data.Crime
import me.alfredobejarano.safetymetrocdmx.data.CrimesRepository
import me.alfredobejarano.safetymetrocdmx.data.Station
import me.alfredobejarano.safetymetrocdmx.data.StationRepository
import me.alfredobejarano.safetymetrocdmx.utilities.fromJson
import me.alfredobejarano.safetymetrocdmx.utilities.runOnIOThread
import javax.inject.Inject

/**
 *
 * ViewModel that handles UI requests for the LauncherActivity.
 *
 * @author Alfredo Bejarano
 * @since November 12, 2018 - 15:25
 * @version 1.0
 **/
class LauncherViewModel
@Inject constructor(
    application: Application,
    private val crimesRepo: CrimesRepository,
    private val stationRepo: StationRepository
) : AndroidViewModel(application) {
    /**
     * Checks if the database has registries in it
     * for crimes and stations.
     */
    fun checkDatabaseRegistry(): LiveData<Boolean> {
        val result = MediatorLiveData<Boolean>()
        runOnIOThread {
            val hasCrimes = crimesRepo.getAllCrimes().isNotEmpty()
            val hasStations = stationRepo.getStations().isNotEmpty()
            result.postValue(hasCrimes && hasStations)
        }
        return result
    }

    /**
     * Fills the database with the local JSON files.
     */
    fun fillDatabase(): LiveData<Boolean> {
        val result = MediatorLiveData<Boolean>()
        result.value = false
        runOnIOThread {
            crimesRepo.saveCrimes(getCrimesFromJSON())
            stationRepo.saveStations(getStationsFromJSON())
            result.postValue(true)
        }
        return result
    }

    /**
     * Reads the list of crimes from the JSON file.
     */
    private fun getCrimesFromJSON() =
        Gson().fromJson<List<Crime>>(readJSONFile(R.raw.crimes))

    /**
     * Reads all the stations from the JSON file.
     */
    private fun getStationsFromJSON() =
        Gson().fromJson<List<Station>>(readJSONFile(R.raw.lines))

    /**
     * Reads a JSON file from a raw resource Id.
     * @param json The json ID resource.
     */
    private fun readJSONFile(@RawRes json: Int) =
        getApplication<Application>().resources.openRawResource(json).bufferedReader(Charsets.UTF_8).use {
            it.readText()
        }.toString()

    class Factory
    @Inject constructor(
        private val application: Application,
        private val crimesRepo: CrimesRepository,
        private val stationRepo: StationRepository
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>) =
            LauncherViewModel(application, crimesRepo, stationRepo) as T
    }
}