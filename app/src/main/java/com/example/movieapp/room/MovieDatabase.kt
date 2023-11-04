package com.example.movieapp.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
@Database(entities = [Movies::class], version = 1, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun moviesDao(): MovieDao

    companion object{
        @Volatile
        var INSTANCE: MovieDatabase? = null
        fun getDatabaseInstance (context: Context): MovieDatabase{
            val tempInstance = INSTANCE
            if (tempInstance !=null) {
                return tempInstance
            }
            synchronized(this){
                val roomDatabaseInstance = Room.databaseBuilder(context, MovieDatabase::class.java, "Movies")
                    .allowMainThreadQueries().build()
                INSTANCE = roomDatabaseInstance
                return roomDatabaseInstance
            }
        }

    }
}