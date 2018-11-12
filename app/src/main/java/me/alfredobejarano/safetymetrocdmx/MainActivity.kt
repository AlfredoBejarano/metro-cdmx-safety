package me.alfredobejarano.safetymetrocdmx

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.toolbar.*
import me.alfredobejarano.safetymetrocdmx.StationSearchResultsFragment.Companion.RESULTS_TYPE_DESTINATION
import me.alfredobejarano.safetymetrocdmx.StationSearchResultsFragment.Companion.RESULTS_TYPE_ORIGIN
import me.alfredobejarano.safetymetrocdmx.StationSearchResultsFragment.Companion.newInstance
import me.alfredobejarano.safetymetrocdmx.utilities.Injector
import me.alfredobejarano.safetymetrocdmx.viewmodel.StationSearchViewModel
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var searchVMFactory: StationSearchViewModel.Factory
    private lateinit var searchVM: StationSearchViewModel
    /**
     * Defines the behaviour when a search field text changes.
     */
    private val searchTextWatcher = object : TextWatcher {
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
        override fun afterTextChanged(s: Editable?) = searchVM.searchStation(s?.toString() ?: "")
    }
    /**
     * Defines the click behaviour of a search view.
     */
    private val searchClickListener = View.OnClickListener { v ->
        when (v?.id) {
            R.id.origin_search ->
                setFragment(newInstance(RESULTS_TYPE_ORIGIN))
            R.id.destination_search ->
                setFragment(newInstance(RESULTS_TYPE_DESTINATION))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Injector.inject(this)
        setContentView(R.layout.activity_main)
        searchVM = ViewModelProviders.of(this, searchVMFactory)[StationSearchViewModel::class.java]
        setSearchFieldsListeners()
    }

    /**
     * Sets the listeners for the search fields.
     */
    private fun setSearchFieldsListeners() {
        origin_search?.setOnClickListener(searchClickListener)
        destination_search?.setOnClickListener(searchClickListener)
        origin_search?.addTextChangedListener(searchTextWatcher)
        destination_search?.addTextChangedListener(searchTextWatcher)
    }

    /**
     * Sets a fragment in the content.
     * @param fragment The fragment to be set as the content.
     */
    private fun setFragment(fragment: Fragment) = supportFragmentManager?.beginTransaction()
        ?.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
        ?.replace(R.id.content, fragment)
        ?.disallowAddToBackStack()
        ?.commitAllowingStateLoss()
}
