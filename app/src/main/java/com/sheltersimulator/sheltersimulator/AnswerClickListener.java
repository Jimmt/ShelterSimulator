package com.sheltersimulator.sheltersimulator;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.view.View;

public class AnswerClickListener implements View.OnClickListener {
    private Card.OnCardCompleteListener mListener;
    private Answer answer;
    private View cardView;

    public AnswerClickListener(Card.OnCardCompleteListener mListener, View cardView, Answer answer) {
        this.mListener = mListener;
        this.cardView = cardView;
        this.answer = answer;
    }

    @Override
    public void onClick(View view) {
        CardView card = (CardView) cardView.findViewById(R.id.card_view);
        ConstraintLayout cl = (ConstraintLayout) cardView.findViewById(R.id.constraint_layout);

        card.removeAllViews();

        CardResult cr = new CardResult(answer, cardView.getContext());
        card.addView(cr);

        if (mListener != null) {
            mListener.onCardComplete();
        }
    }
}
