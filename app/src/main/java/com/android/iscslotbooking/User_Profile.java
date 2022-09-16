package com.android.iscslotbooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class User_Profile extends AppCompatActivity {
TextView userGreetings,slotDays,slotTimmings;
private String Slotdays, SlotTimmings, CurrentUserid,userName;
FirebaseFirestore firestore;
FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        userGreetings= findViewById(R.id.user_greeting);
        slotDays= findViewById(R.id.slot_days_user);
        slotTimmings= findViewById(R.id.slot_timmings_user);
        auth= FirebaseAuth.getInstance();
        firestore= FirebaseFirestore.getInstance();
        CurrentUserid= auth.getCurrentUser().getUid();

        firestore.collection("Users").document(CurrentUserid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                Slotdays = task.getResult().getString("Slot Days");
                SlotTimmings= task.getResult().getString("Slot Time");
                userName=task.getResult().getString("Name");
              //  Log.d("Name11",userName);
                slotTimmings.setText(SlotTimmings);
                slotDays.setText(Slotdays);
                userGreetings.setText("Welcome ,"+userName);
            }
        });





    }
}