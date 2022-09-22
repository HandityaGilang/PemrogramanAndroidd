package com.example.clickrush

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    var gamemulai = false
    internal lateinit var countdown : CountDownTimer
    internal var mulaicountdown : Long = 60000
    var count = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        gamereset()
    }
    private fun gamereset(){
        count = 0
        val textscore = findViewById(R.id.Score) as TextView
        val textwaktu = findViewById(R.id.Waktu) as TextView
        textscore.text="Your Score:$count"
        val mulaiwaktucountdown = mulaicountdown / 1000
        textwaktu.text = "Time Left:$mulaiwaktucountdown"
        countdown = object: CountDownTimer(60000,1000){
            override fun onTick(millisUntilFinished: Long) {
                textwaktu.setText("Time Left:"+millisUntilFinished / 1000)
            }
            override fun onFinish() {
                gameselesai()
            }
        }
        gamemulai = false
    }
    fun startgame(){
        countdown.start()
        gamemulai = true
    }

    fun gameselesai(){
        var tex1 = getString(R.string.game_Selesai)
        var tex2 = count.toString()
        Toast.makeText(this,"$tex1$tex2",Toast.LENGTH_SHORT).show()
        gamereset()
    }

    fun onTap(view: View) {
        if (!gamemulai){
            startgame()
        }
        count++
        val textscore = findViewById(R.id.Score) as TextView
        textscore.text="Your Score:$count"

    }
}