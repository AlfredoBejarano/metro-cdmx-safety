package me.alfredobejarano.safetymetrocdmx.data

import android.arch.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 *
 * Database Access Object class to be implemented by Room
 * to provide operations for the [Station] entity.
 *
 * @author Alfredo Bejarano
 * @since November 11, 2018 - 23:49
 * @version 1.0
 **/
@Dao
interface StationDao {
    /**
     * Inserts a [Crime] record into the local database.
     * If there is a conflict (duplicates), the duplicate
     * insertion attempt is ignored.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(station: Station)

    /**
     * Retrieves all the stations from a given line.
     * @param line The line name to fetch stations from.
     * @return [LiveData] object containing the stations, sorted by its order in the line.
     */
    @Query("SELECT * FROM Stations WHERE line = :line ORDER BY orderInLine ASC")
    fun findByLine(line: String): List<Station>

    /**
     * Performs a query to retrieve all the stations containing the query string.
     */
    @Query("SELECT * FROM Stations WHERE name LIKE '%' || :query || '%'")
    fun searchStationByName(query: String) : List<Station>

    /**
     * Retrieves all the stations from the db.
     */
    @Query("SELECT * FROM Stations")
    fun read(): List<Station>
}