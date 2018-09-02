package com.example.huypham.alarmproject_pro391x_fx00066.activity.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;
import android.widget.Toast;
import java.util.Calendar;
import java.util.Date;

import com.example.huypham.alarmproject_pro391x_fx00066.R;
import com.example.huypham.alarmproject_pro391x_fx00066.activity.model.Alarm;

public class AddAlarmActivity extends AppCompatActivity {
    public final static int  RESULT_CODE_SEND = 2;
    public final static int RESULT_CODE_EDIT = 3;
    public static String  GET_ALARM = "GET_ALARM";
    TimePicker time;
    Alarm alarm;
    Button btnOK;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alarm);
        initView();
    }
    void initView(){
        time = findViewById(R.id.timePicker);
        btnOK = findViewById(R.id.btnOK);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        Bundle bundle = getIntent().getExtras();
        String action = getIntent().getAction();

        if(action.equals("ADD")){
            getSupportActionBar().setTitle("ADD");
            alarm = (Alarm) bundle.getSerializable(AlarmMainActivity.SEND_ALARM);
            Calendar calendar = Calendar.getInstance();
            Date date = calendar.getTime();
            alarm.setHour(date.getHours());
            alarm.setMinutes(date.getMinutes());
            time.setOnTimeChangedListener(new OnTimeChangedListener() {
                @Override
                public void onTimeChanged(TimePicker timePicker, int hour, int minute) {
                    alarm.setHour(hour);
                    alarm.setMinutes(minute);
                    Toast.makeText(AddAlarmActivity.this,alarm.getHour()+"-"+alarm.getMinutes(),Toast.LENGTH_SHORT).show();
                }
            });
            btnOK.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    setIntent();
                }
            });
        }
        else if(action.equals("EDIT")){
            getSupportActionBar().setTitle("EDIT");
            alarm = (Alarm) bundle.getSerializable(AlarmMainActivity.SEND_ALARM);
            Calendar calendar = Calendar.getInstance();
            Date date = calendar.getTime();
            alarm.setHour(date.getHours());
            alarm.setMinutes(date.getMinutes());
            final int position = bundle.getInt("position");
            time.setOnTimeChangedListener(new OnTimeChangedListener() {
                @Override
                public void onTimeChanged(TimePicker timePicker, int hour, int minute) {
                    alarm.setHour(hour);
                    alarm.setMinutes(minute);
                    Toast.makeText(AddAlarmActivity.this,alarm.getHour()+"-"+alarm.getMinutes(),Toast.LENGTH_SHORT).show();
                }
            });
            btnOK.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    setIntentForEdit(position,alarm);
                }
            });
        }



    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()== android.R.id.home){
            super.onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
    public void setIntent(){
        Intent intent = new Intent();
        intent.putExtra(GET_ALARM,alarm);
        setResult(RESULT_CODE_SEND,intent);
        finish();
    }
    public void setIntentForEdit(int position,Alarm alarm){
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable(GET_ALARM,alarm);
        bundle.putInt("position",position);
        intent.putExtras(bundle);
        setResult(RESULT_CODE_EDIT,intent);
        finish();
    }
}
