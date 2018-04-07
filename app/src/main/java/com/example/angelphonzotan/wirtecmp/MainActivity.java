package com.example.angelphonzotan.wirtecmp;

import android.content.Context;
import android.content.Intent;
import android.hardware.SensorManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    //AudioManager am = new AudioManager();
    ImageView dice_picture;     //reference to dice picture
    Random rng = new Random();    //generate random numbers
    SoundPool dice_sound;       //For dice sound playing
    int sound_id;               //Used to control sound stream return by SoundPool
    Handler handler;            //Post message to start roll
    Timer timer = new Timer();    //Used to implement feedback to user
    boolean rolling = false;      //Is die rolling?
    private SensorManager sm;
    private float aCelVal; //current accerleration
    private float aCelLast;
    private float shake;
    TextView sample;
    public MySQLiteHelper db = new MySQLiteHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.firstscreen);
        dbManipulate();
        //Get a reference to image widget
       // dice_picture = (ImageView) findViewById(R.id.imageView);
       // dice_picture.setOnClickListener(new HandleClick());
        //link handler to callback
        handler = new Handler(callback);
        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        //sm.registerListener(sensorListener, sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        Button b = findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               goToFirstQuestion();
            };
        });
        b = findViewById(R.id.button2);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToHall();
            };
        });
        Button c = findViewById(R.id.button3);
        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToRules();
            };
        });
        aCelVal = SensorManager.GRAVITY_EARTH;
        aCelLast = SensorManager.GRAVITY_EARTH;
        shake = 0.00f;

        //prepareUserData();

    }

    private void dbManipulate() {
        db.onUpgrade(db.getWritableDatabase(), 1,2);
        Log.d("MainActivity","dbManipulate-START");
        //create question
        Question question;
        question = new Question("Yes","2","Yes","No","Maybe","HELLO","Is This A GAME?");
        db.addQuestion(question);
        question = new Question("0","2","35","0","46","50","How many animals are there in Moses' Ark?");
        db.addQuestion(question);
        question = new Question("Not an Animal", "2", "Fish", "Mermaid", "Swordfish", "Human", "In the fairy tail Mermaid Princess, what animal is Arial?");
        db.addQuestion(question);
        question = new Question("TRUE","1","TRUE","FALSE","","","If 8+2=16106, then is 9+4=36135 ?");
        db.addQuestion(question);
        question = new Question("Pork","2","Cow","Pork","Wild Pig","Pig","If fish is to fish and chicken is to chicken then pig is to?");
        db.addQuestion(question);
        question = new Question("Boilers","2","Hamsters","Captains","Boilers","God","Is the Titanic run by?");
        db.addQuestion(question);
        dbuserpopulate();
        //question new Quetion(asdvnbahjdbas)
        //add to databaseq
        //ex:db.addBook(question));
        Log.d("MainActivity","dbManipulate-END");
    }

    private void dbuserpopulate() {
        User user;
        user = new User("alvin", "10");
        db.addWinner(user);
        user = new User("alvin", "10");
        db.addWinner(user);
        user = new User("alvin", "10");
        db.addWinner(user);
        user = new User("alvin", "10");
        db.addWinner(user);
        user = new User("alvin", "10");
        db.addWinner(user);
        user = new User("alvin", "10");
        db.addWinner(user);
        user = new User("alvin", "10");
        db.addWinner(user);
        user = new User("alvin", "10");
        db.addWinner(user);
        user = new User("alvin", "10");
        db.addWinner(user);
    }
    /*private final SensorEventListener sensorListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {

            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];

            aCelLast = aCelVal;
            aCelVal = (float) Math.sqrt((double) (x*x + y*y + z*z));
            float delta = aCelVal - aCelLast;
            shake = shake * 0.9f + delta;

            if(shake > 12){
                rolling = true;
                //Show rolling image
                dice_picture.setImageResource(R.mipmap.dice3d);
                //Start rolling sound
                dice_sound.play(sound_id, 1.0f, 1.0f, 0, 0, 1.0f);
                //Pause to allow image to update
                timer.schedule(new Roll(), 400);
            }

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };*/

    //User pressed dice, lets start
    private class HandleClick implements View.OnClickListener {
        public void onClick(View arg0) {
            if (!rolling) {
                rolling = true;
                //Show rolling image
                dice_picture.setImageResource(R.mipmap.dice3d);
                //Start rolling sound
                dice_sound.play(sound_id, 1.0f, 1.0f, 0, 0, 1.0f);
                //Pause to allow image to update
                timer.schedule(new Roll(), 400);
            }
        }

    }

    //When pause completed message sent to callback
    class Roll extends TimerTask {
        public void run() {
            handler.sendEmptyMessage(0);
        }
    }

    //Receives message from timer to start dice roll
    Handler.Callback callback = new Handler.Callback() {
        public boolean handleMessage(Message msg) {
            //Get roll result
            //Remember nextInt returns 0 to 5 for argument of 6
            //hence + 1
            switch (rng.nextInt(6) + 1) {
                case 1:
                    dice_picture.setImageResource(R.mipmap.diceone);
                    sample  = (TextView) findViewById(R.id.secret);
                    sample.setText("ONE");
                    break;
                case 2:
                    dice_picture.setImageResource(R.mipmap.dicetwo);
                    sample  = (TextView) findViewById(R.id.secret);
                    sample.setText("TWO");
                    break;
                case 3:
                    dice_picture.setImageResource(R.mipmap.dicethree);
                    sample  = (TextView) findViewById(R.id.secret);
                    sample.setText("THREE");
                    break;
                case 4:
                    dice_picture.setImageResource(R.mipmap.dicefour);
                    sample  = (TextView) findViewById(R.id.secret);
                    sample.setText("FOUR");
                    break;
                case 5:
                    dice_picture.setImageResource(R.mipmap.dicefive);
                    sample  = (TextView) findViewById(R.id.secret);
                    sample.setText("FIVE");
                    break;
                case 6:
                    dice_picture.setImageResource(R.mipmap.dicesix);
                    sample  = (TextView) findViewById(R.id.secret);
                    sample.setText("SIX");

                    break;
                default:
            }
            rolling = false;  //user can press again
            return true;
        }
    };

    //Clean up
    protected void onPause() {
        super.onPause();
    }

    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }

    protected void goToFirstQuestion(){
        Intent i = new Intent(this,startGame.class);
        startActivity(i);
    }
    protected void goToHall(){
        Intent i = new Intent(this,hallOfFame.class);
        startActivity(i);
    }
    protected void goToRules(){
        Intent i = new Intent(this,Rules.class);
        startActivity(i);
    }
}
