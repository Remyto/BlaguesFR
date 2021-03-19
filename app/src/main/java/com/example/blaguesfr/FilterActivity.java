package com.example.blaguesfr;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONObject;

import java.util.Vector;

import androidx.appcompat.app.AppCompatActivity;

public class FilterActivity extends AppCompatActivity {
    private Button b_search;
    private ListView l_categories;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        b_search = findViewById(R.id.b_search);
        b_search.setOnClickListener(v -> {
            l_categories.setAdapter(new MyAdapter());
            myAsyncTaskClass myAsyncTask = new myAsyncTaskClass();
            myAsyncTask.execute();
        });
    }


    /// Classes for the FilterActivity

    private class MyAdapter extends BaseAdapter {
        private final Vector <JSONObject> jokes;


        public MyAdapter () { jokes = new Vector<>();}


        public int getCount() {
            return jokes.size();
        }

        @Override
        public Object getItem(int position) {
            return jokes.get(position);
        }

        @Override
        public long getItemId(int position) { return 0; }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            /// TODO
            return null;
        }
    }

    private class myAsyncTaskClass extends AsyncTask<String, Void, JSONObject> {

        @Override
        protected JSONObject doInBackground(String... strings) {
            /// TODO
            return null;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            /// TODO
        }
    }
}