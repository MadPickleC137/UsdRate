package com.madpickle.usdrate.core.utils

import androidx.lifecycle.Observer

/**
 * Created by David Madilyan on 04.06.2022.
 */

class EventObserver<T>(private val onEventUnhandledContent: (T) -> Unit) : Observer<Event<T>> {
    override fun onChanged(event: Event<T>?) {
        event?.getContentIfNotHandled()?.let { value ->
            onEventUnhandledContent(value)
        }
    }
    interface OnEventChanged<T> {
        fun onUnhandledContent(data: T)
    }
}