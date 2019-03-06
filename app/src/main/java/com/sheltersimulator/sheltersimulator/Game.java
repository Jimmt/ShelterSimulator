package com.sheltersimulator.sheltersimulator;

import android.util.Log;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Random;

public class Game {
    private ArrayList<Decision> allDecisions;
    private Random random;
    private int funds;
    private int costs;
    private int week;

    public Game(ArrayList<Decision> allDecisions){
        this.allDecisions = allDecisions;
        random = new Random();
        funds = 1000;
        costs = 0;
        week = 1;
    }

    public ArrayList<Decision> pickDecisions(){
        int numDecisions = random.nextInt(3) + 1;

        ArrayList<Decision> result = new ArrayList<>();
        for(int i = 0; i < numDecisions; i++) {
            result.add(sampleDecision());
        }
        return result;
    }

    private Decision sampleDecision(){
        return allDecisions.get(random.nextInt(allDecisions.size()));
    }

    public void runWeek() {
        //funds += etc
        //funds -= costs
        week++;
    }

    public int getFunds(){
        return funds;
    }

    public int getWeek(){
        return week;
    }

    public int getCosts(){
        return costs;
    }
}
