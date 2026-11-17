package com.example.skyjournal.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

//connection to the db; no role in crud

@Database(entities = [Observation::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun observationDao(): ObservationDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder( //in case 2 threads get here at same time
                    context.applicationContext,
                    AppDatabase::class.java,
                    "skyjournal.db"
                ).build().also { INSTANCE = it }
            }
    }
}