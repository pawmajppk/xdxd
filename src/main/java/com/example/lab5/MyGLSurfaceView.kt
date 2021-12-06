package com.example.lab5

import android.content.Context
import android.opengl.GLSurfaceView

/* Implementacja płótna */

class MyGLSurfaceView(context: Context?) :GLSurfaceView(context) {
    init {
        //ustawienie renderera (malarza) w ciele konstruktora podstawowego
        setRenderer(MyRenderer())
    }
}