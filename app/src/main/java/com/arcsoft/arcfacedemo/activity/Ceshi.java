package com.arcsoft.arcfacedemo.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.arcsoft.arcfacedemo.ExitApplication;
import com.arcsoft.arcfacedemo.R;

public class Ceshi extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ceshi);

        exitActivity();
    }
    private void exitActivity() {
        ExitApplication.getInstance().addActivity(Ceshi.this);
    }
}
