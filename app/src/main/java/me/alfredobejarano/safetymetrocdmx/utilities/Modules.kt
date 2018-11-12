package me.alfredobejarano.safetymetrocdmx.utilities

import android.content.Context
import dagger.Module
import dagger.Provides
import me.alfredobejarano.safetymetrocdmx.data.AppDatabase
import me.alfredobejarano.safetymetrocdmx.data.StationRepository
import me.alfredobejarano.safetymetrocdmx.viewmodel.StationSearchViewModel
import javax.inject.Singleton

/**
 *
 * Kotlin file that contains all the dagger modules for the app.
 *
 * @author Alfredo Bejarano
 * @since November 12, 2018 - 14:06
 * @version 1.0
 **/

@Module
class RepositoriesModule(private val ctx: Context) {
    /**
     * Tells to dagger how to create a [StationRepository]
     * class for injection.
     */
    @Provides
    @Singleton
    fun provideStationRepository() =
        StationRepository(AppDatabase.getInstance(ctx).getStationDao())
}

@Module
class ViewModelFactoriesModule {
    /**
     * Tells to dagger how to create a [StationSearchViewModel.Factory]
     * class for injection.
     */
    @Provides
    @Singleton
    fun provideStationSearchViewModelFactory() =
        StationSearchViewModel.Factory(Injector.component.provideStationRepository())
}