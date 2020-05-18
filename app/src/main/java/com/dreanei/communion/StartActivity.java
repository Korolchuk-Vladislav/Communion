package com.dreanei.communion;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.dreanei.communion.models.ServerMessage;
import com.dreanei.communion.models.User;
import com.dreanei.communion.server.Server;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dreanei.communion.CommunionApp.TAG;
import static com.dreanei.communion.CommunionApp.uid;
import static com.dreanei.communion.CommunionApp.user;

public class StartActivity extends Activity {

    private Button signin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        initialize();
    }

    private void initialize() {
        signin = (Button) findViewById(R.id.signin_start);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VKSdk.login(StartActivity.this);
            }
        });
        findViewById(R.id.towebsite_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String urlString="https://communion.herokuapp.com";
                Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(urlString));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setPackage("com.android.chrome");
                try {
                    startActivity(intent);
                } catch (ActivityNotFoundException ex) {
                    // Chrome browser presumably not installed so allow user to choose instead
                    intent.setPackage(null);
                    startActivity(intent);
                }
            }
        });
        findViewById(R.id.guest_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user = new User();
                user.setUserid(3);
                user.setFirst_name("Гость");
                startActivity(new Intent(getBaseContext(),MainActivity.class));
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                // Пользователь успешно авторизовался
                Log.d(TAG, "VK success");
                //извлечение данных
                String token = VKSdk.getAccessToken().accessToken;
                VKParameters parameters = VKParameters.from(VKApiConst.ACCESS_TOKEN, token);
                VKRequest request = new VKRequest("account.getProfileInfo", parameters);
                request.executeWithListener(new VKRequest.VKRequestListener()
                {
                    @Override
                    public void onComplete(VKResponse response) {
                        super.onComplete(response);
                        try {
                            JSONObject jsonObject = response.json.getJSONObject("response");
                            final String first_name = jsonObject.getString("first_name");
                            final String last_name = jsonObject.getString("last_name");
                            uid = Integer.valueOf(VKSdk.getAccessToken().userId);
                            Server.getInstance().gotUser(Integer.valueOf(VKSdk.getAccessToken().userId), new Callback<ServerMessage>() {
                                @Override
                                public void onResponse(Call<ServerMessage> call, Response<ServerMessage> response) {
                                    if (response.body().getMessage().equals("1")) {
                                        Log.d(TAG, "onResponse: user exists");
                                        user = new User();
                                        user.setFirst_name(first_name);
                                        user.setLast_name(last_name);
                                        user.setUserid(Integer.valueOf(VKSdk.getAccessToken().userId));
                                        Log.d(TAG, "onResponse: uid="+user.getUserid());
                                        startActivity(new Intent(getBaseContext(),MainActivity.class));
                                    } else {
                                        Toast.makeText(StartActivity.this,
                                                "Зарегистрируйтесь через вэб-интерфейс",
                                                Toast.LENGTH_SHORT).show();
                                        findViewById(R.id.towebsite_start).setVisibility(View.VISIBLE);
                                    }
                                }

                                @Override
                                public void onFailure(Call<ServerMessage> call, Throwable t) {
                                    CommunionApp.user = new User();
                                    user.setUserid(3);
                                    Toast.makeText(getBaseContext(), "Ошибка авторизации", Toast.LENGTH_SHORT).show();
                                    Log.d(TAG, "onFailure: sign in error");
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
            @Override
            public void onError(VKError error) {
                // Произошла ошибка авторизации (например, пользователь запретил авторизацию)
                Toast.makeText(StartActivity.this, "Ошибка авторизации", Toast.LENGTH_SHORT).show();
            }
        })) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
