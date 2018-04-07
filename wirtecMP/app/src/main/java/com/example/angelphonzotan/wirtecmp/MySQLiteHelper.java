package com.example.angelphonzotan.wirtecmp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Angel Phonzo Tan on 06/04/2018.
 */

public class MySQLiteHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "QuestionDB";
    // Books table name
    private static final String TABLE_QUESTION = "Question";
    private static final String TABLE_WINNERS = "Winners";

    // Books Table Columns names
    private static final String KEY_WINID = "Winid";
    private static final String KEY_WINNAME = "Winname";
    private static final String KEY_WINDATE = "Windate";
    private static final String KEY_ID = "id";
    private static final String KEY_TYPE = "Type";
    private static final String KEY_QUESTION = "Question";
    private static final String KEY_ANSWER = "Answer";
    private static final String KEY_CHOICE1 = "Choice1";
    private static final String KEY_CHOICE2 = "Choice2";
    private static final String KEY_CHOICE3 = "Choice3";
    private static final String KEY_CHOICE4 = "Choice4";


    private static final String[] WINNER_COLUMNS = {KEY_WINID,KEY_WINNAME,KEY_WINDATE};
    private static final String[] COLUMNS = {KEY_ID,KEY_TYPE,KEY_QUESTION,KEY_ANSWER,KEY_CHOICE1,KEY_CHOICE2,KEY_CHOICE3,KEY_CHOICE4};

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create question table
        String CREATE_QUESTION_TABLE = "CREATE TABLE Question ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "Type TEXT, "+
                "Question TEXT, "+
                "Answer TEXT, "+
                "Choice1 TEXT, "+
                "Choice2 TEXT, "+
                "Choice3 TEXT, "+
                "Choice4 TEXT)";

        String CREATE_WINNERS_TABLE = "CREATE TABLE Winners ( " +
                "Winid INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "Winname TEXT, "+
                "Windate DATE)";

        // create question table
        db.execSQL(CREATE_QUESTION_TABLE);
        db.execSQL(CREATE_WINNERS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older question table if existed
        db.execSQL("DROP TABLE IF EXISTS Question");
        db.execSQL("DROP TABLE IF EXISTS Winners");

        // create fresh question table
        this.onCreate(db);
    }
    //---------------------------------------------------------------------

    /**
     * CRUD operations (create "add", read "get", update, delete) book + get all books + delete all books
     */


    public void addQuestion(Question question){
        Log.d("addQuestion", question.toString());
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_TYPE, question.getType()); // get Type
        values.put(KEY_QUESTION, question.getQuestion()); // get Question
        values.put(KEY_ANSWER, question.getAnswer()); // get Answer
        values.put(KEY_CHOICE1, question.getChoice1()); // get Choice1
        values.put(KEY_CHOICE2, question.getChoice2()); // get Choice2
        values.put(KEY_CHOICE3, question.getChoice3()); // get Choice3
        values.put(KEY_CHOICE4, question.getChoice4()); // get Choice4

        // 3. insert
        db.insert(TABLE_QUESTION, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        // 4. close
        db.close();
    }

    public void addWinner(User user){
        Log.d("addQuestion", user.toString());
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_WINNAME, user.getName());
        values.put(KEY_WINDATE, user.getTime());

        db.insert(TABLE_WINNERS, // table
                null, //nullColumnHack
                values);
        db.close();
    }

    public Question getQuestion(int id){

        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        Cursor cursor =
                db.query(TABLE_QUESTION, // a. table
                        COLUMNS, // b. column names
                        " id = ?", // c. selections
                        new String[] { String.valueOf(id) }, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit

        // 3. if we got results get the first one
        if (cursor != null)
            cursor.moveToFirst();

        // 4. build question object
        Question question = new Question();
        question.setId(Integer.parseInt(cursor.getString(0)));
        question.setType(cursor.getString(1));
        question.setQuestion(cursor.getString(2));
        question.setAnswer(cursor.getString(3));
        question.setChoice1(cursor.getString(4));
        question.setChoice2(cursor.getString(5));
        question.setChoice3(cursor.getString(6));
        question.setChoice4(cursor.getString(7));

        Log.d("getQuestion("+id+")", question.toString());

        // 5. return question
        return question;
    }

    // Get All question
    public ArrayList<Question> getAllQuestions() {
        ArrayList<Question> questions = new ArrayList<Question>();

        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_QUESTION;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build book and add it to list
        Question question = null;
        if (cursor.moveToFirst()) {
            do {
                question = new Question();
                question.setId(Integer.parseInt(cursor.getString(0)));
                question.setType(cursor.getString(1));
                question.setQuestion(cursor.getString(2));
                question.setAnswer(cursor.getString(3));
                question.setChoice1(cursor.getString(4));
                question.setChoice2(cursor.getString(5));
                question.setChoice3(cursor.getString(6));
                question.setChoice4(cursor.getString(7));

                // Add book to books
                questions.add(question);
            } while (cursor.moveToNext());
        }

        Log.d("getAllQuestion()", questions.size()+"");

        // return questions
        return questions;
    }

    public ArrayList<User> getAllWinners(){
        ArrayList<User> users = new ArrayList<User>();
        String query = "SELECT  * FROM " + TABLE_WINNERS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        User user = null;
        if(cursor.moveToFirst()){
            do{
                user = new User();
                user.setId(Integer.parseInt(cursor.getString(0)));
                user.setName(cursor.getString(1));
                user.setTime(cursor.getString(2));
                users.add(user);
            }while (cursor.moveToNext());
        }

        return users;
    }

    // Updating single
    public int updateQuestion(Question question) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_TYPE, question.getType()); // get type
        values.put(KEY_QUESTION, question.getQuestion()); // get question
        values.put(KEY_ANSWER, question.getAnswer()); // get answer
        values.put(KEY_CHOICE1, question.getChoice1()); // get choice1
        values.put(KEY_CHOICE2, question.getChoice2()); // get choice2
        values.put(KEY_CHOICE3, question.getChoice3()); // get choice3
        values.put(KEY_CHOICE4, question.getChoice4()); // get choice4

        // 3. updating row
        int i = db.update(TABLE_QUESTION, //table
                values, // column/value
                KEY_ID+" = ?", // selections
                new String[] { String.valueOf(question.getId()) }); //selection args

        // 4. close
        db.close();
        return i;
    }

    // Deleting single question
    public void deleteQuestion(Question question) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. delete
        db.delete(TABLE_QUESTION,
                KEY_ID+" = ?",
                new String[] { String.valueOf(question.getId()) });

        // 3. close
        db.close();

        Log.d("deleteQuestion", question.getId()+"");

    }
}