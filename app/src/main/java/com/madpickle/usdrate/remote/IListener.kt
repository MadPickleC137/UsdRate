package com.madpickle.usdrate.remote

/**
 * Created by David Madilyan on 02.06.2022.
 *
 * Интерфейс приёма коллбэков, реализуемый в вызывающем класcе
 *
 * @param <T>
 */
interface IListener<T> {
    fun onResponse(response: T)
    fun onError() {}
}