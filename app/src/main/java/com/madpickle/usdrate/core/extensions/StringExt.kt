package com.madpickle.usdrate.core.extensions

import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * Created by David Madilyan on 04.06.2022.
 */

fun String?.getSimpleDate(): Date? {
    var date: Date? = null
    try {
        checkNotNull(this)
        val formatString = "dd.MM.yyyy"
        val formatter = SimpleDateFormat(formatString, Locale.getDefault())
        date = formatter.parse(this)
    }catch (e: Exception){ }
    return date
}

/**
 * исходная сторка должна быть формата dd/MM/yyyy
 * */
fun String.convertToDate(): Date? {
    var date: Date? = null
    try {
        checkNotNull(this)
        val formatString = "dd/MM/yyyy"
        val formatter = SimpleDateFormat(formatString, Locale.getDefault())
        date = formatter.parse(this)
    }catch (e: Exception){ }
    return date
}

//dd.MM.yyyy
fun LocalDateTime?.getStringPointDate(): String{
    val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
    return if(this != null) formatter.format(this) else ""
}

fun LocalDateTime?.getStringSlashDate(): String{
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    return if(this != null) formatter.format(this) else ""
}