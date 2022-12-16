package com.example.calculator

class CalculatorRepository(private val historyDao : CalculatorDao) {

    val allHistory:List<CalculatorEntity> = historyDao.getAllHistory()

    suspend fun insert(history : CalculatorEntity){
        historyDao.insert(history)
    }
    suspend fun delete(history: CalculatorEntity){
        historyDao.delete(history)
    }
}