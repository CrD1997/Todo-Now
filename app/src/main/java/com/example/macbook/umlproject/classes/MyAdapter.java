package com.example.macbook.umlproject.classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.macbook.umlproject.R;
import com.example.macbook.umlproject.views.SwipeMenuViewGroup;

import java.util.List;

/**
 * Created by MacBook on 2018/8/23.
 */

public class MyAdapter extends BaseAdapter implements View.OnClickListener {

        private Context mContext;
        private LayoutInflater inflater;
        private InnerItemOnclickListener mListener;
        private List<Tag> mList;

        public MyAdapter(List<Tag> mList,Context mContext) {
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
                convertView = inflater.inflate(R.layout.tag_list_item, parent, false);
                viewHolder.rootView = (SwipeMenuViewGroup) convertView;
                viewHolder.rootView.setSwipeEnable(true);
                viewHolder.tagView= (LinearLayout) convertView.findViewById(R.id.tag_view);
                viewHolder.tagNameTv= (TextView) convertView.findViewById(R.id.tag_name_tv);
                viewHolder.tagColorTv = (TextView) convertView.findViewById(R.id.tag_color_tv);
                viewHolder.deleteIv = (TextView) convertView.findViewById(R.id.iv_delete);
                viewHolder.editIv = (TextView) convertView.findViewById(R.id.iv_edit);

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
//            if(position % 2 == 0) {
//                viewHolder.rootView.setSwipeEnable(false);
//            }
            viewHolder.tagNameTv.setText(mList.get(position).getName());
            viewHolder.tagColorTv.setBackgroundColor(mList.get(position).getColor());
            viewHolder.editIv.setTag(position);
            viewHolder.deleteIv.setTag(position);
            viewHolder.editIv.setOnClickListener(this);
            viewHolder.deleteIv.setOnClickListener(this);

            return convertView;
        }

        class ViewHolder {
            SwipeMenuViewGroup rootView;
            LinearLayout tagView;
            TextView tagNameTv;
            TextView tagColorTv;
            TextView deleteIv;
            TextView editIv;
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

