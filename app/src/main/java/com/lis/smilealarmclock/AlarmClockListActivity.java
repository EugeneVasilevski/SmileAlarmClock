package com.lis.smilealarmclock;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AlarmClockListActivity extends AppCompatActivity {

    private AlarmClockDatabase alarmClockDatabase;
    private List<AlarmClock> alarmClockList;

    private ImageButton imageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_clock_list);

        alarmClockDatabase = new AlarmClockDatabase(this);
        alarmClockDatabase.connection();

        alarmClockList = alarmClockDatabase.getAll();

        imageButton = (ImageButton) findViewById(R.id.create);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(alarmClockList);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    private class RecyclerViewAdapter extends
            RecyclerView.Adapter<RecyclerViewAdapter.AlarmClockHolder> {

        private List<AlarmClock> alarmClockList;
        private AlarmClockHolder alarmClockHolder;

        class AlarmClockHolder extends RecyclerView.ViewHolder {

            CardView cardView;
            TextView hour;
            TextView minute;
            TextView period;
            Button delete;

            public AlarmClockHolder(View itemView) {
                super(itemView);

                cardView = (CardView) itemView.findViewById(R.id.card_view);
                hour = (TextView) itemView.findViewById(R.id.hour);
                minute = (TextView) itemView.findViewById(R.id.minute);
                period = (TextView) itemView.findViewById(R.id.period);
                delete = (Button) itemView.findViewById(R.id.delete);

                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alarmClockList.remove(getPosition());
                        notifyItemRemoved(getPosition());
                    }
                });

                imageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alarmClockList.add(new AlarmClock(0, 20, 13, false, false, null));

                        notifyItemInserted(alarmClockList.size() - 1);
                        //notifyDataSetChanged();

                        /*Collections.sort(alarmClockList, new Comparator<AlarmClock>() {
                            @Override
                            public int compare(AlarmClock alarmClock1, AlarmClock alarmClock2) {
                                if (alarmClock1.getHour() > alarmClock2.getHour()) {
                                    return 1;
                                }
                                return 0;
                            }
                        });*/
                    }
                });
            }
        }

        RecyclerViewAdapter(List<AlarmClock> alarmClockList) {
            this.alarmClockList = alarmClockList;
        }

        @Override
        public AlarmClockHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

            View view = LayoutInflater.from(
                    viewGroup.getContext()).inflate(R.layout.card_alarm_clock, viewGroup, false);
            alarmClockHolder = new AlarmClockHolder(view);
            return alarmClockHolder;
        }

        @Override
        public void onBindViewHolder(AlarmClockHolder alarmClockHolder, int position) {
            final AlarmClock alarmClock = alarmClockList.get(position);

            alarmClockHolder.hour.setText(String.valueOf(alarmClock.getHour()));
            alarmClockHolder.minute.setText(String.valueOf(alarmClock.getMinute()));
        }

        @Override
        public int getItemCount() {
            return alarmClockList.size();
        }

        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
        }
    }
}
