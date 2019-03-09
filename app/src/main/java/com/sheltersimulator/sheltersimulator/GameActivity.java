package com.sheltersimulator.sheltersimulator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONException;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity implements Card.OnCardCompleteListener, Game.GameOverListener {
    private Game game;
    private static final String TAG = GameActivity.class.getName();
    private ArrayList<Card> cards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        try {
            game = new Game(BuildDecisions.getAllDecisions(this), this);
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }

        cards = new ArrayList<>();
        runWeek();

        //        ArrayList<Answer> answers = new ArrayList<>();
        //        answers.add(new Answer("Accept Offer", 1));
        //        answers.add(new Answer("Decline Offer", 2));
        //        String q = "A big tech company wants to add a camera to your shelter and use software to track the amount of homeless coming in and out of the shelter. " +
        //                "They think this software can help you streamline your system of letting people in. They say they will provide the technology free of cost, and do a trial run.";
        //        Decision decision = new Decision(q, "other", new int[]{1, 5}, false, answers);
    }

    private void runWeek(){
        game.runWeek();
        addCards();
        updateGameText();
    }

    private void updateGameText(){
        TextView weekText = findViewById(R.id.week_text);
        weekText.setText("Week " + game.getWeek());
        TextView fundsText = findViewById(R.id.funds_text);
        fundsText.setText("Funds: " + game.getFunds());
        TextView reputationText = findViewById(R.id.reputation_text);
        reputationText.setText("Reputation: " + game.getReputation());
    }

    private void addCards() {
        ArrayList<Decision> decisions = game.pickDecisions();
        for (Decision d : decisions) {
            addCard(Card.newInstance(d));
        }
    }

    public void addCard(Card card) {
        LinearLayout cardContainer = findViewById(R.id.linear_layout);

        FrameLayout frameLayout = new FrameLayout(this);
        frameLayout.setId(ViewCompat.generateViewId());
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 0, 0, Utils.dpToPx(this, 17));
        frameLayout.setLayoutParams(lp);

        cardContainer.addView(frameLayout);

        getSupportFragmentManager().beginTransaction()
                .add(frameLayout.getId(), card)
                .commit();
        cards.add(card);
    }

    @Override
    public void onCardComplete(Decision decision, Answer answer) {
        game.registerAnswer(answer);
        updateGameText();
    }

    @Override
    public void onGameOver(Game game) {
        Intent intent = new Intent(GameActivity.this, GameOverActivity.class);
        intent.putExtra("funds", game.getFunds());
        intent.putExtra("reputation", game.getReputation());
        startActivity(intent);
    }
}
