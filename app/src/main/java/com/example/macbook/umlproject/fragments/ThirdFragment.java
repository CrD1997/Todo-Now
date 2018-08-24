package com.example.macbook.umlproject.fragments;

import android.app.Notification;
import android.app.NotificationManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.macbook.umlproject.R;
import com.example.macbook.umlproject.views.TomatoView;

import static android.content.Context.NOTIFICATION_SERVICE;

public class ThirdFragment extends Fragment {

    public static TomatoView clockView;
    TextView startView;
    TextView giveupView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.fragment_third,container,false);

        clockView = view.findViewById(R.id.clockView);
        //开始一个番茄时钟
        startView = view.findViewById(R.id.tv_start);
        startView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //震动
                NotificationManager manager=(NotificationManager) ThirdFragment.this.getActivity().getSystemService(NOTIFICATION_SERVICE);
                Notification notification=new NotificationCompat.Builder(ThirdFragment.this.getActivity(),"default")
                        .setContentTitle("消息提醒")
                        .setContentText("有一个番茄时钟结束了")
                        .setWhen(System.currentTimeMillis())
                        .setSmallIcon(R.mipmap.ic_launcher_round)
                        .setVibrate(new long[]{0,1000,1000,1000})
                        .setDefaults(Notification.DEFAULT_ALL)
                        .build();
                //锁屏显示
                getActivity().getWindow().addFlags(
                        WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED|
                                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

                clockView.giveUp=false;
                clockView.start(manager,notification);

            }
        });
        //放弃当前番茄时钟
        giveupView=view.findViewById(R.id.tv_giveup);
        giveupView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                clockView.giveUp=true;
                //Toast.makeText(ThirdFragment.this.getActivity(),"放弃",Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


}

