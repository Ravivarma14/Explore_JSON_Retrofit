package com.example.jsonexample;

import androidx.annotation.LongDef;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.jsonexample.Retrofit.JsonResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.LongFunction;

public class MainActivity extends AppCompatActivity {

    ListView ls;

    RecyclerView rc;
    ListView lsurl;
    Button movie_btn,retrofit_btn;

    String JSON_URL="https://run.mocky.io/v3/96a5500d-ff19-4c37-96a8-7a7ba17a9ad3";

    ArrayList<HashMap<String,String>> listfromurl=new ArrayList<>();

    ArrayList<String> names=new ArrayList<>();
    ArrayList<String> emails=new ArrayList<>();
    ArrayList<String> mobiles=new ArrayList<>();

    String json_string="{\n" +
            "\"title\": \"Json Tutorial\" ,\n " +
            "\"array\": [\n" +
            "{\n" +
            "\"company\" : \"Google\" \n " +
            "},\n" +
            "{\n" +
            "\"company\" : \"Microsoft\" \n" +
            "},\n" +
            "{\n" +
            "\"company\" : \"LinkedIn\" \n" +
            "}\n" +
            "]\n" +
            "}";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ls=findViewById(R.id.list_view);
        rc=findViewById(R.id.recycler_view);
        lsurl=findViewById(R.id.listview_url);
        movie_btn=findViewById(R.id.movie_list_btn);
        retrofit_btn=findViewById(R.id.json_from_retrofit);

        movie_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,MovieListActivity.class);
                startActivity(intent);
            }
        });

        retrofit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, JsonResponse.class);
                startActivity(intent);
            }
        });


        try {
            JSONObject jsonObj = new JSONObject(json_string);

            List<String> list=new ArrayList<>();

            JSONArray arr=jsonObj.getJSONArray("array");

            Log.d("varma","length: "+arr.length());

            this.setTitle(jsonObj.getString("title"));

            for(int i=0;i<arr.length();i++){
                JSONObject ob=arr.getJSONObject(i);
                Log.d("varma","obj: "+ob + "i= "+ i);
                String str=ob.getString("company");
                list.add(str);
            }
            Log.d("varma","after loop");

            if(ls!=null){
                ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,list);
                ls.setAdapter(adapter);
            }

        }
        catch (Exception e){
            Log.d("varma",e.toString());
        }


        //Recycler view part

        rc.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        try {


            JSONObject object=new JSONObject(getJsonfromAssets());

            JSONArray array=object.getJSONArray("users");

            for(int i=0;i<array.length();i++){
                JSONObject obj=array.getJSONObject(i);
                names.add(obj.getString("name"));
                emails.add(obj.getString("email"));

                JSONObject ob=obj.getJSONObject("contact");
                mobiles.add(ob.getString("mobile"));
            }

            RecyclerViewAdapter recyclerViewAdapter=new RecyclerViewAdapter(MainActivity.this,names,emails,mobiles);
            rc.setAdapter(recyclerViewAdapter);

        }
        catch (Exception e){
            Log.d("varma","recycler: "+e.toString());
        }

        setUpListFromUrl();

    }

    private String getJsonfromAssets(){

        String json=null;
        try{
            InputStream in=getAssets().open("users_lists.json");

            int size=in.available();
            byte buffer[]=new byte[size];

            in.read(buffer);
            in.close();

            json=new String(buffer,"UTF-8");
        }
        catch (Exception e){
            Log.d("varma","asset: "+e.toString());
        }

        return json;
    }

    private void setUpListFromUrl(){

        GetData getData=new GetData();
        getData.execute();

    }

    public class GetData extends AsyncTask<String,String,String> {

        @Override
        protected String doInBackground(String... strings) {

            String current="";

            try {

                URL url;
                HttpURLConnection urlc=null;


                try {

                    url = new URL(JSON_URL);
                    urlc = (HttpURLConnection) url.openConnection();

                    InputStream in = urlc.getInputStream();
                    InputStreamReader inr = new InputStreamReader(in);

                    int data = inr.read();
                    while ((data != -1)) {
                        current += (char) data;
                        data = inr.read();
                    }
                }
                catch (Exception e){
                    Log.d("varma",e.toString());
                }
                finally {
                    if(urlc!=null)
                        urlc.disconnect();
                }

            }
            catch (Exception e){
                Log.d("varma",e.toString());
            }

            return current;
        }

        @Override
        protected void onPostExecute(String s) {

            try {
                JSONObject jsonObject=new JSONObject(s);
                JSONArray jsonArray=jsonObject.getJSONArray("Friends");

                for(int i=0;i<jsonArray.length();i++){
                    JSONObject object=jsonArray.getJSONObject(i);

                    String str1=object.getString("name");
                    String str2=object.getString("age");

                    HashMap<String,String> entry=new HashMap<>();
                    entry.put("name",str1);
                    entry.put("age",str2);
                    listfromurl.add(entry);
                }
            }
            catch (Exception e){
                Log.d("varma",e.toString());
            }

            //ListAdapter lsAdapter=new SimpleAdapter(MainActivity.this, listfromurl,R.layout.recycler_itemview,new String[] {"name","age"},new int[] {R.id.name, R.id.mobile});

            ListAdapter lsAdapter=new SimpleAdapter(MainActivity.this, listfromurl,R.layout.list_itemview,new String[] {"name","age"},new int[] {R.id.name_url, R.id.age_url});
            lsurl.setAdapter(lsAdapter);
        }
    }
}