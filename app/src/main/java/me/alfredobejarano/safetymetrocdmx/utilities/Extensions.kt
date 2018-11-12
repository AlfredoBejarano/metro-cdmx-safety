package me.alfredobejarano.safetymetrocdmx.utilities

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes

/**
 *
 * File containing the list of extensions functions for the app.
 *
 * @author Alfredo Bejarano
 * @since November 12, 2018 - 12:26
 * @version 1.0
 **/
fun ViewGroup.inflate(@LayoutRes layout: Int) =
    LayoutInflater.from(this.context).inflate(layout, this, false)

/**
 * Provides readability to execute operations when a collection is not null or empty.
 * @param f The function to execute if the collection is not null or empty.
 * @param g The function to execute when the collection is null or empty.
 */
fun <T> Collection<T>?.whenNotNullOrEmpty(
    f: (collection: Collection<T>) -> Unit,
    g: (collection: Collection<T>?) -> Unit
) {
    if (this?.isNotEmpty() == true) {
        f(this)
    } else {
        g(this)
    }
}