package me.alfredobejarano.safetymetrocdmx.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 *
 * Entity class that represents the details of a Station.
 *
 * @author Alfredo Bejarano
 * @since November 11, 2018 - 23:28
 * @version 1.0
 **/
@Entity(tableName = "Stations")
data class Station(
    @ColumnInfo(name = "pk")
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @Expose
    @SerializedName("Nombre")
    val name: String,
    @Expose
    @SerializedName("Línea")
    val line: String,
    @Expose
    @SerializedName("Orden")
    val order: Int
)