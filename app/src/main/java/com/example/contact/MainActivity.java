package com.example.contact;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Controllerdb db =new Controllerdb(this);
    SQLiteDatabase database;
    EditText Organization,Name,Number;
    Button btnSave;
    private ImageView del;
    String organization_string = "";
    String name_string = "";
    String number_string = "";
    boolean isFromList = false;

//    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_add_organization);
//        sharedpreferences = getSharedPreferences(Constant.id_string, Context.MODE_PRIVATE);

        organization_string=getIntent().getStringExtra("organization");
        name_string=getIntent().getStringExtra("name");
        number_string=getIntent().getStringExtra("number");
        isFromList = getIntent().getBooleanExtra("isFromList",false);

        Organization= (EditText) findViewById(R.id.organization);
        Name= (EditText) findViewById(R.id.name);
        Number= (EditText) findViewById(R.id.number);
        btnSave= (Button) findViewById(R.id.button);
        btnSave.setText(isFromList? "UPDATE": "SAVE");

        Name.setText(name_string);
        Organization.setText(organization_string);
        Number.setText(number_string);

        btnSave.setOnClickListener(this);
        del=findViewById(R.id.dellv);
        del.setOnClickListener(this);
        del.setVisibility(isFromList ? del.VISIBLE : del.GONE);


    }

    public void setData(String organization, String name, String number){
        this.organization_string = organization;
        this.name_string = name;
        this.number_string = number;
//        this.Organization.setText(organization);
//        this.Name.setText(name);
//        this.Number.setText(number);
    }
   public void setData(){
       this.organization_string = "";
       this.name_string = "";
       this.number_string = "";
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button) {
            String org = Organization.getText().toString().trim();
            String name = Name.getText().toString().trim();
            String nu =  Number.getText().toString().trim();

            if(org.length() == 0||name.length() == 0||nu.length() == 0){
                Toast t = Toast.makeText(MainActivity.this, "Field cannot be empty", Toast.LENGTH_LONG);
                t.setGravity(Gravity.BOTTOM,Gravity.CENTER_HORIZONTAL, 20);//display message in bottom and center_horizontal
                t.show();
                return;
            }

            database = db.getWritableDatabase();
            if (this.btnSave.getText().toString().equals("SAVE")) {
//                SharedPreferences.Editor editor = sharedpreferences.edit();
//                editor.putInt("id",0);
                try {
                    database.execSQL("INSERT INTO UserDetails(Organization,Name,Number)VALUES('"+
                            Organization.getText() + "','" + Name.getText() + "','" + Number.getText() + "')");
                    Toast.makeText(this, "Data Inserted To Sqlite Database", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(MainActivity.this, ShowdataListview.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent);
//                    Bundle b = intent.getExtras();
//                    startActivity(intent,b);
                    finish();
                } catch (Exception e){
                    Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    return;
                }

            } else {
                String olderString[]={organization_string, name_string, number_string};
                boolean isUpdate = db.UpdateData(Organization.getText().toString(),
                                                 Name.getText().toString(),
                                                 Number.getText().toString(), olderString);
                if(isUpdate == true) {
                    Toast t = Toast.makeText(MainActivity.this, "Data Update", Toast.LENGTH_LONG);
                    t.setGravity(Gravity.BOTTOM,Gravity.CENTER_HORIZONTAL, 20);
                    t.show();
                    Intent intent = new Intent(MainActivity.this, ShowdataListview.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent);
                    finish();
                }

                else {
                    Toast t = Toast.makeText(MainActivity.this, "Data not Updated", Toast.LENGTH_LONG);
                    t.setGravity(Gravity.BOTTOM,Gravity.CENTER_HORIZONTAL, 20);
                    t.show();
                    return;
                }
            }

        }else if (v.getId() == R.id.dellv) {
            deleteTapped();
        }




    }

    void deleteTapped(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure want to delete this contact?").setPositiveButton("Delete", dialogClickListener)
                .setNegativeButton("Cancel", dialogClickListener).show();
    }

    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                   boolean isRemove = db.removeSingleContact(organization_string,name_string,number_string);
                   if(isRemove) {
                       Toast.makeText(MainActivity.this, "Deleted Successfully", Toast.LENGTH_LONG).show();
                       Intent intent = new Intent(MainActivity.this, ShowdataListview.class);
                       intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                       startActivity(intent);
                       finish();
                   }else {
                       Toast.makeText(MainActivity.this, "Sorry! Operation cannot be completed", Toast.LENGTH_LONG).show();
                   }
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    dialog.cancel();
                    break;
            }
        }
    };

}