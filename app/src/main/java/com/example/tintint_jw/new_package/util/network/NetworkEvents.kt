package com.example.tintint_jw.new_package.util.network

import androidx.lifecycle.LiveData

/**
 * This liveData enabling network connectivity monitoring
 * @see NetworkStateHolder to get current connection state
 */
object NetworkEvents : LiveData<Event>() {

    internal fun notify(event: Event) {
        postValue(event)
    }

}