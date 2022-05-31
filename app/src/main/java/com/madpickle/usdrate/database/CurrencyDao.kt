package com.madpickle.usdrate.database

import androidx.room.*

/**
 * Created by David Madilyan on 01.06.2022.
 */
@Dao
interface CurrencyDao {
    @Query("SELECT * FROM currencies")
    fun getAllCurrencies(): List<CurrencyEntity>

    @Query("SELECT * FROM currencies WHERE idCode=:code")
    fun selectCurrencyByCode(code: String): CurrencyEntity

    @Insert
    fun insertAllCurrencies(list: List<CurrencyEntity>)
}


@Entity(tableName = "currencies")
data class CurrencyEntity(@PrimaryKey(autoGenerate = true) val id: Int,
                          val name: String? = null,
                          val enName: String? = null,
                          val idCode: String? = null,
                          val nominal: Int? = null)