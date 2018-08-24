package com.example.macbook.umlproject.classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.macbook.umlproject.R;

import java.util.List;

public class MyThingAdapter extends BaseAdapter implements View.OnClickListener {

    private Context mContext;
    private LayoutInflater inflater;
    private InnerItemOnclickListener mListener;
    private List<Thing> mList;

    public MyThingAdapter(List<Thing> mList,Context mContext) {
        this.mList=mList;
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
    }
    @Override
    public int getCount() {
        return mList.size();
    }
    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.thing_list_item, parent, false);
            viewHolder.rootView = (SwipeMenuViewGroup) convertView;
            viewHolder.rootView.setSwipeEnable(true);
            viewHolder.thingView=(LinearLayout)convertView.findViewById(R.id.myThing_view);
            viewHolder.thingTagTv=(TextView)convertView.findViewById(R.id.myThing_color);
            viewHolder.thingNameTv=(TextView)convertView.findViewById(R.id.myThing_name);
            viewHolder.thingFinishedTv=(TextView)convertView.findViewById(R.id.myThing_finished);
            viewHolder.thingAllTv=(TextView)convertView.findViewById(R.id.myThing_remaining);
            viewHolder.thingDateTv=(TextView)convertView.findViewById(R.id.myThing_date);
            viewHolder.thingStartIv=(ImageView)convertView.findViewById(R.id.start_myThing);
            viewHolder.thingPrb=(ProgressBar)convertView.findViewById(R.id.myThing_prb);
            viewHolder.thingEditIv=(ImageView)convertView.findViewById(R.id.edit_myThing);
            viewHolder.thingDeleteIv=(ImageView)convertView.findViewById(R.id.delete_myThing);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //完成的任务不能侧滑
        if(mList.get(position).isIfDone()) {
            viewHolder.rootView.setSwipeEnable(false);
            viewHolder.thingStartIv.setImageResource(R.drawable.ic_more);
        }
        viewHolder.thingTagTv.setBackgroundColor(mList.get(position).getColor());
        viewHolder.thingNameTv.setText(mList.get(position).getName());
        viewHolder.thingFinishedTv.setText(mList.get(position).getFinished()+"");
        viewHolder.thingAllTv.setText(mList.get(position).getAll()+"");
        viewHolder.thingDateTv.setText(mList.get(position).getDate());
        viewHolder.thingPrb.setMax(mList.get(position).getAll());
        viewHolder.thingPrb.setProgress(mList.get(position).getFinished());

        viewHolder.thingStartIv.setOnClickListener(this);
        viewHolder.thingDeleteIv.setOnClickListener(this);
        viewHolder.thingEditIv.setOnClickListener(this);

        return convertView;
    }

    class ViewHolder {
        SwipeMenuViewGroup rootView;
        LinearLayout thingView;
        TextView thingTagTv;
        TextView thingNameTv;
        TextView thingFinishedTv;
        TextView thingAllTv;
        TextView thingDateTv;
        ImageView thingStartIv;
        ProgressBar thingPrb;
        ImageView thingEditIv;
        ImageView thingDeleteIv;
    }

    public interface InnerItemOnclickListener {
        void itemClick(View v);
    }

    public void setOnInnerItemOnClickListener(InnerItemOnclickListener listener){
        this.mListener=listener;
    }

    @Override
    public void onClick(View v) {
        mListener.itemClick(v);
    }

}
