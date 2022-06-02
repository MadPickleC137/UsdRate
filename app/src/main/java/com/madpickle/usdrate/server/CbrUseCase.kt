package com.madpickle.usdrate.server

import com.madpickle.usdrate.core.utils.ITEM
import com.madpickle.usdrate.core.utils.RECORD
import com.madpickle.usdrate.core.utils.VALUTE
import com.madpickle.usdrate.data.CourseDay
import com.madpickle.usdrate.data.CourseRange
import com.madpickle.usdrate.data.Currency
import com.madpickle.usdrate.server.data.CourseDayResponse
import com.madpickle.usdrate.server.data.CourseRangeResponse
import com.madpickle.usdrate.server.data.CurrencyResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.xmlpull.v1.XmlPullParser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

/**
 * Created by David Madilyan on 01.06.2022.
 */
class CbrUseCase @Inject constructor(private val cbrICbrService: ICbrService) {

    suspend fun getAllCurrencies(listener: IListener<List<Currency>?>)
    = withContext(Dispatchers.IO) {
        cbrICbrService.fetchAllCurrencies().enqueue(object: Callback<XmlPullParser> {
            override fun onResponse(call: Call<XmlPullParser>, response: Response<XmlPullParser>) {
                if(response.isSuccessful){
                    val parser = ResponseXmlParser(response.body(), ITEM)
                    val dataList = parser.parseObject(CurrencyResponse()).map {
                        it.toCurrency()
                    }
                    listener.onResponse(dataList)
                }
            }

            override fun onFailure(call: Call<XmlPullParser>, t: Throwable) {
                listener.onError()
            }
        })
    }

    suspend fun getCourseByDay(dayDate: String, listener: IListener<List<CourseDay>?>)
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
                               idCode: String,
                               listener: IListener<List<CourseRange>?>)
            = withContext(Dispatchers.IO) {
        cbrICbrService.getCourseByRange(start, end, idCode).enqueue(object: Callback<XmlPullParser> {
            override fun onResponse(call: Call<XmlPullParser>, response: Response<XmlPullParser>) {
                if(response.isSuccessful){
                    val parser = ResponseXmlParser(response.body(), RECORD)
                    val dataList = parser.parseObject(CourseRangeResponse()).map {
                        it.toCourseRange()
                    }
                    listener.onResponse(dataList)
                }
            }

            override fun onFailure(call: Call<XmlPullParser>, t: Throwable) {
                listener.onError()
            }
        })
    }
}