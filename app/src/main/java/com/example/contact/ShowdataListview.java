package com.example.contact;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import static android.os.Build.ID;

public class ShowdataListview extends AppCompatActivity implements View.OnClickListener {
    Controllerdb controllerdb = new Controllerdb(this);
    public static  SQLiteDatabase db;

    private ArrayList<String> Id = new ArrayList<String>();
    private ArrayList<String> Organization = new ArrayList<String>();
    private ArrayList<String> Name = new ArrayList<String>();
    private ArrayList<String> Number = new ArrayList<String>();
    ListView lv;
    private ImageView addIv;
    private MenuItem login_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showdata_listview);

       // Login_setup();
        lv = (ListView) findViewById(R.id.lstv);
        addIv=findViewById(R.id.addIv);
        addIv.setOnClickListener(this);
        addIv.setVisibility(Constant.is_logIn ? addIv.VISIBLE : addIv.GONE);
        if(login_btn != null)
            login_btn.setTitle(Constant.is_logIn ? "LOGOUT" : "LOGIN");
        controllerdb =new Controllerdb (this);
        displayData();
        }


    @Override
    protected void onResume() {
        super.onResume();
        addIv.setVisibility(Constant.is_logIn ? addIv.VISIBLE : addIv.GONE);
        if(login_btn != null)
            login_btn.setTitle(Constant.is_logIn ? "LOGOUT" : "LOGIN");
        displayData();

    }
    private void displayData() {
        db = controllerdb.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM  UserDetails",null);

        Organization.clear();
        Name.clear();
        Number.clear();
        if (cursor.moveToFirst()) {
            do {
                //Id.add(cursor.getString(cursor.getColumnIndex("Id")));
                Organization.add(cursor.getString(cursor.getColumnIndex("Organization")));
                Name.add(cursor.getString(cursor.getColumnIndex("Name")));
                Number.add(cursor.getString(cursor.getColumnIndex("Number")));

            } while (cursor.moveToNext());
        }

        CustomAdapter ca = new CustomAdapter(ShowdataListview.this,Id,Organization,Name,Number);
        lv.setAdapter(ca);
        //code to set adapter to populate list
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(Constant.is_logIn) {

                    MainActivity ma = new MainActivity();
                    ma.setData(Organization.get(i), Name.get(i), Number.get(i));
                    Intent intent = new Intent(ShowdataListview.this, MainActivity.class);
                    intent.putExtra("organization",Organization.get(i));
                    intent.putExtra("name",Name.get(i));
                    intent.putExtra("number",Number.get(i));
                    intent.putExtra("isFromList", true);
                    startActivity(intent);
                }
            }
        });
        cursor.close();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        //this.login_menu = menu;
       this.login_btn = menu.getItem(0);
        login_btn.setTitle(Constant.is_logIn ? "LOGOUT" : "LOGIN");
        addIv.setVisibility(Constant.is_logIn ? addIv.VISIBLE : addIv.GONE);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.login_id) {
            if(!Constant.is_logIn) {
                startActivity(new Intent(this, Login.class));
//                item.setTitle("LOGOUT");
                return true;
            } else {
                Constant.is_logIn = false;
                item.setTitle("LOGIN");
                addIv.setVisibility(addIv.GONE);

                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.addIv){
            Intent intent=new Intent(this,MainActivity.class);
            startActivity(intent);
        }
    }


}
