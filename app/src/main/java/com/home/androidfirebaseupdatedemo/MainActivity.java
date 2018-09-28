package com.home.androidfirebaseupdatedemo;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.home.androidfirebaseupdatedemo.widget.NotificationUpdateDialog;

public class MainActivity extends AppCompatActivity implements UpdateHelper.OnUpdateCheckListener {

    private NotificationUpdateDialog notificationUpdateDialog;
    private TextView appVersionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appVersionTextView =  findViewById(R.id.appVersionTextView);
        appVersionTextView.setText("Version: "+getAppVersion(this));

        UpdateHelper.with(this)
                .onUpdateCheck(this)
                .check();
    }

    @Override
    public void onUpdateCheckListener(final String url) {
        ShowNotificationUpdateDialog(url);
    }

    private void ShowNotificationUpdateDialog(final String url) {
        notificationUpdateDialog =
                new NotificationUpdateDialog(
                        this, R.style.ShowNotificationUpdateDialog);
        notificationUpdateDialog.setUrlText(url);
        notificationUpdateDialog.setOnOkListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.urlTextView:
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(url));
                        startActivity(intent);
                        break;
                    case R.id.okButton:
                        notificationUpdateDialog.dismiss();
                        break;
                }
            }
        });
        notificationUpdateDialog.show();
    }

    /**
     * 取得App的版本, 預設是1.0
     */
    public String getAppVersion(Context context) {
        String result = "";
        try {
            result = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
            result = result.replaceAll("[a-zA-Z]|-", "");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }
}