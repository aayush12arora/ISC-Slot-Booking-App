package com.android.iscslotbooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.android.iscslotbooking.Models.slotcard;
import com.android.iscslotbooking.adapters.Gym_selelect_timmings_adapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Gym_selelect_timmings extends AppCompatActivity implements SelectListner {
private  RecyclerView recyclerView;
private Gym_selelect_timmings_adapter adapter;
private FirebaseFirestore firestore;
private FirebaseAuth auth;
List<slotcard>slotslist;
private String slotid;
private  String mwf="M-W-F";
private String Currentuserid;
    String daycombination;
    private  String tts ="T-T-S";
    int initalslotleft;

//public  boolean isuserhasslot = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gym_selelect_timmings);
firestore= FirebaseFirestore.getInstance();
auth= FirebaseAuth.getInstance();
slotslist= new ArrayList<>();
        recyclerView= findViewById(R.id.recyclerview_gym_slots);
auth= FirebaseAuth.getInstance();
Currentuserid= auth.getCurrentUser().getUid();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(Gym_selelect_timmings.this));
        recyclerView.setNestedScrollingEnabled(false);
        Intent intent= getIntent();
         daycombination = intent.getStringExtra("daycombination");
      //  Log.d("daycomb",daycombination);





getdata(daycombination);





    }


    public  void refresh(int milliseconds){

        // REFRESHING ACTIVITY AUTOMATICALLY AFTER 5 SECONDS

        final    Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                getdata(daycombination);
            }
        };

        handler.postDelayed(runnable,milliseconds);

    }
    private void getdata(String daycombination) {
        slotslist.clear();
        Log.d("refreshing","done");
        if (Objects.equals(daycombination, "Monday- Wednesday-Friday")){
            Log.d("daycomb",daycombination);
            getavailableSlots(mwf);
            adapter=new Gym_selelect_timmings_adapter(this,slotslist,mwf,this);
        }
        else if (Objects.equals(daycombination, "Tuesday-Thursday- Saturday")){
            Log.d("daycomb",daycombination);
            getavailableSlots(tts);
            adapter=new Gym_selelect_timmings_adapter(this,slotslist,tts,this);
        }

        recyclerView.setAdapter(adapter);

        refresh(5000);
    }


    private void getavailableSlots(String days) {





        firestore.collection("Gym Slots").whereEqualTo("Days",days).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                for (QueryDocumentSnapshot document : task.getResult()) {



                    slotcard slot =  document.toObject(slotcard.class);
                    Log.d("slot111",slot.getTime());
                    slotslist.add(slot);
                    Log.d("slot111",""+slotslist.size());
                    adapter.notifyDataSetChanged();

                }
            }
        });





    }

    @Override
    public void onItemClicked(slotcard slot,String days) {




        initalslotleft = slot.getNumber_of_Slots();


        firestore.collection("Users").document(Currentuserid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override

            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                Log.d("isslot","118");
                String Slotdays = task.getResult().getString("Slot Days");
                if (Slotdays==null) {

                    firestore.collection("Gym Slots").whereEqualTo("Days", days).whereEqualTo("Time", slot.getTime())
                            .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        slotid = document.getId();
                                        Log.d("onitem11", slotid);
                                        if (initalslotleft == 0) {
                                            Toast.makeText(Gym_selelect_timmings.this, "All Slots for this time are booked", Toast.LENGTH_SHORT).show();
                                        }

                                        else{

                                        initalslotleft--;
                                        slot.setNumber_of_Slots(initalslotleft);
                                        HashMap<String, Object> updateuserslot = new HashMap<>();
                                        updateuserslot.put("Slot Days", days);
                                        updateuserslot.put("Slot Time", slot.getTime());
                                        firestore.collection("Users").document(Currentuserid).set(updateuserslot);


                                        HashMap<String, Object> updatemao = new HashMap<>();

                                        updatemao.put("Number_of_Slots", initalslotleft);
                                        firestore.collection("Gym Slots").document(slotid).update(updatemao);
                                        Toast.makeText(Gym_selelect_timmings.this, "Slot Booked", Toast.LENGTH_SHORT).show();
                                        adapter.notifyDataSetChanged();
                                    }

                                    }
                                }
                            });
















                }
                  else
          {
              Toast.makeText(Gym_selelect_timmings.this, "You have Already booked your Slot", Toast.LENGTH_SHORT).show();
          }
                adapter.notifyDataSetChanged();


            }
        });

















        adapter.notifyDataSetChanged();

    }
}



// onitemclicked in recycler view
/*
  firestore.collection("Gym Slots").whereEqualTo("Days",selecteddaycombination).whereEqualTo("Time",slots.getTime())
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                           slotid = document.getId();

                        }
                    }
                });
initalslotleft--;
        firestore.collection("Gym Slots").document(slotid).update("Number_of_Slots",initalslotleft);
adapter.notifyDataSetChanged();
 */