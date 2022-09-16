package com.android.iscslotbooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
Button BadmintionSlot,GymSlot;
FirebaseAuth auth;

    private Toolbar maintoolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GymSlot = findViewById(R.id.gym_slot_book_button);
        BadmintionSlot= findViewById(R.id.badmintion_slot_book_button2);

        maintoolbar=findViewById(R.id.toolbar);
        auth = FirebaseAuth.getInstance();
        setSupportActionBar(maintoolbar);
        getSupportActionBar().setTitle(R.string.app_name);
        GymSlot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Which_Week_Days.class));
            }
        });
    }

    // OVERRIDING ON START METHOD TO CHECK IF USER IS SIGNED IN OR NOT

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser==null){
            Log.d("onstart11","here");
            startActivity(new Intent(MainActivity.this,SignupActivity.class));
            finish();
        }
    }


    // OVERRIDING ON BACK PRESSED

    // overriding on backpressed for alertdialog box
    @Override
    public void onBackPressed() {


        AlertDialog.Builder alertdb = new AlertDialog.Builder(this);
        alertdb.setIcon(R.drawable.ic_launcher_foreground);
        alertdb.setTitle("Exit");
        alertdb.setMessage("Do You Want to Exit");
        alertdb.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finishAffinity();

                System.exit(0);
            }
        });
        String Btn= "No";
        alertdb.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });


        alertdb.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "Operation Cancelled", Toast.LENGTH_SHORT).show();
            }
        });


        alertdb.show();
    }


    // inflating menu  to toolbar


    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    // on item selected for  menu

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        if (item.getItemId()==R.id.profile_menu){
            startActivity(new Intent(MainActivity.this,User_Profile.class));
        }
        else if (item.getItemId()==R.id.sign_out_menu){
            auth.signOut();
            startActivity(new Intent(MainActivity.this,SignInActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }



}