package ca.rjrnaik.stopwatchapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.util.Locale
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    private var sec : Int = 0
    private var isRunning : Boolean = false
    private var wasRunning : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState != null) {
            sec = savedInstanceState.getInt("sec")
            isRunning = savedInstanceState.getBoolean("isRunning")
            wasRunning = savedInstanceState.getBoolean("wasRunning")
        }
        startTimer()
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
            super.onSaveInstanceState(savedInstanceState)
            savedInstanceState.putInt("sec", sec)
            savedInstanceState.putBoolean("isRunning", isRunning)
            savedInstanceState.putBoolean("wasRunning", wasRunning)

    }

    fun btnStartClick(view: View) {
        isRunning = true
        Toast.makeText(this, "Timer started", Toast.LENGTH_SHORT).show()
    }
    fun btnStopClick(view: View) {
        isRunning = false
        Toast.makeText(this, "Timer stopped", Toast.LENGTH_SHORT).show()
    }
    fun btnResetClick(view: View) {
        isRunning = false
        sec = 0
        Toast.makeText(this, "Timer reset", Toast.LENGTH_SHORT).show()
    }
    fun btnCloseClick(view: View) {
        finish()
        exitProcess(0)
    }


    override fun onPause() {
        super.onPause()
        wasRunning = isRunning
        isRunning = false
    }

    override fun onResume() {
        super.onResume()
        if (wasRunning) {
            isRunning = true
        }
    }



    private fun startTimer() {
        val textViewTime: TextView = findViewById(R.id.textViewTime)
        val handler : Handler = Handler()
        handler.post(object :Runnable {
            override fun run(){
                val hours = sec / 3600
                val minutes = (sec % 3600) / 60
                val secs = sec % 60

                val time = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, secs)
                textViewTime.text = time

                if (isRunning) {
                    sec++
                }

                handler.postDelayed(this, 1000)
            }
        })
    }
}