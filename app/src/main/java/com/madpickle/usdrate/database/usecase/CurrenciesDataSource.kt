package com.madpickle.usdrate.database.usecase

import com.madpickle.usdrate.data.Currency
import com.madpickle.usdrate.database.CurrencyDao
import com.madpickle.usdrate.database.CurrencyEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

/**
 * Created by David Madilyan on 03.06.2022.
 *
 * Служит для создания потоков данных и конвертации их в основные типы с которыми уже работает репозиторий
 */
class CurrenciesDataSource @Inject constructor(private val currencyDao: CurrencyDao) {

    suspend fun getAllCurrencies(): Flow<List<Currency>> {
        return currencyDao.getAllCurrencies().transform { entityList ->
           val currencies = entityList.map {
                it.toCurrency()
            }.toList()
            emit(currencies)
        }.flowOn(Dispatchers.IO)
    }

    /**
     * Добавление новых элементов в таблицу, игнорирование повторяющихся элементов
     * */
    suspend fun updateCurrencies(listCurrencies: List<Currency>){
        val listEntities = listCurrencies.map {
            CurrencyEntity.fromCurrency(it)
        }
        currencyDao.updateCurrencies(listEntities)
    }
}