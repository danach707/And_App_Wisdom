package com.example.danacoh1.qme;

import java.util.LinkedList;
import java.util.Vector;

/**
 * Created by danacoh1 on 5/26/2017.
 */

public class Question {
    private String UID;
    private String question;
    private long yes_counter, no_counter;
    private String id;
    private String type;
    private Comments comments;

    //=============================================================================================

    public Question(){
        question = Constants.NULL_STRING;
        yes_counter = 0;
        type = "General";
        no_counter = 0;
        comments = new Comments();
    }

    public Question(String question) {
        this.question = question;
        type = "General";
        yes_counter = 0;
        no_counter = 0;
        comments = new Comments();
    }
    public Question(String question, String type) {
        this.question = question;
        this.type = type;
        yes_counter = 0;
        no_counter = 0;
        comments = new Comments();
    }

    //=============================================================================================

    public String getQuestion() {
        return question;
    }

    //=============================================================================================

    public void setQuestion(String question) {
        this.question = question;
    }

    //=============================================================================================

    public long getYes_counter() {
        return yes_counter;
    }

    //=============================================================================================

    public void setYes_counter(long yes_counter) {
        this.yes_counter = yes_counter;
    }

    //=============================================================================================

    public long getNo_counter() {
        return no_counter;
    }

    //=============================================================================================

    public void setNo_counter(long no_counter) {
        this.no_counter = no_counter;
    }

    //=============================================================================================

    public String getId() {
        return id;
    }


    //=============================================================================================

    public void setId(String id) {
        this.id = id;
    }

    //=============================================================================================

    public String getType() {return type;}

    //=============================================================================================

    public void setType(String type) {this.type = type;}

    //=============================================================================================

    public static Question[] getInitQuestions() {

        String [] questions_set;
        questions_set = new String[10];
        Question[] qset;
        qset = new Question[10];
        questions_set[0] = "Do you like camels?";
        questions_set[1] = "Do you like smartphone?";
        questions_set[2] = "Do you think pasta is tasty?";
        questions_set[3] = "Do you like the gui of the app?";
        questions_set[4] = "Do you love Hamburgers?";
        questions_set[5] = "Do you want to be Dana Cohen?";
        questions_set[6] = "Are you going to Britney spears concert?";
        questions_set[7] = "Can you lift a Woman with one hand?";
        questions_set[8] = "Are you going to the beach today?";
        questions_set[9] = "Is Lior Sapir samrt (NO)?";

        for(int i = 0; i < 10; i++){
            qset[i] = new Question(questions_set[i]);
        }
        return qset;
    }

    //=============================================================================================

    public String toString(){
        return "id: " + id + "\nquestion: " + question + "\nyesC: " + yes_counter + "\nnoC: " + no_counter;
    }

    public Comments getComments() {
        return comments;
    }

}

