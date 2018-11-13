package me.alfredobejarano.safetymetrocdmx.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import me.alfredobejarano.safetymetrocdmx.BuildConfig

/**
 *
 * Abstract class to be implemented by Room that
 * defines the structure of the app local database.
 *
 * @author Alfredo Bejarano
 * @since November 12, 2018 - 13:57
 * @version 1.0
 **/
@Database(version = 5, entities = [Crime::class, Station::class], exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getCrimeDao(): CrimeDao
    abstract fun getStationDao(): StationDao

    companion object {
        @Volatile
        private var mInstance: AppDatabase? = null

        /**
         * Allows access to an instance of this app database.
         */
        fun getInstance(ctx: Context): AppDatabase = mInstance ?: synchronized(this) {
            buildDatabase(ctx).also { mInstance = it }
        }

        /**
         * Creates the instance for the database.
         * @param ctx The context to create the database.
         */
        private fun buildDatabase(ctx: Context) =
            Room.databaseBuilder(ctx, AppDatabase::class.java, "${BuildConfig.APPLICATION_ID}-DB")
                .fallbackToDestructiveMigration()
                .build()
    }
}