package com.example.nationalizeapiandroid.dao.model

import androidx.room.Entity
import androidx.room.*

@Entity(tableName = "nationalize")
data class NationalizeEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val count: Int
)