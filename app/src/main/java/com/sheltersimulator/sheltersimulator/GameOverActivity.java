package com.sheltersimulator.sheltersimulator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameOverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        int funds = (Integer) getIntent().getExtras().get("funds");
        int reputation = (Integer) getIntent().getExtras().get("reputation");

        String gameOverText = getResources().getString(R.string.game_over_text);
        gameOverText = String.format(gameOverText, funds, reputation);

        TextView textView = findViewById(R.id.text_view);
        textView.setText(gameOverText);

        Button restartButton = findViewById(R.id.restart_button);
        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameOverActivity.this, StartActivity.class);
                startActivity(intent);
            }
        });
    }
}
