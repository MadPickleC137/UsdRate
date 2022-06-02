package com.madpickle.usdrate.server

import com.madpickle.usdrate.server.data.XmlObject
import org.xmlpull.v1.XmlPullParser
import timber.log.Timber
import com.madpickle.usdrate.server.data.CourseDayResponse

/**
 * Created by David Madilyan on 02.06.2022.
 *
 * Дженерик для использования в Response класса сущностях
 * @sample CourseDayResponse
 * @param parser xml содержимое ответа сервера
 * @param tag основной тэг по которому осуществляется разделение данных
 */
class ResponseXmlParser(private val parser: XmlPullParser?, private val tag: String) {

    fun <T> parseObject(entityClass: XmlObject<T>): MutableList<T> {
        val list: MutableList<T> = mutableListOf()
        val mapData: MutableMap<String?, String?> = mutableMapOf()
        try {
            checkNotNull(parser)
            var key = ""
            while (parser.eventType != XmlPullParser.END_DOCUMENT) {
                if(parser.depth <= 1) {                                                             //пропускаем самый верхний тэг
                    parser.next()
                    continue
                }
                when (parser.eventType) {
                    XmlPullParser.START_TAG -> {
                        if(parser.name == tag) {
                            var i = 0
                            while (i < parser.attributeCount) {
                                mapData[parser.getAttributeName(i)] = parser.getAttributeValue(i)   //записываем все атрибуты в наш список
                                i++
                            }
                        }else if(!parser.name.isNullOrEmpty()){                                     //дабы ключи у мапы не затирались и значения не обнулялись
                            key = parser.name
                            mapData[key] = mapData[parser.name].orEmpty()
                        }
                    }
                    XmlPullParser.END_TAG -> {
                        if(parser.name == tag){
                            //передать мапу в объект и обнулить мапу
                            val data: T = entityClass.toObject(mapData)
                            list.add(data)
                            Timber.d(mapData.toString())
                            mapData.clear()
                        }
                    }
                    XmlPullParser.TEXT -> {
                        mapData[key] = parser.text
                    }
                    else -> { }
                }
                parser.next()
            }
        } catch (e: Exception) {
            Timber.e(e.message.toString())
        }
        return list
    }
}