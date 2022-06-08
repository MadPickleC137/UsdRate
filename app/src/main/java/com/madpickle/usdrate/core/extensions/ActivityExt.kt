package com.madpickle.usdrate.core.extensions

import android.app.Activity
import android.os.Bundle
import androidx.annotation.IdRes
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import timber.log.Timber

/**
 * Created by David Madilyan on 05.06.2022.
 */

fun Activity.setSystemNavBarColor(colorRes: Int){
    this.window?.statusBarColor = ContextCompat.getColor(this, colorRes)
    this.window?.navigationBarColor = ContextCompat.getColor(this, colorRes)
}

/**
 * Ну типа навигация инициализируется в активити, поэтому почему бы и не запихнуть сюда эти статичные функиции
 * в этот класс
 * */
fun NavController.safeNavigate(
    direction: Int,
    args: Bundle? = null
) {
    Timber.d("NAVIGATION: $direction")
    Timber.d("ARGS: ${args.toString()}")
    checkNotNull(args)
    currentDestination?.getAction(direction)?.run {
        navigate(direction, args)
    }
}

fun NavController.safeNavigate(direction: Int) {
    Timber.d("NAVIGATION: $direction")
    currentDestination?.getAction(direction)?.run {
        navigate(direction)
    }
}