package com.sheltersimulator.sheltersimulator;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;

/**
 * A simple container for the card_result XML layout file. FrameLayout is used because it's designed
 * to hold one item.
 */
public class CardResult extends FrameLayout {

    public CardResult(Answer answer, Context context) {
        super(context);

        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT);
        setLayoutParams(lp);

        View result = View.inflate(this.getContext(), R.layout.card_result, this);
    }
}
