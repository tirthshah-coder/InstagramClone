package com.example.instagramclone;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

public class Login extends AppCompatActivity implements View.OnClickListener {

    private EditText email, password;
    private Button login, signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Login");
        email = findViewById(R.id.LOGIN_EMAIL);
        password = findViewById(R.id.LOGIN_PASSWORD);
        login = findViewById(R.id.ACTIVITY_LOGIN);
        signup = findViewById(R.id.ACTIVITY_SIGNUP);

        login.setOnClickListener(Login.this);
        signup.setOnClickListener(Login.this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ACTIVITY_LOGIN:
                ParseUser.logInInBackground(email.getText().toString(), password.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if (user != null) {
                            if (user.getBoolean("emailVerified")) {
                                alertDisplayed("Login successful", "Welcome, " + email.getText().toString(), false);
                            } else {
                                ParseUser.logOut();
                                alertDisplayed("Login Failed", "Please verify your email first", true);
                            }
                        } else {
                            ParseUser.logOut();
                            alertDisplayed("Login Failed", e.getMessage() + " Please retry", true);
                        }
                    }
                });
                break;
            case R.id.ACTIVITY_SIGNUP:
                startActivity(new Intent(Login.this, SignUp.class));
                break;
        }
    }

    private void alertDisplayed(String title, String message, final boolean error) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Login.this)
                .setCancelable(false)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                        if (!error) {
                            Intent intent = new Intent(Login.this, Welcome.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    }
                });

        AlertDialog ok = builder.create();
        ok.show();
    }
}
