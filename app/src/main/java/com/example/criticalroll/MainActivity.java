package com.example.criticalroll;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    //Initiate random number generator class.
    Random rngeezus = new Random();

    ImageView die;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Image View for with the die
        die = findViewById(R.id.die_face_image);
        die.setImageResource(R.drawable.dice1);

        //onClick listener
        die.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rollDice();
            }
        });
    }

    public void rollDice(){
        int rng = rngeezus.nextInt(6) + 1;

        switch (rng){
            case 1:
                die.setImageResource(R.drawable.dice1);
                break;
            case 2:
                die.setImageResource(R.drawable.dice2);
                break;
            case 3:
                die.setImageResource(R.drawable.dice3);
                break;
            case 4:
                die.setImageResource(R.drawable.dice4);
                break;
            case 5:
                die.setImageResource(R.drawable.dice5);
                break;
            case 6:
                die.setImageResource(R.drawable.dice6);
                break;
        }
    }
}