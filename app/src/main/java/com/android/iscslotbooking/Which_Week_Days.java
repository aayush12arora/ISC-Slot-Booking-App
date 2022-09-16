package com.android.iscslotbooking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class Which_Week_Days extends AppCompatActivity {
Spinner spinner;
Button seeSlotsButton;
    String Daycomb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_which_week_days);
          spinner=findViewById(R.id.spn);
seeSlotsButton= findViewById(R.id.available_slots_button);
        ArrayAdapter<CharSequence>adapter=ArrayAdapter.createFromResource(this,R.array.spinnerlistgym, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
       spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Daycomb = parent.getItemAtPosition(position).toString();
               Toast.makeText(Which_Week_Days.this, Daycomb, Toast.LENGTH_SHORT).show();
           }

           @Override
           public void onNothingSelected(AdapterView<?> parent) {

           }
       });



       seeSlotsButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent= new Intent(Which_Week_Days.this,Gym_selelect_timmings.class);
               intent.putExtra("daycombination",Daycomb);
               startActivity(intent);
           }
       });


    }
}