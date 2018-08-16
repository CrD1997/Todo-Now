package com.example.macbook.umlproject.fragments;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.macbook.umlproject.classes.Constants;
import com.example.macbook.umlproject.helpers.DatabaseHelper;
import com.example.macbook.umlproject.R;
import com.example.macbook.umlproject.classes.Thing;
import com.example.macbook.umlproject.classes.ThingAdapter;

import java.util.ArrayList;
import java.util.List;


public class FirstFragment extends Fragment {

    public List<Thing> thingList=new ArrayList<>();
    public static DatabaseHelper mDatabaseHelper;///

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.fragment_first,container,false);

        mDatabaseHelper = new DatabaseHelper(this.getActivity());
        //初始化RecyclerView
        initThings();
        RecyclerView recyclerView=(RecyclerView) view.findViewById(R.id.recycler_view_things);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this.getActivity());////!!!
        recyclerView.setLayoutManager(layoutManager);
        final ThingAdapter adapter=new ThingAdapter(thingList);
        recyclerView.setAdapter(adapter);

        //悬浮标添加数据
        FloatingActionButton addFab=(FloatingActionButton)view.findViewById(R.id.first_add_fab);
        addFab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //Toast.makeText(getActivity(), "haha", Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(FirstFragment.this.getActivity());
                LayoutInflater inflater = LayoutInflater.from(FirstFragment.this.getActivity());
                View viewDialog = inflater.inflate(R.layout.dialog_add_thing, null);
                final EditText name = (EditText) viewDialog.findViewById(R.id.add_thing_name);
                final DatePicker date = (DatePicker) viewDialog.findViewById(R.id.add_thing_date);
                builder.setView(viewDialog);
                builder.setTitle(Constants.TITLE_ADD_THING);
                builder.setPositiveButton(Constants.STATUS_OK, new DialogInterface.OnClickListener() {
                    @Override public void onClick(DialogInterface dialog, int which) {
                        //新增一条新数据
                        Thing thing = new Thing();
                        thing.name=name.getText().toString();
                        thing.date = date.getYear() + "-" + (date.getMonth() + 1) + "-" + date.getDayOfMonth();
                        //将新数据添加入数据库
                        mDatabaseHelper.insertThing(thing);
                        thingList.add(thing);
                        adapter.notifyDataSetChanged();
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

    private void initThings(){
        //mDatabaseHelper.deleteAllData();
        Cursor cursor=mDatabaseHelper.getAllThingData();
        if(cursor!=null){
            while(cursor.moveToNext()){
                Thing thing = new Thing();
                thing.date= cursor.getString(cursor.getColumnIndex("thing_date"));
                thing.name = cursor.getString(cursor.getColumnIndex("thing_name"));
                if(cursor.getString(cursor.getColumnIndex("thing_ifdone")).equals("1")){
                    thing.ifDone=true;
                }else{
                    thing.ifDone=false;
                }
                thingList.add(thing);
            }
            cursor.close();
        }
    }

}

