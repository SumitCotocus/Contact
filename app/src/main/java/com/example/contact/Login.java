package com.example.contact;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import mx.jesusmartinoza.widget.SweetPassword;

public class Login extends AppCompatActivity {

    private static EditText username;
    private static EditText password;
    //private static TextView attempts;

    private static Button btn_login;

    TextView mTextViewRegister;
    // private ProgressBar spinner;


    //int attempt_counter = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        LoginButton();

    }
    public void LoginButton()
    {
        username=(EditText)findViewById(R.id.username);
        password=(EditText)findViewById(R.id.password);
        btn_login=(Button)findViewById(R.id.btn_login);

        btn_login.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(username.getText().toString().trim().length()==0){
                            username.setError("Username is not entered");
                            username.requestFocus();
                        }
                        if(password.getText().toString().trim().length()==0){
                            password.setError("Password is not entered");
                            password.requestFocus();
                        }
                        if(username.getText().toString().equals("admin") &&
                                password.getText().toString().equals("sumit")  ) {
                            Toast.makeText(Login.this,"User and Password is correct",
                                    Toast.LENGTH_SHORT).show();
                            Constant.is_logIn = true;
                            Intent intent = new Intent(Login.this,ShowdataListview.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                            startActivity(intent);
                            finish();
                        }
                        else {
                            Toast t = Toast.makeText(Login.this,"User and Password is not correct",
                                    Toast.LENGTH_SHORT);
                            t.setGravity(Gravity.BOTTOM,Gravity.CENTER_HORIZONTAL, 20);
                            t.show();

                        }


                    }
                }

        );
    }

}
