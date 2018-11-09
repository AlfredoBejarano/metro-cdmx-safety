package me.alfredobejarano.safetymetrocdmx.data

import android.arch.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 *
 * Database Access Object for the Station entity.
 *
 * Provides database operations for said entity.
 *
 * @author Alfredo Bejarano
 * @since November 09, 2018 - 16:54
 * @version 1.0
 **/
@Dao
interface StationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun createOrUpdate()

    @Query("SELECT * FROM Stations ORDER BY name ASC")
    fun readAll(): LiveData<List<Station>>
}