package me.alfredobejarano.safetymetrocdmx.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 *
 * Entity class that contains data of a crime.
 *
 * @author Alfredo Bejarano
 * @since November 09, 2018 - 15:47
 * @version 1.0
 **/
@Entity(tableName = "Crimes")
data class Crime(
    @ColumnInfo(name = "pk")
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    /**
     * Describes in which year the crime was committed.
     */
    @Expose
    @SerializedName("year")
    var year: Int,
    /**
     * Describes to which route of the service the station belongs to.
     */
    @Expose
    @SerializedName("line")
    var line: Char,
    /**
     * Describes the name of the station the crime was committed.
     */
    @Expose
    @SerializedName("station")
    var stationName: String,
    /**
     * Describes how many crimes were committed to that station in said year.
     */
    @Expose
    @SerializedName("total")
    var totalCrimes: Int,
    /**
     * Describes the type of crime that was committed.
     * 
     * @see CRIME_TYPE_HEALTH
     * @see CRIME_TYPE_VIOLENT
     * @see CRIME_TYPE_NON_VIOLENT
     */
    @Expose
    @SerializedName("crime_type")
    var crimeType: String
) {
    companion object {
        /**
         * Defines a crime committed with non violent methods (pickpocket).
         */
        const val CRIME_TYPE_VIOLENT = "Robo con violencia"
        /**
         * Defines a crime committed with violence (guns, knifes).
         */
        const val CRIME_TYPE_HEALTH = "Delitos contra la salud"
        /**
         * Defines a crime against the passengers health (drug abuse).
         */
        const val CRIME_TYPE_NON_VIOLENT = "Robo sin violencia"
    }
}