package com.sheltersimulator.sheltersimulator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Game {
    private ArrayList<Decision> allDecisions;
    private ArrayList<Decision> currentDecisions;
    private Random random;
    private int funds;
    private int costs;
    private int reputation;
    private int week;

    private GameListener gListener;

    public Game(ArrayList<Decision> allDecisions, GameListener gListener) {
        this.allDecisions = allDecisions;
        this.gListener = gListener;
        currentDecisions = new ArrayList<>();
        random = new Random();
        funds = 1000;
        reputation = 50;
        costs = 0;
        week = 0;
    }

    public ArrayList<Decision> getCurrentDecisions(){
        return currentDecisions;
    }

    public void pickDecisions() {
        int numDecisions = random.nextInt(2) + 1;
        currentDecisions = sampleDecisions(numDecisions);
    }

    public void registerAnswer(Decision decision, Answer answer) {
        decision.setChoice(answer);
        funds += answer.getCost();
        reputation += answer.getReputationCost();

        if (funds < 0) {
            gListener.gameOver(this);
        }

        for(Decision d: currentDecisions){
            if(!d.choiceSelected()){
                return;
            }
        }
        // Only executes if all decisions return true for choiceSelected()
        gListener.decisionsDone();
    }

    /**
     * Sample n decisions, without repeats, from the set of all decisions.
     * TODO: restrict the set of decisions
     */
    private ArrayList<Decision> sampleDecisions(int n) {
        ArrayList<Integer> indices = new ArrayList<>();
        // Build a list 0, 1, 2, 3...
        for (int i = 0; i < allDecisions.size(); i++) {
            indices.add(i);
        }
        // Shuffle the indices into random order
        Collections.shuffle(indices);

        ArrayList<Decision> result = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            Decision d = allDecisions.get(i);
            d.setChoice(null);
            result.add(d);
        }
        return result;
    }

    public void runWeek() {
        //funds += etc
        //funds -= costs
        currentDecisions.clear();
        week++;
        pickDecisions();
    }

    public int getFunds() {
        return funds;
    }

    public int getWeek() {
        return week;
    }

    public int getCosts() {
        return costs;
    }

    public int getReputation() {
        return reputation;
    }

    public interface GameListener {
        void gameOver(Game game);
        void decisionsDone();
    }
}
