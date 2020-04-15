package com.example.moon_v3

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import java.io.File
import java.io.FileInputStream
import java.time.LocalDateTime


class MainActivity : AppCompatActivity() {

    var hemisphere = ""
    var algorithm = ""

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.kalendarz)
        button.setOnClickListener{
            val intent = Intent(this, Calendar::class.java)
            startActivity(intent)
        }

        val settings = findViewById<Button>(R.id.settings)
        settings.setOnClickListener{
            val intent = Intent(this, Settings::class.java)
            startActivity(intent)
        }

        val file = File(filesDir, "data.txt")
        val isNewFileCreated :Boolean = file.createNewFile()
        if(isNewFileCreated){
            file.appendText("N\n3")
        }


        val readResult = FileInputStream(file).bufferedReader().use { it.readText() }
        val data = readResult.split("\n").toTypedArray()
        hemisphere = data[0]
        algorithm = data[1]

        // policz dzisiejszy księżyc
        val currentYear = LocalDateTime.now().year
        val currentMonth = LocalDateTime.now().monthValue
        val currentDay = LocalDateTime.now().dayOfMonth
        val now: Int
        now = when(algorithm){
            "1" -> simple(currentYear, currentMonth, currentDay)
            "2" -> conway(currentYear, currentMonth, currentDay)
            "3" -> Trig1(currentYear, currentMonth, currentDay)
            "4" -> Trig2(currentYear, currentMonth, currentDay)
            else -> {
                Trig1(currentYear, currentMonth, currentDay)
            }
        }
        val nowStr = now.toBigDecimal().toPlainString()
        val currentText = findViewById<TextView>(R.id.now_percent)
        val setStr = "Dzisiaj: $nowStr%"
        currentText.text = setStr


        val photoName = hemisphere.toLowerCase()
        val way: Char
        val currentPercent: Int
        if (now <= 50){
            currentPercent = now * 2
            way = 'u'
        }
        else{
            currentPercent = (now * 2 - 200) * (-1)
            way = 'd'
        }

        var breakAppeared = false
        val a = listOf(0, -1, 1, -2, 2, -3, 3, -4, 4, -5, 5, -6, 6, -7, 7)
        for(i in a){
            val zfill = (currentPercent + i).toString().padStart(3, '0')
            val name = photoName.plus(zfill).plus(way).plus(".jpg")
            val resId = nameToRes(name)

            if (resId != 0){
                val photo = findViewById<ImageView>(R.id.moon_photo)
                photo.setImageResource(resId)
                breakAppeared = true
                break
            }
        }
        if (!breakAppeared){
            val photo = findViewById<ImageView>(R.id.moon_photo)
            photo.setImageResource(R.drawable.moon)
        }


        val current = LocalDateTime.now()
        for(i in 1..31){
            val iteration = current.plusDays(i.toLong())
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
                val currentText2 = findViewById<TextView>(R.id.nastepna_pelnia)
                val setStr2 = "Następna pełnia: $pelnia"
                currentText2.text = setStr2
                break
            }
        }

        for(i in 1..31){
            val iteration = current.plusDays(i.toLong()*(-1))
            val calculate: Int = when(algorithm){
                "1" -> simple(iteration.year, iteration.monthValue, iteration.dayOfMonth)
                "2" -> conway(iteration.year, iteration.monthValue, iteration.dayOfMonth)
                "3" -> Trig1(iteration.year, iteration.monthValue, iteration.dayOfMonth)
                "4" -> Trig2(iteration.year, iteration.monthValue, iteration.dayOfMonth)
                else -> {
                    Trig1(iteration.year, iteration.monthValue, iteration.dayOfMonth)
                }
            }
            if (calculate == 0){
                val lastNow = iteration.year.toString() +"."+ iteration.monthValue.toString() +"."+ iteration.dayOfMonth.toString()
                val currentText3 = findViewById<TextView>(R.id.poprzedni_now)
                val setStr3 = "Poprzedni nów: $lastNow"
                currentText3.text = setStr3
                break
            }
        }

        var textView = findViewById<TextView>(R.id.hemi)
        textView.text = "Półkula: $hemisphere"
        textView = findViewById<TextView>(R.id.algo)
        textView.text = "Algorytm: $algorithm"

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onResume() {
        super.onResume()

        val file = File(filesDir, "data.txt")
        val readResult = FileInputStream(file).bufferedReader().use { it.readText() }
        val data = readResult.split("\n").toTypedArray()
        hemisphere = data[0]
        algorithm = data[1]

        // policz dzisiejszy księżyc
        val currentYear = LocalDateTime.now().year
        val currentMonth = LocalDateTime.now().monthValue
        val currentDay = LocalDateTime.now().dayOfMonth
        val now: Int
        now = when (algorithm) {
            "1" -> simple(currentYear, currentMonth, currentDay)
            "2" -> conway(currentYear, currentMonth, currentDay)
            "3" -> Trig1(currentYear, currentMonth, currentDay)
            "4" -> Trig2(currentYear, currentMonth, currentDay)
            else -> {
                Trig1(currentYear, currentMonth, currentDay)
            }
        }
        val nowStr = now.toBigDecimal().toPlainString()
        val currentText = findViewById<TextView>(R.id.now_percent)
        val setStr = "Dzisiaj: $nowStr%"
        currentText.text = setStr


        val photoName = hemisphere.toLowerCase()
        val way: Char
        val currentPercent: Int
        if (now <= 50){
            currentPercent = now * 2
            way = 'u'
        }
        else{
            currentPercent = (now * 2 - 200) * (-1)
            way = 'd'
        }

        var breakAppeared = false
        val a = listOf(0, -1, 1, -2, 2, -3, 3, -4, 4, -5, 5, -6, 6, -7, 7)
        for(i in a){
            val zfill = (currentPercent + i).toString().padStart(3, '0')
            val name = photoName.plus(zfill).plus(way).plus(".jpg")
            val resId = nameToRes(name)

            if (resId != 0){
                val photo = findViewById<ImageView>(R.id.moon_photo)
                photo.setImageResource(resId)
                breakAppeared = true
                break
            }
        }
        if (!breakAppeared){
            val photo = findViewById<ImageView>(R.id.moon_photo)
            photo.setImageResource(R.drawable.moon)
        }

        val current = LocalDateTime.now()
        for (i in 1..31) {
            val iteration = current.plusDays(i.toLong())
            val calculate: Int = when(algorithm){
                "1" -> simple(iteration.year, iteration.monthValue, iteration.dayOfMonth)
                "2" -> conway(iteration.year, iteration.monthValue, iteration.dayOfMonth)
                "3" -> Trig1(iteration.year, iteration.monthValue, iteration.dayOfMonth)
                "4" -> Trig2(iteration.year, iteration.monthValue, iteration.dayOfMonth)
                else -> {
                    Trig1(iteration.year, iteration.monthValue, iteration.dayOfMonth)
                }
            }
            if (calculate == 50) {
                val pelnia =
                    iteration.year.toString() + "." + iteration.monthValue.toString() + "." + iteration.dayOfMonth.toString()
                val currentText2 = findViewById<TextView>(R.id.nastepna_pelnia)
                val setStr2 = "Następna pełnia: $pelnia"
                currentText2.text = setStr2
                break
            }
        }

        for (i in 1..31) {
            val iteration = current.plusDays(i.toLong() * (-1))
            val calculate: Int = when(algorithm){
                "1" -> simple(iteration.year, iteration.monthValue, iteration.dayOfMonth)
                "2" -> conway(iteration.year, iteration.monthValue, iteration.dayOfMonth)
                "3" -> Trig1(iteration.year, iteration.monthValue, iteration.dayOfMonth)
                "4" -> Trig2(iteration.year, iteration.monthValue, iteration.dayOfMonth)
                else -> {
                    Trig1(iteration.year, iteration.monthValue, iteration.dayOfMonth)
                }
            }
            if (calculate == 0) {
                val lastNow =
                    iteration.year.toString() + "." + iteration.monthValue.toString() + "." + iteration.dayOfMonth.toString()
                val currentText3 = findViewById<TextView>(R.id.poprzedni_now)
                val setStr3 = "Poprzedni nów: $lastNow"
                currentText3.text = setStr3
                break
            }
        }

        var textView = findViewById<TextView>(R.id.hemi)
        textView.text = "Półkula: $hemisphere"
        textView = findViewById<TextView>(R.id.algo)
        textView.text = "Algorytm: $algorithm"

    }

    private fun nameToRes(name: String): Int {
        when(name){
            "n000d.jpg" -> return R.drawable.n000d
            "n000u.jpg" -> return R.drawable.n000u
            "n001u.jpg" -> return R.drawable.n001u
            "n003d.jpg" -> return R.drawable.n003d
            "n005u.jpg" -> return R.drawable.n005u
            "n007d.jpg" -> return R.drawable.n007d
            "n010u.jpg" -> return R.drawable.n010u
            "n014d.jpg" -> return R.drawable.n014d
            "n018u.jpg" -> return R.drawable.n018u
            "n021d.jpg" -> return R.drawable.n021d
            "n027u.jpg" -> return R.drawable.n027u
            "n030d.jpg" -> return R.drawable.n030d
            "n037u.jpg" -> return R.drawable.n037u
            "n039d.jpg" -> return R.drawable.n039d
            "n048d.jpg" -> return R.drawable.n048d
            "n049u.jpg" -> return R.drawable.n049u
            "n057d.jpg" -> return R.drawable.n057d
            "n059u.jpg" -> return R.drawable.n059u
            "n060u.jpg" -> return R.drawable.n060u
            "n067d.jpg" -> return R.drawable.n067d
            "n071u.jpg" -> return R.drawable.n071u
            "n075d.jpg" -> return R.drawable.n075d
            "n081u.jpg" -> return R.drawable.n081u
            "n083d.jpg" -> return R.drawable.n083d
            "n089u.jpg" -> return R.drawable.n089u
            "n090d.jpg" -> return R.drawable.n090d
            "n095d.jpg" -> return R.drawable.n095d
            "n095u.jpg" -> return R.drawable.n095u
            "n099d.jpg" -> return R.drawable.n099d
            "n099u.jpg" -> return R.drawable.n099u
            "n100d.jpg" -> return R.drawable.n100d
            "n100u.jpg" -> return R.drawable.n100u

            "s000d.jpg" -> return R.drawable.s000d
            "s000u.jpg" -> return R.drawable.s000u
            "s001d.jpg" -> return R.drawable.s001d
            "s003u.jpg" -> return R.drawable.s003u
            "s005d.jpg" -> return R.drawable.s005d
            "s008u.jpg" -> return R.drawable.s008u
            "s011d.jpg" -> return R.drawable.s011d
            "s016u.jpg" -> return R.drawable.s016u
            "s020d.jpg" -> return R.drawable.s020d
            "s025u.jpg" -> return R.drawable.s025u
            "s029d.jpg" -> return R.drawable.s029d
            "s036u.jpg" -> return R.drawable.s036u
            "s039d.jpg" -> return R.drawable.s039d
            "s047u.jpg" -> return R.drawable.s047u
            "s049d.jpg" -> return R.drawable.s049d
            "s058u.jpg" -> return R.drawable.s058u
            "s059d.jpg" -> return R.drawable.s059d
            "s068d.jpg" -> return R.drawable.s068d
            "s069u.jpg" -> return R.drawable.s069u
            "s076d.jpg" -> return R.drawable.s076d
            "s079u.jpg" -> return R.drawable.s079u
            "s084d.jpg" -> return R.drawable.s084d
            "s086u.jpg" -> return R.drawable.s086u
            "s090d.jpg" -> return R.drawable.s090d
            "s093u.jpg" -> return R.drawable.s093u
            "s095d.jpg" -> return R.drawable.s095d
            "s097u.jpg" -> return R.drawable.s097u
            "s098d.jpg" -> return R.drawable.s098d
            "s099u.jpg" -> return R.drawable.s099u
            "s100d.jpg" -> return R.drawable.s100d
            "s100u.jpg" -> return R.drawable.s100u

            else ->{return 0}
        }
    }

}
