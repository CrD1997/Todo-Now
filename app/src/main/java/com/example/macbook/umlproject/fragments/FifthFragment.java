package com.example.macbook.umlproject.fragments;

import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.macbook.umlproject.R;
import com.example.macbook.umlproject.classes.Constants;
import com.example.macbook.umlproject.classes.MyAdapter;
import com.example.macbook.umlproject.classes.Tag;
import com.example.macbook.umlproject.classes.TagAdapter;
import com.example.macbook.umlproject.classes.Thing;
import com.github.mummyding.colorpickerdialog.ColorPickerDialog;
import com.github.mummyding.colorpickerdialog.OnColorChangedListener;

import java.util.ArrayList;
import java.util.List;


import static android.widget.AdapterView.*;
import static com.example.macbook.umlproject.activitys.MainActivity.mDatabaseHelper;

public class FifthFragment extends Fragment implements MyAdapter.InnerItemOnclickListener,OnItemClickListener {

    private View view;
    private ListView mTagListView;
    public static List<Tag> mTagList;
    private ImageView mAddTag;
    MyAdapter myAdapter;
    //Tag tag;
    int mPosition=0;

    int [] colors = new int[]{Color.parseColor("#FF6D6D"),
            Color.parseColor("#FFFA60"),
            Color.parseColor("#8EFF99"),
            Color.parseColor("#6EFFD2"),
            Color.parseColor("#BBA8FF"),
            Color.parseColor("#FFAAE2"),
            Color.parseColor("#FF942C"),
            Color.parseColor("#B8EB3E"),
            Color.parseColor("#6EA4FF"),
            Color.parseColor("#3B6EEC"),
            Color.parseColor("#904CC5"),
            Color.parseColor("#C19083")
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        view=inflater.inflate(R.layout.fragment_fifth,container,false);

        mTagListView = (ListView) view.findViewById(R.id.list_view_tags);
        getData();
        myAdapter=new MyAdapter(mTagList,FifthFragment.this.getActivity());
        myAdapter.setOnInnerItemOnClickListener(FifthFragment.this);
        mTagListView.setAdapter(myAdapter);
        mTagListView.setOnItemClickListener(FifthFragment.this);

        //添加标签
        mAddTag=(ImageView)view.findViewById(R.id.add_tag);
        mAddTag.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                AlertDialog.Builder builder = new AlertDialog.Builder(FifthFragment.this.getActivity());
                LayoutInflater inflater = LayoutInflater.from(FifthFragment.this.getActivity());
                View viewDialog = inflater.inflate(R.layout.dialog_add_tag, null);
                final EditText name = (EditText) viewDialog.findViewById(R.id.add_tag_name);
                final TextView color=(TextView)viewDialog.findViewById(R.id.add_tag_color);
                builder.setView(viewDialog);
                //为标签添加颜色
                color.setOnClickListener(new View.OnClickListener(){
                    @Override public void onClick(View v){
                        ColorPickerDialog dialog =
                        // Constructor,the first argv is Context,second one is the colors you want to add
                        new ColorPickerDialog(FifthFragment.this.getActivity(),colors)
                                .setDismissAfterClick(true)
                                .setTitle("标签颜色")
                                //Optional, current checked color
                                .setCheckedColor(Color.BLACK)
                                .setOnColorChangedListener(new OnColorChangedListener() {
                                    @Override
                                    public void onColorChanged(int newColor) {
                                        // do something here
                                        //Toast.makeText(view.getContext(),"Color "+newColor,Toast.LENGTH_SHORT).show();
                                        color.setBackgroundColor(newColor);
                                        color.setTextColor(newColor);
                                    }})
                                .build(6)
                                .show();
                    }
                });
                //确定修改标签
                builder.setPositiveButton(Constants.STATUS_OK, new DialogInterface.OnClickListener() {
                    @Override public void onClick(DialogInterface dialog, int which) {
                        Tag tag=new Tag(name.getText().toString(),color.getCurrentTextColor(),false);
                        mTagList.add(tag);
                        myAdapter.notifyDataSetChanged();
                        //Toast.makeText(view.getContext(),"成功修改标签",Toast.LENGTH_SHORT).show();
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

    @Override
    public void itemClick(View v) {
        final int position = (Integer) v.getTag();
        //TagAdapter.ViewHolder holder=new TagAdapter.ViewHolder(view);
        switch (v.getId()) {
            case R.id.iv_edit:
                AlertDialog.Builder builder = new AlertDialog.Builder(FifthFragment.this.getActivity());
                LayoutInflater inflater = LayoutInflater.from(FifthFragment.this.getActivity());
                View viewDialog = inflater.inflate(R.layout.dialog_add_tag, null);
                final EditText name = (EditText) viewDialog.findViewById(R.id.add_tag_name);
                final TextView color=(TextView)viewDialog.findViewById(R.id.add_tag_color);
                name.setText(mTagList.get(position).getName());
                color.setTextColor(mTagList.get(position).getColor());
                color.setBackgroundColor(mTagList.get(position).getColor());
                builder.setView(viewDialog);
                //为标签添加颜色
                color.setOnClickListener(new View.OnClickListener(){
                    @Override public void onClick(View v){
                        ColorPickerDialog dialog =
                                // Constructor,the first argv is Context,second one is the colors you want to add
                                new ColorPickerDialog(FifthFragment.this.getActivity(),colors)
                                        .setDismissAfterClick(true)
                                        .setTitle("标签颜色")
                                        //Optional, current checked color
                                        .setCheckedColor(Color.BLACK)
                                        .setOnColorChangedListener(new OnColorChangedListener() {
                                            @Override
                                            public void onColorChanged(int newColor) {
                                                // do something here
                                                //Toast.makeText(view.getContext(),"Color "+newColor,Toast.LENGTH_SHORT).show();
                                                color.setBackgroundColor(newColor);
                                                color.setTextColor(newColor);
                                            }})
                                        .build(6)
                                        .show();
                    }
                });
                //确定修改标签
                builder.setPositiveButton(Constants.STATUS_OK, new DialogInterface.OnClickListener() {
                    @Override public void onClick(DialogInterface dialog, int which) {
                        Tag tag=new Tag(name.getText().toString(),color.getCurrentTextColor(),false);
                        mTagList.get(position).name=tag.getName();
                        mTagList.get(position).color=tag.getColor();
                        myAdapter.notifyDataSetChanged();
                        //Toast.makeText(view.getContext(),"成功修改标签",Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton(Constants.STATUS_CANCEL, null);
                builder.create().show();
                //Toast.makeText(view.getContext(),"编辑",Toast.LENGTH_SHORT).show();
                break;
            case R.id.iv_delete:
                mTagList.remove(position);
                myAdapter.notifyDataSetChanged();
                //Toast.makeText(view.getContext(),"删除",Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //整个子项
        //mPosition=position;
        //tag=new Tag(mTagList.get(position).name,mTagList.get(position).color,mTagList.get(position).ifUse);
    }

    private List<Tag> getData() {
        mTagList = new ArrayList<>();
        for(int i=0;i<5;i++){
            Tag tag=new Tag("标签"+i,Color.parseColor("#6bbbec"),false);
            mTagList.add(tag);
        }
        return mTagList;
    }

}



