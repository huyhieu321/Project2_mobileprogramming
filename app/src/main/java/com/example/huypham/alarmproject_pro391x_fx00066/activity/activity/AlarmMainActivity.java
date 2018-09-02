package com.example.huypham.alarmproject_pro391x_fx00066.activity.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.huypham.alarmproject_pro391x_fx00066.R;
import com.example.huypham.alarmproject_pro391x_fx00066.activity.model.Alarm;
import com.example.huypham.alarmproject_pro391x_fx00066.activity.receiver.AlarmReceiver;
import com.example.huypham.alarmproject_pro391x_fx00066.activity.view.AlarmAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AlarmMainActivity extends AppCompatActivity implements AlarmAdapter.CallBack {
    private List<Alarm> listAlarm = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private AlarmAdapter mRcvAdapter;
    private Alarm alarm;
    public static String SEND_ALARM = "SEND_ALARM";
    public static int REQUEST_CODE = 1;
    private Intent intent;
    private Bundle bundle;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private Calendar calendar;
    private  Intent broadcastIntent;
    int count = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_main);
        initView();
    }

    private void initView() {
        //TODO: Khởi tạo các thành phần của giao diện và cài đặt sự kiện cho màn hình MH-1
        mRcvAdapter = new AlarmAdapter(listAlarm, this);
        mRecyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mRcvAdapter);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
    }

    @Override
    public void onMenuAction(Alarm alarm, MenuItem item, int position) {
        //TODO: Xử lý logic khi chọn Edit, Delete trên menu
        // send by Bundle.
         intent = new Intent(AlarmMainActivity.this, AddAlarmActivity.class);
         bundle = new Bundle();
         intent.setAction("EDIT");
         bundle.putString("title","EDIT"); // send title for AddAlarmActivity.
//         bundle.putInt("position",position);
         bundle.putSerializable(SEND_ALARM,alarm);
         intent.putExtras(bundle);
         startActivityForResult(intent,REQUEST_CODE);
    }

    @Override
    public void startAlarm(Alarm alarm,int position) {
        //TODO: Xử lý truyền thông tin giờ hẹn cho AlarmReceiver
        Log.i("MES","I'm in start Alarm");
        broadcastIntent = new Intent(AlarmMainActivity.this,AlarmReceiver.class);
        Toast.makeText(this,alarm.getTitle()+" ON",Toast.LENGTH_SHORT).show();
        calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,alarm.getHour());
        calendar.set(Calendar.MINUTE,alarm.getMinutes());
        broadcastIntent.putExtra("extra","on");
        pendingIntent = PendingIntent.getBroadcast(AlarmMainActivity.this,1,broadcastIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, pendingIntent); // Set Alarm repeat interval_day.
//       alarmManager.set(AlarmManager.RTC,calendar.getTimeInMillis(),pendingIntent);


    }

    @Override
    public void cancelAlarm(Alarm timeItem,int position) {
        //TODO: Gửi thông tin giờ hẹn cần hủy sang cho AlarmReceiver
        Log.i("MES","I'm in cancle Alarm");
        broadcastIntent.putExtra("extra","off");
        sendBroadcast(broadcastIntent);
        //pendingIntent = PendingIntent.getBroadcast(AlarmMainActivity.this,1,broadcastIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pendingIntent);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //TODO: Xử lý stop service
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        intent = new Intent(AlarmMainActivity.this, AddAlarmActivity.class);
        bundle = new Bundle();
        int id = item.getItemId();
        switch (id) {
            case R.id.action_add:
                alarm = new Alarm();
                /// send by Bundle.
                intent.setAction("ADD");
                bundle.putString("title","ADD"); // Send title for AddAlarmActivity.
                bundle.putSerializable(SEND_ALARM, alarm);
                intent.putExtras(bundle);
                startActivityForResult(intent, REQUEST_CODE);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE){
            switch (resultCode){
                case AddAlarmActivity.RESULT_CODE_SEND:
                    alarm =(Alarm) data.getSerializableExtra(AddAlarmActivity.GET_ALARM); //  Alarm from AddAlarmActivity with RESULT CODE SEND
                    alarm.setTitle("Alarm"+ count);
                    count++;
                    listAlarm.add(alarm); // add new Alarm to current
                    mRcvAdapter.insertAlarm(listAlarm); // add new Alarm to Apdater to get View
                    mRcvAdapter.notifyDataSetChanged();
                    startAlarm(alarm,1);
                    break;
                case AddAlarmActivity.RESULT_CODE_EDIT:
                    Bundle bundle = data.getExtras();
                    alarm = (Alarm)bundle.getSerializable(AddAlarmActivity.GET_ALARM); // Get Alarm from AddAlarmActivity with RESULT CODE EDIT
                    listAlarm.remove(bundle.getInt("position"));
                    listAlarm.add(bundle.getInt("position"),alarm);
                    mRcvAdapter.updateAlarm(alarm,bundle.getInt("position"));// Update Alarm in current list.
                    mRcvAdapter.notifyDataSetChanged();
                    break;

            }
        }
    }

        //TODO: Nhận kết quả trả về của AlarmMainActivity để xử lý cancel alarm và thêm alarm

}


