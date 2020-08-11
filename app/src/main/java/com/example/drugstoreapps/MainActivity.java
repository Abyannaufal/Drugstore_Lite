package com.example.drugstoreapps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    MainActivity mainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void stockPhase(View view) {
        Intent intent = new Intent(MainActivity.this, ShowStock.class);
        startActivity(intent);
    }
    public void entryPhase(View view) {
        Intent intent = new Intent(MainActivity.this, ShowEntry.class);
        startActivity(intent);
    }
    public void aboutPhase(View view) {
        Intent intent = new Intent(MainActivity.this, ShowAbout.class);
        startActivity(intent);
    }
    public void exitPhase(View view) {
        mainActivity.finish();
        System.exit(0);
    }
}