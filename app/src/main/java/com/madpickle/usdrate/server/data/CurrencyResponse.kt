package com.madpickle.usdrate.server.data

import com.madpickle.usdrate.data.Currency
import kotlin.jvm.Throws

/**
 * Created by David Madilyan on 02.06.2022.
 *
 * Класс сущности получаемый из парсинга xml объекта
 *
 * Потом Приводим его к нашему основному типу, который используется программой
 */
class CurrencyResponse(): XmlObject<CurrencyResponse>() {
    private var name: String? = null
    private var enName: String? = null
    private var idCode: String? = null
    private var nominal: Int? = null

    constructor(_name: String?, _enName: String?, _idCode: String?, _nominal: Int?): this(){
        this.name = _name
        this.enName = _enName
        this.idCode = _idCode
        this.nominal = _nominal
    }

    fun toCurrency(): Currency = Currency(name, enName, idCode, nominal)

    @Throws(NumberFormatException::class)
    override fun toObject(map: Map<String?, String?>): CurrencyResponse {
        this.name = map["Name"]
        this.enName = map["EngName"]
        this.nominal = map["Nominal"]?.toInt()
        this.idCode = map["ParentCode"]
        return CurrencyResponse(name, enName, idCode, nominal)
    }
}
