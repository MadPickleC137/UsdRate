package com.madpickle.usdrate.core.extensions

import android.app.Activity
import androidx.core.content.ContextCompat

/**
 * Created by David Madilyan on 05.06.2022.
 */

fun Activity.setSystemNavBarColor(colorRes: Int){
    this.window?.statusBarColor = ContextCompat.getColor(this, colorRes)
    this.window?.navigationBarColor = ContextCompat.getColor(this, colorRes)
}