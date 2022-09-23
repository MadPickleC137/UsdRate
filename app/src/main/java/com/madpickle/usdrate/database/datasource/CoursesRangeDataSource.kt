package com.madpickle.usdrate.database.datasource

import com.madpickle.usdrate.core.extensions.getSimpleDate
import com.madpickle.usdrate.data.CourseRange
import com.madpickle.usdrate.database.CourseRangeDao
import com.madpickle.usdrate.database.CourseRangeEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.transform
import java.util.*
import javax.inject.Inject

/**
 * Created by David Madilyan on 04.06.2022.
 *
 * Служит для создания потоков данных и конвертации их в основные типы с которыми уже работает репозиторий
 */
class CoursesRangeDataSource @Inject constructor(private val courseRangeDao: CourseRangeDao) {

    /**
     * Получение потока с данными о курсе одной валюты по датам
     * @param code код валюты, приходит с бэка
     * @param start дата начала выборки, форматом даты dd.MM.yyyy
     * @param end дата окончания выборки, форматом даты dd.MM.yyyy
     * */
    suspend fun getFlowCourseRange(code: String, start: Date, end: Date): Flow<List<CourseRange>> {
        return courseRangeDao.getCoursesByCode(code).transform { entities ->
            val courseRangeList = entities.filter { entity ->
                val date = entity.date?.getSimpleDate()
                if(date == null) {
                    false
                } else {
                    date in start..end
                }
            }.toSet().map {
                it.toCourseRange()
            }
            emit(courseRangeList)
        }.flowOn(Dispatchers.IO)
    }

    /**
     * Добавление в кэш новых элементов
     * @param listCurrencies список с курсом валюты по датам
     * */
    suspend fun putListCourseRange(listCurrencies: List<CourseRange>){
        val listEntities = listCurrencies.map {
            CourseRangeEntity.fromCourseRange(it)
        }
        courseRangeDao.addNewCourseRanges(listEntities)
    }

    /**
     * Очищает кэш
     * */
    suspend fun clearAll(){
        courseRangeDao.deleteAll()
    }
}