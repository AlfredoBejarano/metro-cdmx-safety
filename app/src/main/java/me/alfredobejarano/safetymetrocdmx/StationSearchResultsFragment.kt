package me.alfredobejarano.safetymetrocdmx

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import me.alfredobejarano.safetymetrocdmx.adapter.StationSearchResultsAdapter
import me.alfredobejarano.safetymetrocdmx.data.Station
import me.alfredobejarano.safetymetrocdmx.utilities.Injector
import me.alfredobejarano.safetymetrocdmx.utilities.whenNotNullOrEmpty
import me.alfredobejarano.safetymetrocdmx.viewmodel.StationSearchViewModel
import javax.inject.Inject

/**
 *
 * Simple [Fragment] sub class that displays
 * the results of a [Station] search.
 *
 * @author Alfredo Bejarano
 * @since November 12, 2018 - 11:11
 * @version 1.0
 **/
class StationSearchResultsFragment : Fragment(), StationSearchResultsAdapter.OnResultSelectedListener {
    @Inject
    lateinit var searchVMFactory: StationSearchViewModel.Factory
    private lateinit var searchVM: StationSearchViewModel

    companion object {
        private const val RESULTS_TYPE_EXTRA = "me.alfredobejarano.safetymetrocdmx.RESULTS_TYPE"
        const val RESULTS_TYPE_ORIGIN = "me.alfredobejarano.safetymetrocdmx.RESULTS_TYPE.ORIGIN"
        const val RESULTS_TYPE_DESTINATION = "me.alfredobejarano.safetymetrocdmx.RESULTS_TYPE.DESTINATION"

        /**
         * Creates a new instance of this fragment, defining
         * for which type of results this fragment will be.
         * @param resultType The type of result to choose.
         * @see RESULTS_TYPE_ORIGIN
         * @see RESULTS_TYPE_DESTINATION
         */
        fun newInstance(resultType: String): StationSearchResultsFragment {
            // Create the fragment.
            val fragment = StationSearchResultsFragment()
            // Put the arguments.
            fragment.arguments = Bundle().apply {
                this.putString(RESULTS_TYPE_EXTRA, resultType)
            }
            // Return the fragment.
            return fragment
        }
    }

    /**
     * Creates a RecyclerView for this fragment as its view.
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        RecyclerView(requireContext()).apply {
            // Assign a LinearLayoutManager when creating the RecyclerView.
            this.layoutManager = LinearLayoutManager(this.context)
        }

    /**
     * After creating the view, observe results from the ViewModel.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Injector.inject(this)
        // Retrieve the ViewModel from the activity.
        searchVM = ViewModelProviders.of(requireActivity(), searchVMFactory)[StationSearchViewModel::class.java]
        // Observe the results.
        searchVM.getSearchResults().observe(this, Observer { results ->
            // Get the results list.
            val list = view as RecyclerView
            results?.whenNotNullOrEmpty({
                // If the list adapter is not null, assign the new results to it.
                list.adapter?.let { adapter ->
                    (adapter as StationSearchResultsAdapter).setResults(it)
                } ?: run {
                    // If it is null, create a new one.
                    list.adapter = StationSearchResultsAdapter(it, this)
                }
            }, {
                if (it != null) {
                    // Display that no results were found.
                    Toast.makeText(requireContext(), R.string.no_results_found, Toast.LENGTH_SHORT).show()
                }
            })
        })
        // Used for fetching all the stations at startup.
        searchVM.searchStation("")
    }

    /**
     * Retrieves which type of result was assigned to this fragment.
     * @return Int value for this fragment type.
     * @see RESULTS_TYPE_ORIGIN
     * @see RESULTS_TYPE_DESTINATION
     */
    private fun getResultType() = arguments?.getString(RESULTS_TYPE_EXTRA)

    /**
     * Reports to the ViewModel the selected result.
     */
    override fun onResultSelected(station: Station) = when (getResultType()) {
        RESULTS_TYPE_ORIGIN -> searchVM.setOriginStation(station)
        RESULTS_TYPE_DESTINATION -> searchVM.setDestinationStation(station)
        else -> Unit
    }
}