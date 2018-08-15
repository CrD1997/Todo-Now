package com.example.macbook.umlproject;

import android.widget.PopupWindow;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.PopupWindow;

public class SelectPicPopupWindow extends PopupWindow {

    private Button edit,delete,cancel;
    private View mMenuView;

    public SelectPicPopupWindow(Context context,OnClickListener itemsOnClick) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.popupwindow_first, null);
        edit = (Button) mMenuView.findViewById(R.id.edit_thing);
        delete = (Button) mMenuView.findViewById(R.id.delete_thing);
        cancel = (Button) mMenuView.findViewById(R.id.cancel_thing);

        //点击取消按钮
        cancel.setOnClickListener(new OnClickListener() {
            public void onClick(View v){
                dismiss();
            }
        });

        //设置按钮监听
        edit.setOnClickListener(itemsOnClick);
        delete.setOnClickListener(itemsOnClick);
        //设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(LayoutParams.FILL_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LayoutParams.WRAP_CONTENT);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);

    }

}
