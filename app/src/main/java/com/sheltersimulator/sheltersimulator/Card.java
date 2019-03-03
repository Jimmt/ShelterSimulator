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
 * {@link Card.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Card#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Card extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String DECISION = "arg_decision";

    // TODO: Rename and change types of parameters
    private Decision decision;

    private OnFragmentInteractionListener mListener;

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
        final FragmentActivity fragActivity = this.getActivity();
        final View view = inflater.inflate(R.layout.fragment_card, container, false);

        TextView title = (TextView) view.findViewById(R.id.title);
        TextView description = (TextView) view.findViewById(R.id.description);
        LinearLayout buttonContainer = (LinearLayout) view.findViewById(R.id.button_container);

        title.setText("Decision");
        description.setText(decision.getQuestion());

        ArrayList<Button> answerButtons = new ArrayList<>();
        for(int i = 0; i < decision.getAnswers().size(); i++){
            buttonContainer.addView(makeSpace(1));

            Answer answer = decision.getAnswers().get(i);
            Button btn = makeAnswerButton(answer.getAnswerText(), 1);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CardView card = (CardView) view.findViewById(R.id.card_view);
                    ConstraintLayout cl = (ConstraintLayout) view.findViewById(R.id.constraint_layout);

                    View cardResult = View.inflate(fragActivity, R.layout.card_result, null);
                    card.removeAllViews();
                    card.addView(cardResult);
                }
            });
            answerButtons.add(btn);

            buttonContainer.addView(btn);
        }
        buttonContainer.addView(makeSpace(1));

        return view;
    }

    private Button makeAnswerButton(String text, int weight){
        Button btn = new Button(this.getActivity());
        btn.setText(text);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.weight = 1;
        btn.setLayoutParams(lp);
        return btn;
    }

    private Space makeSpace(int weight){
        Space space = new Space(this.getActivity());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.weight = weight;
        space.setLayoutParams(lp);
        return space;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
