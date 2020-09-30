package com.example.mosjidapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.ALARM_SERVICE;

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
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.startTextView.setText(allNotes.get(position).getStartTime());
        holder.endTextView.setText(allNotes.get(position).getEntTime());
      //  holder.nameTextView.setText(allNotes.get(position).get());
        switchButtonStatus=allNotes.get(position).getSwitchStatus();

        if (switchButtonStatus==1){
            holder.switchButton.setChecked(true);
        }
        else if (switchButtonStatus==0){
            holder.switchButton.setChecked(false);
        }else {

        }


        holder.switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    currentMilliSecond=System.currentTimeMillis();

                    Toast.makeText(context, "check", Toast.LENGTH_SHORT).show();

                    long status = databaseHelper.updateData(new Notes(allNotes.get(position).getId(),
                            allNotes.get(position).getStartTime(),allNotes.get(position).getEntTime(),
                            allNotes.get(position).getStartTimeMilli(),
                            allNotes.get(position).getEndTimeMilli(),
                            allNotes.get(position).getAudioMode(),
                            1));

                    if (status == 1){
                        allNotes.clear();
                        allNotes.addAll(databaseHelper.getAllNotes());
                        //notifyDataSetChanged();

                        if (currentMilliSecond<=Long.parseLong(allNotes.get(position).getStartTimeMilli())){
                            Intent intent=new Intent(context, NotificationReceiver.class);
                            intent.putExtra("notificationRequestCode",allNotes.get(position).getId());
                            intent.putExtra("TargetTimeMilliSecond",allNotes.get(position).getStartTimeMilli());
                            alarmIntent= PendingIntent.getBroadcast(context,
                                    allNotes.get(position).getId(),intent,PendingIntent.FLAG_CANCEL_CURRENT);
                            //get ALARM_SERVICE from SystemService
                            alarm= (AlarmManager) context.getSystemService(ALARM_SERVICE);
                            //alarm set
                            alarm.set(AlarmManager.RTC_WAKEUP,Long.parseLong(allNotes.get(position).getStartTimeMilli()),alarmIntent);


                            alarmIntent= PendingIntent.getBroadcast(context,
                                    allNotes.get(position).getId()+100,intent,PendingIntent.FLAG_CANCEL_CURRENT);
                            //get ALARM_SERVICE from SystemService
                            alarm= (AlarmManager) context.getSystemService(ALARM_SERVICE);
                            int minuteInt =Integer.parseInt(allNotes.get(position).getEntTime());
                            //alarm set
                            alarm.set(AlarmManager.RTC_WAKEUP,(Long.parseLong(allNotes.get(position).getStartTimeMilli())+(minuteInt*60000)),alarmIntent);
                        }else {
                            Toast.makeText(context, "Current Time is bigger than target time", Toast.LENGTH_SHORT).show();
                        }
                        Toast.makeText(context, String.valueOf(allNotes.get(position).getId()), Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    long status = databaseHelper.updateData(new Notes(allNotes.get(position).getId(),
                            allNotes.get(position).getStartTime(),allNotes.get(position).getEntTime(),
                            allNotes.get(position).getStartTimeMilli(),
                            allNotes.get(position).getEndTimeMilli(),
                            allNotes.get(position).getAudioMode(),
                            0));
                    if (status == 1){
                        allNotes.clear();
                        allNotes.addAll(databaseHelper.getAllNotes());
                        //notifyDataSetChanged();

                      Intent intent=new Intent(context, NotificationReceiver.class);
                        alarmIntent= PendingIntent.getBroadcast(context,
                                allNotes.get(position).getId(),intent,PendingIntent.FLAG_CANCEL_CURRENT);
                        alarm= (AlarmManager) context.getSystemService(ALARM_SERVICE);
                     alarm.cancel(alarmIntent);

                        alarmIntent= PendingIntent.getBroadcast(context,
                                allNotes.get(position).getId()+100,intent,PendingIntent.FLAG_CANCEL_CURRENT);
                        //get ALARM_SERVICE from SystemService
                        alarm= (AlarmManager) context.getSystemService(ALARM_SERVICE);
                        alarm.cancel(alarmIntent);

                        //  Toast.makeText(context, "Successfully Updated", Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(context, String.valueOf(allNotes.get(position).getId()), Toast.LENGTH_SHORT).show();

                }
            }
        });

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
