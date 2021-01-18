package com.example.helper;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.google.android.youtube.player.YouTubePlayer.ErrorReason;
import static com.google.android.youtube.player.YouTubePlayer.OnInitializedListener;
import static com.google.android.youtube.player.YouTubePlayer.PlaybackEventListener;
import static com.google.android.youtube.player.YouTubePlayer.PlayerStateChangeListener;
import static com.google.android.youtube.player.YouTubePlayer.Provider;

public class SecondActivity extends YouTubeBaseActivity implements OnInitializedListener {

    private MyAdapter mAdapter;
    private RecyclerView recyclerView;
    protected static final String API_KEY = "AIzaSyBFuzYK-TOjO9kQCvm7iJNCwtab0bKwaSE";
    private String Playlist_Id;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        if(savedInstanceState == null){
            Bundle extras = getIntent().getExtras();
            if(extras == null){
                Playlist_Id = null;
            }
            else
                Playlist_Id = extras.getString("INTENT_TWO");
        }
        else
            Playlist_Id = (String)savedInstanceState.getSerializable("INTENT_TWO");

        recyclerView = findViewById(R.id.List);
        url = "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId="+Playlist_Id+"&key="+API_KEY+"&maxResults=20";

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
                        items.add(new Items(titleobj,discobj,imageobj));
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
        YouTubePlayerView youTubePlayerView = findViewById(R.id.Youtube_player);
        youTubePlayerView.initialize(API_KEY, this);
    }


    private void processRequest(ArrayList<Items> items) {
        mAdapter = new MyAdapter(this,items);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


    }

    @Override
    public void onInitializationSuccess(Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        youTubePlayer.setPlayerStateChangeListener(playerStateChangeListener);
        youTubePlayer.setPlaybackEventListener(playbackEventListener);

        if(!b){
            youTubePlayer.cuePlaylist(Playlist_Id);
        }
    }


    @Override
    public void onInitializationFailure(Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        Toast toast = Toast.makeText(this,"Error in playing playlist",Toast.LENGTH_SHORT);
        toast.show();
    }

    private PlaybackEventListener playbackEventListener = new PlaybackEventListener() {
        @Override
        public void onPlaying() {

        }

        @Override
        public void onPaused() {

        }

        @Override
        public void onStopped() {

        }

        @Override
        public void onBuffering(boolean b) {

        }

        @Override
        public void onSeekTo(int i) {

        }
    };
    private PlayerStateChangeListener playerStateChangeListener = new PlayerStateChangeListener() {
        @Override
        public void onLoading() {

        }

        @Override
        public void onLoaded(String s) {

        }

        @Override
        public void onAdStarted() {

        }

        @Override
        public void onVideoStarted() {

        }

        @Override
        public void onVideoEnded() {

        }

        @Override
        public void onError(ErrorReason errorReason) {

        }
    };

}