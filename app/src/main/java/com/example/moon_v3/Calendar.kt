package com.example.moon_v3

import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_calendar.*
import java.io.File
import java.io.FileInputStream
import java.time.LocalDate


class Calendar : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)

        increment()
        decrement()

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun increment(){
        try {
            val plus = findViewById<Button>(R.id.imageButton2)
            plus.setOnClickListener {
                val inputData = input_year.text.toString()
                if (inputData == ""){
                    input_year.setText("1901")
                }
                else {
                    val nextNum = (inputData.toInt() + 1).toString()
                    input_year.setText(nextNum)
                }
            }
        }
        catch (e: Throwable){
            return
        }
        update()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun decrement(){
        try {
            val minus = findViewById<Button>(R.id.imageButton)
            minus.setOnClickListener {
                val inputData = input_year.text.toString()
                if (inputData == ""){
                    input_year.setText("2199")
                }
                else {
                    val prevNum = (inputData.toInt() - 1).toString()
                    input_year.setText(prevNum)
                }
            }
        }
        catch (e: Throwable){
            return
        }
        update()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun update(){
        val inputData: Int
        try{
            inputData = input_year.text.toString().toInt()
            if (inputData <= 1900 || inputData >= 2200){
                val info = findViewById<TextView>(R.id.info_control)
                info.text = "Podaj rok pomiędzy 1900 a 2200"
                return
            }
        }
        catch (e: Throwable){
            return
        }

        val info = findViewById<TextView>(R.id.info_control)
        info.text = "Pełnie w roku $inputData"

        val file = File(filesDir, "data.txt")
        val readResult = FileInputStream(file).bufferedReader().use { it.readText() }
        val data = readResult.split("\n").toTypedArray()
        val algorithm = data[1]

        val date = LocalDate.of(inputData, 1, 1)
        var row = 1
        var id = R.id.textView1
        for(i in 0..366){
            val iteration = date.plusDays(i.toLong())
            val calculate: Int = when(algorithm){
                "1" -> simple(iteration.year, iteration.monthValue, iteration.dayOfMonth)
                "2" -> conway(iteration.year, iteration.monthValue, iteration.dayOfMonth)
                "3" -> Trig1(iteration.year, iteration.monthValue, iteration.dayOfMonth)
                "4" -> Trig2(iteration.year, iteration.monthValue, iteration.dayOfMonth)
                else -> {
                    Trig1(iteration.year, iteration.monthValue, iteration.dayOfMonth)
                }
            }
            if (calculate == 50){
                val pelnia = iteration.year.toString() +"."+ iteration.monthValue.toString() +"."+ iteration.dayOfMonth.toString()
                when (row){
                    1  -> id = R.id.textView1
                    2  -> id = R.id.textView2
                    3  -> id = R.id.textView3
                    4  -> id = R.id.textView4
                    5  -> id = R.id.textView5
                    6  -> id = R.id.textView6
                    7  -> id = R.id.textView7
                    8  -> id = R.id.textView8
                    9  -> id = R.id.textView9
                    10 -> id = R.id.textView10
                    11 -> id = R.id.textView11
                    12 -> id = R.id.textView12
                }
                val row_text = findViewById<TextView>(id)
                row_text.text = "Pełnia: " + pelnia
                row ++
            }
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onUserInteraction() {
        super.onUserInteraction()
        update()
    }

}
