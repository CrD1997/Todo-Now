package com.example.macbook.umlproject.fragments;

import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.macbook.umlproject.classes.Constants;
import com.example.macbook.umlproject.classes.MyAdapter;
import com.example.macbook.umlproject.classes.MyThingAdapter;
import com.example.macbook.umlproject.classes.Tag;
import com.example.macbook.umlproject.classes.TagAdapter;
import com.example.macbook.umlproject.helpers.DatabaseHelper;
import com.example.macbook.umlproject.R;
import com.example.macbook.umlproject.classes.Thing;
import com.example.macbook.umlproject.classes.ThingAdapter;
import com.github.mummyding.colorpickerdialog.ColorPickerDialog;
import com.github.mummyding.colorpickerdialog.OnColorChangedListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.macbook.umlproject.activitys.MainActivity.mDatabaseHelper;
import static com.example.macbook.umlproject.fragments.FifthFragment.mTagList;


public class FirstFragment extends Fragment implements MyThingAdapter.InnerItemOnclickListener,TagAdapter.InnerItemOnclickListener,AdapterView.OnItemClickListener {

    private View view;
    private ImageView mAddTag;

    private ListView mListView;
    private List<Thing> mList;
    MyThingAdapter myThingAdapter;
    private ListView mFinishedListView;
    private List<Thing> mFinishedList;
    MyThingAdapter myFinishedThingAdapter;

    int choseColor=0;
    String choseTag="";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        view=inflater.inflate(R.layout.fragment_first,container,false);
        //待办任务列表
        getData();
        mListView = (ListView) view.findViewById(R.id.list_view_things);
        myThingAdapter=new MyThingAdapter(mList,FirstFragment.this.getActivity());
        myThingAdapter.setOnInnerItemOnClickListener(FirstFragment.this);
        mListView.setAdapter(myThingAdapter);
        setListViewHeightBasedOnChildren(mListView);
        mListView.setOnItemClickListener(FirstFragment.this);
        //完成任务列表
        getFinishedData();
        mFinishedListView = (ListView) view.findViewById(R.id.list_view_finished);
        myFinishedThingAdapter=new MyThingAdapter(mFinishedList,FirstFragment.this.getActivity());
        myFinishedThingAdapter.setOnInnerItemOnClickListener(FirstFragment.this);
        mFinishedListView.setAdapter(myFinishedThingAdapter);
        setListViewHeightBasedOnChildren(mFinishedListView);
        mFinishedListView.setOnItemClickListener(FirstFragment.this);
        //添加任务
        mAddTag=(ImageView)view.findViewById(R.id.add_myThing);
        mAddTag.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                final Thing thing=new Thing();
                AlertDialog.Builder builder = new AlertDialog.Builder(FirstFragment.this.getActivity());
                LayoutInflater inflater = LayoutInflater.from(FirstFragment.this.getActivity());
                View viewDialog = inflater.inflate(R.layout.dialog_add_thing, null);
                final TextView tag=(TextView)viewDialog.findViewById(R.id.add_myThing_tag);
                final EditText name = (EditText) viewDialog.findViewById(R.id.add_myThing_name);
                final DatePicker date = (DatePicker) viewDialog.findViewById(R.id.add_myThing_date);
                final TextView num=(TextView)viewDialog.findViewById(R.id.add_myThing_all);
                builder.setView(viewDialog);
                //...其它动作
                tag.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        AlertDialog.Builder nBuilder = new AlertDialog.Builder(FirstFragment.this.getActivity());
                        LayoutInflater inflater = LayoutInflater.from(FirstFragment.this.getActivity());
                        View viewDialog = inflater.inflate(R.layout.dialog_choose_tag, null);
                        final ListView mTagListView=(ListView) viewDialog.findViewById(R.id.list_view_chose_tags);
                        TagAdapter tagAdapter=new TagAdapter(mTagList,FirstFragment.this.getActivity());
                        tagAdapter.setOnInnerItemOnClickListener(FirstFragment.this);
                        mTagListView.setAdapter(tagAdapter);
                        mTagListView.setOnItemClickListener(FirstFragment.this);
                        nBuilder.setView(viewDialog);
                        //...其它动作
                        nBuilder.setPositiveButton(Constants.STATUS_OK, new DialogInterface.OnClickListener() {
                            @Override public void onClick(DialogInterface dialog, int which) {
                                tag.setText(choseTag);
                                tag.setGravity(Gravity.CENTER);
                                tag.setBackgroundColor(choseColor);
                                thing.color=choseColor;
                                thing.tag=choseTag;
                                //Toast.makeText(view.getContext(),"成功选择标签",Toast.LENGTH_SHORT).show();
                            }
                        });
                        nBuilder.setNegativeButton(Constants.STATUS_CANCEL, null);
                        nBuilder.create().show();
                        //Toast.makeText(view.getContext(),"选择标签",Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setPositiveButton(Constants.STATUS_OK, new DialogInterface.OnClickListener() {
                    @Override public void onClick(DialogInterface dialog, int which) {
                        thing.name=name.getText().toString();
                        thing.date = date.getYear() + "-" + (date.getMonth() + 1) + "-" + date.getDayOfMonth();
                        thing.all=Integer.parseInt(num.getText().toString());
                        mList.add(thing);
                        myThingAdapter.notifyDataSetChanged();
                        setListViewHeightBasedOnChildren(mListView);
                        //Toast.makeText(view.getContext(),"成功添加任务",Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton(Constants.STATUS_CANCEL, null);
                builder.create().show();
            }
        });

