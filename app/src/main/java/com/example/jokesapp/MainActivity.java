package com.example.jokesapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    TextView tvjoke;
    TextView jokeType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvjoke = findViewById(R.id.textViewJoke);
        jokeType = findViewById(R.id.textViewJokeType);
//        ImageButton ibRefreshJoke = ibRefreshJoke = findViewById(R.id.imageButtonRefreshJoke);

        new AsyncGetJoke().execute();

//        ibRefreshJoke.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                new AsyncGetJoke().execute();
//            }
//        });
    }

    public void NewJoke(View v){
        new AsyncGetJoke().execute();
    }

    public String getJoke() {
        // make get request to api
        String resp = "";

        try {
            // url object
            URL url = new URL("https://official-joke-api.appspot.com/jokes/programming/random");
            //creates new scanner which opens a connection to the url and returns input in the UTF-8 format
            resp = new Scanner(url.openStream(),"UTF-8")
                    .useDelimiter("\\A")
                    .next();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return resp;
    }

    class AsyncGetJoke extends AsyncTask<Void, Void, String>{

        @Override
        protected String doInBackground(Void... voids) {
            return getJoke();
        }

        @Override
        protected void onPostExecute(String s) {
            Gson gson = new Gson();

            //Check what type - get array list of joke objects - call it jokes
            ArrayList<Joke> jokes = gson.fromJson(s, new TypeToken<List<Joke>>(){}.getType());

            Log.i(TAG, "onPostExecute: " + jokes.get(0).getSetup());
            Log.i(TAG, "onPostExecute: " + jokes.get(0).getPunchLine());

            tvjoke.setText(jokes.get(0).getSetup() + " - " + jokes.get(0).getPunchLine());
            jokeType.setText(jokes.get(0).getType());
        }
    }

}
