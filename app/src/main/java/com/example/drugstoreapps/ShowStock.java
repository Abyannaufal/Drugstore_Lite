package com.example.drugstoreapps;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

public class ShowStock extends AppCompatActivity {
    public static ShowStock stock_Page;
    protected Cursor cursor;
    String[] showList, getId;
    ListView listView;
    DatabaseHelper dataHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_stock);
        stock_Page = this;
        dataHelper = new DatabaseHelper(this);
        RefreshList();
    }

    public void RefreshList() {

        SQLiteDatabase db = dataHelper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM drug", null);
        showList = new String[cursor.getCount()];
        getId = new String[cursor.getCount()];
        cursor.moveToFirst();
        for (int cc = 0; cc < cursor.getCount(); cc++) {
            cursor.moveToPosition(cc);
            showList[cc] = cursor.getString(2).toString();
            //Tampilkan kolom ke 2 pada listview
        }
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, showList));
        listView.setSelected(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView arg0, View arg1, int arg2, long arg3) {
                final String selection = showList[arg2]; //.getItemAtPosition(arg2).toString();
                final CharSequence[] dialogitem = {"Update Data", "Delete Data"};
                AlertDialog.Builder builder = new AlertDialog.Builder(ShowStock.this);
                builder.setTitle("Option");
                builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        switch (item) {
                            case 0:
                                Intent in = new Intent(getApplicationContext(), ShowUpdate.class);
                                in.putExtra("name", selection);
                                startActivity(in);
                                break;
                            case 1:
                                SQLiteDatabase db = dataHelper.getWritableDatabase();
                                db.execSQL("DELETE FROM drug WHERE name = '" + selection + "'");
                                RefreshList();
                                break;
                        }
                    }
                });
                builder.create().show();
            }
        });
        ((ArrayAdapter) listView.getAdapter()).notifyDataSetInvalidated();
    }

    public void addPhase(View view) {
        Intent intent = new Intent(ShowStock.this, ShowAdd.class);
        startActivity(intent);
    }

    public void finishPhase(View view) {
        finish();
    }
}