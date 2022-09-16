package com.android.iscslotbooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.android.iscslotbooking.Models.slotcard;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class gym_admin_putting_slots extends AppCompatActivity {
EditText mwf,tts;
FirebaseFirestore firestore;
FirebaseAuth auth;
Button addSlots;
CheckBox checkBox;
private  int l =0;
List<String> documentid;
    int noofSlotstts;
    int noofSlotsmwf;
    int flag=10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gym_admin_putting_slots);
        addSlots= findViewById(R.id.add_Slots);
        auth= FirebaseAuth.getInstance();
        checkBox= findViewById(R.id.checkBox);
        firestore= FirebaseFirestore.getInstance();
        mwf= findViewById(R.id.number_of_slots_for_mwf);
        tts= findViewById(R.id.number_of_slots_for_TTS);

documentid= new ArrayList<>();
checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            checkBox.clearFocus();
            checkBox.setError(null);
            noofSlotsmwf = Integer.parseInt(mwf.getText().toString().trim());

            noofSlotstts = Integer.parseInt(tts.getText().toString().trim());

            documentid.clear();
            Log.d("where","line 64");
            Log.d("idsgg","After Clear"+documentid.size());

            firestore.collection("Gym Slots").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    Log.d("idsgg","\n");
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        // Log.d("documentid","here");
                        String id = document.getId();
                        Log.d("where","line 74");
                        Log.d("idsgg",document.getId() +"\n");
                        documentid.add(id);
                        // Log.d("idsgg",""+documentid.size());


                    }
                    Log.d("idsgg","in get loop"+documentid.size());
                    Log.d("documentid1","here" +documentid.size());
                    for (int i=0;i<documentid.size();i++ ) {

                        firestore.collection("Gym Slots").document(documentid.get(i)).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Log.d("abv","cleard");
                                }
                            }
                        });

                    }


                }




            });



        }
    }
});

       // DOING ONLY FOR PM(EVENING)
        addSlots.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (checkBox.isChecked()){

                    // Log.d("documentids",""+ k);
                    Log.d("where", "line 104");


                Log.d("idsgg", "After delete function" + documentid.size());


                for (int i = 4, j = 5; j <= 9; i++, j++) {
                    l++;
                    // STORING DAY WITH NUMBER OF SLOTSIN FIRESTORE
                   HashMap<String, Object> postmap = new HashMap<>();
                    postmap.put("Time", i + "-" + j + "PM");
                    postmap.put("Number_of_Slots", noofSlotsmwf);
                    postmap.put("Days", "M-W-F");


                    // Checking Weather the data exits
                    firestore.collection("Gym Slots").document().get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if (documentSnapshot.exists()) {

                                firestore.collection("Gym Slots").document().set(postmap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {

                                        } else {
                                            Toast.makeText(gym_admin_putting_slots.this, "Failed", Toast.LENGTH_SHORT).show();
                                            flag = 11;
                                        }
                                    }

                                });


// If data dosen't exists
                            } else {

                                firestore.collection("Gym Slots").document().set(postmap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            //Toast.makeText(gym_admin_putting_slots.this, "Slots added for all timmings", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(gym_admin_putting_slots.this, "Failed", Toast.LENGTH_SHORT).show();
                                            flag = 11;
                                        }
                                    }

                                });


                            }

                        }
                    });


                    // STORING DAY WITH NUMBER OF SLOTSIN FIRESTORE
                    HashMap<String, Object> postmaptts = new HashMap<>();
                    postmaptts.put("Time", i + "-" + j + "PM");
                    postmaptts.put("Number_of_Slots", noofSlotstts);
                    postmaptts.put("Days", "T-T-S");


                    // Checking Weather the data exits
                    firestore.collection("Gym Slots").document().get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if (documentSnapshot.exists()) {

                                firestore.collection("Gym Slots").document().set(postmaptts).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
// mkmk
                                        } else {
                                            Toast.makeText(gym_admin_putting_slots.this, "Failed", Toast.LENGTH_SHORT).show();
                                            flag = 11;
                                        }
                                    }

                                });


// If data dosen't exists
                            } else {

                                firestore.collection("Gym Slots").document().set(postmaptts).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            //Toast.makeText(gym_admin_putting_slots.this, "Slots added for all timmings", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(gym_admin_putting_slots.this, "Failed", Toast.LENGTH_SHORT).show();
                                            flag = 11;
                                        }
                                    }

                                });


                            }

                        }
                    });


                    if (flag == 11) {
                        break;
                    }
                }


                Log.d("where1", "line 227  "+l);


                if (flag == 10) {
                    checkBox.toggle();
                    mwf.getText().clear();
                    tts.getText().clear();
                    Toast.makeText(gym_admin_putting_slots.this, "Slots added for all timmings", Toast.LENGTH_SHORT).show();
                }
            }
                else{
                    checkBox.requestFocus();
                    checkBox.setError("Confirm the Slots");
                }
            }

        });


    }
}