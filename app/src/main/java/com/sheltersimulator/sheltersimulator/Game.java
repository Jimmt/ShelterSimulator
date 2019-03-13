package com.sheltersimulator.sheltersimulator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Game {
    private ArrayList<Decision> allDecisions;
    private ArrayList<Decision> currentDecisions;
    private Random random;
    private int funds;
    private int revenue;
    private int costs;
    private int reputation;
    private int month;
    private int week;

    private int decisionIndex;

    private GameListener gListener;

    public Game(ArrayList<Decision> allDecisions, GameListener gListener) {
        this.allDecisions = allDecisions;
        this.gListener = gListener;
        currentDecisions = new ArrayList<>();
        random = new Random();
        reputation = 50;
        revenue = 7000;
        costs = 0;
        week = 0;
        month = 1;
        decisionIndex = 0;

        applyIncomeAndCosts();
    }

    public ArrayList<Decision> getCurrentDecisions() {
        return currentDecisions;
    }

    public void pickDecisions() {
        if (decisionIndex <= allDecisions.size() - 1) {
            currentDecisions.add(allDecisions.get(decisionIndex++));
        }

//        int numDecisions = random.nextInt(2) + 1;
//        currentDecisions = sampleDecisions(numDecisions);
    }

    public void registerAnswer(Decision decision, Answer answer) {
        decision.setChoice(answer);
        costs += answer.getCost();
        reputation += answer.getReputationCost();

        if (funds < 0) {
            gListener.gameOver(this);
        }

        for (Decision d : currentDecisions) {
            if (!d.choiceSelected()) {
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
        currentDecisions.clear();

        applyIncomeAndCosts();

        if (week == 4) {
            week = 1;
            month++;
        } else {
            week++;
        }

        pickDecisions();
    }

    private void applyIncomeAndCosts() {
        funds = revenue + costs;
    }

    public int getRevenue() {
        return revenue;
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

    public int getMonth() {
        return month;
    }

    public int getReputation() {
        return reputation;
    }

    public interface GameListener {
        void gameOver(Game game);

        void decisionsDone();
    }
}
