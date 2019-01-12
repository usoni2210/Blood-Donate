package in.twister.blood_donate;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import in.twister.blood_donate.Bean.LoginResponseBean;
import in.twister.blood_donate.Connection.ConnectionConfig;
import in.twister.blood_donate.Connection.ConnectionMethod;

public class SplashActivity extends AppCompatActivity implements Runnable {
    Intent intent;
    SharedPreferences sharedPreferences;
    LoginResponseBean bean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        final Thread thread = new Thread(this);
        sharedPreferences = getSharedPreferences("user_db", Context.MODE_PRIVATE);

        if(!PermissionManagers.checkPermission(this))
            return;

        // User Validating
        if(sharedPreferences.getBoolean("user_isLogged", false)) {
            ConnectionMethod method = ConnectionConfig.getApiClient().create(ConnectionMethod.class);
            Call<LoginResponseBean> call = method.userLogin(
                    sharedPreferences.getString("user_email",null),
                    sharedPreferences.getString("user_password",null)
            );

            call.enqueue(new Callback<LoginResponseBean>() {
                @Override
                public void onResponse(@NonNull Call<LoginResponseBean> call, @NonNull Response<LoginResponseBean> response) {
                    bean = response.body();
                    if (bean != null){
                        if (!bean.getError()) {
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("user_name", bean.getName());
                            editor.putString("user_contact_no", bean.getContact_no());
                            editor.putString("user_dob", bean.getDob());
                            editor.putString("user_gender", bean.getGender());
                            editor.putString("user_bloodgrp", bean.getBloodgrp());
                            editor.putBoolean("user_isdonor", bean.getIsDonor());
                            editor.putBoolean("user_isLogged", true);
                            editor.apply();

                            if(!isMyServiceRunning(UpdateLocationService.class)){
                                startService(new Intent(getApplicationContext(), UpdateLocationService.class));
                            }
                            intent = new Intent(getApplicationContext(), MainActivity.class);
                            thread.start();
                        } else {
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.clear();
                            editor.apply();
                            intent = new Intent(getApplicationContext(), MainActivity.class);
                            thread.start();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Some Error Occur, Try Again", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<LoginResponseBean> call, @NonNull Throwable t) {
                    Toast.makeText(getApplicationContext(), "Check Your Connectivity", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
        } else {
            intent = new Intent(this, MainActivity.class);
            thread.start();
        }
    }

    @Override
    public void run() {
        try{
            Thread.sleep(1500);
            startActivity(intent);
        }catch (Exception e){
            Log.d("Error ","splash screen not working \n" + e);
        }
    }

    public boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        if (manager != null) {
            for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
                if (serviceClass.getName().equals(service.service.getClassName())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 1: {
                if (!(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    PermissionManagers.checkPermission(this);
                }
            }
        }
    }
}
