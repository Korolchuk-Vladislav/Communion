package com.dreanei.communion;

import android.app.Application;
import android.widget.Toast;

import com.dreanei.communion.async.UpdateService;
import com.dreanei.communion.database.Database;
import com.dreanei.communion.models.User;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKAccessTokenTracker;
import com.vk.sdk.VKSdk;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Александр on 08.05.2017.
 */

public class CommunionApp extends Application implements Callback<User> {

    public static final String TAG = "communion_app";
    public static User user;
    public static Database database;
    public static int uid;
    private VKAccessTokenTracker vkAccessTokenTracker;
    private UpdateService updateService;
    {
        vkAccessTokenTracker = new VKAccessTokenTracker() {
            @Override
            public void onVKAccessTokenChanged(VKAccessToken oldToken, VKAccessToken newToken) {
                if (newToken == null) {
                    // VKAccessToken is invalid
                    Toast.makeText(CommunionApp.this, "Invalid VKAccessTocken", Toast.LENGTH_SHORT).show();
                }
            }
        };
    }

    @Override
    public void onCreate() {
        super.onCreate();
        vkAccessTokenTracker.startTracking();
        VKSdk.initialize(this);
        database = Database.getInstance(this);
        database.getUser(3,this);
        uid = 3;
        updateService = UpdateService.getInstance();
        new Thread(updateService).start();
    }

    @Override
    public void onResponse(Call<User> call, Response<User> response) {
        user = response.body();
        uid = user.getUserid();
    }

    @Override
    public void onFailure(Call<User> call, Throwable t) {
        user = new User();
        user.setUserid(3);
    }
}
