package com.sheltersimulator.sheltersimulator;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Card.OnCardCompleteListener} interface
 * to handle interaction events.
 * Use the {@link Card#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Card extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String DECISION = "arg_decision";

    private Decision decision;

    private OnCardCompleteListener mListener;

    public Card() {
        // Required empty public constructor
    }


    public static Card newInstance(Decision decision) {
        Card fragment = new Card();
        Bundle args = new Bundle();
        args.putSerializable(DECISION, decision);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            decision = (Decision) getArguments().getSerializable(DECISION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_card, container, false);

        TextView title = (TextView) view.findViewById(R.id.title);
        TextView description = (TextView) view.findViewById(R.id.description);

        title.setText("Decision");
        description.setText(decision.getQuestion());

        addAnswerButtons(getActivity(), view);

        return view;
    }

    private void addAnswerButtons(final Context context, View view) {
        LinearLayout buttonContainer = (LinearLayout) view.findViewById(R.id.button_container);
        ArrayList<Button> answerButtons = new ArrayList<>();
        for (int i = 0; i < decision.getAnswers().size(); i++) {
//            buttonContainer.addView(makeSpace(1));

            final Answer answer = decision.getAnswers().get(i);

            Button btn = makeAnswerButton(answer.getAnswerText(), 1);
            btn.setOnClickListener(new AnswerClickListener(mListener, view, decision, answer));
            answerButtons.add(btn);

            buttonContainer.addView(btn);
        }
//        buttonContainer.addView(makeSpace(1));
    }

    private Button makeAnswerButton(String text, int weight) {
        Button btn = new Button(this.getActivity());
        btn.setText(text);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        // For horizontal
//        lp.width = 0;
//        lp.weight = 1;
        btn.setLayoutParams(lp);
        return btn;
    }

    private Space makeSpace(int weight) {
        Space space = new Space(this.getActivity());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.weight = weight;
        space.setLayoutParams(lp);
        return space;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnCardCompleteListener) {
            mListener = (OnCardCompleteListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnCardCompleteListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnCardCompleteListener {
        void onCardComplete(Decision decision, Answer answer);
    }
}
