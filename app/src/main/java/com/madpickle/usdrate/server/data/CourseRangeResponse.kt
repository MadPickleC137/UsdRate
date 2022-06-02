package com.madpickle.usdrate.server.data

import com.madpickle.usdrate.data.CourseRange
import kotlin.jvm.Throws

/**
 * Created by David Madilyan on 01.06.2022.
 *
 * Класс сущности получаемый из парсинга xml объекта
 *
 * Потом Приводим его к нашему основному типу, который используется программой
 */
class CourseRangeResponse(): XmlObject<CourseRangeResponse>() {
    private var idCode: String? = null
    private var date: String? = null
    private var nominal: Int? = null
    private var value: Long? = null
    constructor(_idCode: String?, _data: String?, _nominal: Int?, _value: Long?): this(){
        this.idCode = _idCode
        this.date = _data
        this.nominal = _nominal
        this.value = _value
    }
    fun toCourseRange(): CourseRange{
        return CourseRange(idCode, date, nominal, value)
    }

    @Throws(NumberFormatException::class)
    override fun toObject(map: Map<String?, String?>): CourseRangeResponse  {
        this.nominal = map["Nominal"]?.toInt()
        this.date = map["Date"]
        this.value = map["Value"]?.toLong()
        this.idCode = map["Id"]
        return CourseRangeResponse(idCode, date, nominal, value)
    }
}