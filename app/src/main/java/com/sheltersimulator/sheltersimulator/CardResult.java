package com.sheltersimulator.sheltersimulator;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * A simple container for the card_result XML layout file. FrameLayout is used because it's designed
 * to hold one item.
 */
public class CardResult extends FrameLayout {

    public CardResult(Answer answer, Context context) {
        super(context);

        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.CENTER_HORIZONTAL;
        setLayoutParams(lp);

        View result = View.inflate(this.getContext(), R.layout.card_result, this);

        LinearLayout ll = findViewById(R.id.linear_layout);

        if(!answer.getImage().equals("")) {
            ImageView iv = new ImageView(this.getContext());
            iv.setLayoutParams(new android.view.ViewGroup.LayoutParams(80, 60));
            int resID = getResources().getIdentifier(answer.getImage(), "drawable", getContext().getPackageName());
            iv.setImageResource(resID);
            ll.addView(iv, 1);
        }

        TextView resultText = findViewById(R.id.result_text);
        resultText.setText(answer.getResultText());

        for (int i = 0; i < ll.getChildCount(); i++) {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) ll.getChildAt(i).getLayoutParams();
            int margin = Utils.dpToPx(this.getContext(), 8);
            params.setMargins(0, margin, 0, (i == ll.getChildCount() - 1 ? margin : 0));
        }
    }
}
