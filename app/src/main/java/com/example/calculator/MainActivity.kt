package com.example.calculator

import android.app.Activity
import android.app.AlertDialog
import android.app.Notification
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private var canAddOperation = false
    private var canAddDecimal = true
    lateinit var viewModel : CalculatorViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadLocate()
        setContentView(R.layout.activity_main)


        val actionBar = supportActionBar
        actionBar!!.title = resources.getString(R.string.app_name)

        viewModel = ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(CalculatorViewModel::class.java)

        // Intent

        historybtn.setOnClickListener {

            if(!(TextUtils.isEmpty(workingtv.text.toString().trim()) || TextUtils.isEmpty(resultTv.text.toString().trim()))){
                val history = CalculatorEntity(workingtv.text.toString() + " = " + resultTv.text.toString())
                viewModel.insertHistory(history)
            }
            Log.i("working data is ", workingtv.text.toString())
            Log.i("result data is ", resultTv.text.toString())
            val intent = Intent(this,HistoryActivity::class.java)
            intent.putExtra("workingtv",workingtv.text.toString())
            intent.putExtra("resultTv",resultTv.text.toString())
            startActivity(intent)
        }

        //handling languages

        val listItems = arrayOf("English","Hindi","Punjabi")

        val mBuilder = AlertDialog.Builder(this@MainActivity)
        mBuilder.setTitle("Choose Preferred Language")
        mBuilder.setSingleChoiceItems(listItems,-1){ dialog , which ->
            if (which == 0){
                setLocate("en")
                recreate()
            }else if (which == 1){
                setLocate("hi")
                recreate()
            }else if (which == 2){
                setLocate("pa")
                recreate()
            }
            dialog.dismiss()
        }
        val mDialog = mBuilder.create()
        mDialog.show()
    }

    private fun setLocate(Lang: String) {
        val locale = Locale(Lang)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        baseContext.resources.updateConfiguration(config , baseContext.resources.displayMetrics)

        val editor = getSharedPreferences("Settings" ,Context.MODE_PRIVATE).edit()
        editor.putString("My_Lang" ,Lang)
        editor.apply()
    }

    private fun loadLocate(){
        val sharedPreferences = getSharedPreferences("Settings" ,Activity.MODE_PRIVATE)
        val language = sharedPreferences.getString("My_Lang","")
        if (language != null) {
            setLocate(language)
        }
    }

    override fun onResume() {
        super.onResume()
        workingtv.text = ""
        resultTv.text = ""
    }


    fun allClearAction(view: View) {
        workingtv.text = ""
        resultTv.text = ""
    }
    fun equalsAction(view: View) {
        resultTv.text = calculateResults()
    }

    private fun calculateResults():String{
        val digitOperators = digitOperators()
        if (digitOperators.isEmpty())
            return ""

        val timesDivision = timeDivisionCalc(digitOperators)
        if (timesDivision.isEmpty())
            return ""
        val result = addSubtractCalc(timesDivision)
        return result.toString()

    }

    fun addSubtractCalc(passedList: MutableList<Any>): Float {
        if (passedList.isEmpty()){
            return 0.0f
        }
        if (!(passedList[0] is Float)){
            return 0.0f
        }
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
