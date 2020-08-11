package com.example.drugstoreapps;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class ShowAdd extends AppCompatActivity {
    DatabaseHelper dataHelper;
    LinearLayout add_Button;
    EditText text1, text2, text3, text4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_add);
        dataHelper = new DatabaseHelper(this);
        text1 = (EditText) findViewById(R.id.addCode);
        text2 = (EditText) findViewById(R.id.addName);
        text3 = (EditText) findViewById(R.id.addPrice);
        text4 = (EditText) findViewById(R.id.addAmount);
        add_Button = (LinearLayout) findViewById(R.id.saveData);


        add_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                SQLiteDatabase db = dataHelper.getWritableDatabase();
                db.execSQL("INSERT INTO drug(code, name, price, pack) VALUES('" +
                        text1.getText().toString() + "','" + text2.getText().toString() + "','" +
                        text3.getText().toString() + "','" + text4.getText().toString() + "')" );
                Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
                ShowStock.stock_Page.RefreshList();
                finish();
            }
        });
    }

    public void finishPhase(View view) {
        finish();
    }
}