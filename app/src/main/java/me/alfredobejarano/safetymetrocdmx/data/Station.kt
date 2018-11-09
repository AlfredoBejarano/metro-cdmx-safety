package me.alfredobejarano.safetymetrocdmx.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 *
 * Model class that describes the details of a station.
 *
 * @author Alfredo Bejarano
 * @since November 09, 2018 - 15:59
 * @version 1.0
 **/
@Entity(tableName = "Stations")
data class Station(
    /**
     * Defines the identifier for Room.
     */
    @ColumnInfo(name = "pk")
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    /**
     * Describes to which line the station belongs to.
     */
    var line: Char,
    /**
     * Describes the name of the station.
     */
    var name: String,
    /**
     * Describes the total of health related crimes that happened between January 2012 to March 2018.
     * @see Crime.CrimeType
     */
    val healthCrimesAmount: Int,
    /**
     * Describes the total of violent crimes that happened between January 2012 to March 2018.
     * @see Crime.CrimeType
     */
    var violenceCrimesAmount: Int,
    /**
     * Describes the total of non violent crimes that happened between January 2012 to March 2018.
     * @see Crime.CrimeType
     */
    val noViolenceCrimesAmount: Int
)