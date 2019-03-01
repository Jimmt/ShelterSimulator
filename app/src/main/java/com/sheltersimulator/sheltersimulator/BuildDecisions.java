package com.sheltersimulator.sheltersimulator;

import android.app.Application;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;


public class BuildDecisions {

    private int[] range = new int[2];
    private ArrayList<Decision> preReq;
    private ArrayList<Answer> answers;

    public BuildDecisions(int[] range, ArrayList<Decision> preReq, ArrayList<Answer> answers){
        this.range = range;
        this.preReq = preReq;
//        answers = new ArrayList<Answer>(Arrays.asList(new Answer("buy more food", 50), new Answer("do nothing", 0)));
        this.answers = answers;
        Decision test = new Decision("You don't have any food, would you like to go buy some?", preReq, range, false, answers);
    }
}


class Decision implements Serializable {

    private String question;
    private ArrayList<Decision> preReq;
    private int[] range;
    private boolean visited;
    private ArrayList<Answer> answers;

    public Decision(String question,ArrayList<Decision> preReq,int[] range,boolean visited,ArrayList<Answer> answers  ){
        this.question = question;
        this.preReq = preReq;
        this.range = range;
        this.visited = visited;
        this.answers = answers;
    }


    public String getQuestion(){
        return question;
    }

    public ArrayList<Decision> getPreReq(){
        return preReq;
    }

    public int[] getRange(){
        return range;
    }

    public boolean getVisited() {return visited;}

    public ArrayList<Answer> getAnswers() {return answers;}

}

class Answer {
    private String answerText;
    private int cost;

    public Answer(String answerText, int cost){
        this.answerText = answerText;
        this.cost = cost;
    }
    public String getAnswerText(){
        return answerText;
    }

    public int getCost(){
        return cost;
    }

}