package com.home.androidfirebaseupdatedemo;

import android.content.Context;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

public class UpdateHelper {

    public static String KET_UPDATE_ENABLE = "is_update";
    public static String KET_UPDATE_VERSION = "version";
    public static String KET_UPDATE_URL = "update_url";
    private boolean isEnable;
    private String appVersion;
    private String remoteConfigVersion;
    private String updateURL;
    private FirebaseRemoteConfig firebaseRemoteConfig;

    public interface OnUpdateCheckListener {
        void onUpdateCheckListener(String url);
    }

    public static Builder with(Context context) {
        return new Builder(context);
    }

    private OnUpdateCheckListener onUpdateCheckListener;
    private Context context;

    public UpdateHelper(Context context, OnUpdateCheckListener onUpdateCheckListener) {
        this.context = context;
        this.onUpdateCheckListener = onUpdateCheckListener;
    }

    public void check() {
        firebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        isEnable = firebaseRemoteConfig.getBoolean(KET_UPDATE_ENABLE);

        if (isEnable) {
            remoteConfigVersion = firebaseRemoteConfig.getString(KET_UPDATE_VERSION);
            appVersion = getAppVersion(context);
            updateURL = firebaseRemoteConfig.getString(KET_UPDATE_URL);

            /** 如果版本號不同, 並且OnUpdateCheckListener有被實現, 就彈出通知更新的Dialog */
            if (!TextUtils.equals(remoteConfigVersion, appVersion) && onUpdateCheckListener != null) {
                onUpdateCheckListener.onUpdateCheckListener(updateURL);
            }
        }
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

    public static class Builder {

        private Context context;
        private OnUpdateCheckListener onUpdateCheckListener;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder onUpdateCheck(OnUpdateCheckListener onUpdateCheckListener) {
            this.onUpdateCheckListener = onUpdateCheckListener;
            return this;
        }

        public UpdateHelper build() {
            return new UpdateHelper(context, onUpdateCheckListener);
        }

        public UpdateHelper check() {
            UpdateHelper updateHelper = build();
            updateHelper.check();
            return updateHelper;
        }
    }
}

