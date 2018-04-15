package com.example.angelphonzotan.wirtecmp;

import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Anjoh on 12/03/2018.
 */

public class startGame extends AppCompatActivity implements SensorEventListener,GestureDetector.OnGestureListener {
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
    private int numFlipQuestionNo = 1;
    private int numMotionQuestionNo = 1;
    private MediaPlayer mp;


    private String currentpos = "down";
    private int count = 0;

    private int change = 0;

    private boolean cocked = false;

    private Button proceed;

    TextView scaleText;
    ScaleGestureDetector scaleGestureDetector;
    TextView Text;
    Button b1;
    String[] code ={"2","4","1","5"};
    CountDownTimer time;
    TextView t ;
    GestureDetector gestureDetector;
    int rubs = 0;
    ImageView Texti;
    int current;
    TextView[] lock ;

    private int currentpos1;
    int[][] maze = {{0,0,1,1,1},
            {0,0,1,0,0},
            {0,0,1,0,0},
            {1,1,1,0,0}};
    int curX = 4;
    int curY=0;
    ImageView[][] texts;
    String curr;

    private int counter;
    private CountDownTimer time1;
    private CountDownTimer time2;

    private boolean start;

    private ArrayList<Integer> flipQuestions = new ArrayList<>();
    private ArrayList<Integer> motionQuestions = new ArrayList<>();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.startgame);

        mp = MediaPlayer.create(this, R.raw.coin);

        lives = 3;
        skips = 3;
        sensorMan = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorMan.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mAccel = 0.00f;
        mAccelCurrent = SensorManager.GRAVITY_EARTH;
        mAccelLast = SensorManager.GRAVITY_EARTH;
        scaleGestureDetector =
                new ScaleGestureDetector(this,
                        new MyOnScaleGestureListener());
        for(int i=0;i<10;i++) {
            flipQuestions.add(i);
        }

        for(int i=0;i<10;i++) {
            motionQuestions.add(i);
        }
        gestureDetector = new GestureDetector(startGame.this, startGame.this);
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
                if(questionNo==2){
                    if (y > 0) {
                        findViewById(R.id.imageView6).setVisibility(View.VISIBLE);
                        findViewById(R.id.imageView9).setVisibility(View.INVISIBLE);
                        ((Button) findViewById(R.id.button)).setVisibility(View.VISIBLE);
                    }
                }
                else if(questionNo==4) {
                    if (Math.abs(x) > Math.abs(y)) {
                        if (x < 0) {
                            moved("right");
                        }
                        if (x > 0) {
                            moved("left");
                        }
                    } else {
                        if (y < 0) {
                            moved("down");
                        }
                        if (y > 0) {
                            moved("up");
                        }
                    }
                    if (x > (-2) && x < (2) && y > (-2) && y < (2)) {
                        moved("None");
                    }
                }

                else if(questionNo==6) {
                    if (Math.abs(x) > Math.abs(y)) {
                        if (x < 0 && count <10) {
                            count++;
                            ((TextView) findViewById(R.id.count)).setText(""+count);
                        }
                        if (x > 0 && count <10) {
                            count++;
                            ((TextView) findViewById(R.id.count)).setText(""+count);
                        }
                    }
                    if(count == 10){
                        ((Button) findViewById(R.id.button)).setVisibility(View.VISIBLE);
                    }
                }

                else if(questionNo==8) {
                    if (z < 0 && count <10) {
                        count++;
                        ((TextView) findViewById(R.id.count)).setText(""+count);
                    }
                    if (z > 0 && count <10) {
                        count++;
                        ((TextView) findViewById(R.id.count)).setText(""+count);
                    }

                    if(count == 10){
                        ((Button) findViewById(R.id.button)).setVisibility(View.VISIBLE);
                    }
                }
                else if(questionNo==10) {
                    if (z < 0 && count <100) {
                        count++;
                        ((TextView) findViewById(R.id.count)).setText(""+count);
                    }
                    if (z > 0 && count <100) {
                        count++;
                        ((TextView) findViewById(R.id.count)).setText(""+count);
                    }

                    if (x < 0 && count <100) {
                        count++;
                        ((TextView) findViewById(R.id.count)).setText(""+count);
                    }
                    if (x > 0 && count <100) {
                        count++;
                        ((TextView) findViewById(R.id.count)).setText(""+count);
                    }

                    if (y < 0 && count <100) {
                        count++;
                        ((TextView) findViewById(R.id.count)).setText(""+count);
                    }
                    if (y> 0 && count <100) {
                        count++;
                        ((TextView) findViewById(R.id.count)).setText(""+count);
                    }

                    if(count == 100){
                        ((Button) findViewById(R.id.button)).setVisibility(View.VISIBLE);
                    }
                }
                else if(questionNo==12) {
                    if (Math.abs(x) > Math.abs(y)) {
                        if (x < 0) {
                            change--;

                        }
                        if (x > 0) {
                            change++;

                        }
                    }
                }
                else if(questionNo==14) {
                    if (z < 0 && cocked) {

                        findViewById(R.id.button).setVisibility(View.VISIBLE);
                        cocked = false;
                    }
                    if (z > 0 && !cocked) {
                        cocked = true;

                    }
                }
                else if(questionNo==16) {
                    if (Math.abs(x) > Math.abs(y)) {
                        if (x < 0) {
                            moved("left");
                        }
                        if (x > 0) {
                            moved("right");
                        }
                    } else {
                        if (y < 0) {
                            moved("up");
                        }
                        if (y > 0) {
                            moved("down");
                        }
                    }
                    if (x > (-2) && x < (2) && y > (-2) && y < (2)) {
                        moved("None");
                    }
                }
                else if(questionNo==18) {
                    if (x > 0 && mAccel >12) {
                        findViewById(R.id.button).setVisibility(View.VISIBLE);
                    }
                }
                else if(questionNo==20) {
                    if (z < 0 && count < 10) {
                        count++;
                        String prog = "{";
                        for(int i = 0;i<10;i++) {
                            if(i<=count){
                                prog+="|";
                            }
                            else{
                                prog+=" ";

                            }


                        }
                        prog+="}";
                        ((TextView) findViewById(R.id.count2)).setText(prog);
                    }
                    if (z > 0 && count < 10) {
                        count++;
                        String prog = "{";
                        for(int i = 0;i<10;i++) {

                            if(i<=count){
                                prog+="|";
                            }
                            else{
                                prog+=" ";

                            }

                        }
                        prog+="}";
                        ((TextView) findViewById(R.id.count2)).setText(prog);
                    }
                }
            }


        }

    }



    public void onAccuracyChanged(Sensor sensor, int i) {

    }


    private void prepareQuestions() {
        getAllQuestion();
        numQuestions = 40;
        Random rand = new Random();
        int randInt = rand.nextInt(questions.size());
        setUpUi(randInt);
    }

    private void nextQuestion() {
        Random rand = new Random();
        TextView questNo = findViewById(R.id.questionNo);
        int randInt = rand.nextInt(questions.size());
        setUpUi(randInt);
    }

    private void setUpUi(int rand) {

        setContentView(R.layout.startgame);
        TextView tv_questionNo = (TextView) findViewById(R.id.questionNo);
        if(lives==2){
            ImageView h = findViewById(R.id.heart3);
            h.setVisibility(View.INVISIBLE);


        }
        if(lives==1){
            ImageView h = findViewById(R.id.heart3);
            h.setVisibility(View.INVISIBLE);
            ImageView h2 = findViewById(R.id.heart2);
            h2.setVisibility(View.INVISIBLE);
        }
        if(skips==2){
            ImageView h = findViewById(R.id.skip3);
            h.setVisibility(View.INVISIBLE);


        }
        if(skips==1){
            ImageView h = findViewById(R.id.skip3);
            h.setVisibility(View.INVISIBLE);
            ImageView h2 = findViewById(R.id.skip2);
            h2.setVisibility(View.INVISIBLE);
        }

        if(skips==0){
            ImageView h = findViewById(R.id.skip3);
            h.setVisibility(View.INVISIBLE);
            ImageView h2 = findViewById(R.id.skip2);
            h2.setVisibility(View.INVISIBLE);
            ImageView h3= findViewById(R.id.skip);
            h3.setVisibility(View.INVISIBLE);
        }
        tv_questionNo.setText("Question No: " + questionNo);
        currentQ = questions.get(rand);
        Button b = findViewById(R.id.main_menu);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMain();
            }

            ;
        });
        questions.remove(rand);
        TextView q = findViewById(R.id.questionplaceholder);
        q.setText(currentQ.getQuestion());


        Button b7 = findViewById(R.id.skipbutton);
        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (skips == 3) {
                    skips--;

                    ImageView i = findViewById(R.id.skip);
                    i.setVisibility(View.INVISIBLE);
                    nextQuestion();
                    goToNext("skip");

                } else if (skips == 2) {
                    skips--;

                    ImageView i = findViewById(R.id.skip2);
                    i.setVisibility(View.INVISIBLE);
                    nextQuestion();
                    goToNext("skip");
                } else if (skips == 1) {
                    skips--;


                    ImageView i = findViewById(R.id.skip3);
                    i.setVisibility(View.INVISIBLE);
                    nextQuestion();
                    goToNext("skip");
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




    private boolean contains(int[] array,int no){
        for(int i = 0;i<10;i++){
            if(array[i]==no){
                return true;
            }
        }
        return false;
    }
    private void goToNext(String ans) {
        int[] flips = {2,4,6,8,10,12,14,16,18,20};
        int[] motion = {3,5,7,9,11,13,15,17,19,21};
        if (questionNo > numQuestions) {
            finish();
            Intent i = new Intent(this, finished_Game.class);
            startActivity(i);

        } else if (currentQ.getAnswer().equals(ans)||ans.equals("skip")) {
            mp.start();
            questionNo++;
            if (questionNo > numQuestions) {
                finish();
                Intent i = new Intent(this, finished_Game.class);
                startActivity(i);
            } else if(contains(flips,questionNo)){
                if(questionNo == 2){
                    setContentView(R.layout.activity_flip_test);
                    count = 0;
                    currentpos = "down";



                    Text = findViewById(R.id.textView4);
                }
                else if(questionNo == 4){
                    setContentView(R.layout.activity_flip_test_2);
                    currentpos = "down";
                    count = 0;



                    Text = findViewById(R.id.textView4);
                }

                else if(questionNo == 6){
                    setContentView(R.layout.activity_flip_test_3);
                    count = 0;
                    Text = findViewById(R.id.textView4);
                }

                else if(questionNo == 8){
                    setContentView(R.layout.activity_flip_test_3);
                    count = 0;
                    Text = findViewById(R.id.textView4);
                }
                else if(questionNo == 10){
                    setContentView(R.layout.activity_flip_test_5);
                    count = 0;
                    Text = findViewById(R.id.textView4);
                }
                else if(questionNo == 12){
                    setContentView(R.layout.activity_flip_test_6);
                    change = 0;
                    start = false;
                    counter = 20;
                    b1 = findViewById(R.id.button);
                    time2 = new CountDownTimer(2000, 1000) {

                        public void onTick(long millisUntilFinished) {

                        }

                        public void onFinish() {
                            b1.setVisibility(View.VISIBLE);
                            time1.cancel();

                        }


                    };
                    time1 = new CountDownTimer(120000, 1000) {

                        public void onTick(long millisUntilFinished) {
                            TextView t = findViewById(R.id.count);
                            counter-=change;
                            t.setText(""+(counter));

                            if(counter <=1 && counter>= -1 && !start){
                                time2.start();
                                start=true;
                            }

                            else if(counter >1 || counter< -1){
                                time2.cancel();
                                start = false;
                            }
                        }

                        public void onFinish() {

                        }


                    };
                    time1.start();
                }
                else if(questionNo == 14){
                    setContentView(R.layout.activity_flip_test_7);
                    cocked = false;
                }
                else if(questionNo == 16){
                    setContentView(R.layout.activity_flip_test_8);
                    currentpos = "down";
                    count = 0;

                    Text = findViewById(R.id.textView4);
                }
                else if(questionNo == 18){
                    setContentView(R.layout.activity_flip_test_9);
                    Text = findViewById(R.id.textView4);
                }
                else if(questionNo == 20){
                    setContentView(R.layout.activity_flip_test_10);
                    count = 0;
                    Text = findViewById(R.id.textView4);
                }

                TextView questNo = findViewById(R.id.questionNo);
                questNo.setText("Question No: "+questionNo);
                if(lives==2){
                    ImageView h = findViewById(R.id.heart3);
                    h.setVisibility(View.INVISIBLE);


                }
                if(lives==1){
                    ImageView h = findViewById(R.id.heart3);
                    h.setVisibility(View.INVISIBLE);
                    ImageView h2 = findViewById(R.id.heart2);
                    h2.setVisibility(View.INVISIBLE);
                }

                if(skips==2){
                    ImageView h = findViewById(R.id.skip3);
                    h.setVisibility(View.INVISIBLE);


                }
                if(skips==1){
                    ImageView h = findViewById(R.id.skip3);
                    h.setVisibility(View.INVISIBLE);
                    ImageView h2 = findViewById(R.id.skip2);
                    h2.setVisibility(View.INVISIBLE);
                }
                if(skips==0){
                    ImageView h = findViewById(R.id.skip3);
                    h.setVisibility(View.INVISIBLE);
                    ImageView h2 = findViewById(R.id.skip2);
                    h2.setVisibility(View.INVISIBLE);
                    ImageView h3= findViewById(R.id.skip);
                    h3.setVisibility(View.INVISIBLE);
                }
                Button b = findViewById(R.id.main_menu);
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

                            ImageView i = findViewById(R.id.skip);
                            i.setVisibility(View.INVISIBLE);
                            nextQuestion();
                            goToNext("skip");
                            if(time1!=null){
                                time1.cancel();
                            }
                        } else if (skips == 2) {
                            skips--;

                            ImageView i = findViewById(R.id.skip2);
                            i.setVisibility(View.INVISIBLE);
                            nextQuestion();
                            goToNext("skip");
                            if(time1!=null){
                                time1.cancel();
                            }
                        } else if (skips == 1) {
                            skips--;


                            ImageView i = findViewById(R.id.skip3);
                            i.setVisibility(View.INVISIBLE);
                            nextQuestion();
                            goToNext("skip");
                            if(time1!=null){
                                time1.cancel();
                            }
                        } else if (skips == 0) {

                        }
                    }

                    ;
                });
                proceed = findViewById(R.id.button);
                proceed.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        goToNext("skip");
                    }
                });
            } else if(contains(motion,questionNo)){
                if(questionNo == 3){
                    setContentView(R.layout.activity_pinch__test);


                    ImageView i = findViewById(R.id.imageView);

                    Button b = findViewById(R.id.button);
                    b1 = b;
                    b1.setVisibility(View.INVISIBLE);
                    Texti = i;
                }
                else if(questionNo == 5){
                    setContentView(R.layout.activity_pinch__test_2);






                    ImageView i = findViewById(R.id.imageView);

                    Button b = findViewById(R.id.button);
                    b1 = b;
                    b1.setVisibility(View.INVISIBLE);
                    Texti = i;
                }

                else if(questionNo == 7){
                    setContentView(R.layout.activity_pinch__test_3);






                    ImageView i = findViewById(R.id.imageView);

                    Button b = findViewById(R.id.button);
                    b1 = b;
                    b1.setVisibility(View.INVISIBLE);
                    Texti = i;
                    time = new CountDownTimer(3000, 1000) {

                        public void onTick(long millisUntilFinished) {
                            TextView t = findViewById(R.id.textView3);
                            t.setText(""+millisUntilFinished/1000);
                        }

                        public void onFinish() {
                            b1.setVisibility(View.VISIBLE);
                            TextView t = findViewById(R.id.textView3);
                            t.setText("0");
                        }


                    };
                }

                else if(questionNo == 9){
                    setContentView(R.layout.activity_pinch__test_4);
                    t= findViewById(R.id.textView);

                }
                else if(questionNo == 11){
                    setContentView(R.layout.activity_pinch_test_5);
                    current = 1;
                    currentpos1 = 0;
                    lock = new TextView[5];
                    lock[0] = findViewById(R.id.lock1);
                    lock[1] = findViewById(R.id.lock2);
                    lock[2] = findViewById(R.id.lock3);
                    lock[3] = findViewById(R.id.lock4);
                    lock[4] = findViewById(R.id.lock5);

                }
                else if(questionNo == 13){
                    setContentView(R.layout.activity_pinch__test_6);
                    count = 25;

                }
                else if(questionNo == 15){
                    setContentView(R.layout.activity_pinch__test_7);
                    cocked = false;

                }
                else if(questionNo == 17){
                    setContentView(R.layout.activity_pinch__test_8);
                    curX = 4;
                    curY = 0;
                    texts = new ImageView[4][5];
                    gestureDetector = new GestureDetector(startGame.this, startGame.this);
                    texts[0][2] = findViewById(R.id.block2);
                    texts[0][3] = findViewById(R.id.block1);
                    texts[0][4] = findViewById(R.id.start);
                    texts[1][2] = findViewById(R.id.block3);
                    texts[2][2] = findViewById(R.id.block4);
                    texts[3][0] = findViewById(R.id.end);
                    texts[3][1] = findViewById(R.id.block6);
                    texts[3][2] = findViewById(R.id.block5);
                }
                else if(questionNo == 19){
                    setContentView(R.layout.activity_pinch__test_9);

                }
                else if(questionNo == 21){
                    setContentView(R.layout.activity_pinch__test_10);
                    cocked = false;
                    curr = "down";
                    count = 0;


                }
                Button b = findViewById(R.id.main_menu);
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

                            ImageView i = findViewById(R.id.skip);
                            i.setVisibility(View.INVISIBLE);
                            nextQuestion();
                            goToNext("skip");

                        } else if (skips == 2) {
                            skips--;

                            ImageView i = findViewById(R.id.skip2);
                            i.setVisibility(View.INVISIBLE);
                            nextQuestion();
                            goToNext("skip");
                        } else if (skips == 1) {
                            skips--;


                            ImageView i = findViewById(R.id.skip3);
                            i.setVisibility(View.INVISIBLE);
                            nextQuestion();
                            goToNext("skip");
                        } else if (skips == 0) {

                        }
                    }

                    ;
                });
                if(lives==2){
                    ImageView h = findViewById(R.id.heart3);
                    h.setVisibility(View.INVISIBLE);


                }
                if(lives==1){
                    ImageView h = findViewById(R.id.heart3);
                    h.setVisibility(View.INVISIBLE);
                    ImageView h2 = findViewById(R.id.heart2);
                    h2.setVisibility(View.INVISIBLE);
                }
                if(skips==2){
                    ImageView h = findViewById(R.id.skip3);
                    h.setVisibility(View.INVISIBLE);


                }
                if(skips==1){
                    ImageView h = findViewById(R.id.skip3);
                    h.setVisibility(View.INVISIBLE);
                    ImageView h2 = findViewById(R.id.skip2);
                    h2.setVisibility(View.INVISIBLE);
                }
                if(skips==0){
                    ImageView h = findViewById(R.id.skip3);
                    h.setVisibility(View.INVISIBLE);
                    ImageView h2 = findViewById(R.id.skip2);
                    h2.setVisibility(View.INVISIBLE);
                    ImageView h3= findViewById(R.id.skip);
                    h3.setVisibility(View.INVISIBLE);
                }

                TextView questNo = findViewById(R.id.questionNo);
                questNo.setText("Question No: "+questionNo);

                proceed = findViewById(R.id.button);
                proceed.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        goToNext("skip");
                    }
                });

            }
            else {
                setContentView(R.layout.startgame);
                if(lives==2){
                    ImageView h = findViewById(R.id.heart3);
                    h.setVisibility(View.INVISIBLE);


                }
                if(lives==1){
                    ImageView h = findViewById(R.id.heart3);
                    h.setVisibility(View.INVISIBLE);
                    ImageView h2 = findViewById(R.id.heart2);
                    h2.setVisibility(View.INVISIBLE);
                }
                if(skips==2){
                    ImageView h = findViewById(R.id.skip3);
                    h.setVisibility(View.INVISIBLE);


                }
                if(skips==1){
                    ImageView h = findViewById(R.id.skip3);
                    h.setVisibility(View.INVISIBLE);
                    ImageView h2 = findViewById(R.id.skip2);
                    h2.setVisibility(View.INVISIBLE);
                }
                if(skips==0){
                    ImageView h = findViewById(R.id.skip3);
                    h.setVisibility(View.INVISIBLE);
                    ImageView h2 = findViewById(R.id.skip2);
                    h2.setVisibility(View.INVISIBLE);
                    ImageView h3= findViewById(R.id.skip);
                    h3.setVisibility(View.INVISIBLE);
                }
                TextView questNo = findViewById(R.id.questionNo);
                questNo.setText("Question No: "+questionNo);

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

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        if(questionNo==13) {
            count--;
            if (count > 0) {
                TextView t = findViewById(R.id.count);
                t.setText("" + count);
            } else {
                findViewById(R.id.button).setVisibility(View.VISIBLE);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        if(questionNo==9) {
            if(rubs >= 100){
                findViewById(R.id.button).setVisibility(View.VISIBLE);
            }
            rubs++;
            t.setText(""+rubs);
            return true;
        }
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent motionEvent1, MotionEvent motionEvent2, float v, float v1) {
        if(questionNo==9) {

            if(rubs >= 100){
                findViewById(R.id.button).setVisibility(View.VISIBLE);
            }
            t = findViewById(R.id.textView);
            if(motionEvent1.getY() - motionEvent2.getY() > 50){

                rubs++;
                t.setText(""+rubs);
                return true;
            }

            if(motionEvent2.getY() - motionEvent1.getY() > 50){

                rubs++;
                t.setText(""+rubs);

                return true;
            }

            if(motionEvent1.getX() - motionEvent2.getX() > 50){

                rubs++;
                t.setText(""+rubs);
                return true;
            }

            if(motionEvent2.getX() - motionEvent1.getX() > 50) {

                rubs++;
                t.setText(""+rubs);
                return true;
            }
            else return true;
        }
        else if(questionNo==11) {
        if(motionEvent1.getY() - motionEvent2.getY() > 100 && current!= 1){
            lock[current-1].setText("  "+(current));
            current--;

        }

        if(motionEvent2.getY() - motionEvent1.getY() > 100 && current!= 5){
            lock[current-1].setText("  "+(current));
            current++;



        }
        lock[current-1].setText("- "+current);
        if(currentpos1<4) {
            if (("" + current).equals(code[currentpos1])) {
                TextView finalCode = findViewById(R.id.code);
                currentpos1++;
                finalCode.setText("" + finalCode.getText() + "" + current);
            }
        }
        if(currentpos1==4){
            findViewById(R.id.button).setVisibility(View.VISIBLE);
        }
        }
        else if (questionNo==15){
            if(motionEvent1.getY() - motionEvent2.getY() > 100 && cocked){

                Button b = findViewById(R.id.button);
                b.setVisibility(View.VISIBLE);
            }

            else if(motionEvent2.getY() - motionEvent1.getY() > 100 && !cocked){
                findViewById(R.id.readysling).setVisibility(View.VISIBLE);
                findViewById(R.id.firstsling).setVisibility(View.INVISIBLE);
                cocked = true;



            }
        }
        else if(questionNo==17) {
            if (motionEvent1.getY() - motionEvent2.getY() > 100 && curY != 0) {
                if (maze[curY - 1][curX] == 1) {
                    texts[curY][curX].setBackgroundColor(Color.BLACK);
                    curY--;
                    texts[curY][curX].setBackgroundColor(Color.RED);
                }

            }

            if (motionEvent2.getY() - motionEvent1.getY() > 100 && curY != 3) {
                if (maze[curY + 1][curX] == 1) {
                    texts[curY][curX].setBackgroundColor(Color.BLACK);
                    curY++;
                    texts[curY][curX].setBackgroundColor(Color.RED);
                }


            }

            if (motionEvent2.getX() - motionEvent1.getX() > 100 && curX != 4) {
                if (maze[curY][curX + 1] == 1) {
                    texts[curY][curX].setBackgroundColor(Color.BLACK);
                    curX++;
                    texts[curY][curX].setBackgroundColor(Color.RED);
                }


            }

            if (motionEvent1.getX() - motionEvent2.getX() > 100 && curX != 0) {
                if (maze[curY][curX - 1] == 1) {
                    texts[curY][curX].setBackgroundColor(Color.BLACK);
                    curX--;
                    texts[curY][curX].setBackgroundColor(Color.RED);
                    if (curY == 3 && curX == 0) {
                        Button b = findViewById(R.id.button);
                        b.setVisibility(View.VISIBLE);

                    }

                }
            }
        }
        else if(questionNo==19){
            if(motionEvent1.getY() - motionEvent2.getY() > 100){

                Button b = findViewById(R.id.button);
                b.setVisibility(View.VISIBLE);
            }
        }
        else if(questionNo == 20){
            if(motionEvent1.getY() - motionEvent2.getY() > 100 && count >=10){

                Button b = findViewById(R.id.button);
                b.setVisibility(View.VISIBLE);
            }
        }
        else if(questionNo == 21){
            Boolean correct = false;
            if(motionEvent1.getY() - motionEvent2.getY() > 100 && curr.equals("up")){
                correct = true;


            }

            if(motionEvent2.getY() - motionEvent1.getY() > 100 && curr.equals("down")){
                correct = true;


            }

            if(motionEvent2.getX() - motionEvent1.getX() > 100 && curr.equals("right")){

                correct = true;

            }

            if(motionEvent1.getX() - motionEvent2.getX() > 100 && curr.equals("left")){
                correct = true;
            }

            if(correct){
                count++;
                if(count == 5){
                    Button b = findViewById(R.id.button);

                    b.setVisibility(View.VISIBLE);
                }
                else {
                    Random r = new Random();
                    int n = r.nextInt(4);
                    TextView dir = findViewById(R.id.dir);
                    if (n == 0) {
                        dir.setText("up");
                        curr = "up";
                    } else if (n == 1) {
                        dir.setText("down");
                        curr = "down";
                    } else if (n == 2) {
                        dir.setText("left");
                        curr = "left";
                    } else if (n == 3) {
                        dir.setText("right");
                        curr = "right";
                    }
                }
            }
        }
        return false;
    }

    public boolean onTouchEvent(MotionEvent event) {
        scaleGestureDetector.onTouchEvent(event);
        gestureDetector.onTouchEvent(event);
        return true;
    }

    public class MyOnScaleGestureListener extends
            ScaleGestureDetector.SimpleOnScaleGestureListener {

        @Override
        public boolean onScale(ScaleGestureDetector detector) {

            float scaleFactor = detector.getScaleFactor();
            if(questionNo==3){
                if (scaleFactor > 1) {

                    zoomIn();
                } else {

                    zoomOut();
                }
            }

            else if(questionNo==5){
                if (scaleFactor > 1) {


                } else {

                    zoomOut();
                }
            }
            else if(questionNo==7) {
                if (scaleFactor > 1) {

                    zoomIn();
                } else {

                    zoomOut();
                }
            }
            return true;
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            return true;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {

        }
    }
    protected void zoomIn(){
        if(questionNo==3) {
            if (Texti.getHeight() >= 750) {


                b1.setVisibility(View.VISIBLE);
            }
            Texti.getLayoutParams().height = Texti.getHeight() + 10;
            Texti.getLayoutParams().width = Texti.getWidth() + 10;


            Texti.requestLayout();
        }
        if(questionNo==7) {
            if(Texti.getHeight()<=500 && Texti.getHeight()>=450){


                TextView t = findViewById(R.id.textView5);
                t.setText(""+(450-Texti.getHeight())/100);
                time.start();
            }
            else{

                TextView t = findViewById(R.id.textView5);
                t.setText(""+(450-Texti.getHeight())/100);
                time.cancel();
                TextView t1 = findViewById(R.id.textView3);
                t1.setText("");
            }
            Texti.getLayoutParams().height = Texti.getHeight()+10;
            Texti.getLayoutParams().width= Texti.getWidth()+10;




            Texti.requestLayout();

        }

    }

    protected void zoomOut(){
        if(questionNo==3) {
            if (Texti.getHeight() < 750) {


                b1.setVisibility(View.INVISIBLE);
            }
            Texti.getLayoutParams().height = Texti.getHeight() - 10;
            Texti.getLayoutParams().width = Texti.getWidth() - 10;
            Texti.requestLayout();
        }
        else if(questionNo==5){
            b1.setVisibility(View.VISIBLE);
        }
        if(questionNo==7) {
            TextView t = findViewById(R.id.textView5);
            t.setText(""+(450-Texti.getHeight())/100);
            if(Texti.getHeight()<=500 && Texti.getHeight()>=450){



                time.start();
            }
            else{
                TextView t1 = findViewById(R.id.textView3);
                t.setText("");
                time.cancel();
            }
            Texti.getLayoutParams().height = Texti.getHeight()-10;
            Texti.getLayoutParams().width= Texti.getWidth()-10;
            Texti.requestLayout();
        }

    }
//Flip Questions
    protected void moved(String direct) {
        TextView Text = findViewById(R.id.textView4);
        if (direct.equals(currentpos)) {
            Random r = new Random();
            int n = r.nextInt(4);
            count++;
            if (count < 5) {
                if (n == 0) {
                    Text.setText("up");
                    currentpos = "up";
                } else if (n == 1) {
                    Text.setText("down");
                    currentpos = "down";
                } else if (n == 2) {
                    Text.setText("left");
                    currentpos = "left";
                } else if (n == 3) {
                    Text.setText("right");
                    currentpos = "right";
                }
            }
            else{
                findViewById(R.id.button).setVisibility(View.VISIBLE);
            }
        }
    }
}
