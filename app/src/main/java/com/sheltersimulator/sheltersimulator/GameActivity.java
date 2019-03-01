package com.sheltersimulator.sheltersimulator;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity implements Card.OnFragmentInteractionListener {
    private Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        game = new Game();

        ArrayList<Answer> answers = new ArrayList<>();
        answers.add(new Answer("Accept Offer", 1));
        answers.add(new Answer("Decline Offer", 2));
        String q = "A big tech company wants to add a camera to your shelter and use software to track the amount of homeless coming in and out of the shelter. " +
                "They think this software can help you streamline your system of letting people in. They say they will provide the technology free of cost, and do a trial run.";
        Decision decision = new Decision(q, "budget", new int[]{1, 5}, false, answers);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_layout, Card.newInstance(decision))
                .commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
