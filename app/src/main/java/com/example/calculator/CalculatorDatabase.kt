package com.example.calculator

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = arrayOf(Calc_Entity::class) , version = 1 , exportSchema = false)

abstract class CalculatorDatabase : RoomDatabase(){

    abstract fun getDao() : CalculatorDao

    companion object{
        // Singleton object
        @Volatile
        private var INSTANCE : CalculatorDatabase? = null

        fun getDatabase(context :Context):CalculatorDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CalculatorDatabase ::class.java,
                    "calc_db"
                ).allowMainThreadQueries().build()
                INSTANCE =instance
                instance
            }
        }
    }
}