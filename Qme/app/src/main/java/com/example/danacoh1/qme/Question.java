package com.example.danacoh1.qme;

/**
 * Created by danacoh1 on 5/26/2017.
 */

public class Question {

    private String question;
    private int yes_counter, no_counter;
    private String id;

    public Question(){
        question = Constants.NULL_STRING;
        yes_counter = 0;
        no_counter = 0;
    }

    public Question(String question) {
        this.question = question;
        yes_counter = 0;
        no_counter = 0;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getYes_counter() {
        return yes_counter;
    }

    public void setYes_counter(int yes_counter) {
        this.yes_counter = yes_counter;
    }

    public int getNo_counter() {
        return no_counter;
    }

    public void setNo_counter(int no_counter) {
        this.no_counter = no_counter;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public static Question[] getInitQuestions() {

        String [] questions_set;
        questions_set = new String[10];
        Question[] qset;
        qset = new Question[10];
        questions_set[0] = "Do you like camels?";
        questions_set[1] = "Do you like smartphone?";
        questions_set[2] = "Do you think pasta is tasty?";
        questions_set[3] = "Do you like the gui of the app?";
        questions_set[4] = "Do you love shrimps?";
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
}
