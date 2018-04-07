package com.example.angelphonzotan.wirtecmp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Anjoh on 13/03/2018.
 */

public class Game_Over extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameover);
        Button b = findViewById(R.id.tryagain);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToFirstQuestion();

            };
        });
        Button b1 = findViewById(R.id.mainmenu);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMainMenu();

            };
        });

    }

    public void goToFirstQuestion(){
        Intent i = new Intent(this,startGame.class);
        startActivity(i);
            finish();
    }

    public void goToMainMenu(){
        finish();
    }
}
