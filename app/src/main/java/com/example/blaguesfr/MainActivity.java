package com.example.blaguesfr;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoiMTg0Mzk5MDM5OTcyOTAwODY0IiwibGltaXQiOjEwMCwia2V5IjoiWWVFNzNUWmZHT0Ntd0hPNnZNUEc5V2tyV3Y2a0JrRGt3RGpTQUlYdDI0TWJycnJXcVEiLCJjcmVhdGVkX2F0IjoiMjAyMS0wMy0xOFQxNjo1ODoxNiswMDowMCIsImlhdCI6MTYxNjA4NjY5Nn0.TAKMMb52FK_W67zIS8uufaUs7DYikw5tm0xkmNWijvw";

    private Button b_getRndmJoke;
    private TextView tv_question, tv_answer;


    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Toolbar toolbar = findViewById(R.id.toolbar_fragment);
        GetJoke_OnClickLister myGetJokes_OnClickListener = new GetJoke_OnClickLister();
        b_getRndmJoke = findViewById(R.id.b_getRndmJoke);
        b_getRndmJoke.setOnClickListener(myGetJokes_OnClickListener);
        tv_question = findViewById(R.id.tv_question);
        tv_answer = findViewById(R.id.tv_answer);

    }



    /// Classes for the MainActivity
    class GetJoke_OnClickLister implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            myAsyncTaskClass myAsyncTask = new myAsyncTaskClass();
            myAsyncTask.execute();
        }
    }

    class myAsyncTaskClass extends AsyncTask <String, Void, JSONObject> {
        ///L'api de blagues utilise un token d'authentification Bearer pour les requêtes.
        // Les requêtes doivent toutes être effectuées via HTTPS. Tous les appels effectuées sans authentification ou en HTTP échoueront.

        private String readStream (InputStream is) {
            try {
                ByteArrayOutputStream bo = new ByteArrayOutputStream();

                int i = is.read();
                while(i != -1) {
                    bo.write(i);
                    i = is.read();
                }
                return bo.toString();

            } catch (IOException e) {
                e.printStackTrace();
                return "";
            }
        }

        @Override
        protected JSONObject doInBackground(String... strings) {
            /// Perform the HTTP connection, and re-instantiate the JSON object

            try{
                Log.i("doInBackground", "here");// Sending get request
                URL url = new URL("https://www.blagues-api.fr/" + "api/random");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                /// Connect
                conn.setRequestProperty("Authorization", "Bearer " + token);
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestMethod("GET");

                /// Read JSON
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String output;
                StringBuffer response = new StringBuffer();
                while ((output = in.readLine()) != null) {
                    response.append(output);
                }
                in.close();

                // printing result from response
                JSONObject jObjt = new JSONObject(response.toString());
                System.out.println("Response:-" + response.toString());
                return jObjt;

            }
            catch (MalformedURLException e){
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(JSONObject JObjt) {
            /// Get Joke from JSON and set joke to textView

            String errorMsg = "JSON element could not be found.";
            String question, answer;


            try {
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

            tv_question.setText(question);
            tv_answer.setText(answer);
        }
    }
}
