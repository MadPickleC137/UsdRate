package com.madpickle.usdrate.core.extensions

import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Created by David Madilyan on 08.06.2022.
 */

@MainThread
fun <X, Y> LiveData<X>.mapAsync(
    scope: CoroutineScope,
    mapFunction: (X) -> Y,
): LiveData<Y> = switchMap { value ->
    liveData(scope.coroutineContext) {
        withContext(Dispatchers.IO) {
            emit(mapFunction(value))
        }
    }
}

fun <T, K, R> LiveData<T>.combine(
    liveData: LiveData<K>,
    block: (T?, K?) -> R
): LiveData<R> {
    val result = MediatorLiveData<R>()
    result.addSource(this) {
        result.value = block(this.value, liveData.value)
    }
    result.addSource(liveData) {
        result.value = block(this.value, liveData.value)
    }
    return result
}