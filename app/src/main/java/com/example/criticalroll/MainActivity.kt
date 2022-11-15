package com.example.criticalroll

import android.hardware.Sensor
import androidx.appcompat.app.AppCompatActivity
import android.hardware.SensorEventListener
import android.view.animation.Animation
import android.media.MediaPlayer
import android.widget.TextView
import android.os.Vibrator
import android.hardware.SensorManager
import android.os.Bundle
import android.hardware.SensorEvent
import android.os.VibrationEffect
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import java.util.*

class MainActivity : AppCompatActivity(), SensorEventListener {
    //Initiate random number generator class.
    private var rngeezus = Random()

    //Initiate animation class
    private var animation_dice: Animation? = null
    private var animation_splash: Animation? = null

    //Initiates sound class.
    private var miss: MediaPlayer? = null
    private var hit: MediaPlayer? = null
    private var roll: MediaPlayer? = null

    //Views
    private var die: ImageView? = null
    private var splash: ImageView? = null
    var crit: TextView? = null

    //Axis attributes for shake function
    var xText: TextView? = null
    var yText: TextView? = null
    var zText: TextView? = null
    var isAccelerometerAvailable = false
    var accelerometerAlreadyInUse = false
    var currentX = 0f
    var currentY = 0f
    var currentZ = 0f
    var lastX = 0f
    var lastY = 0f
    var lastZ = 0f
    var xDifference = 0f
    var yDifference = 0f
    var zDifference = 0f
    var shakeThreshold = 5f
    var vibrator: Vibrator? = null
    var sensorManager: SensorManager? = null
    var accelerometerSensor: Sensor? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Sound
        roll = MediaPlayer.create(this, R.raw.roll)
        miss = MediaPlayer.create(this, R.raw.miss)
        hit = MediaPlayer.create(this, R.raw.hit)

        //Image View for with the die
        die = findViewById(R.id.die_face_image)
        die?.setImageResource(R.drawable.d20_20)

        //Text View for critical message
        crit = findViewById(R.id.crit_message)
        splash = findViewById(R.id.crit_splash)

        //Shake function attributes
        xText = findViewById(R.id.xText)
        yText = findViewById(R.id.yText)
        zText = findViewById(R.id.zText)

        //Only make text visible for debugging purposes
        xText?.setVisibility(View.GONE)
        yText?.setVisibility(View.GONE)
        zText?.setVisibility(View.GONE)
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        vibrator = getSystemService(VIBRATOR_SERVICE) as Vibrator
        if (sensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
            accelerometerSensor = sensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
            isAccelerometerAvailable = true
        } else {
            xText?.setText("Accelerometer unavailable")
            isAccelerometerAvailable = false
        }


        //onClick listener
        die?.setOnClickListener(View.OnClickListener {
            animateDice()
            rollDice()
        })
    }

    fun rollDice() {
        val rng = rngeezus.nextInt(20) + 1

        //Plays a sound
        roll!!.start()
        roll!!.seekTo(0)
        toggleCritMessage(rng)
        when (rng) {
            1 -> die!!.setImageResource(R.drawable.d20_1)
            2 -> die!!.setImageResource(R.drawable.d20_2)
            3 -> die!!.setImageResource(R.drawable.d20_3)
            4 -> die!!.setImageResource(R.drawable.d20_4)
            5 -> die!!.setImageResource(R.drawable.d20_5)
            6 -> die!!.setImageResource(R.drawable.d20_6)
            7 -> die!!.setImageResource(R.drawable.d20_7)
            8 -> die!!.setImageResource(R.drawable.d20_8)
            9 -> die!!.setImageResource(R.drawable.d20_9)
            10 -> die!!.setImageResource(R.drawable.d20_10)
            11 -> die!!.setImageResource(R.drawable.d20_11)
            12 -> die!!.setImageResource(R.drawable.d20_12)
            13 -> die!!.setImageResource(R.drawable.d20_13)
            14 -> die!!.setImageResource(R.drawable.d20_14)
            15 -> die!!.setImageResource(R.drawable.d20_15)
            16 -> die!!.setImageResource(R.drawable.d20_16)
            17 -> die!!.setImageResource(R.drawable.d20_17)
            18 -> die!!.setImageResource(R.drawable.d20_18)
            19 -> die!!.setImageResource(R.drawable.d20_19)
            20 -> die!!.setImageResource(R.drawable.d20_20)
        }
    }

    //Only display the message if the dice is 1 or 20.
    fun toggleCritMessage(toggle: Int) {
        if (toggle == 1) {
            splash!!.setImageResource(R.drawable.crit_miss_splash)
            animateCritSplash()

            //Plays a sound
            miss!!.start()
            miss!!.seekTo(0)
        } else if (toggle == 20) {
            splash!!.setImageResource(R.drawable.crit_hit_splash)
            animateCritSplash()

            //Plays a sound
            hit!!.start()
            hit!!.seekTo(0)
        } else {
            splash!!.setImageResource(android.R.color.transparent)
        }
    }

    //Logic for Accelerometer sensor.
    override fun onSensorChanged(sensorEvent: SensorEvent) {
        xText!!.text = sensorEvent.values[0].toString() + "m/s2"
        yText!!.text = sensorEvent.values[1].toString() + "m/s2"
        zText!!.text = sensorEvent.values[2].toString() + "m/s2"
        currentX = sensorEvent.values[0]
        currentY = sensorEvent.values[1]
        currentZ = sensorEvent.values[2]
        if (accelerometerAlreadyInUse) {
            xDifference = Math.abs(lastX - currentX)
            yDifference = Math.abs(lastY - currentY)
            zDifference = Math.abs(lastZ - currentZ)
            if (xDifference > shakeThreshold && yDifference > shakeThreshold ||
                    xDifference > shakeThreshold && zDifference > shakeThreshold ||
                    yDifference > shakeThreshold && zDifference > shakeThreshold) {
                vibrator!!.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
                //Rolls dice upon shaking the phone.
                rollDice()
                animateDice()
            }
        }
        lastX = currentX
        lastY = currentY
        lastZ = currentZ
        accelerometerAlreadyInUse = true
    }

    override fun onAccuracyChanged(sensor: Sensor, i: Int) {}

    //Allows the app to use the shake function upon opening again.
    public override fun onResume() {
        super.onResume()
        if (isAccelerometerAvailable) sensorManager!!.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    //Prevents phone from shaking when app is closed.
    public override fun onPause() {
        super.onPause()
        if (isAccelerometerAvailable) sensorManager!!.unregisterListener(this)

        //Pauses sound when app is in background
        miss!!.pause()
        hit!!.pause()
        roll!!.pause()
    }

    //Animate the die on click or shake.
    fun animateDice() {
        animation_dice = AnimationUtils.loadAnimation(this, R.anim.rotate)
        die!!.startAnimation(animation_dice)
    }

    fun animateCritSplash() {
        animation_splash = AnimationUtils.loadAnimation(this, R.anim.bounce)
        splash!!.startAnimation(animation_splash)
    }
}