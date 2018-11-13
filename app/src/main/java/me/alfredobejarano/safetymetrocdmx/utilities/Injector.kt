package me.alfredobejarano.safetymetrocdmx.utilities

import android.app.Application
import dagger.Component
import me.alfredobejarano.safetymetrocdmx.LauncherActivity
import me.alfredobejarano.safetymetrocdmx.MainActivity
import me.alfredobejarano.safetymetrocdmx.StationSearchResultsFragment
import me.alfredobejarano.safetymetrocdmx.data.CrimesRepository
import me.alfredobejarano.safetymetrocdmx.data.StationRepository
import me.alfredobejarano.safetymetrocdmx.viewmodel.LauncherViewModel
import me.alfredobejarano.safetymetrocdmx.viewmodel.StationSearchViewModel
import javax.inject.Singleton

/**
 *
 * Kotlin object that provides Singleton access for
 * the app dagger component.
 *
 * @author Alfredo Bejarano
 * @since November 12, 2018 - 14:11
 * @version 1.0
 **/
object Injector {
    @Volatile
    private lateinit var app: Application

    val component: AppComponent by lazy {
        DaggerAppComponent.builder()
            .repositoriesModule(RepositoriesModule(app))
            .viewModelFactoriesModule(ViewModelFactoriesModule(app))
            .build()
    }

    /**
     * Uses reflection to invoke an inject function for the
     * given object, if this function is not defined in the
     * [AppComponent] interface, it will throw a [RuntimeException].
     */
    fun inject(injectedObject: Any) {
        try {
            component.javaClass.getMethod("inject", injectedObject.javaClass)
                .invoke(component, injectedObject)
        } catch (t: NoSuchMethodException) {
            throw RuntimeException(
                "No inject function found for a " +
                        "${injectedObject.javaClass.name} instance. " +
                        "is it already added in the AppComponent?"
            )
        }
    }

    /**
     * Starts the app dagger module.
     */
    fun initialize(application: Application) {
        app = application
    }
}

@Singleton
@Component(modules = [RepositoriesModule::class, ViewModelFactoriesModule::class])
interface AppComponent {
    /**
     * Injects all the properties annotated with @Inject.
     * @param stationSearchResultsFragment The StationSearchResultsFragment instance
     * to provide injection to.
     */
    fun inject(stationSearchResultsFragment: StationSearchResultsFragment)

    /**
     * Injects all the properties annotated with @Inject.
     * @param mainActivity The MainActivity instance to provide injection to.
     */
    fun inject(mainActivity: MainActivity)

    /**
     * Injects all the properties annotated with @Inject.
     * @param launcherActivity The LauncherActivity instance to provide injection to.
     */
    fun inject(launcherActivity: LauncherActivity)

    /**
     * Provides static injection of a [StationRepository] object.
     */
    fun provideStationRepository(): StationRepository

    /**
     * Provides static injection of a [CrimesRepository] object.
     */
    fun provideCrimesRepository(): CrimesRepository

    /**
     * Provides static injection of a [LauncherViewModel.Factory] object.
     */
    fun provideLauncherViewModelFactory(): LauncherViewModel.Factory

    /**
     * Provides static injection of a [StationSearchViewModel.Factory] object.
     */
    fun provideStationSearchViewModelFactory(): StationSearchViewModel.Factory
}