package com.example.lab5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    //var mySensor :MySensor? = null //odkomentowac do zadania nr 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //do zadania nr 1
        //rozciągnięcie płótna na całą aktywność
        setContentView(MyGLSurfaceView(applicationContext))

        /* do zadania nr 2
        //do testów przywrócenie fasady zdefiniowanej w xmlu
        setContentView(R.layout.activity_main)
        //nowy obiekt obsługujący sensor
        mySensor=MySensor(this)
        */

    }
}