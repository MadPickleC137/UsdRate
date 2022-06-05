package com.madpickle.usdrate.remote

import com.madpickle.usdrate.core.utils.ITEM
import com.madpickle.usdrate.core.utils.RECORD
import com.madpickle.usdrate.core.utils.VALUTE
import com.madpickle.usdrate.data.CourseDay
import com.madpickle.usdrate.data.CourseRange
import com.madpickle.usdrate.data.Currency
import com.madpickle.usdrate.remote.data.CourseDayResponse
import com.madpickle.usdrate.remote.data.CourseRangeResponse
import com.madpickle.usdrate.remote.data.CurrencyResponse
import kotlinx.coroutines.*
import org.xmlpull.v1.XmlPullParser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

/**
 * Created by David Madilyan on 01.06.2022.
 */
class CbrUseCase @Inject constructor(private val cbrICbrService: ICbrService) {

    suspend fun getAllCurrencies(): List<Currency>?{
       val parser = cbrICbrService.fetchAllCurrencies()
        val currencies: List<Currency>? = suspendCancellableCoroutine { continuation ->
            parser.enqueue(object: Callback<XmlPullParser> {
                override fun onResponse(call: Call<XmlPullParser>, response: Response<XmlPullParser>) {
                    if(response.isSuccessful){
                        val parserXml = ResponseXmlParser(response.body(), ITEM)
                        val dataList = parserXml.parseObject(CurrencyResponse()).map {
                            it.toCurrency()
                        }
                        continuation.resume(dataList)
                    }
                }

                override fun onFailure(call: Call<XmlPullParser>, t: Throwable) {
                    if (continuation.isActive) {
                        continuation.resumeWithException(t)
                    }
                }
            })
        }
        return currencies
    }

    /**
     * Способ получения данных через suspendCancellableCoroutine
     *
     * Основной способ в приложении
     * */
    suspend fun getCourseByDay(dayDate: String): List<CourseDay>? {
        val xmlParser = cbrICbrService.getCourseByDay(dayDate)
        val listCourseDay: List<CourseDay>? = suspendCancellableCoroutine { continuation ->
            xmlParser.enqueue(object: Callback<XmlPullParser> {
                override fun onResponse(call: Call<XmlPullParser>, response: Response<XmlPullParser>) {
                    if(response.isSuccessful){
                        val parser = ResponseXmlParser(response.body(), VALUTE)
                        val dataList = parser.parseObject(CourseDayResponse()).map {
                            it.toCourseDay()
                        }
                        continuation.resume(dataList)
                    }
                }
                override fun onFailure(call: Call<XmlPullParser>, t: Throwable) {
                    if (continuation.isActive) {
                        continuation.resumeWithException(t)
                    }
                }
            })
        }
        return listCourseDay
    }

    /**
     * Способ получения данных через колбэк [IListener]
     * */
    suspend fun getCourseByDayAlt(dayDate: String, listener: IListener<List<CourseDay>?>)
    = withContext(Dispatchers.IO) {
        cbrICbrService.getCourseByDay(dayDate).enqueue(object: Callback<XmlPullParser> {
            override fun onResponse(call: Call<XmlPullParser>, response: Response<XmlPullParser>) {
                if(response.isSuccessful){
                    val parser = ResponseXmlParser(response.body(), VALUTE)
                    val dataList = parser.parseObject(CourseDayResponse()).map {
                        it.toCourseDay()
                    }
                    listener.onResponse(dataList)
                }
            }

            override fun onFailure(call: Call<XmlPullParser>, t: Throwable) {
                listener.onError()
            }
        })
    }


    suspend fun getCourseRange(start: String,
                               end: String,
                               idCode: String): List<CourseRange>?{
        val xmlResponse = cbrICbrService.getCourseByRange(start, end, idCode)
        val coursesRange: List<CourseRange>? = suspendCancellableCoroutine { continuation ->
            xmlResponse.enqueue(object: Callback<XmlPullParser> {
                override fun onResponse(call: Call<XmlPullParser>, response: Response<XmlPullParser>) {
                    if(response.isSuccessful){
                        val parser = ResponseXmlParser(response.body(), RECORD)
                        val dataList = parser.parseObject(CourseRangeResponse()).map {
                            it.toCourseRange()
                        }
                        continuation.resume(dataList)
                    }
                }

                override fun onFailure(call: Call<XmlPullParser>, t: Throwable) {
                    if (continuation.isActive) {
                        continuation.resumeWithException(t)
                    }
                }
            })
        }
        return coursesRange
    }
}