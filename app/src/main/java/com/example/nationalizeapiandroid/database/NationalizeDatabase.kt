package com.example.nationalizeapiandroid.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.nationalizeapiandroid.dao.Dao
import com.example.nationalizeapiandroid.dao.model.CountryEntity
import com.example.nationalizeapiandroid.dao.model.NationalizeEntity

@Database(entities = [NationalizeEntity::class, CountryEntity::class], version = 1)
abstract class NationalizeDatabase : RoomDatabase() {
    abstract fun nationalizeDao(): Dao

    companion object {
        @Volatile
        private var INSTANCE: NationalizeDatabase? = null

        fun getDatabase(context: Context): NationalizeDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NationalizeDatabase::class.java,
                    "nationalize_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}