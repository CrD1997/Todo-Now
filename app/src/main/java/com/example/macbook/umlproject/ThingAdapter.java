package com.example.macbook.umlproject;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;

import static com.example.macbook.umlproject.FirstFragment.mDatabaseHelper;

public class ThingAdapter extends RecyclerView.Adapter<ThingAdapter.ViewHolder>{

    private List<Thing> mThingList;

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

    public ThingAdapter(List<Thing> thingList) {
        mThingList=thingList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.thing_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        //点击子项时进行编辑或删除
        holder.thingView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Thing thing = mThingList.get(position);
                Toast.makeText(v.getContext(), "删除或编辑事项 " + thing.getName(), Toast.LENGTH_SHORT).show();
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

}
