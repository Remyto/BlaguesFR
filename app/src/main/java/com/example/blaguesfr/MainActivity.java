package com.example.blaguesfr;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.blaguesfr.FilterActivity.FilterActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private final String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoiMTg0Mzk5MDM5OTcyOTAwODY0IiwibGltaXQiOjEwMCwia2V5IjoiWWVFNzNUWmZHT0Ntd0hPNnZNUEc5V2tyV3Y2a0JrRGt3RGpTQUlYdDI0TWJycnJXcVEiLCJjcmVhdGVkX2F0IjoiMjAyMS0wMy0xOFQxNjo1ODoxNiswMDowMCIsImlhdCI6MTYxNjA4NjY5Nn0.TAKMMb52FK_W67zIS8uufaUs7DYikw5tm0xkmNWijvw";

    private Button b_getRndmJoke;
    private TextView tv_jokeID, tv_jokeType, tv_question, tv_answer;

    private Button b_filterActivity;


    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b_getRndmJoke = findViewById(R.id.b_getRndmJoke);
        b_getRndmJoke.setOnClickListener(v -> {
            myAsyncTaskClass myAsyncTask = new myAsyncTaskClass();
            myAsyncTask.execute();
        });
        tv_jokeID = findViewById(R.id.tv_jokeID);
        tv_jokeType = findViewById(R.id.tv_jokeType);
        tv_question = findViewById(R.id.tv_question);
        tv_answer = findViewById(R.id.tv_answer);

        b_filterActivity = findViewById(R.id.b_filterActivity);
        b_filterActivity.setOnClickListener(v -> {
            Intent intent = new Intent(this, FilterActivity.class);
            startActivity(intent);
        });
    }



    /// Classes for the MainActivity

    private class myAsyncTaskClass extends AsyncTask <String, Void, JSONObject> {
        ///L'api de blagues utilise un token d'authentification Bearer pour les requêtes.
        // Les requêtes doivent toutes être effectuées via HTTPS. Tous les appels effectuées sans authentification ou en HTTP échoueront.

        @Override
        protected JSONObject doInBackground(String... strings) {
            /// Perform the HTTP connection, and re-instantiate the JSON object

            try{
                /// Send get request
                URL url = new URL("https://www.blagues-api.fr/" + "api/random");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                /// Connect
                conn.setRequestProperty("Authorization", "Bearer " + token);
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestMethod("GET");

                /// Read JSON
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String output;
                StringBuilder response = new StringBuilder();
                while ((output = in.readLine()) != null) {
                    response.append(output);
                }
                in.close();

                // printing result from response
                JSONObject jObjt = new JSONObject(response.toString());
                System.out.println("Response:-" + response.toString());
                return jObjt;

            }
            catch (JSONException | IOException e){
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(JSONObject JObjt) {
            /// Get Joke from JSON and set joke to textView

            /// TODO: Add JsonObject to user history

            String errorMsg = "JSON element could not be found.";
            String id, type, question, answer;
            /// JSON file contains the following:
            //  "id": int, "type", "joke": String, "answer": String

            /// Prevent crashes
            if (JObjt == null) {
                id = errorMsg;
                type = errorMsg;
                question = errorMsg;
                answer = errorMsg;
            }
            else {
                /// Get Data from JSON object
                try {
                    id = JObjt.getString("id");
                } catch (JSONException e) {
                    e.printStackTrace();
                    id = errorMsg;
                }
                try {
                    type = JObjt.getString("type");
                } catch (JSONException e) {
                    e.printStackTrace();
                    type = errorMsg;
                }try {
                    question = JObjt.getString("joke");
                } catch (JSONException e) {
                    e.printStackTrace();
                    question = errorMsg;
                }
                try {
                    answer = JObjt.getString("answer");
                } catch (JSONException e) {
                    e.printStackTrace();
                    answer = errorMsg;
                }
            }

            /// Set data to views
            tv_jokeID.setText(id);
            tv_jokeType.setText(type);
            tv_question.setText(question);
            tv_answer.setText(answer);
        }
    }
}
