package me.alfredobejarano.safetymetrocdmx.utilities

import java.util.concurrent.Executors

/**
 *
 * Utils class that provides handling of multi threaded work.
 *
 * @author Alfredo Bejarano
 * @since November 12, 2018 - 00:11
 * @version 1.0
 **/

private val singleThreadedExecutor = Executors.newSingleThreadExecutor()

/**
 * Executes a given work in a background thread.
 * @param f The work to be executed.
 */
fun runOnIOThread(f: () -> Unit) = singleThreadedExecutor.execute(f)