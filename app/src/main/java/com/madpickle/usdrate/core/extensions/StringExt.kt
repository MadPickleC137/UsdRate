package com.madpickle.usdrate.core.extensions

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by David Madilyan on 04.06.2022.
 */

fun String?.getSimpleDate(): Date? {
    var date: Date? = null
    try {
        checkNotNull(this)
        val formatStrings = listOf("dd.MM.yyyy", "dd/MM/yyyy")
        for(format in formatStrings){
            try {
                val formatter = SimpleDateFormat(format, Locale.getDefault())
                date = formatter.parse(this)
                break
            } catch (e: ParseException) { }
        }
    }catch (e: Exception){ }
    return date
}