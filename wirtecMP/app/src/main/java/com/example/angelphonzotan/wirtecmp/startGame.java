package com.example.angelphonzotan.wirtecmp;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Anjoh on 12/03/2018.
 */

public class startGame extends AppCompatActivity implements SensorEventListener {
    private int questionNo = 1;
    private ArrayList<Question> questions = new ArrayList<>();
    private Question currentQ;
    private int lives;
    private int skips;
    private SensorManager sensorMan;
    private Sensor accelerometer;
    private float[] mGravity;
    private float mAccel;
    private float mAccelCurrent;
    private float mAccelLast;
    private MySQLiteHelper db = new MySQLiteHelper(this);
    private int numQuestions;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.startgame);

        lives = 3;
        skips = 3;
        sensorMan = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorMan.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mAccel = 0.00f;
        mAccelCurrent = SensorManager.GRAVITY_EARTH;
        mAccelLast = SensorManager.GRAVITY_EARTH;

        prepareQuestions();
    }


    @Override
    public void onResume() {
        super.onResume();
        sensorMan.registerListener((SensorEventListener) this, accelerometer,
                SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorMan.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            mGravity = event.values.clone();
            // Shake detection
            float x = mGravity[0];
            float y = mGravity[1];
            float z = mGravity[2];
            mAccelLast = mAccelCurrent;
            mAccelCurrent = (float) Math.sqrt(x * x + y * y + z * z);
            float delta = mAccelCurrent - mAccelLast;
            mAccel = mAccel * 0.9f + delta;
            // Make this higher or lower according to how much
            // motion you want to detect
            if (mAccel > 6) {

                //goToNext(0);
            }
        }

    }

    public void onAccuracyChanged(Sensor sensor, int i) {

    }


    private void prepareQuestions() {
        getAllQuestion();
        numQuestions = questions.size();
        Random rand = new Random();
        int randInt = rand.nextInt(questions.size());
        setUpUi(randInt);
    }

    private void nextQuestion() {
        Random rand = new Random();
        int randInt = rand.nextInt(questions.size());
        setUpUi(randInt);
    }

    private void setUpUi(int rand) {
        TextView tv_questionNo = (TextView) findViewById(R.id.questionNo);
        tv_questionNo.setText("No:" + questionNo);
        currentQ = questions.get(rand);
        Button b = findViewById(R.id.main_menu);
        questions.remove(rand);
        TextView q = findViewById(R.id.questionplaceholder);
        q.setText(currentQ.getQuestion());
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMain();
            }

            ;
        });

        Button b7 = findViewById(R.id.skipbutton);
        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (skips == 3) {
                    skips--;
                    questionNo++;
                    if (questionNo > numQuestions) {
                        goToFinish();
                    }else{
                    ImageView i = findViewById(R.id.skip);
                    i.setVisibility(View.INVISIBLE);
                    nextQuestion();}
                } else if (skips == 2) {
                    skips--;
                    questionNo++;
                    if (questionNo > numQuestions) {
                        goToFinish();
                    }else{
                    ImageView i = findViewById(R.id.skip2);
                    i.setVisibility(View.INVISIBLE);
                    nextQuestion();}
                } else if (skips == 1) {
                    skips--;
                    questionNo++;
                    if (questionNo > numQuestions) {
                        goToFinish();
                    }else{
                    ImageView i = findViewById(R.id.skip3);
                    i.setVisibility(View.INVISIBLE);
                    nextQuestion();}
                } else if (skips == 0) {

                }
            }

            ;
        });

        Button b1 = findViewById(R.id.totoo);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToNext(currentQ.getChoice1());
            }

            ;
        });

        Button b2 = findViewById(R.id.ditotoo);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToNext(currentQ.getChoice2());
            }

            ;
        });

        Button b3 = findViewById(R.id.multiA);
        b3.setText(currentQ.getChoice1());
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToNext(currentQ.getChoice1());
            }

            ;
        });

        Button b4 = findViewById(R.id.multiB);
        b4.setText(currentQ.getChoice2());
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToNext(currentQ.getChoice2());
            }

            ;
        });

        Button b5 = findViewById(R.id.multiC);
        b5.setText(currentQ.getChoice3());
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToNext(currentQ.getChoice3());
            }

            ;
        });

        Button b6 = findViewById(R.id.multiD);
        b6.setText(currentQ.getChoice4());
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToNext(currentQ.getChoice4());
            }

            ;
        });
        q.setText(currentQ.getQuestion());
        if (currentQ.getType().equalsIgnoreCase("1")) {
            b1.setVisibility(View.VISIBLE);
            b2.setVisibility(View.VISIBLE);

            b3.setVisibility(View.INVISIBLE);
            b4.setVisibility(View.INVISIBLE);
            b5.setVisibility(View.INVISIBLE);
            b6.setVisibility(View.INVISIBLE);
        } else if (currentQ.getType().equalsIgnoreCase("2")) {
            b1.setVisibility(View.INVISIBLE);
            b2.setVisibility(View.INVISIBLE);
            b3.setVisibility(View.VISIBLE);
            b4.setVisibility(View.VISIBLE);
            b5.setVisibility(View.VISIBLE);
            b6.setVisibility(View.VISIBLE);
            b3.setText(currentQ.getChoice1());
            b4.setText(currentQ.getChoice2());
            b5.setText(currentQ.getChoice3());
            b6.setText(currentQ.getChoice4());
        } else {
            b1.setVisibility(View.INVISIBLE);
            b2.setVisibility(View.INVISIBLE);
            b3.setVisibility(View.INVISIBLE);
            b4.setVisibility(View.INVISIBLE);
            b5.setVisibility(View.INVISIBLE);
            b6.setVisibility(View.INVISIBLE);
        }
    }

    private void getAllQuestion() {
        questions.clear();
        questions.addAll(db.getAllQuestions());
    }


    private void goToNext(String ans) {
        if (questionNo > numQuestions) {
            finish();
            Intent i = new Intent(this, finished_Game.class);
            startActivity(i);
        } else if (currentQ.getAnswer().equals(ans)) {
            questionNo++;
            if (questionNo > numQuestions) {
                finish();
                Intent i = new Intent(this, finished_Game.class);
                startActivity(i);
            } else {
                nextQuestion();
            }
        } else {
            lives--;
            if (lives == 2) {
                ImageView i = findViewById(R.id.heart3);
                i.setVisibility(View.INVISIBLE);
            } else if (lives == 1) {
                ImageView i = findViewById(R.id.heart2);
                i.setVisibility(View.INVISIBLE);
            } else if (lives == 0) {
                finish();
                Intent i = new Intent(this, Game_Over.class);
                startActivity(i);
            }
        }
    }

    private void goToMain() {
        finish();
    }

    protected void goToFinish() {
        if (questionNo > numQuestions) {
            finish();
            Intent i = new Intent(this, finished_Game.class);
            startActivity(i);
        }
    }
}
