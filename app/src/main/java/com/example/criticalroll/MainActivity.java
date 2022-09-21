package com.example.criticalroll;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    //Initiate random number generator class.
    Random rngeezus = new Random();

    //Views
    ImageView die;
    TextView crit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Image View for with the die
        die = findViewById(R.id.die_face_image);
        die.setImageResource(R.drawable.d20_20);

        //Text View for critical message
        crit = findViewById(R.id.crit_message);

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

        toggleCritMessage(rng);

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
            case 7:
                die.setImageResource(R.drawable.dice5);
                break;
            case 8:
                die.setImageResource(R.drawable.dice5);
                break;
            case 9:
                die.setImageResource(R.drawable.dice5);
                break;
            case 10:
                die.setImageResource(R.drawable.dice5);
                break;
            case 11:
                die.setImageResource(R.drawable.dice5);
                break;
            case 12:
                die.setImageResource(R.drawable.dice5);
                break;
            case 13:
                die.setImageResource(R.drawable.dice5);
                break;
            case 14:
                die.setImageResource(R.drawable.dice5);
                break;
            case 15:
                die.setImageResource(R.drawable.dice5);
                break;
            case 16:
                die.setImageResource(R.drawable.dice5);
                break;
            case 17:
                die.setImageResource(R.drawable.dice5);
                break;
            case 18:
                die.setImageResource(R.drawable.dice5);
                break;
            case 19:
                die.setImageResource(R.drawable.dice5);
                break;
            case 20:
                die.setImageResource(R.drawable.dice5);
                break;
        }
    }

    //Only display the message if the dice is 1 or 20.
    public void toggleCritMessage(int toggle){
        if (toggle == 1){
            crit.setText(getString(R.string.crit_miss));
            crit.setVisibility(View.VISIBLE);
        }
        else if (toggle == 20){
            crit.setText(getString(R.string.crit_hit));
            crit.setVisibility(View.VISIBLE);
        }
        else{
            crit.setVisibility(View.INVISIBLE);
        }
    }

}