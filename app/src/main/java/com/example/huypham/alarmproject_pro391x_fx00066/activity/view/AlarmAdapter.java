package com.example.huypham.alarmproject_pro391x_fx00066.activity.view;


import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.example.huypham.alarmproject_pro391x_fx00066.R;
import com.example.huypham.alarmproject_pro391x_fx00066.activity.model.Alarm;

import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.TimeViewHolder> {
    private List<Alarm> mAlarms= new ArrayList<>();
    public AlarmAdapter(List<Alarm> listAlarm, CallBack listener) {

        insertAlarm(listAlarm);
        this.CallBack = listener;
    }

    public interface CallBack {
        //Callback xử lý logic cho menu edit và delete
        void onMenuAction(Alarm object, MenuItem item, int position);

        //Callback xử lý logic start alarm
        void startAlarm(Alarm timeItem,int position);

        //Callback xử lý logic cancel alarm
        void cancelAlarm(Alarm timeItem,int position);
    }

    public CallBack CallBack;

    @Override
    public TimeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //TODO: Khởi tạo alarm_item thông qua inflate.
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.alarm_item,parent,false);
        return  new TimeViewHolder(itemView,CallBack);
    }

    @Override
    public void onBindViewHolder(TimeViewHolder holder, int position) {
            holder.bindView(mAlarms.get(position));
            holder.txtAlarmTime.setText(holder.getStringFromTime(mAlarms.get(position).getHour(),mAlarms.get(position).getMinutes()));
            holder.txtAlarmTilte.setText(mAlarms.get(position).getTitle());

    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return mAlarms.size();
    }

    public class TimeViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener, PopupMenu.OnMenuItemClickListener {
        TextView txtAlarmTime;
        TextView txtAlarmTilte;
        Button btn_onoff;
        public TimeViewHolder(View itemView, final CallBack CallBack) {
            super(itemView);
            itemView.setOnCreateContextMenuListener(this);
            txtAlarmTilte = itemView.findViewById(R.id.txtAlarmTitle);
            txtAlarmTime = itemView.findViewById(R.id.txtAlarmTime);
            btn_onoff = itemView.findViewById(R.id.btnAlarmItem);
            btn_onoff.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean selected = btn_onoff.isSelected();
                    btn_onoff.setText ((selected) ? "ON" : "OFF");
                    btn_onoff.setSelected(!selected);
                    if(selected == false){
                        CallBack.cancelAlarm(mAlarms.get(getAdapterPosition()),getAdapterPosition());

                    }
                    else{
                        CallBack.startAlarm(mAlarms.get(getAdapterPosition()),getAdapterPosition());
                    }

                }
            });

        }

        private void bindView(Alarm alarm) {
        }

        private String getStringFromTime(int hour, int minute) {
            return hour +" : "+minute;
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            PopupMenu popup = new PopupMenu(view.getContext(), view);
            popup.getMenuInflater().inflate(R.menu.menu_edit_cancle, popup.getMenu());
            popup.setOnMenuItemClickListener(this);
            popup.show();
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            int id = menuItem.getItemId();
            switch (id){
                case R.id.cancle:
                    removeAlarm(this.getAdapterPosition());
                    break;
                case R.id.edit:
                    CallBack.onMenuAction(mAlarms.get(getAdapterPosition()),menuItem,getAdapterPosition());
                    break;
            }
            return true;
        }
    }

    public void insertAlarm(List<Alarm> alarm) {
        //TODO: Xử lý logic thêm alarm vào adapter
        this.mAlarms = alarm;
        notifyDataSetChanged();
    }

    public void updateAlarm(Alarm alarm, int position) {
        //TODO: Xử lý logic sửa alarm từ adapter
        mAlarms.remove(position);
        mAlarms.add(position,alarm);
        notifyDataSetChanged();

    }
    public void removeAlarm(int position) {
        //TODO: Xử lý logic xóa alarm khỏi adapter
        mAlarms.remove(position);
        notifyDataSetChanged();
    }

}
