package com.example.clickrush

import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {

    var gamemulai = false
    internal lateinit var countdown : CountDownTimer
    internal var mulaicountdown : Long = 60000

    var count = 0
    internal val TAG =MainActivity::class.java.simpleName
    internal var timeleftontimer : Long = 60000

    companion object{
        private val SCORE_KEY = "SCORE_KEY"
        private val TIME_LEFT_KEY = "TIME_LEFT_KEY"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG,"onCreate called. Score is $count")

        val btnmasuk = findViewById<Button>(R.id.btnmasuk)

        if(savedInstanceState != null){
            count = savedInstanceState.getInt(SCORE_KEY)
            timeleftontimer = savedInstanceState.getLong(TIME_LEFT_KEY)
            restoregame()
        }else{
            gamereset()
        }
        btnmasuk.setOnClickListener {view ->
            val animasi = AnimationUtils.loadAnimation(this,R.anim.bounce)
            view.startAnimation(animasi)
            scorejalan()

        }

    }
    private fun restoregame(){
        val textscore = findViewById(R.id.Score) as TextView
        val textwaktu = findViewById(R.id.Waktu) as TextView
        textscore.text="Your Score:${count.toString()}"
        val restoretime = timeleftontimer/1000
        textwaktu.text= "Time Left :${restoretime.toString()}"

        countdown = object :CountDownTimer(timeleftontimer,1000){
            override fun onTick(millisUntilFinished: Long) {
                timeleftontimer = millisUntilFinished
                textwaktu.text = "Time Left:"+millisUntilFinished / 1000
            }

            override fun onFinish() {
                gameselesai()
            }
        }
        countdown.start()
        gamemulai=true

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(SCORE_KEY,count)
        outState.putLong(TIME_LEFT_KEY,timeleftontimer)
        countdown.cancel()
        Log.d(TAG,"onSaveInstanceState : Saving score: $count, Time left : $timeleftontimer")
    }


    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG,"onDestroy execute")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.About){
            ShowAbout()
        }
        return true
    }

    private fun ShowAbout(){
        val isijudulVersi = getString(R.string.about_title) + BuildConfig.VERSION_NAME
        val isiTulisan = getString(R.string.about_text,)
        val isibuild = AlertDialog.Builder(this)
        isibuild.setTitle(isijudulVersi)
        isibuild.setMessage(isiTulisan)
        isibuild.create().show()
    }

    private fun gamereset(){
        val textscore = findViewById(R.id.Score) as TextView
        val textwaktu = findViewById(R.id.Waktu) as TextView
        count = 0
        textscore.text="Your Score:$count"
        val mulaiwaktucountdown = mulaicountdown / 1000
        textwaktu.text = "Time Left:$mulaiwaktucountdown"
        countdown = object: CountDownTimer(60000,1000){
            override fun onTick(millisUntilFinished: Long) {
                timeleftontimer = millisUntilFinished
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

    fun scorejalan() {
        if (!gamemulai){
            startgame()
        }
        count++
        val textscore = findViewById(R.id.Score) as TextView
        textscore.text="Your Score:$count"

    }
}