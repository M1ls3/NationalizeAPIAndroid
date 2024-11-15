package com.example.nationalizeapiandroid
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

data class Country(
    val country_id: String,
    val probability: Double
)

@Entity(
    tableName = "CountryInfo",
    foreignKeys = [ForeignKey(
        entity = NationalizeEntity::class,
        parentColumns = ["id"],
        childColumns = ["nationalize_id"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class CountryInfoEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nationalize_id: Int,
    val country_id: String,
    val probability: Double
)

