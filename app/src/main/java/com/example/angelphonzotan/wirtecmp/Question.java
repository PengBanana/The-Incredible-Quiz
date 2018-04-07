package com.example.angelphonzotan.wirtecmp;

/**
 * Created by Anjoh on 12/03/2018.
 */

public class Question {

    //choices
    private int id;
    private String answer;
    private String type;
    private String choice1;
    private String choice2;
    private String choice3;
    private String choice4;
    //types is either 1 2 or 3
    private String question;
    //>>when getting a question
    //get random id from arraylist.size

    //>>after answering the question
    //id.delete from arraylist


    public Question() {
    }

    public Question(String answer, String type, String choice1, String choice2, String choice3, String choice4, String question) {
        this.answer = answer;
        this.type = type;
        this.choice1 = choice1;
        this.choice2 = choice2;
        this.choice3 = choice3;
        this.choice4 = choice4;
        this.question = question;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getChoice1() {
        return choice1;
    }

    public void setChoice1(String choice1) {
        this.choice1 = choice1;
    }

    public String getChoice2() {
        return choice2;
    }

    public void setChoice2(String choice2) {
        this.choice2 = choice2;
    }

    public String getChoice3() {
        return choice3;
    }

    public void setChoice3(String choice3) {
        this.choice3 = choice3;
    }

    public String getChoice4() {
        return choice4;
    }

    public void setChoice4(String choice4) {
        this.choice4 = choice4;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

}
