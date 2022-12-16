package com.example.calculator

import androidx.room.*

@Dao
interface CalculatorDao{

    @Insert
    suspend fun insert(history : CalculatorEntity)

    @Delete
    suspend fun delete(history: CalculatorEntity)

    @Query("Select * from calcHistory_table order by id ASC")
    fun getAllHistory() : List<CalculatorEntity>

}