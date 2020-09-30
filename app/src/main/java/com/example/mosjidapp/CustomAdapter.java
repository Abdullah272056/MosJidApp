package com.example.mosjidapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder>{

    Context context;
    private List<Notes> allNotes;
    List<Notes> copyAllNotes;
    private DataBaseHelper databaseHelper;
    AlarmManager alarm;
    PendingIntent alarmIntent;
    int switchButtonStatus;
    long currentMilliSecond;
    public CustomAdapter(Context context,List<Notes> allNotes){
        this.context = context;
        this.allNotes = allNotes;
        this.context=context;
        databaseHelper=new DataBaseHelper(context);
        copyAllNotes = new ArrayList<>(allNotes);
        //for searchView//dataList's copy
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater= LayoutInflater.from(context);
        View view= layoutInflater.inflate(R.layout.recyclerview_item_design,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.startTextView.setText(allNotes.get(position).getStartTime());
        holder.endTextView.setText(allNotes.get(position).getEntTime());
      //  holder.nameTextView.setText(allNotes.get(position).get());

    }

    @Override
    public int getItemCount() {
        return allNotes.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        Switch switchButton;
        TextView nameTextView,startTextView,endTextView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            switchButton=itemView.findViewById(R.id.switchId);
            nameTextView=itemView.findViewById(R.id.waktoNameTextViewId);
            startTextView=itemView.findViewById(R.id.startTimeTextViewId);
            endTextView=itemView.findViewById(R.id.endTimeTextViewId);
        }
    }
}
