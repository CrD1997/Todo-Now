package com.example.macbook.umlproject.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.macbook.umlproject.R;
import com.example.macbook.umlproject.classes.Constants;
import com.example.macbook.umlproject.classes.Thing;

import static com.example.macbook.umlproject.fragments.FirstFragment.mDatabaseHelper;
import static com.example.macbook.umlproject.fragments.ThirdFragment.clockView;

public class FifthFragment extends Fragment {

    LinearLayout set;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.fragment_fifth,container,false);

        set=(LinearLayout)view.findViewById(R.id.set_clocktime);
        set.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //mDatabaseHelper.updateSet();
                AlertDialog.Builder builder = new AlertDialog.Builder(FifthFragment.this.getActivity());
                LayoutInflater inflater = LayoutInflater.from(FifthFragment.this.getActivity());
                View viewDialog = inflater.inflate(R.layout.dialog_set_maxclock, null);
                final EditText time = (EditText) viewDialog.findViewById(R.id.set_clock_time);
                builder.setView(viewDialog);
                builder.setTitle("修改最大时钟时间");
                builder.setPositiveButton(Constants.STATUS_OK, new DialogInterface.OnClickListener() {
                    @Override public void onClick(DialogInterface dialog, int which) {
                        mDatabaseHelper.updateSet(Integer.parseInt(time.getText().toString()));
                        clockView.MAX_TIME=Integer.parseInt(time.getText().toString());
                    }
                });
                builder.setNegativeButton(Constants.STATUS_CANCEL, null);
                builder.create().show();
            }
        });

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

}