package com.example.nationalizeapiandroid.dao
import androidx.room.Dao
//import androidx.room.Insert
//import androidx.room.OnConflictStrategy
//import androidx.room.Query
import androidx.room.*
import com.example.nationalizeapiandroid.dao.model.CountryEntity
import com.example.nationalizeapiandroid.dao.model.NationalizeEntity
import com.example.nationalizeapiandroid.data.NationalizeWithCountries

import androidx.room.*

@Dao
interface Dao {

    // Вставка або оновлення запису в таблиці nationalize
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNationalize(nationalizeEntity: NationalizeEntity)

    // Вставка або оновлення списку країн для певної nationalize
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCountries(countries: List<CountryEntity>)

    // Оновлення націоналізації
    @Update
    suspend fun updateNationalize(nationalizeEntity: NationalizeEntity)

    // Оновлення інформації про країни
    @Update
    suspend fun updateCountry(countryEntity: CountryEntity)

    // Видалення націоналізації (при цьому автоматично видаляються пов'язані країни)
    @Delete
    suspend fun deleteNationalize(nationalizeEntity: NationalizeEntity)

    // Видалення окремих країн за nationalize_id
    @Query("DELETE FROM country WHERE nationalize_id = :nationalizeId")
    suspend fun deleteCountriesByNationalizeId(nationalizeId: Int)

    // Отримання націоналізації разом із списком країн по імені
    @Transaction
    @Query("SELECT * FROM nationalize WHERE name = :name")
    suspend fun getNationalizeWithCountriesByName(name: String): NationalizeWithCountries?

    // Отримання всіх записів націоналізації з країнами
    @Transaction
    @Query("SELECT * FROM nationalize")
    suspend fun getAllNationalizeWithCountries(): List<NationalizeWithCountries>
}

