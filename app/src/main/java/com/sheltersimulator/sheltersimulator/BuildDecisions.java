package com.sheltersimulator.sheltersimulator;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;


public class BuildDecisions extends Application {
    Context ctx;
    private ArrayList<Decision> allDecisions;

    @Override
    public void onCreate() {
        super.onCreate();
        ctx = this;

        JSONArray obj = loadJSONFromAsset(ctx);
        if(obj!= null){
            allDecisions = readJsonArray(obj);
            Log.i("Data","size of array list: "+allDecisions.size());
        }

    }

    public ArrayList<Decision> getAllDecisions() {
        return allDecisions;
    }

    private ArrayList<Decision> readJsonArray(JSONArray obj) {
        ArrayList<Decision> allDecisions = null;
        try {
            for (int i = 0; i < obj.length(); i++) {
                JSONObject currDecision = obj.getJSONObject(i);

                JSONArray rangeJSON = currDecision.getJSONArray("Range");
                int[] range = new int[]{rangeJSON.getInt(0), rangeJSON.getInt(0)};

                JSONArray answersJSON = currDecision.getJSONArray("Answers");
                ArrayList<Answer> answers = new ArrayList<Answer>();
                for (int j = 0; j < answersJSON.length(); j++) {
                    JSONObject currAnswer = answersJSON.getJSONObject(i);
                    Answer ans = new Answer(currAnswer.getString("option"), currAnswer.getInt("cost"));
                    answers.add(ans);
                }
                Decision decision = new Decision(currDecision.getString("Decision"), currDecision.getString("Tyoe"),
                        range, currDecision.getBoolean("visited"), answers);
                allDecisions.add(decision);
            }
        } catch (JSONException je) {
            je.printStackTrace();
            return null;
        }
        return allDecisions;
    }

    private JSONArray loadJSONFromAsset(Context context) {
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
            ex.printStackTrace();
            return null;
        } catch (JSONException je) {
            je.printStackTrace();
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

class Answer {
    private String answerText;
    private int cost;

    Answer(String answerText, int cost){
        this.answerText = answerText;
        this.cost = cost;
    }

    public String getAnswerText() {
        return answerText;
    }

    public int getCost() {
        return cost;
    }

}