package com.example.nationalizeapiandroid

import androidx.room.Entity
import androidx.room.*

@Entity(tableName = "nationalize")
data class NationalizeEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val count: Int
)


data class Nationalize(
    val count: Int,
    val name: String,
    val country: List<Country>
)


data class NationalizeWithCountries(
    @Embedded val nationalize: NationalizeEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "nationalize_id"
    )
    val countries: List<CountryInfoEntity>
)

