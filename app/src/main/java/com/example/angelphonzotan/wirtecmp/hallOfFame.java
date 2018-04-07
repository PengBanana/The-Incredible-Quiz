package com.example.angelphonzotan.wirtecmp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

/**
 * Created by Anjoh on 12/03/2018.
 */

public class hallOfFame extends AppCompatActivity {

    private ArrayList<User> UserList = new ArrayList<>();
    private RecyclerView recyclerView;
    private UserAdapter mAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.halloffame);
        recyclerView = (RecyclerView) findViewById(R.id.winners);

        mAdapter = new UserAdapter(UserList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        Button b = findViewById(R.id.menu);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goMainMenu();

            };
        });
        prepareUserData();

    }

private void prepareUserData() {

        mAdapter.notifyDataSetChanged();
    }

    private void goMainMenu(){
        finish();
    }
}
