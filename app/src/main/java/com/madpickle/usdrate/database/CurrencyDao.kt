package com.madpickle.usdrate.database

import androidx.room.*
import com.madpickle.usdrate.data.Currency
import kotlinx.coroutines.flow.Flow

/**
 * Created by David Madilyan on 01.06.2022.
 */
@Dao
interface CurrencyDao {
    @Query("SELECT * FROM currencies")
    fun getAllCurrencies(): Flow<List<CurrencyEntity>>

    @Transaction
    suspend  fun updateCurrencies(list: List<CurrencyEntity>){
        deleteCurrencies()
        insertCurrencies(list)
    }

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCurrencies(list: List<CurrencyEntity>)

    @Query("DELETE FROM currencies")
    suspend fun deleteCurrencies()
}


@Entity(tableName = "currencies")
data class CurrencyEntity(@PrimaryKey(autoGenerate = true) val id: Int = 0,
                          val name: String? = null,
                          val enName: String? = null,
                          val idCode: String? = null,
                          val nominal: Int? = null){



    fun toCurrency(): Currency{
        return Currency(name, enName, idCode, nominal)
    }

    companion object{
        fun fromCurrency(currency: Currency): CurrencyEntity{
            return CurrencyEntity(
                name = currency.name,
                enName = currency.enName,
                idCode = currency.idCode,
                nominal = currency.nominal
            )
        }
    }
}