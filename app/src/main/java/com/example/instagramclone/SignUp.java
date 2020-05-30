package com.example.instagramclone;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class SignUp extends AppCompatActivity implements View.OnClickListener {
    private TextInputEditText username, email, password, confirmpassword;
    private Button signup, login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        setTitle("Sign Up");
        ParseInstallation.getCurrentInstallation().saveInBackground();
        username = findViewById(R.id.USERNAME);
        email = findViewById(R.id.EMAIL);
        password = findViewById(R.id.PASSWORD);
        confirmpassword = findViewById(R.id.CPASSWORD);
        signup = findViewById(R.id.SIGNUP);
        login = findViewById(R.id.LOGIN);
        signup.setOnClickListener(SignUp.this);
        login.setOnClickListener(SignUp.this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.SIGNUP:
                if (username.getText().toString().trim().isEmpty() || email.getText().toString().trim().isEmpty() ||
                        password.getText().toString().isEmpty() || confirmpassword.getText().toString().isEmpty()) {
                    Toast.makeText(SignUp.this, "Please enter above fields", Toast.LENGTH_SHORT).show();
                }
                if (!password.getText().toString().equals(confirmpassword.getText().toString())) {
                    Toast.makeText(SignUp.this, "Password not match", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        ParseUser user = new ParseUser();
                        user.setUsername(username.getText().toString());
                        user.setEmail(email.getText().toString());
                        user.setPassword(password.getText().toString());
                        user.setPassword(confirmpassword.getText().toString());

                        final ProgressDialog progressDialog = new ProgressDialog(this);
                        progressDialog.setMessage("Signing up " + username.getText().toString());
                        progressDialog.show();

                        user.signUpInBackground(new SignUpCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (e == null) {


                                    ParseUser.logOut();
                                    alertDisplayed("Account created successfully", "Please verify your email before login", false);


                                } else {
                                    ParseUser.logOut();
                                    alertDisplayed("Account creation failed", "Account could not be created " + " :" + e.getMessage(), true);
                                }

                                progressDialog.dismiss();
                            }


                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.LOGIN:
                startActivity(new Intent(SignUp.this, Login.class));
                break;
        }
    }

    private void alertDisplayed(String title, String message, final boolean error) {
        AlertDialog.Builder builder = new AlertDialog.Builder(SignUp.this)
                .setCancelable(false)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                        if (!error) {
                            Intent intent = new Intent(SignUp.this, Login.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    }
                });

        AlertDialog ok = builder.create();
        ok.show();
    }
}