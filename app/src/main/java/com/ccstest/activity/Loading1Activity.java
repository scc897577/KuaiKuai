package com.ccstest.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ccstest.R;
import com.ccstest.views.Loading1View;

public class Loading1Activity extends AppCompatActivity {

    private Loading1View mView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading1);

        mView = (Loading1View) findViewById(R.id.lv);
    }
}
