package me.alfredobejarano.safetymetrocdmx.utilities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

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

/**
 * Shorthand function for navigation through activities.
 */
fun <A : AppCompatActivity> Context.navigateTo(destination: Class<A>, arguments: Bundle?) {
    this.startActivity(Intent(this, destination).apply {
        arguments?.let {
            this.putExtras(it)
        }
    })
}

/**
 * Retrieves the type of a [Collection].
 */
inline fun <reified T> Gson.fromJson(json: String) =
    this.fromJson<T>(json, object : TypeToken<T>() {}.type)