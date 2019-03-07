package com.sheltersimulator.sheltersimulator;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;


public class BuildDecisions {
    private static ArrayList<Decision> allDecisions;
    private static final String TAG = BuildDecisions.class.getName();

    public static ArrayList<Decision> getAllDecisions(Context ctx) throws JSONException {
        if (allDecisions == null) {
            JSONArray obj = loadJSONFromAsset(ctx);
            if (obj != null) {
                allDecisions = readJsonArray(obj);
                return allDecisions;
            }
            throw new JSONException("Failed to load decisions from JSON");
        } else {
            return allDecisions;
        }
    }

    private static ArrayList<Decision> readJsonArray(JSONArray obj) {
        ArrayList<Decision> allDecisions = new ArrayList<>();
        try {
            for (int i = 0; i < obj.length(); i++) {
                JSONObject currDecision = obj.getJSONObject(i);

                JSONArray rangeJSON = currDecision.getJSONArray("range");
                int[] range = new int[]{rangeJSON.getInt(0), rangeJSON.getInt(0)};

                JSONArray answersJSON = currDecision.getJSONArray("answers");
                ArrayList<Answer> answers = new ArrayList<>();
                for (int j = 0; j < answersJSON.length(); j++) {
                    JSONObject currAnswer = answersJSON.getJSONObject(j);
                    Answer ans = new Answer(currAnswer.getString("option"),
                            currAnswer.getString("result_text"),
                            currAnswer.getInt("cost"),
                            currAnswer.getInt("reputation_cost"));
                    answers.add(ans);
                }
                Decision decision = new Decision(currDecision.getString("decision"), currDecision.getString("type"),
                        range, currDecision.getBoolean("visited"), answers);
                allDecisions.add(decision);
            }
        } catch (JSONException je) {
            Log.e(TAG, je.getMessage());
            return null;
        }
        return allDecisions;
    }

    private static JSONArray loadJSONFromAsset(Context context) {
        JSONArray obj;
        try {
            InputStream is = context.getAssets().open("data.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
//            json = new String(buffer, "UTF-8");
            obj = new JSONArray(new String(buffer, "UTF-8"));
        } catch (IOException ex) {
            Log.e(TAG, ex.getMessage());
            return null;
        } catch (JSONException je) {
            Log.e(TAG, je.getMessage());
            return null;
        }
        return obj;
    }
}


class Decision implements Serializable {

    private String question;
    private String type;
    private int[] range;
    private boolean visited;
    private ArrayList<Answer> answers;

    Decision(String question, String type, int[] range, boolean visited, ArrayList<Answer> answers) {
        this.question = question;
        this.type = type;
        this.range = range;
        this.visited = visited;
        this.answers = answers;
    }


    public String getQuestion() {
        return question;
    }

    public String getType() {
        return type;
    }

    public int[] getRange() {
        return range;
    }

    public boolean getVisited() {
        return visited;
    }

    public ArrayList<Answer> getAnswers() {
        return answers;
    }

}

class Answer implements Serializable {
    private String answerText;
    private String resultText;
    private int cost;
    private int reputationCost;

    Answer(String answerText, String resultText, int cost, int reputationCost) {
        this.answerText = answerText;
        this.resultText = resultText;
        this.cost = cost;
        this.reputationCost = reputationCost;
    }

    public String getResultText() { return resultText; }

    public String getAnswerText() {
        return answerText;
    }

    public int getCost() {
        return cost;
    }

    public int getReputationCost() { return reputationCost; }

}