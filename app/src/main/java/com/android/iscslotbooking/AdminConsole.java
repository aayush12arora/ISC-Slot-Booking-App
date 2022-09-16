package com.android.iscslotbooking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class AdminConsole extends AppCompatActivity {


   private Button Logout,Gym,Attendance;
  private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_console);
        Gym = findViewById(R.id.Gym_slots);
        Logout = findViewById(R.id.admin_conslole_logout_button);
        auth = FirebaseAuth.getInstance();
Attendance= findViewById(R.id.mark_attendance_button);
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                startActivity(new Intent(AdminConsole.this,AdminLogin.class));
            }
        });


        // MARK ATTENDANCE BUTTON ON CLICK LISTENER
Attendance.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
       startActivity(new Intent(AdminConsole.this, Attendance_list_for_admin.class));
    }
});





        Gym.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminConsole.this,gym_admin_putting_slots.class));
            }
        });
    }
}