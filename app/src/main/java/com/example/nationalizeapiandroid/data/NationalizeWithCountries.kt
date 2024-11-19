package com.example.nationalizeapiandroid.data

import androidx.room.Embedded
import androidx.room.Relation
import com.example.nationalizeapiandroid.dao.model.CountryEntity
import com.example.nationalizeapiandroid.dao.model.NationalizeEntity

data class NationalizeWithCountries(
    @Embedded val nationalize: NationalizeEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "nationalize_id"
    )
    val countries: List<CountryEntity>
)
