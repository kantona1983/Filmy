package com.basha.karim.filmy;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.basha.karim.filmy.data.Contract;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

public class MoviesDetails extends AppCompatActivity {

    String Id;
    String title;
    String decription;
    String releaseDate;
    String posterUrl;
    static TextView rev;
    static String key;
    static String moviesreview;
    ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_details);
        scrollView=(ScrollView)findViewById(R.id.scroll);
        Intent intent = getIntent();
        Id=intent.getStringExtra("ID");
        title = intent.getStringExtra("title");
        decription = intent.getStringExtra("decription");
        releaseDate = intent.getStringExtra("release_date");
        posterUrl = intent.getStringExtra("poster_url");
        double voteAverage = intent.getDoubleExtra("vote_average", 0.0);
        ImageView Image = findViewById(R.id.iv_film);
        Picasso.with(getApplicationContext()).load(posterUrl).into(Image);
        TextView Title = findViewById(R.id.tv_original);
        Title.setText(title);
        TextView ReleaseDate= findViewById(R.id.tv_relase);
        ReleaseDate.setText(releaseDate);
        TextView voteTextView = findViewById(R.id.tv_vote);
        voteTextView.setText(String.valueOf(voteAverage));
        TextView plot = findViewById(R.id.tv_description);
        plot.setText(decription);
        MovieAppAsyncTask task=new MovieAppAsyncTask();
        task.execute(ApiData.baseRequest+Id+ApiData.review+ApiData.apiKey);
        rev=(TextView)findViewById(R.id.review);
        MovietrailAsyncTask task2=new MovietrailAsyncTask();
        task2.execute(ApiData.baseRequest+Id+ApiData.video+ApiData.apiKey);
        Button trail=(Button)findViewById(R.id.bt_trailer);
        trail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(ApiData.videoBase+key));
                startActivity(i);

            }
        });



    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putIntArray("ARTICLE_SCROLL_POSITION",
                new int[]{ scrollView.getScrollX(), scrollView.getScrollY()});
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        final int[] position = savedInstanceState.getIntArray("ARTICLE_SCROLL_POSITION");
        if(position != null)
            scrollView.post(new Runnable() {
                public void run() {
                    scrollView.scrollTo(position[0], position[1]);
                }
            });
    }
    public void AddFavorite(View view) {
        ContentValues values=new ContentValues();
        values.put(Contract.MovieEntry.COLUMN_NAME,title);
        values.put(Contract.MovieEntry.COLUMN_MOVIEID,Id);
        getContentResolver().insert(Contract.MovieEntry.CONTENT_URI,values);
        Toast.makeText(this, "Movie add to favorite  ", Toast.LENGTH_LONG).show();

    }


    private class MovieAppAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            if (strings.length < 1 || strings[0] == null) {
                return null;
            }
            return fetchMovieAppData(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            rev.setText(moviesreview);

        }
    }
    private class MovietrailAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            if (strings.length < 1 || strings[0] == null) {
                return null;
            }
            return fetchMovietrialData(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }


    public static String fetchMovieAppData(String requestUrl) {
        URL url = JsonUtl.makeUrl(requestUrl);
        String jsonResponse = null;
        try {
            jsonResponse = JsonUtl.makeHttpRequest(url);

            Log.e("karim","result"+jsonResponse);

        } catch (IOException e) {
            Log.e("", "Throw an Exception in fetchMovieAppData", e);
        }
        return extractreveiwData(jsonResponse);
    }
    private static String extractreveiwData(String movieJson) {
        if (TextUtils.isEmpty(movieJson)) {
            return null;
        }
        moviesreview=null;
        try {
            JSONObject root = new JSONObject(movieJson);
            JSONArray resultArray = root.getJSONArray("results");
            for (int i = 0; i < resultArray.length(); i++) {
                JSONObject resultObject = resultArray.getJSONObject(i);
                moviesreview=resultObject.getString("content");
            }

        } catch (JSONException e) {
            Log.e("", "Throw an Exception in extractMovieData", e);
        }

        return moviesreview;
    }

    private static String extracttrialData(String movieJson) {
        if (TextUtils.isEmpty(movieJson)) {
            return null;
        }
        key =null;
        try {
            JSONObject root = new JSONObject(movieJson);
            JSONArray resultArray = root.getJSONArray("results");
            for (int i = 0; i < resultArray.length(); i++) {
                JSONObject resultObject = resultArray.getJSONObject(i);
                key=resultObject.getString("key");
            }

        } catch (JSONException e) {
            Log.e("", "Throw an Exception in extractMovieData", e);
        }

        return key;
    }
    public static String fetchMovietrialData(String requestUrl) {
        URL url = JsonUtl.makeUrl(requestUrl);
        String jsonResponse = null;
        try {
            jsonResponse = JsonUtl.makeHttpRequest(url);

            Log.e("karim","result"+jsonResponse);

        } catch (IOException e) {
            Log.e("", "Throw an Exception in fetchMovieAppData", e);
        }
        return extracttrialData(jsonResponse);
    }
}
