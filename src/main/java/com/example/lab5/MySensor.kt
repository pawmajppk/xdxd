package com.example.lab5

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.widget.TextView


/* Przykład obsługi czujnika - żyroskop */

class MySensor(/*konstr. podst.*/val activity: MainActivity) :/*interf. sensora*/SensorEventListener {
    val label:TextView //etykieta w ktorej pomiar bedzie wyswietlany
        get() { // wlasny getter aby pobrac uchwyt do etykiety z fasady
            return activity.findViewById<TextView>(R.id.label)
        }

    //cialo konstruktora podstawowego
    init {
        //pobranie usługi zarządzającej sensorami
        val sensorService = activity.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        //pobranie listy wybranych sensorów - tu żyroskopów
        val sensorList = sensorService.getSensorList(Sensor.TYPE_GYROSCOPE)
        //pobranie pierwszego z listy sensora
        val thermometer = sensorList[0]
        //rejestracja obiektu tej klasy jako odbiornika zdarzeń - pomiarów z sensora
        sensorService.registerListener(this,thermometer,SensorManager.SENSOR_DELAY_NORMAL)
    }

    //funkcja wywolywana za kazdym razem gdy przyjdzie nowy pomiar
    override fun onSensorChanged(event: SensorEvent?) {
        //wstawienie wartosci pomiaru do etykiety
        label.text=event?.values?.get(0)?.toString()
        //pobieram z wektora (tablicy) tylko jeden wymiar (w rad/s) dla przykładu
        //proszę zwrócić uwagę na label.text - wykorzystuję domyślny setter oraz operatory ?.
    }

    //funkcja wywolywana za kazdym razem gdy zmieni się dokładność - nie potrzebna w projekcie teraz
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }
}