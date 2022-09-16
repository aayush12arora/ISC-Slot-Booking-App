package com.android.iscslotbooking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.android.iscslotbooking.adapters.Admin_marking_attendence_adapters;
import com.android.iscslotbooking.adapters.ViewPagerMessngerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class Attendance_list_for_admin extends AppCompatActivity {
private  RecyclerView recyclerView;
    TabLayout tabLayout;
    ViewPager viewPager;
private  Admin_marking_attendence_adapters adapter;
private  FirebaseAuth auth;
private FirebaseFirestore firestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_list_for_admin);


        tabLayout = findViewById(R.id.tab);
        viewPager = findViewById(R.id.viewpager);

        ViewPagerMessngerAdapter adapter = new ViewPagerMessngerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        auth = FirebaseAuth.getInstance();
        firestore= FirebaseFirestore.getInstance();
        recyclerView= findViewById(R.id.recyclerview_admin);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // GETTING USERS
        getusers();

    }

    private void getusers() {

        //firestore.collection("Users

    }
}