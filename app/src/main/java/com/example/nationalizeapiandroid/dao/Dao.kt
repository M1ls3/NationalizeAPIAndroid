package com.example.nationalizeapiandroid.dao
import androidx.room.Dao
//import androidx.room.Insert
//import androidx.room.OnConflictStrategy
//import androidx.room.Query
import androidx.room.*
import com.example.nationalizeapiandroid.dao.model.CountryEntity
import com.example.nationalizeapiandroid.dao.model.NationalizeEntity
import com.example.nationalizeapiandroid.data.NationalizeWithCountries

@Dao
interface Dao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNationalize(nationalizeEntity: NationalizeEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCountryInfo(countryInfoList: List<CountryEntity>)

    @Transaction
    @Query("SELECT * FROM nationalize WHERE name = :name")
    suspend fun getNationalizeWithCountriesByName(name: String): NationalizeWithCountries?

    @Transaction
    @Query("SELECT * FROM nationalize")
    suspend fun getAllNationalizeWithCountries(): List<NationalizeWithCountries>

    @Delete
    suspend fun deleteNationalize(nationalizeEntity: NationalizeEntity)
}