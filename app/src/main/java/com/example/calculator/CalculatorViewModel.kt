package com.example.calculator

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CalculatorViewModel(application: Application) : AndroidViewModel(application) {
    private val repository :CalculatorRepository
    val allHistory : List<CalculatorEntity>

    init {
        val dao=CalculatorDatabase.getDatabase(application).getDao()
        repository = CalculatorRepository(dao)
        allHistory = repository.allHistory
    }

    fun deleteHistory(history :CalculatorEntity) = viewModelScope.launch(Dispatchers.IO){
        repository.delete(history)
    }
    fun insertHistory(history: CalculatorEntity) =viewModelScope.launch(Dispatchers.IO){
        repository.insert(history)
    }

//    fun addSubtractCalc(passedList: MutableList<Any>): Float {
//       var calculationhelper = calculationhelper()
//        return calculationhelper.addSubtractCalc(passedList)
//    }

}