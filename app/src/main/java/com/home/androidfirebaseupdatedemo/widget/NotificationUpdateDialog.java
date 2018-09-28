package com.home.androidfirebaseupdatedemo.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.home.androidfirebaseupdatedemo.R;

/**
 * 自定义Dialog
 */
public class NotificationUpdateDialog extends Dialog {

    private TextView urlTextView;
    private Button okButton;
    private String url;
    private View.OnClickListener onOkListener;                                                                   // 0、隐藏1、显示

    public NotificationUpdateDialog(Context context) {
        super(context);
    }

    /**
     * @param context 上下文
     * @param theme   给dialog设置的主题
     */
    public NotificationUpdateDialog(Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.widget_dialog_notification_update);

        initializationView();
        initializeBusinessDetails();
    }

    private void initializationView() {
        urlTextView = findViewById(R.id.urlTextView);
        urlTextView.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);                            // 下划线
        okButton = findViewById(R.id.okButton);
    }

    private void initializeBusinessDetails() {
        if (!TextUtils.isEmpty(url)) {
            urlTextView.setVisibility(View.VISIBLE);
            urlTextView.setText(url);
        } else {
            urlTextView.setVisibility(View.GONE);
        }
        if (onOkListener != null) {
            urlTextView.setVisibility(View.VISIBLE);
            urlTextView.setOnClickListener(onOkListener);
            okButton.setVisibility(View.VISIBLE);
            okButton.setOnClickListener(onOkListener);
        } else {
            urlTextView.setVisibility(View.GONE);
            okButton.setVisibility(View.GONE);
        }
    }

    public void setUrlText(String url) {
        this.url = url;
    }

    public void setOnOkListener(View.OnClickListener onOkListener) {
        this.onOkListener = onOkListener;
    }
}