package com.example.macbook.umlproject.classes;


import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.macbook.umlproject.R;
import com.example.macbook.umlproject.views.SelectPicPopupWindow;

import java.util.List;


import static com.example.macbook.umlproject.fragments.FirstFragment.mDatabaseHelper;


public class ThingAdapter extends RecyclerView.Adapter<ThingAdapter.ViewHolder>{

    private List<Thing> mThingList;
    int editPosition;
    SelectPicPopupWindow menuWindow;
    Thing editThing;

    //内部类：放置CheckBox与TextView的容器
    static class ViewHolder extends RecyclerView.ViewHolder {
        View thingView;
        CheckBox mThingCheckBox;
        TextView nTextView;
        TextView mTextView;

        public ViewHolder(View view) {
            super(view);
            thingView=view;
            mThingCheckBox = (CheckBox) view.findViewById(R.id.thing_checkBox);
            nTextView=(TextView)view.findViewById(R.id.thing_name);
            mTextView=(TextView)view.findViewById(R.id.thing_date);
        }
    }

    public ThingAdapter(List<Thing> nthingList) {
        mThingList=nthingList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.thing_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        //点击子项时进行编辑或删除
        holder.thingView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editPosition = holder.getAdapterPosition();
                editThing = mThingList.get(editPosition);
                //弹出底部窗口，进行删除
                menuWindow= new SelectPicPopupWindow(v.getContext(), itemsOnClick);
                menuWindow.showAtLocation(v, Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
                //Toast.makeText(v.getContext(), "删除或编辑事项 " + thing.getName(), Toast.LENGTH_SHORT).show();
            }
        });
        //响应checkbox
        holder.mThingCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                int position = holder.getAdapterPosition();
                Thing thing= mThingList.get(position);
                if(b){
                    thing.ifDone=true;
                    mDatabaseHelper.updateThing(thing);
                    Toast.makeText(compoundButton.getContext(), "完成 " + thing.getName(), Toast.LENGTH_SHORT).show();
                }else{
                    thing.ifDone=false;
                    mDatabaseHelper.updateThing(thing);
                    Toast.makeText(compoundButton.getContext(), "取消完成 " + thing.getName(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Thing thing = mThingList.get(position);
        holder.nTextView.setText(thing.getName());
        holder.mThingCheckBox.setChecked(thing.getIfDone());
        holder.mTextView.setText(thing.getDate());
    }

    @Override
    public int getItemCount() {
        return mThingList.size();
    }

    //为底部弹出窗口设置监听事件
    public View.OnClickListener itemsOnClick = new View.OnClickListener() {
        public void onClick(View v) {
            menuWindow.dismiss();
            switch (v.getId()) {
                case R.id.edit_thing:
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    LayoutInflater inflater = LayoutInflater.from(v.getContext());
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
                            mDatabaseHelper.deleteThing(editThing);
                            mDatabaseHelper.insertThing(thing);
                            mThingList.remove(editThing);
                            mThingList.add(thing);
                            notifyDataSetChanged();
                        }
                    });
                    builder.setNegativeButton(Constants.STATUS_CANCEL, null);
                    builder.create().show();
                    break;
                case R.id.delete_thing:
                    mDatabaseHelper.deleteThing(editThing);
                    mThingList.remove(editThing);
                    notifyDataSetChanged();
                    break;
                default:
                    break;
            }
        }
    };


}
