package com.example.criticalroll;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    //Initiate random number generator class.
    public Random rngeezus = new Random();

    //Initiate animation class
    public Animation animation_dice;
    public Animation animation_splash;

    //Initiates sound class.
    public MediaPlayer miss;
    public MediaPlayer hit;
    public MediaPlayer roll;

    //Views
    public ImageView die;
    public ImageView splash;
    public TextView crit;

    //Axis attributes for shake function
    public TextView xText;
    public TextView yText;
    public TextView zText;
    public boolean isAccelerometerAvailable, accelerometerAlreadyInUse  = false;
    public float currentX, currentY, currentZ, lastX, lastY, lastZ;
    public float xDifference, yDifference, zDifference;
    public float shakeThreshold = 5f;
    public Vibrator vibrator;
    public SensorManager sensorManager;
    public Sensor accelerometerSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Image View for with the die
        die = findViewById(R.id.die_face_image);
        die.setImageResource(R.drawable.d20_20);

        //Text View for critical message
        crit = findViewById(R.id.crit_message);
        splash = findViewById(R.id.crit_splash);

        //Shake function attributes
        xText = findViewById(R.id.xText);
        yText = findViewById(R.id.yText);
        zText = findViewById(R.id.zText);

        //Only make text visible for debugging purposes
        xText.setVisibility(View.GONE);
        yText.setVisibility(View.GONE);
        zText.setVisibility(View.GONE);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null){
            accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            isAccelerometerAvailable = true;
        }
        else{
            xText.setText("Accelerometer unavailable");
            isAccelerometerAvailable = false;
        }


        //onClick listener
        die.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animateDice();
                rollDice();
            }
        });
    }

    public void rollDice(){
        int rng = rngeezus.nextInt(20) + 1;

        //Plays a sound
        roll = MediaPlayer.create(this, R.raw.roll);
        roll.start();

        toggleCritMessage(rng);

        switch (rng){
            case 1:
                die.setImageResource(R.drawable.d20_1);
                break;
            case 2:
                die.setImageResource(R.drawable.d20_2);
                break;
            case 3:
                die.setImageResource(R.drawable.d20_3);
                break;
            case 4:
                die.setImageResource(R.drawable.d20_4);
                break;
            case 5:
                die.setImageResource(R.drawable.d20_5);
                break;
            case 6:
                die.setImageResource(R.drawable.d20_6);
                break;
            case 7:
                die.setImageResource(R.drawable.d20_7);
                break;
            case 8:
                die.setImageResource(R.drawable.d20_8);
                break;
            case 9:
                die.setImageResource(R.drawable.d20_9);
                break;
            case 10:
                die.setImageResource(R.drawable.d20_10);
                break;
            case 11:
                die.setImageResource(R.drawable.d20_11);
                break;
            case 12:
                die.setImageResource(R.drawable.d20_12);
                break;
            case 13:
                die.setImageResource(R.drawable.d20_13);
                break;
            case 14:
                die.setImageResource(R.drawable.d20_14);
                break;
            case 15:
                die.setImageResource(R.drawable.d20_15);
                break;
            case 16:
                die.setImageResource(R.drawable.d20_16);
                break;
            case 17:
                die.setImageResource(R.drawable.d20_17);
                break;
            case 18:
                die.setImageResource(R.drawable.d20_18);
                break;
            case 19:
                die.setImageResource(R.drawable.d20_19);
                break;
            case 20:
                die.setImageResource(R.drawable.d20_20);
                break;
        }
    }

    //Only display the message if the dice is 1 or 20.
    public void toggleCritMessage(int toggle){
        if (toggle == 1){
            splash.setImageResource(R.drawable.crit_miss_splash);
            animateCritSplash();

            //Plays a sound
            miss =  MediaPlayer.create(this, R.raw.miss);
            miss.start();
        }
        else if (toggle == 20){
            splash.setImageResource(R.drawable.crit_hit_splash);
            animateCritSplash();

            //Plays a sound
            hit = MediaPlayer.create(this, R.raw.hit);
            hit.start();
        }
        else{
            splash.setImageResource(android.R.color.transparent);
        }
    }

    //Logic for Accelerometer sensor.
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        xText.setText(sensorEvent.values[0]+"m/s2");
        yText.setText(sensorEvent.values[1]+"m/s2");
        zText.setText(sensorEvent.values[2]+"m/s2");

        currentX = sensorEvent.values[0];
        currentY = sensorEvent.values[1];
        currentZ = sensorEvent.values[2];

        if (accelerometerAlreadyInUse == true){
            xDifference = Math.abs(lastX - currentX);
            yDifference = Math.abs(lastY - currentY);
            zDifference = Math.abs(lastZ - currentZ);

            if ((xDifference > shakeThreshold && yDifference > shakeThreshold) ||
                    (xDifference > shakeThreshold && zDifference > shakeThreshold) ||
                    (yDifference > shakeThreshold && zDifference > shakeThreshold)){

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                    vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                    //Rolls dice upon shaking the phone.
                    rollDice();
                    animateDice();
                }
                else {
                    //Deprecated in API 26.
                    vibrator.vibrate(500);

                    //Rolls dice upon shaking the phone.
                    rollDice();
                    animateDice();
                }
            }
        }

        lastX = currentX;
        lastY = currentY;
        lastZ = currentZ;

        accelerometerAlreadyInUse = true;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    //Allows the app to use the shake function upon opening again.
    @Override
    public void onResume(){
        super.onResume();

        if (isAccelerometerAvailable == true)
            sensorManager.registerListener(this, accelerometerSensor, sensorManager.SENSOR_DELAY_NORMAL);
    }

    //Prevents phone from shaking when app is closed.
    @Override
    public void onPause(){
        super.onPause();

        if (isAccelerometerAvailable == false)
            sensorManager.unregisterListener(this);
    }

    //Animate the die on click or shake.
    public void animateDice(){
        animation_dice = AnimationUtils.loadAnimation(this, R.anim.rotate);

        die.startAnimation(animation_dice);
    }

    public void animateCritSplash(){
        animation_splash = AnimationUtils.loadAnimation(this, R.anim.bounce);

        splash.startAnimation(animation_splash);
    }

    public void soundEffect(){
    }
}