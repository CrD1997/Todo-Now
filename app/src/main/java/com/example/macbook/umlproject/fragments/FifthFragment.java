package com.example.macbook.umlproject.fragments;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
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
import com.example.macbook.umlproject.classes.Thing;
import com.github.mummyding.colorpickerdialog.ColorPickerDialog;
import com.github.mummyding.colorpickerdialog.OnColorChangedListener;

import java.util.ArrayList;
import java.util.List;


import static android.widget.AdapterView.*;
import static com.example.macbook.umlproject.activitys.MainActivity.mDatabaseHelper;

public class FifthFragment extends Fragment implements MyAdapter.InnerItemOnclickListener,OnItemClickListener {

    private View view;
    private ListView mListView;
    public static List<Tag> mTagList;
    private ImageView mAddTag;

    int [] colors = new int[]{Color.YELLOW,Color.BLACK,Color.BLUE,Color.GRAY,
            Color.GREEN,Color.CYAN,Color.RED,Color.DKGRAY, Color.LTGRAY,Color.MAGENTA,
            Color.rgb(100,22,33),Color.rgb(82,182,2), Color.rgb(122,32,12),Color.rgb(82,12,2),
            Color.rgb(89,23,200),Color.rgb(13,222,23), Color.rgb(222,22,2),Color.rgb(2,22,222)};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        view=inflater.inflate(R.layout.fragment_fifth,container,false);

        mListView = (ListView) view.findViewById(R.id.list_view_tags);
        getData();
        MyAdapter myAdapter=new MyAdapter(mTagList,FifthFragment.this.getActivity());
        myAdapter.setOnInnerItemOnClickListener(FifthFragment.this);
        mListView.setAdapter(myAdapter);
        mListView.setOnItemClickListener(FifthFragment.this);

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
                                        Toast.makeText(view.getContext(),"Color "+newColor,Toast.LENGTH_SHORT).show();
                                        color.setBackgroundColor(newColor );
                                    }})
                                .build(6)
                                .show();
                    }
                });
                //确定修改标签
                builder.setPositiveButton(Constants.STATUS_OK, new DialogInterface.OnClickListener() {
                    @Override public void onClick(DialogInterface dialog, int which) {
                        //
                        Toast.makeText(view.getContext(),"成功修改标签",Toast.LENGTH_SHORT).show();
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
        switch (v.getId()) {
            case R.id.iv_edit:
                Toast.makeText(view.getContext(),"编辑",Toast.LENGTH_SHORT).show();
                break;
            case R.id.iv_delete:
                Toast.makeText(view.getContext(),"删除",Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //整个子项
    }

    private List<Tag> getData() {
        mTagList = new ArrayList<>();
        for(int i=0;i<5;i++){
            Tag tag=new Tag("标签"+i,Color.parseColor("#6bbbec"),false);
            mTagList.add(tag);
        }
        for(int i=0;i<5;i++){
            Tag tag=new Tag("标签"+i,Color.parseColor("#6bbbec"),true);
            mTagList.add(tag);
        }
        return mTagList;
    }

}
