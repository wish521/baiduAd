package com.baidu.baiduapplication;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class WaitingDialog extends Dialog {

    private String message;

    public WaitingDialog(Context context, String message) {
        super(context);
        this.message = message;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 设置对话框外部区域背景为透明，去除默认的白色背景
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#80000000")));

        // 设置对话框没有标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 设置对话框内容视图
        setContentView(R.layout.waiting);

        // 设置对话框宽度为屏幕宽度的70%
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.width = (int) (getContext().getResources().getDisplayMetrics().widthPixels * 0.7);
        getWindow().setAttributes(layoutParams);

        // 设置对话框不可取消
        setCancelable(false);

        // 设置等待消息
        TextView messageTextView = findViewById(R.id.message_textview);
        messageTextView.setText(message);
    }
}