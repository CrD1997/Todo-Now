package com.example.macbook.umlproject.classes;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.macbook.umlproject.R;
import com.example.macbook.umlproject.views.SelectPicPopupWindow;

import java.util.List;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class TagAdapter extends BaseAdapter implements View.OnClickListener {

    private Context mContext;
    private LayoutInflater inflater;
    private TagAdapter.InnerItemOnclickListener mListener;
    private List<Tag> mList;

    public TagAdapter(List<Tag> mList,Context mContext) {
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
        final TagAdapter.ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new TagAdapter.ViewHolder();
            convertView = inflater.inflate(R.layout.tag_item, parent, false);
            viewHolder.tagView=(LinearLayout)convertView.findViewById(R.id.chose_tag);
            viewHolder.tag=(TextView)convertView.findViewById(R.id.chose_tag_name);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (TagAdapter.ViewHolder) convertView.getTag();
        }
        viewHolder.tag.setBackgroundColor(mList.get(position).getColor());
        viewHolder.tag.setText(mList.get(position).getName());
        viewHolder.tag.setOnClickListener(this);
        return convertView;
    }

    class ViewHolder {
        LinearLayout tagView;
        TextView tag;
    }

    public interface InnerItemOnclickListener {
        void itemClick(View v);
    }

    public void setOnInnerItemOnClickListener(TagAdapter.InnerItemOnclickListener listener){
        this.mListener=listener;
    }

    @Override
    public void onClick(View v) {
        mListener.itemClick(v);
    }

}
