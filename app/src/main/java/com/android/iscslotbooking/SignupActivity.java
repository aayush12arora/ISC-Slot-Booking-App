package com.android.iscslotbooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity {
    EditText Password, Email_SignUp;
    Button SignUpButton;
    TextView SignInText;
    FirebaseAuth mauth;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Password= findViewById(R.id.signup_password);
        Email_SignUp=findViewById(R.id.signup_email);
        SignUpButton=findViewById(R.id.sign_up_button);
        SignInText=findViewById(R.id.sign_in_text);
        progressBar=findViewById(R.id.progressBarsignup);
        mauth= FirebaseAuth.getInstance();


        SignInText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this, SignInActivity.class));
            }
        });

        SignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String signupemail = Email_SignUp.getText().toString().trim();
                String signuppassword = Password.getText().toString().trim();


                //  FOR EMPTY FILELDS
                if (signupemail.isEmpty()){
                    Email_SignUp.setError("Email is Required");
                    Email_SignUp.requestFocus();
                    return;
                }


                if (signuppassword.isEmpty()){
                    Password.setError("Password is Required");
                    Password.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(signupemail).matches()){
                    Email_SignUp.setError("Email does not exist");
                    Email_SignUp.requestFocus();
                    return;
                }


                progressBar.setVisibility(View.VISIBLE);
                mauth.createUserWithEmailAndPassword(signupemail,signuppassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            progressBar.setVisibility(View.GONE);
                            startActivity(new Intent(SignupActivity.this,SignInActivity.class));
                        }
                        else{
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(SignupActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

    }
}