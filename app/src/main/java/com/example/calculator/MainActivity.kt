package com.example.calculator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var canAddOperation = false
    private var canAddDecimal = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Intent

        val databaseBtn = findViewById<Button>(R.id.historybtn)
        databaseBtn.setOnClickListener {
            Log.i("working data is ", workingtv.text.toString())
            Log.i("result data is ", resultTv.text.toString())
            val intent = Intent(this,HistoryActivity::class.java)
            intent.putExtra("workingtv",workingtv.text.toString())
            intent.putExtra("resultTv",resultTv.text.toString())
            startActivity(intent)
        }
    }

    fun allClearAction(view: View) {
        workingtv.text = ""
        resultTv.text = ""
    }
    fun equalsAction(view: View) {
        resultTv.text = CalculateResults()
    }

    private fun CalculateResults():String{
        val digitOperators = digitOperators()
        if (digitOperators.isEmpty())
            return ""

        val timesDivision = timeDivisionCalc(digitOperators)
        if (timesDivision.isEmpty())
            return ""
        val result = addSubtractCalc(timesDivision)
        return result.toString()

    }

    private fun addSubtractCalc(passedList: MutableList<Any>): Float {
        var result = passedList[0] as Float

        for (i in passedList.indices){
            if (passedList[i] is Char && i != passedList.lastIndex){
                val operator = passedList[i]
                val nextDigit = passedList[i+1] as Float
                if (operator == '+')
                    result += nextDigit
                if (operator == '-')
                    result -= nextDigit
            }
        }
        return result
    }

    private fun timeDivisionCalc(passedList : MutableList<Any>) : MutableList<Any>{
        var list = passedList
        while (list.contains('x') || list.contains('/')){
            list = calcTimeDivision(list)
        }
        return list
    }
    private fun calcTimeDivision(passedList: MutableList<Any>) : MutableList<Any>{
        val newList = mutableListOf<Any>()

        var restartIndex = passedList.size

        for (i in passedList.indices){
            if (passedList[i] is Char && i != passedList.lastIndex && i < restartIndex){
                val operator = passedList[i]
                val prevDigit = passedList[i-1] as Float
                val nextDigit = passedList[i+1] as Float

                when(operator){
                    'x' -> {
                        newList.add(prevDigit * nextDigit)
                        restartIndex = i + 1
                    }
                    '/' -> {
                        newList.add(prevDigit / nextDigit)
                        restartIndex = i + 1
                    }
                    else -> {
                        newList.add(prevDigit)
                        newList.add(operator)
                    }
                }

            }

            if (i > restartIndex)
                newList.add(passedList[i])
        }
        return newList
    }
    private fun digitOperators(): MutableList<Any>{
        val list = mutableListOf<Any>()
        var currDigit = ""
        for (character in workingtv.text){
            if (character.isDigit() || character == '.'){
                currDigit += character
            }else{
                if(currDigit.isNotEmpty()) {
                    list.add(currDigit.toFloat())
                    currDigit = ""
                    list.add(character)
                }
            }
        }
        if (currDigit != "")
            list.add(currDigit.toFloat())
        return list
    }

    fun backSpaceAction(view: View) {
        val length = workingtv.length()
        if (length>0){
            workingtv.text= workingtv.text.subSequence(0,length-1)
        }
    }

    fun ActionOperation(view: View){
        if (view is Button && canAddOperation){
            workingtv.append(view.text)
            canAddOperation = false
            canAddDecimal = true
        }
    }

    fun numberAction(view: View) {
        if (view is Button){
            if (view.text == "."){
                if (canAddDecimal)
                    workingtv.append(view.text)
                canAddDecimal =  false
            }
            else
                workingtv.append(view.text)
            canAddOperation = true
        }
    }

}
