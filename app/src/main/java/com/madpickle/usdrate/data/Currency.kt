package com.madpickle.usdrate.data

/**
 * Created by David Madilyan on 31.05.2022.
 *
 * Элемент списка валют из справочника, необходим для получения кодов валют
 */
data class Currency(val name: String?,
                    val enName: String?,
                    val idCode: String?,
                    val nominal: Int?)
