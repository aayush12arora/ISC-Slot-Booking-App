package com.android.iscslotbooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class AdminLogin extends AppCompatActivity {
    Button adminSigninButton;
    EditText email, password;
    FirebaseAuth auth;
    int isuser;
    FirebaseFirestore firestore;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        adminSigninButton = findViewById(R.id.admin_signin_button);
        email = findViewById(R.id.admin_login_emal);
        password = findViewById(R.id.admin_Signin_password);

        // FIREBASE INSTANCES

        auth = FirebaseAuth.getInstance();
       // uid = auth.getCurrentUser().getUid();
        firestore = FirebaseFirestore.getInstance();


        // ADMIN SIGNIN BUTTON ON CLICK LISTENR
        adminSigninButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String signinemail = email.getText().toString().trim();
                String signinpassword = password.getText().toString().trim();

                //  FOR EMPTY FILELDS
                if (signinemail.isEmpty()) {
                    email.setError("Email is Required");
                    email.requestFocus();
                    return;
                }


                if (signinpassword.isEmpty()) {
                    password.setError("Password is Required");
                    password.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(signinemail).matches()) {
                    email.setError("Email does not exist");
                    email.requestFocus();
                    return;
                }

                // progressBar.setVisibility(View.VISIBLE);
                auth.signInWithEmailAndPassword(signinemail, signinpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            uid = auth.getCurrentUser().getUid();
                            firestore.collection("Users").document(uid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    DocumentSnapshot document = task.getResult();
                                    isuser = document.getLong("IsUser").intValue();
                                    if (isuser == 0) {
                                        startActivity(new Intent(AdminLogin.this, AdminConsole.class));

                                    }
                                    else
                                    {
                                        Toast.makeText(AdminLogin.this, "ACCESS DENIED", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        }
                    }
                });
            }
        });
    }
}