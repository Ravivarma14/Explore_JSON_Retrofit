package com.example.jsonexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MovieListActivity extends AppCompatActivity {

    private ArrayList<MovieModel> movieslist=new ArrayList<>();
    private static String JSON_URL="https://run.mocky.io/v3/50c86b0c-5a41-4887-8c18-cf889e5453f2";

    private RecyclerView moveisrv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        moveisrv=findViewById(R.id.movies_rv);

        GetJson getJson=new GetJson();
        getJson.execute();

    }


    public class GetJson extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... strings) {
            String json_str="";

            URL url=null;
            HttpURLConnection httpURLConnection=null;

            try{
                url=new URL(JSON_URL);
                httpURLConnection= (HttpURLConnection) url.openConnection();

                InputStream is= httpURLConnection.getInputStream();
                InputStreamReader isr=new InputStreamReader(is);

                int data=isr.read();
                while (data!=-1){
                    json_str+= (char) data;
                    data=isr.read();
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
                Log.d("varma",e.toString());
            } catch (IOException e) {
                e.printStackTrace();
                Log.d("varma",e.toString());
            }
            finally {
                if(httpURLConnection!=null){
                    httpURLConnection.disconnect();
                }
            }

            return json_str;
        }

        @Override
        protected void onPostExecute(String s) {
            String json=s;
            Log.d("varma","json: "+json);
            try {
                JSONObject jsonObject = new JSONObject(json);
                JSONArray jsonArray = jsonObject.getJSONArray("movies");

                for(int i=0;i<jsonArray.length();i++){
                    JSONObject obj=jsonArray.getJSONObject(i);

                    MovieModel entry=new MovieModel();
                    entry.movie_name=obj.getString("name");
                    entry.year=obj.getString("year");
                    entry.imgurl=obj.getString("imgurl");

                    movieslist.add(entry);
                }

                MoviesAdapter adapter=new MoviesAdapter(MovieListActivity.this,movieslist);
                moveisrv.setLayoutManager(new LinearLayoutManager(MovieListActivity.this));
                moveisrv.setAdapter(adapter);



            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("varma","js: "+ e.toString());
            }

        }
    }
}