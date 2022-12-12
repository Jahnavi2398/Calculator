package com.example.calculator

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.calculator.databinding.ActivityListBinding
import kotlinx.android.synthetic.main.activity_list.*

private const val TAG = "HistoryCalculation"

class HistoryActivity : AppCompatActivity() {

    lateinit var binding: ActivityListBinding

    var worktv: String? = ""
    var resultingTv: String? = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val preferences = getSharedPreferences("myPref", MODE_PRIVATE)
        val editor = preferences.edit()
        val extras = intent.extras

        if (extras != null) {
            worktv = extras.getString("workingtv")
            resultingTv = extras.getString("resultTv")
            // and get whatever type user account id is
        }
        lateinit var list:ArrayList<String>

        editor.apply {
            val str = worktv + "=" + resultingTv
            putString("FinalResult", str)
            list = ArrayList<String>()
            list.add(str)
        }


        recycleview.adapter = MyAdapter(list)
        recycleview.layoutManager = LinearLayoutManager(this)

    }
}