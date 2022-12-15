package com.example.calculator

import androidx.room.*

@Dao
interface CalculatorDao{

    @Insert
    suspend fun insert(history : Calc_Entity)

    @Delete
    suspend fun delete(history: Calc_Entity)

    @Query("Select * from calcHistory_table order by id ASC")
    fun getAllHistory() : List<Calc_Entity>

}