package com.madpickle.usdrate.remote

import org.xmlpull.v1.XmlPullParser
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by David Madilyan on 01.06.2022.
 *
 * Интерфейс по взаимодействию с апи Банком России
 * общедоступные методы
 */
interface ICbrService {

    @GET("XML_val.asp")
    fun fetchAllCurrencies(@Query("d") d : Int = 0): Call<XmlPullParser>

    @GET("XML_daily.asp")
    fun getCourseByDay(@Query("date_req") dayDate: String): Call<XmlPullParser?>

    @GET("XML_dynamic.asp")
    fun getCourseByRange(@Query("date_req1") start: String,
                                 @Query("date_req2") end: String,
                                 @Query("VAL_NM_RQ") code: String): Call<XmlPullParser>

}
