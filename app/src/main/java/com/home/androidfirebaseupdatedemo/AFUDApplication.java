package com.home.androidfirebaseupdatedemo;

import android.app.Application;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

import java.util.HashMap;
import java.util.Map;

public class AFUDApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        /** Default value */
        Map<String, Object> defaultValue = new HashMap<>();
        defaultValue.put(UpdateHelper.KET_UPDATE_ENABLE, true);
        defaultValue.put(UpdateHelper.KET_UPDATE_VERSION, "1.0");
        defaultValue.put(UpdateHelper.KET_UPDATE_URL, "your app url on App Store");

        final FirebaseRemoteConfig firebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        firebaseRemoteConfig.setDefaults(defaultValue);

        /** 如果從RemoteConfig更新數據後, APP要先重開後等5秒得到更新後的數據, 再第二次重開APP才會彈出Dialog */
        firebaseRemoteConfig.fetch(5)                                                             // 每5秒從Firebase獲取數據, 但是在現實應用上, 至少要設置1分鐘 5分鐘以上..等
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            firebaseRemoteConfig.activateFetched();
                        }
                    }
                });
    }
}
