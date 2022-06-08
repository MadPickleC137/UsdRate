package com.madpickle.usdrate.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Created by David Madilyan on 31.05.2022.
 *
 * Элемент списка валют из справочника, необходим для получения кодов валют
 */
@Parcelize
data class Currency(val name: String?,
                    val enName: String?,
                    val idCode: String?,
                    val nominal: Int?): Parcelable
