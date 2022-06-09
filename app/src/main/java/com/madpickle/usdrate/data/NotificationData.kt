package com.madpickle.usdrate.data

/**
 * Created by David Madilyan on 09.06.2022.
 *
 * Сущность небходимая для создания нотификации
 */
data class NotificationData(val codeValute: String? = null,
                            val nameValute: String? = null,
                            val minValue: Double? = null,
                            val maxValue: Double? = null)
