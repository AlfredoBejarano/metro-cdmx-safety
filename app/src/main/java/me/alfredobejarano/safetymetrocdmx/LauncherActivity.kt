package me.alfredobejarano.safetymetrocdmx

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import me.alfredobejarano.safetymetrocdmx.utilities.Injector
import me.alfredobejarano.safetymetrocdmx.utilities.navigateTo
import me.alfredobejarano.safetymetrocdmx.viewmodel.LauncherViewModel
import javax.inject.Inject

/**
 * Activity class that will setup everything the app needs.
 */
class LauncherActivity : AppCompatActivity() {
    private lateinit var viewModel: LauncherViewModel
    @Inject
    lateinit var vmFactory: LauncherViewModel.Factory

    /**
     * Starts the injector and requests its dependencies to the Injector.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Injector.initialize(application)
        Injector.inject(this)
        viewModel = ViewModelProviders.of(this, vmFactory)[LauncherViewModel::class.java]
        viewModel.checkDatabaseRegistry().observe(this, Observer {
            navigateOnResult(it) { observeDatabaseFilling() }
        })
    }

    /**
     * Observes the database filling up with data.
     */
    private fun observeDatabaseFilling() {
        viewModel.fillDatabase().observe(this, Observer {
            navigateOnResult(it) {
                Toast.makeText(this, R.string.setting_app_up, Toast.LENGTH_SHORT).show()
            }
        })
    }

    /**
     * Navigates to the [MainActivity] if the result of the ViewModel is true.
     */
    private fun navigateOnResult(result: Boolean?, onFalse: () -> Unit) = if (result == true) {
        navigateTo(MainActivity::class.java, null)
    } else {
        onFalse()
    }
}
