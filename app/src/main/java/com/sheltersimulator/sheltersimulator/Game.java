package com.sheltersimulator.sheltersimulator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Game {
    private ArrayList<Decision> allDecisions;
    private ArrayList<Answer> userAnswers;
    private Random random;
    private int funds;
    private int costs;
    private int reputation;
    private int week;

    private GameOverListener gListener;

    public Game(ArrayList<Decision> allDecisions, GameOverListener gListener) {
        this.allDecisions = allDecisions;
        this.gListener = gListener;
        userAnswers = new ArrayList<>();
        random = new Random();
        funds = 0;
        reputation = 50;
        costs = 0;
        week = 0;
    }

    public ArrayList<Decision> pickDecisions() {
        int numDecisions = random.nextInt(2) + 1;
        return sampleDecisions(numDecisions);
    }

    public void registerAnswer(Answer answer) {
        userAnswers.add(answer);
        funds += answer.getCost();
        reputation += answer.getReputationCost();

        if (funds < 0) {
            gListener.onGameOver(this);
        }
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
            result.add(allDecisions.get(i));
        }
        return result;
    }

    public void runWeek() {
        //funds += etc
        //funds -= costs
        week++;
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

    public interface GameOverListener {
        void onGameOver(Game game);
    }
}
