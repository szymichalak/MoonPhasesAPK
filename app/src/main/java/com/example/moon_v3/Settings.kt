package com.example.moon_v3

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_settings.*
import java.io.File
import java.io.FileInputStream


class Settings : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val file = File(filesDir, "data.txt")

        val isNewFileCreated :Boolean = file.createNewFile()
        if(isNewFileCreated){
            file.appendText("N\n3")
        } else{
            val readResult = FileInputStream(file).bufferedReader().use { it.readText() }
            val data = readResult.split("\n").toTypedArray()

            when(data[0]){
                "N" -> radioGroup.check(R.id.radioButton)
                "S" -> radioGroup.check(R.id.radioButton2)
                else -> {radioGroup.check(R.id.radioButton)}
            }

            when(data[1]){
                "1" -> radioGroup2.check(R.id.radioButton3)
                "2" -> radioGroup2.check(R.id.radioButton4)
                "3" -> radioGroup2.check(R.id.radioButton5)
                "4" -> radioGroup2.check(R.id.radioButton6)
                else -> {radioGroup.check(R.id.radioButton3)}
            }
        }

    }

    override fun onUserInteraction() {
        super.onUserInteraction()
        var str = ""
        if (radioButton.isChecked){
            str += "N"
        }
        else if (radioButton2.isChecked){
            str += "S"
        }

        if (radioButton3.isChecked){
            str += "\n1"
        }
        else if (radioButton4.isChecked){
            str += "\n2"
        }
        else if (radioButton5.isChecked){
            str += "\n3"
        }
        else if (radioButton6.isChecked){
            str += "\n4"
        }

        val file = File(filesDir, "data.txt")
        file.writeText(str)

    }

}
