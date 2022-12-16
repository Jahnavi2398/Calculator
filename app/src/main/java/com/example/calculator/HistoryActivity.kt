package com.example.calculator

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_list.*



class HistoryActivity : AppCompatActivity() {

    lateinit var viewModel: CalculatorViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        viewModel = ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(CalculatorViewModel::class.java)

        val list:List<CalculatorEntity> = viewModel.allHistory

        if (list.isEmpty())
            noDataTv.visibility = View.VISIBLE

        recycleview.adapter = MyAdapter(list)
        recycleview.layoutManager = LinearLayoutManager(this)

    }
}