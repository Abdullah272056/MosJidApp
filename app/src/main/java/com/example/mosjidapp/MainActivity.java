package com.example.mosjidapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Notes> dataList;

    Button saveButton,cancelButton;
    EditText minuteEditText;
    String endTime;
    Long startTimeMilliSecond,endTimeMilliSecond;

    RecyclerView recyclerView;
    FloatingActionButton floatingActionButton;

    DataBaseHelper dataBaseHelper;

    CustomAdapter customAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.recyclerViewId);
        floatingActionButton=findViewById(R.id.floatingButtonId);

        dataBaseHelper=new DataBaseHelper(MainActivity.this);
        dataBaseHelper.getWritableDatabase();
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        loadData();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                CustomDialog();
            }
        });


    }


    private void loadData(){
        dataList  = new ArrayList<>();
        dataList = dataBaseHelper.getAllNotes();
        if (dataList.size() > 0){
            customAdapter = new CustomAdapter(MainActivity.this,dataList);
            recyclerView.setAdapter(customAdapter);
            customAdapter.notifyDataSetChanged();
        }else {
            Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
        }
    }

    private void CustomDialog(){

        final BottomSheetDialog bottomSheetDialog=new BottomSheetDialog(MainActivity.this);
        bottomSheetDialog.setContentView(R.layout.input_layout);
        bottomSheetDialog.setCanceledOnTouchOutside(false);

        saveButton=bottomSheetDialog.findViewById(R.id.saveButtonId);
        cancelButton=bottomSheetDialog.findViewById(R.id.cancelButtonId);
        minuteEditText=bottomSheetDialog.findViewById(R.id.minuteEditTextId);
        final TimePicker startTimePicker =bottomSheetDialog.findViewById(R.id.timePickerId);

        saveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
            if (minuteEditText.getText().toString().isEmpty()){
                minuteEditText.setError("Enter a Value");
            }else {
                DateFormat dateFormat = new SimpleDateFormat("hh:mm aa");
                int hour=startTimePicker.getCurrentHour();
                int minute=startTimePicker.getCurrentMinute();
                Calendar startTimeCalender=Calendar.getInstance();
                startTimeCalender.set(Calendar.HOUR_OF_DAY,hour);
                startTimeCalender.set(Calendar.MINUTE,minute);
                startTimeCalender.set(Calendar.SECOND,0);
                String startTimeString = dateFormat.format(startTimeCalender.getTime()).toString();
                startTimeMilliSecond=startTimeCalender.getTimeInMillis();
                endTime=minuteEditText.getText().toString();
                int addMinute=Integer.parseInt(endTime);
                endTimeMilliSecond=startTimeMilliSecond+(addMinute*60000);
                int id=dataBaseHelper.insertData(new Notes(startTimeString,endTime,String.valueOf(startTimeMilliSecond),
                          String.valueOf(endTimeMilliSecond),"www",1));
                if (id!=-1){
                    Toast.makeText(MainActivity.this, String.valueOf(id), Toast.LENGTH_SHORT).show();
                    loadData();
                    bottomSheetDialog.dismiss();
                }else {
                    Toast.makeText(MainActivity.this, "insert fail", Toast.LENGTH_SHORT).show();
                    bottomSheetDialog.dismiss();
                }

            }

            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });
        bottomSheetDialog.show();
    }
}
