package com.android.iscslotbooking;
        // WE WILL PUT DATA WITH A EXTRA FIELD ISUSER

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

public class SignInActivity extends AppCompatActivity {
    EditText PasswordSign, Email_SignIn;
    Button SignInButton;
    TextView SignUpText,adminLoginText;
    FirebaseAuth mauth;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        PasswordSign= findViewById(R.id.sign_in_password);
        Email_SignIn=findViewById(R.id.signin_email);
        SignInButton=findViewById(R.id.sign_in_button);
        adminLoginText= findViewById(R.id.admin_sign_in);
        SignUpText=findViewById(R.id.sign_up_text);
        progressBar= findViewById(R.id.progressBarsignin);
        mauth= FirebaseAuth.getInstance();



        //ADMIN LOGIN TEXT CLICKLISTENR

        adminLoginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInActivity.this,AdminLogin.class));
            }
        });



        SignUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInActivity.this, SignupActivity.class));
            }
        });

        SignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String signinemail= Email_SignIn.getText().toString().trim();
                String signinpassword = PasswordSign.getText().toString().trim();

                //  FOR EMPTY FILELDS
                if (signinemail.isEmpty()){
                    Email_SignIn.setError("Email is Required");
                    Email_SignIn.requestFocus();
                    return;
                }


                if (signinpassword.isEmpty()){
                    PasswordSign.setError("Password is Required");
                    PasswordSign.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(signinemail).matches()){
                    Email_SignIn.setError("Email does not exist");
                    Email_SignIn.requestFocus();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                mauth.signInWithEmailAndPassword(signinemail,signinpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            progressBar.setVisibility(View.GONE);
                            startActivity(new Intent(SignInActivity.this,MainActivity.class));
                        }
                        else{
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(SignInActivity.this, "Wrong Credentials, Check Your Credentials", Toast.LENGTH_SHORT).show();
                        }
                    }
                });




            }
        });
    }
}