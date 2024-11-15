package com.example.nationalizeapiandroid

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [NationalizeEntity::class, CountryInfoEntity::class], version = 2)
abstract class NationalizeDatabase : RoomDatabase() {
    abstract fun nationalizeDao(): NationalizeDao

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