package com.example.helper;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FirstActivity extends AppCompatActivity {

    private String url;
    private RecyclerView recyclerView;
    private MyAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        if(savedInstanceState == null){
            Bundle extras = getIntent().getExtras();
            if(extras == null){
                url = null;
            }
            else
                url = extras.getString("INTENT_ONE");
        }
        else
            url = (String)savedInstanceState.getSerializable("INTENT_ONE");
        recyclerView = findViewById(R.id.recyclerView);


        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    ArrayList<Items> items = new ArrayList<>();
                    try {
                            JSONObject jsonObject=new JSONObject(response);
                            JSONArray jsonArray=jsonObject.getJSONArray("items");
                            for(int i = 0;i<jsonArray.length();i++){
                                JSONObject obj = jsonArray.getJSONObject(i);
                                JSONObject jsonSnippet = obj.getJSONObject("snippet");
                                String titleobj = (String) jsonSnippet.getString("title");
                                String discobj = (String) jsonSnippet.getString("channelTitle");
                                JSONObject imageObject = jsonSnippet.getJSONObject("thumbnails").getJSONObject("default");
                                String imageobj = (String) imageObject.getString("url");
                                String playlistId = (String) obj.getJSONObject("id").getString("playlistId");
                                items.add(new Items(titleobj,discobj,imageobj,playlistId));
                                processRequest(items);
                            }
                        } catch (JSONException e) {
                            Log.d("Error",e.toString());
                        }
                    }
                },new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error",error.toString());
                error.printStackTrace();
            }
        });

        queue.add(stringRequest);


    }



    private void processRequest(ArrayList<Items> items) {
        mAdapter = new MyAdapter(this,items);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter.setOnClickListener(new MyAdapter.onItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String Playlist_Id = items.get(position).getPlaylistId();
                Context context = getApplicationContext();
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context,Playlist_Id,duration);
                toast.show();

                Intent intent = new Intent(FirstActivity.this,SecondActivity.class);
                intent.putExtra("INTENT_TWO",Playlist_Id);
                startActivity(intent);
            }
        });
    }

}