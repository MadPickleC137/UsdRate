package com.madpickle.usdrate.database.datasource

import com.madpickle.usdrate.data.CourseDay
import com.madpickle.usdrate.database.CourseDayDao
import com.madpickle.usdrate.database.CourseDayEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.transform
import javax.inject.Inject

/**
 * Created by David Madilyan on 04.06.2022.
 */
class CourseDayDataSource @Inject constructor(private val courseDayDao: CourseDayDao) {

    /**
     * Получение списка курсов валют по выбранному дню
     * @param day формат строки содержащий дату: dd/MM/yyyy
     * */
    suspend fun getCoursesByDay(day: String): Flow<List<CourseDay>>{
        return courseDayDao.selectCourseByDay(day).transform { entityList ->
            val courses = entityList.map {
                it.toCourseDay()
            }.toList()
            emit(courses)
        }.flowOn(Dispatchers.IO)
    }

    /**
     * Добавление новых элементов в таблицу, игнорирование повторяющихся элементов
     * */
    suspend fun putNewCoursesDay(list: List<CourseDay>, day: String){
        val listEntities = list.map {
            CourseDayEntity.fromCourseDay(it, day)
        }
        courseDayDao.updateAllCourseDay(day, listEntities)
    }
}