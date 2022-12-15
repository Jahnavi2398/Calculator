package com.example.calculator

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// Created Entity (Table)

@Entity(tableName = "calcHistory_table")
class Calc_Entity (@ColumnInfo(name = "text")var text :String){
    @PrimaryKey(autoGenerate = true)var id = 0
}