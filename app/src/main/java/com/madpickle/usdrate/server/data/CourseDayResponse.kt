package com.madpickle.usdrate.server.data

import com.madpickle.usdrate.data.CourseDay
import kotlin.jvm.Throws

/**
 * Created by David Madilyan on 02.06.2022.
 *
 * Класс сущности получаемый из парсинга xml объекта
 */
class CourseDayResponse(): XmlObject<CourseDayResponse>() {
    private var idCode: String? = null
    private var nominal: Int? = null
    private var charCode: String? = null
    private var name: String? = null
    private var value: Long? = null

    constructor(_name: String?, _charCode: String?, _idCode: String?, _nominal: Int?, _value: Long?): this(){
        this.name = _name
        this.charCode = _charCode
        this.idCode = _idCode
        this.value = _value
        this.nominal = _nominal
    }
    fun toCourseDay(): CourseDay = CourseDay(idCode, nominal, charCode, name, value)

    @Throws(NumberFormatException::class)
    override fun toObject(map: Map<String?, String?>): CourseDayResponse {
        this.nominal = map["Nominal"]?.toInt()
        this.charCode = map["CharCode"]
        this.name = map["Name"]
        this.value = map["Value"]?.toLong()
        this.idCode = map["ID"]
       return CourseDayResponse(name, charCode, idCode, nominal, value)
    }
}
