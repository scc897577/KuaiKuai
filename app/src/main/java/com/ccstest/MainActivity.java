package com.ccstest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.ccstest.activity.Loading1Activity;
import com.ccstest.activity.ScrollingActivity;
import com.ccstest.activity.TreeActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void myKkD(View view){
        startActivity(new Intent(this, ScrollingActivity.class));
    }

    public void loading1(View view){
        startActivity(new Intent(this, Loading1Activity.class));
    }

    public void tree(View view){
        startActivity(new Intent(this, TreeActivity.class));
    }
}
