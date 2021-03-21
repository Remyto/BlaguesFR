package com.example.blaguesfr.FilterActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.blaguesfr.JokeFragment;
import com.example.blaguesfr.R;
import com.example.blaguesfr.toolbar_fragment;

import org.json.JSONObject;

import java.util.Vector;

import androidx.appcompat.app.AppCompatActivity;

public class FilterActivity extends AppCompatActivity {
    private ListView l_categories, l_jokes;
    private Button b_search;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.FilterAct_toolbarContainer, new toolbar_fragment()).commit();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.categoriesListContainer, new JokeFragment()).commit();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.jokesListContainer, new JokeFragment()).commit();
        }

        /*l_categories = findViewById(R.id.categoriesListContainer);
        l_categories.setAdapter(new MyAdapter());
        b_search = findViewById(R.id.b_search);
        b_search.setOnClickListener(v -> {
            myAsyncTaskClass myAsyncTask = new myAsyncTaskClass();
            myAsyncTask.execute();
        });
        l_jokes = findViewById(R.id.jokesListContainer);*/
        /// TODO: set L_jokes adapter*/
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