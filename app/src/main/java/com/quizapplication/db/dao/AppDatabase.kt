package com.quizapplication.db.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.quizapplication.db.model.Quiz

@Database(entities = [Quiz::class], version=1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun quizDao(): QuizDao

    companion object{

        @Volatile
        private var INSTANCE : AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase{

            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                return instance
            }

        }

    }

}