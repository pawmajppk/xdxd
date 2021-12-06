package com.example.lab5

import android.R.attr
import android.opengl.GLSurfaceView
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10
import android.opengl.GLU
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import android.R.attr.radius

import android.R.attr.y

import android.R.attr.x
import android.opengl.GLES10.glViewport
import android.util.DisplayMetrics
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin


/* Implementacja renderera - malarza na płótnie */

class MyRenderer : /*dziedziczenie*/GLSurfaceView.Renderer {
    private var angle=0f; //kąt do obracania figury na płótnie
    private var x = 0.0f;
    var widthW = 0
    var heightH = 0
    var velocity = 0.005f;


    //funkcja wywoływana przy tworzeniu płótna
    override fun onSurfaceCreated(gl: GL10?, p1: EGLConfig?) {

    }

    //funkcja wywoływana przy zmianie i tworzeniu płótna
    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        /* OpenGL jest silnikiem z implementacją nieobiektową, dlatego jego funkcje dostępne są
        w wraperach w obiekcie gl
         */
        gl?.glMatrixMode(GL10.GL_PROJECTION)
        gl?.glLoadIdentity()
        widthW = width
        heightH = height
        var ratio : Float= widthW.toFloat() / heightH.toFloat();

        gl?.glOrthof(-1.0f*ratio,1.0f*ratio,-1.0f,1.0f,-1.0f*2,1.0f*2)
        //konfiguracja rzutni
        gl?.glViewport(0, 0, width, height)
        //załadowanie jednostkowej macierzy jako macierzy projekcji
        gl?.glMatrixMode(GL10.GL_MODELVIEW)
        gl?.glLoadIdentity()
        //konfiguracja perspektywy, korzystamy z GLU czyli narzędzi OpenGL
        GLU.gluPerspective(gl, 45.0f, width.toFloat() / height.toFloat(), -1.0f, -10.0f)
        //ustawienie domyslnego koloru w modelu barw RGBA
        gl?.glClearColor(0.0f, 0.0f, 1.0f, 1.0f)
    }

    //funkcja wywolywana w pętli odpowiedzialna za renederowanie (malowanie) płótna
    override fun onDrawFrame(gl: GL10?) {
        //wierchołki trójkąta w 3D

        val displayMetrics = DisplayMetrics()

        var ratio : Float= widthW.toFloat() / heightH.toFloat();
        var xInit = 0.0.toFloat();

        val points = 40;
        val vertices = FloatArray((points +2) *3)
        val vertilesAmount = (points+2)*3
        val radius = 0.2.toFloat();
        val twicePi = (2.0*PI).toFloat();
        vertices[0]= 0.0.toFloat()
        vertices[1] = 0.0.toFloat()
         vertices[2] = 0.0.toFloat()
        for(i in 1..points+1 )
        {
            vertices[i*3+0]= radius*cos(i*twicePi/points)
            vertices[i*3+1]= radius*sin(i*twicePi/points)
            vertices[i*3+2]= 0.0.toFloat();
        }

        //wierchołki trzeba przekazać poza JVM więc trzeba zaalokować bufor
        //3 - wierzchołki, 3 - wymiary 4 - bajty na float
        val buffer: ByteBuffer = ByteBuffer.allocateDirect(vertilesAmount * 3 * 4)
        //natywny dla sprzętu układ bajtów w słowie maszynowym
        buffer.order(ByteOrder.nativeOrder())
        //do tego bufora chcemy się odwoływać jak do bufora floatów
        val verticesBuffer: FloatBuffer = buffer.asFloatBuffer()
        //wrzucamy wierzchołki do bufora poza JVM
        verticesBuffer.put(vertices)
        //ustawiamy pozycję w buforze
        verticesBuffer.position(0)
        //konfiguracja bufora kolorów i bufora głębi
        gl?.glClear(GL10.GL_COLOR_BUFFER_BIT or GL10.GL_DEPTH_BUFFER_BIT)
        //ustawienie koloru malowania i wypełniania
        gl?.glColor4f(1.0f, 0.0f, 0.0f, 1.0f)
        //załadowanie macierzy jednostkowej do macierzy transformacji
        gl?.glLoadIdentity()
        //obrót figury tj. wymnożenie macierzy transformacji
        //gl?.glRotatef(angle++ /*kąt obrotu*/, 0.0f, 0.0f, 1.0f /*oś obrotu*/)
        gl?.glTranslatef(x,0.0.toFloat(),0.0.toFloat());
        if((x+velocity+radius*3<=1.0f&&velocity>=0) ||(x+velocity-radius*3>=-1.0f&&velocity<=0)  ) {
            x = x + velocity;
        }
        else {
            velocity  = -1.0f*velocity;
        }
        //przechodzimy do stanu przekazania wierzchołków silnikowi OpenGL
        gl?.glEnableClientState(GL10.GL_VERTEX_ARRAY)
        //przekazujemy do silnika informację o wierzchołkach
        gl?.glVertexPointer(3, GL10.GL_FLOAT, 0, verticesBuffer)
        //zlecamy namalowanie trójkąta
        gl?.glDrawArrays(GL10.GL_TRIANGLE_FAN, 0 /*początek*/, vertilesAmount/*liczba wierz.*/)


        //opuszczamy ten stan
        gl?.glDisableClientState(GL10.GL_VERTEX_ARRAY)
    }
}