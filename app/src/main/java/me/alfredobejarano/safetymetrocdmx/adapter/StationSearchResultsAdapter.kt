package me.alfredobejarano.safetymetrocdmx.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import me.alfredobejarano.safetymetrocdmx.R
import me.alfredobejarano.safetymetrocdmx.data.Station
import me.alfredobejarano.safetymetrocdmx.utilities.inflate

/**
 *
 * Class that defines how to display the results of a Stations search.
 *
 * @author Alfredo Bejarano
 * @since November 12, 2018 - 12:22
 * @version 1.0
 **/
class StationSearchResultsAdapter(
    private var results: Collection<Station>,
    private val listener: OnResultSelectedListener
) :
    RecyclerView.Adapter<StationSearchResultsAdapter.StationResultViewHolder>() {
    /**
     * Creates a [StationResultViewHolder] object ready to be attached.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        StationResultViewHolder(parent.inflate(viewType))

    /**
     * Defines the layout for every item in the list.
     */
    override fun getItemViewType(position: Int) = R.layout.item_station_search

    /**
     * Returns how many stations are going to be rendered.
     */
    override fun getItemCount() = results.size

    /**
     * Draws data for the current station element being rendered.
     */
    override fun onBindViewHolder(holder: StationResultViewHolder, position: Int) {
        // Get the station from the result.
        val station = results.elementAt(position)
        // Draw the holder name
        holder.name?.text = station.name
        // Draw the line indicator
        holder.lineIndicator?.text = station.line
        // Set the indicator background
        holder.lineIndicator?.background?.setTint(
            ContextCompat.getColor(
                holder.itemView.context, when (station.line) {
                    "1" -> R.color.colorLine1
                    "2" -> R.color.colorLine2
                    "3" -> R.color.colorLine3
                    "4" -> R.color.colorLine4
                    "5" -> R.color.colorLine5
                    "6" -> R.color.colorLine6
                    "7" -> R.color.colorLine7
                    "8" -> R.color.colorLine8
                    "9" -> R.color.colorLine9
                    "A" -> R.color.colorLineA
                    "B" -> R.color.colorLineB
                    "12" -> R.color.colorLine12
                    else -> R.color.colorPrimary
                }
            )
        )
        // Report a selected result when clicked.
        holder.itemView.setOnClickListener {
            listener.onResultSelected(station)
        }
    }

    /**
     * Notifies a new set of results to the list.
     * @param results The new list of stations found.
     */
    fun setResults(results: Collection<Station>) {
        this.results = results
        notifyDataSetChanged()
    }

    /**
     * ViewHolder class that defines how a result is going to be displayed in a list.
     */
    class StationResultViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal val name: TextView? = itemView.findViewById(R.id.name)
        internal val lineIndicator: TextView? = itemView.findViewById(R.id.line_indicator)
    }

    /**
     * Interface that defines functions for when a result gets clicked.
     */
    interface OnResultSelectedListener {
        fun onResultSelected(station: Station)
    }
}