//        //初始化RecyclerView
//        initThings();
//        RecyclerView recyclerView=(RecyclerView) view.findViewById(R.id.recycler_view_things);
//        LinearLayoutManager layoutManager=new LinearLayoutManager(this.getActivity());////!!!
//        recyclerView.setLayoutManager(layoutManager);
//        final ThingAdapter adapter=new ThingAdapter(thingList);
//        recyclerView.setAdapter(adapter);
//
//        //悬浮标添加数据
//        FloatingActionButton addFab=(FloatingActionButton)view.findViewById(R.id.first_add_fab);
//        addFab.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v){
//                //Toast.makeText(getActivity(), "haha", Toast.LENGTH_SHORT).show();
//                AlertDialog.Builder builder = new AlertDialog.Builder(FirstFragment.this.getActivity());
//                LayoutInflater inflater = LayoutInflater.from(FirstFragment.this.getActivity());
//                View viewDialog = inflater.inflate(R.layout.dialog_add_thing, null);
//                final EditText name = (EditText) viewDialog.findViewById(R.id.add_thing_name);
//                final DatePicker date = (DatePicker) viewDialog.findViewById(R.id.add_thing_date);
//                builder.setView(viewDialog);
//                builder.setTitle(Constants.TITLE_ADD_THING);
//                builder.setPositiveButton(Constants.STATUS_OK, new DialogInterface.OnClickListener() {
//                    @Override public void onClick(DialogInterface dialog, int which) {
//                        //新增一条新数据
//                        Thing thing = new Thing();
//                        thing.name=name.getText().toString();
//                        thing.date = date.getYear() + "-" + (date.getMonth() + 1) + "-" + date.getDayOfMonth();
//                        //将新数据添加入数据库
//                        mDatabaseHelper.insertThing(thing);
//                        thingList.add(thing);
//                        adapter.notifyDataSetChanged();
//                    }
//                });
//                builder.setNegativeButton(Constants.STATUS_CANCEL, null);
//                builder.create().show();
//            }
//        });
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void itemClick(View v) {
        final int position=(Integer)v.getTag();
        switch (v.getId()) {
            case R.id.edit_myThing:
                final Thing ething=new Thing();
                AlertDialog.Builder builder = new AlertDialog.Builder(FirstFragment.this.getActivity());
                LayoutInflater inflater = LayoutInflater.from(FirstFragment.this.getActivity());
                View viewDialog = inflater.inflate(R.layout.dialog_add_thing, null);
                final TextView tag=(TextView)viewDialog.findViewById(R.id.add_myThing_tag);
                final EditText name = (EditText) viewDialog.findViewById(R.id.add_myThing_name);
                final DatePicker date = (DatePicker) viewDialog.findViewById(R.id.add_myThing_date);
                final TextView num=(TextView)viewDialog.findViewById(R.id.add_myThing_all);
                tag.setBackgroundColor(mList.get(position).getColor());
                tag.setText(mList.get(position).getTag());
                tag.setGravity(Gravity.CENTER);
                name.setText(mList.get(position).getName());
                num.setText(mList.get(position).getAll()+"");
                builder.setView(viewDialog);
                //...其它动作
                tag.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        AlertDialog.Builder nBuilder = new AlertDialog.Builder(FirstFragment.this.getActivity());
                        LayoutInflater inflater = LayoutInflater.from(FirstFragment.this.getActivity());
                        View viewDialog = inflater.inflate(R.layout.dialog_choose_tag, null);
                        final ListView mTagListView=(ListView) viewDialog.findViewById(R.id.list_view_chose_tags);
                        TagAdapter tagAdapter=new TagAdapter(mTagList,FirstFragment.this.getActivity());
                        tagAdapter.setOnInnerItemOnClickListener(FirstFragment.this);
                        mTagListView.setAdapter(tagAdapter);
                        mTagListView.setOnItemClickListener(FirstFragment.this);
                        nBuilder.setView(viewDialog);
                        //...其它动作
                        nBuilder.setPositiveButton(Constants.STATUS_OK, new DialogInterface.OnClickListener() {
                            @Override public void onClick(DialogInterface dialog, int which) {
                                tag.setText(choseTag);
                                tag.setGravity(Gravity.CENTER);
                                tag.setBackgroundColor(choseColor);
                                ething.color=choseColor;
                                ething.tag=choseTag;
                                //Toast.makeText(view.getContext(),"成功选择标签",Toast.LENGTH_SHORT).show();
                            }
                        });
                        nBuilder.setNegativeButton(Constants.STATUS_CANCEL, null);
                        nBuilder.create().show();
                        //Toast.makeText(view.getContext(),"选择标签",Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setPositiveButton(Constants.STATUS_OK, new DialogInterface.OnClickListener() {
                    @Override public void onClick(DialogInterface dialog, int which) {
                        ething.name=name.getText().toString();
                        ething.date = date.getYear() + "-" + (date.getMonth() + 1) + "-" + date.getDayOfMonth();
                        ething.all=Integer.parseInt(num.getText().toString());
                        mList.get(position).name=ething.getName();
                        mList.get(position).color=ething.getColor();
                        mList.get(position).all=ething.getAll();
                        mList.get(position).date=ething.getDate();
                        mList.get(position).tag=ething.getTag();
                        myThingAdapter.notifyDataSetChanged();
                        setListViewHeightBasedOnChildren(mListView);
                        //Toast.makeText(view.getContext(),"成功添加任务",Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton(Constants.STATUS_CANCEL, null);
                builder.create().show();
                //Toast.makeText(FirstFragment.this.getContext(),"编辑",Toast.LENGTH_SHORT).show();
                break;
            case R.id.delete_myThing:
                mList.remove(position);
                myThingAdapter.notifyDataSetChanged();
                setListViewHeightBasedOnChildren(mListView);
                //Toast.makeText(FirstFragment.this.getContext(),"删除",Toast.LENGTH_SHORT).show();
                break;
            case R.id.chose_tag_name:
                choseColor=mTagList.get(position).getColor();
                choseTag=mTagList.get(position).getName();
                Toast.makeText(FirstFragment.this.getContext(),"选择"+choseTag+"标签",Toast.LENGTH_SHORT).show();
            default:
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //整个子项
    }

    private List<Thing> getData() {
        mList = new ArrayList<>();
        for(int i=0;i<5;i++){
            Thing thing=new Thing("学习 "+i, "2018-1-21","学习",Color.parseColor("#6bbbec") ,i,i+1,false);
            mList.add(thing);
        }
        return mList;
    }

    private List<Thing> getFinishedData() {
        mFinishedList=new ArrayList<>();
        for(int i=0;i<5;i++){
            Thing thing=new Thing("学习 "+i, "2018-8-24","学习",Color.parseColor("#6bbbec") ,i+1,i+1,true);
            mFinishedList.add(thing);
        }
        return mFinishedList;
    }

    public void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }
        int totalHeight =0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        System.out.println("aaa==="+totalHeight);
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        //因为有两个layout
        params.height/=2;
        params.height+=10;
        listView.setLayoutParams(params);
    }


//    private void initThings(){
//        //mDatabaseHelper.deleteAllData();
//        Cursor cursor=mDatabaseHelper.getAllThingData();
//        if(cursor!=null){
//            while(cursor.moveToNext()){
//                Thing thing = new Thing();
//                thing.date= cursor.getString(cursor.getColumnIndex("thing_date"));
//                thing.name = cursor.getString(cursor.getColumnIndex("thing_name"));
//                if(cursor.getString(cursor.getColumnIndex("thing_ifdone")).equals("1")){
//                    thing.ifDone=true;
//                }else{
//                    thing.ifDone=false;
//                }
//                thingList.add(thing);
//            }
//            cursor.close();
//        }
//    }

}

