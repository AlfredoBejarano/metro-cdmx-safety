package me.alfredobejarano.safetymetrocdmx.data

import android.arch.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 *
 * Database Access Object class to be implemented by Room
 * to provide operations for the [Crime] entity.
 *
 * @author Alfredo Bejarano
 * @since November 11, 2018 - 23:37
 * @version 1.0
 **/
@Dao
interface CrimeDao {
    /**
     * Inserts a [Crime] record into the local database.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(crime: Crime)

    /**
     * Finds all the crimes with the given station name and crime type.
     * @param stationName Name of the station
     * @param crimeType Type of crime committed.
     */
    @Query("SELECT * FROM Crimes WHERE stationName = :stationName AND crimeType = crimeType")
    fun findByStationNameAndCrimeType(stationName: String, crimeType: Int): List<Station>
}