package com.madpickle.usdrate.core.extensions

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

/**
 * Created by David Madilyan on 04.06.2022.
 */

fun <T> Fragment.observe(liveData: LiveData<T>, observer: (T?) -> Unit) {
    liveData.observe(viewLifecycleOwner, Observer { observer(it) })
}
