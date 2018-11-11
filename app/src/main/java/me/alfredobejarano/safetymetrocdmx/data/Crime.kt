package me.alfredobejarano.safetymetrocdmx.data

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
     */
    @Expose
    @SerializedName("crime_type")
    var crimeType: CrimeType
) {
    /**
     *
     * Enum class that describes the type of crimes that were reported.
     *
     * @author Alfredo Bejarano
     * @since November 09, 2018 - 15:48
     * @version 1.0
     **/
    enum class CrimeType {
        /**
         * Describes that a crime was made with violence (knife, guns).
         */
        WITH_VIOLENCE,
        /**
         * Describes a crime without violence (pickpocketing).
         */
        WITHOUT_VIOLENCE,
        /**
         * Describes a crime against the health of the users (drug abuse).
         */
        HEALTH
    }
}