package com.example.zhuyikun.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.zhuyikun.myapplication.Views.FloatDialog;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FloatDialog dialog = new FloatDialog();
        dialog.setOutContext(this);
        dialog.show(getSupportFragmentManager(), getLocalClassName());
    }
}

