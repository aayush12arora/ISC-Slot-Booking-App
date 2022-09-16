package com.android.iscslotbooking.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.iscslotbooking.Models.slotcard;
import com.android.iscslotbooking.R;
import com.android.iscslotbooking.SelectListner;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class Gym_selelect_timmings_adapter extends RecyclerView.Adapter<Gym_selelect_timmings_adapter.ViewHolder> {
    private Context context;
    private List<slotcard> slotslist;
private FirebaseAuth auth;
private FirebaseFirestore firestore;
String currentUserid, selecteddaycombination;
    String slotid;
    private   Gym_selelect_timmings_adapter adapter;
  private SelectListner  listner;

    public Gym_selelect_timmings_adapter(Context context, List<slotcard>slotslist,String Selectedaycomb,SelectListner  Listner) {
        this.context = context;
        this.slotslist = slotslist;
        this.selecteddaycombination=Selectedaycomb;
        this.listner = Listner;

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.sports_timming_item,parent,false);






        ViewHolder viewHolder = new ViewHolder(v);
auth= FirebaseAuth.getInstance();
firestore= FirebaseFirestore.getInstance();
        return viewHolder;




    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
currentUserid= auth.getCurrentUser().getUid();
     slotcard slots = slotslist.get(position);
     holder.time.setText(slots.getTime());
     holder.slotsLeft.setText(String.valueOf(slots.getNumber_of_Slots()));
        int initalslotleft = slots.getNumber_of_Slots();
     if (slots.getNumber_of_Slots()!=0){

         holder.card.setBackgroundColor(Color.parseColor("#76FF03"));
     }
holder.card.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {


listner.onItemClicked(slotslist.get(position),selecteddaycombination);
    }
});
    }

    @Override
    public int getItemCount() {
        Log.d("adaptar22",""+slotslist.size());
        return slotslist.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView time, slotsLeft;
        CardView card;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
time= itemView.findViewById(R.id.time_text_view);
slotsLeft= itemView.findViewById(R.id.slotsleft);
card= itemView.findViewById(R.id.card_slots);
        }
    }
}
