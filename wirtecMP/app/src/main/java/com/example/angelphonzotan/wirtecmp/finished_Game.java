package com.example.angelphonzotan.wirtecmp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;

/**
 * Created by Anjoh on 13/03/2018.
 */

public class finished_Game extends AppCompatActivity{
    private MySQLiteHelper db = new MySQLiteHelper(this);
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finishedgame);
        Button b1 = findViewById(R.id.done);
        final TextView tv_name = (TextView) findViewById(R.id.inputname);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User();
                String cDate;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                long currentDate = System.currentTimeMillis();
                cDate=sdf.format(currentDate);
                user.setName(tv_name.getText().toString());
                user.setTime(cDate);
                db.addWinner(user);
                goToMainMenu();
            };
        });
    }

    public void goToMainMenu(){
        finish();
    }
}
