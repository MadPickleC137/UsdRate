package com.madpickle.usdrate.server.data

/**
 * Created by David Madilyan on 02.06.2022.
 */
abstract class XmlObject<T> {
    abstract fun toObject(map: Map<String?, String?>): T
}