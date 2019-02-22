package com.sheltersimulator.sheltersimulator;

public class Game {
    private int funds;
    private int costs;
    private int week;

    public Game(){
        funds = 1000;
        costs = 0;
        week = 1;
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
