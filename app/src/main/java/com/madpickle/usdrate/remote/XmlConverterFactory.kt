package com.madpickle.usdrate.remote

import okhttp3.ResponseBody
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import retrofit2.Converter
import retrofit2.Retrofit
import timber.log.Timber
import java.lang.reflect.Type


/**
 * Created by David Madilyan on 02.06.2022.
 *
 * Конвертер для преобразования ответов сервера в xml объект для дальнейшего парсинга
 */
class XmlConverterFactory private constructor(): Converter.Factory() {

    companion object{
        private const val ENCODING = "windows-1251"
        fun create() = XmlConverterFactory()
    }

    override fun responseBodyConverter(type: Type, annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *>? {
        return try {
            Converter<ResponseBody, XmlPullParser> { response ->
                val factory = XmlPullParserFactory.newInstance()
                factory.isNamespaceAware = true
                val xpp: XmlPullParser = factory.newPullParser()
                xpp.setInput(response.byteStream(), ENCODING)
                return@Converter xpp
            }
        } catch (e: Exception){
            Timber.e(e.message.toString())
            null
        }
    }
}