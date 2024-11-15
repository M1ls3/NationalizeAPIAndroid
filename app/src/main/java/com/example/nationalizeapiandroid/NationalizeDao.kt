package com.example.nationalizeapiandroid

import androidx.room.Dao
//import androidx.room.Insert
//import androidx.room.OnConflictStrategy
//import androidx.room.Query
import androidx.room.*

@Dao
interface NationalizeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNationalize(nationalizeEntity: NationalizeEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCountryInfo(countryInfoList: List<CountryInfoEntity>)

    @Query("SELECT * FROM nationalize WHERE name = :name")
    suspend fun getNationalizeByName(name: String): NationalizeEntity?

    @Query("SELECT * FROM nationalize")
    suspend fun getAllNationalize(): List<NationalizeEntity>

    @Delete
    suspend fun deleteNationalize(nationalizeEntity: NationalizeEntity)
}

