package com.example.helper;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static com.example.helper.SecondActivity.API_KEY;

public class MainActivity extends AppCompatActivity{

    private AdapterMain mAdapter;
    private String Url;
    private String Q;
    private static ArrayList<String> querry;
    private RecyclerView recyclerView;
    private Button ADD;
    private Button DELETE;
    private EditText addQuerry;
    private EditText removeQuerry;
    private String newAddQuerry;
    private int newDeleteQuerry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_main);
        querry = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView_main);
        querry.add("C++");
        querry.add("Python");
        querry.add("Data structures | Algorithms");
        querry.add("Java");
        querry.add("Web Development");
        querry.add("Android Studio");

        ADD = findViewById(R.id.ADD);
        DELETE = findViewById(R.id.DELETE);
        addQuerry = findViewById(R.id.add_query);
        removeQuerry = findViewById(R.id.delete_querry);

        addQuerry.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                newAddQuerry = addQuerry.getText().toString();
                ADD.setEnabled(!newAddQuerry.isEmpty());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        removeQuerry.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                newDeleteQuerry = Integer.parseInt(removeQuerry.getText().toString());
                DELETE.setEnabled(!removeQuerry.getText().toString().isEmpty());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        ADD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                querry.add(newAddQuerry);
                mAdapter.notifyDataSetChanged();
            }
        });

        DELETE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    querry.remove(newDeleteQuerry);
                    mAdapter.notifyItemRemoved(newDeleteQuerry);
                }
                catch (IndexOutOfBoundsException e){
                    Context context = getApplicationContext();
                    Toast toast = Toast.makeText(context,e.toString(),Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
        super.onCreate(savedInstanceState);

        mAdapter = new AdapterMain(this,querry);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter.setOnClickListener(new MyAdapter.onItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Q = querry.get(position);
                Url = "https://www.googleapis.com/youtube/v3/search?part=snippet&maxResults=25&q="+Q+"&type=playlist&key="+API_KEY+"";
                Context context = getApplicationContext();
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context,Q,duration);
                toast.show();

                Intent intent = new Intent(MainActivity.this,FirstActivity.class);
                intent.putExtra("INTENT_ONE",Url);
                startActivity(intent);
            }
        });


    }

}