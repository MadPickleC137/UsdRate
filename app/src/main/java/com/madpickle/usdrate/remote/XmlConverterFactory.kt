package com.madpickle.usdrate.remote

import okhttp3.ResponseBody
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import retrofit2.Converter
import retrofit2.Retrofit
import timber.log.Timber
import java.io.BufferedReader
import java.io.StringReader
import java.lang.reflect.Type


/**
 * Created by David Madilyan on 02.06.2022.
 *
 * Конвертер для преобразования ответов сервера в xml объект для дальнейшего парсинга
 */
class XmlConverterFactory private constructor(): Converter.Factory() {

    companion object{
        fun create() = XmlConverterFactory()
    }

    override fun responseBodyConverter(type: Type, annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *>? {
        return try {
            Converter { response ->
                val factory = XmlPullParserFactory.newInstance()
                factory.isNamespaceAware = true
                val xpp: XmlPullParser = factory.newPullParser()
                val content = StringBuilder()
                val reader = BufferedReader(response.byteStream().reader())
                var line = reader.readLine()
                while (line != null) {
                    content.append(line)
                    line = reader.readLine()
                }
                return@Converter xpp.setInput(StringReader(content.toString()))
            }
        } catch (e: Exception){
            Timber.e(e.message.toString())
            null
        }
    }
}