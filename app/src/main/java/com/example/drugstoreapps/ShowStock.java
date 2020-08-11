package com.example.drugstoreapps;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ShowStock extends AppCompatActivity {
    public static ShowStock stock_Page;
    protected Cursor cursor;
    String[] getId, getName, getPrice, getAmount;
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
        getName = new String[cursor.getCount()];
        getPrice = new String[cursor.getCount()];
        getAmount = new String[cursor.getCount()];
        getId = new String[cursor.getCount()];
        cursor.moveToFirst();
        for (int cc = 0; cc < cursor.getCount(); cc++) {
            cursor.moveToPosition(cc);
            getId[cc] = cursor.getString(0).toString();
            getName[cc] = cursor.getString(2).toString();
            getPrice[cc] = cursor.getString(3).toString();
            getAmount[cc] = cursor.getString(4).toString();
            //Tampilkan kolom ke 2 pada listview
        }
        listView = (ListView) findViewById(R.id.listView);
        listView.setDivider(null);
        CustomAdapter adapter = new CustomAdapter(this, getName, getPrice, getAmount, getId);
        listView.setAdapter(adapter);
        //listView.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, getName));
        listView.setSelected(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView arg0, View arg1, int arg2, long arg3) {
                final String selection = getId[arg2]; //.getItemAtPosition(arg2).toString();
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
                                db.execSQL("DELETE FROM drug WHERE id = '" + selection + "'");
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

    class CustomAdapter extends ArrayAdapter<String>{
        Context context;
        String rId[];
        String rName[];
        String rPrice[];
        String rAmount[];

        CustomAdapter(Context c, String name[], String price[], String amount[], String id[]){
            super(c, R.layout.row, R.id.adaptName, name);
            this.context = c;
            this.rId = id;
            this.rName = name;
            this.rPrice = price;
            this.rAmount = amount;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.row, parent, false);
            TextView viewId = row.findViewById(R.id.adaptId);
            TextView viewName = row.findViewById(R.id.adaptName);
            TextView viewPrice = row.findViewById(R.id.adaptPrice);
            TextView viewAmount = row.findViewById(R.id.adaptAmount);

            viewId.setText(rId[position]);
            viewName.setText(rName[position]);
            viewPrice.setText(rPrice[position]);
            viewAmount.setText(rAmount[position]);

            return row;
        }
    }

    public void addPhase(View view) {
        Intent intent = new Intent(ShowStock.this, ShowAdd.class);
        startActivity(intent);
    }

    public void finishPhase(View view) {
        finish();
    }
}