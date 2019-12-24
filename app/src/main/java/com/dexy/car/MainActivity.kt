package com.dexy.car

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), OnTextCallback {

    val car = Car()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        car.setCallback(this)

        turn_on_engine.setOnClickListener { car.TurnOnEngine() }
        turn_off_engine.setOnClickListener { car.TurnOffEngine() }

        turn_on_brake.setOnClickListener { car.TurnOnParkingBrake() }
        turn_off_brake.setOnClickListener { car.TurnOffParkingBrake() }

        set_speed.setOnClickListener {
            val speed = edit_text_speed.text.toString().toInt()
            car.SetSpeed(speed)
        }

        set_gear.setOnClickListener {
            val gear = edit_text_gear.text.toString().toInt()
            car.SetGear(gear)
        }

        get_info.setOnClickListener { info.text = car.toString() }
    }

    override fun onText(value: String) {
        info.text = value
    }
}
