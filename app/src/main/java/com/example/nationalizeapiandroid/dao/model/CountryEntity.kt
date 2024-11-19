package com.example.nationalizeapiandroid.dao.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "country",
    foreignKeys = [ForeignKey(
        entity = NationalizeEntity::class,
        parentColumns = ["id"],
        childColumns = ["nationalize_id"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class CountryEntity (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nationalize_id: Int,
    val country_id: String,
    val probability: Double
)