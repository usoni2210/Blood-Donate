package in.twister.blood_donate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import in.twister.blood_donate.Bean.ErrorResponseBean;
import in.twister.blood_donate.Bean.LoginResponseBean;
import in.twister.blood_donate.Connection.ConnectionConfig;
import in.twister.blood_donate.Connection.ConnectionMethod;

public class LogRegActivity extends AppCompatActivity {
    EditText user_email, user_pwd;
    TextView user_forget_pwd;
    Button login, register;

    LoginResponseBean bean;
    ErrorResponseBean errorBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_reg);

        // Get Layout Elements
        user_email = findViewById(R.id.user_email_id);
        user_pwd = findViewById(R.id.user_pwd);
        user_forget_pwd = findViewById(R.id.user_forget_pwd);
        login = findViewById(R.id.btn_login);
        register = findViewById(R.id.btn_register);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = user_email.getText().toString().trim();
                final String password = user_pwd.getText().toString().trim();
                if(email.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Enter the Email", Toast.LENGTH_SHORT).show();
                } else if (password.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Enter the Password", Toast.LENGTH_SHORT).show();
                } else {
                    ConnectionMethod method = ConnectionConfig.getApiClient().create(ConnectionMethod.class);
                    Call<LoginResponseBean> call = method.userLogin(email, password);

                    call.enqueue(new Callback<LoginResponseBean>() {
                        @Override
                        public void onResponse(@NonNull Call<LoginResponseBean> call, @NonNull Response<LoginResponseBean> response) {
                            bean = response.body();
                            if (bean != null){
                                if (!bean.getError()) {
                                    SharedPreferences sharedPreferences = getSharedPreferences("user_db", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("user_name", bean.getName());
                                    editor.putString("user_email", bean.getEmail_id());
                                    editor.putString("user_password", password);
                                    editor.putString("user_contact_no", bean.getContact_no());
                                    editor.putString("user_dob", bean.getDob());
                                    editor.putString("user_gender", bean.getGender());
                                    editor.putString("user_bloodgrp", bean.getBloodgrp());
                                    editor.putBoolean("user_isdonor", bean.getIsDonor());
                                    editor.putBoolean("user_isLogged", true);
                                    editor.apply();

                                    Toast.makeText(getApplicationContext(), bean.getMessage(), Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                            Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                            Intent.FLAG_ACTIVITY_NEW_TASK
                                    );
                                    startService(new Intent(getApplication(), UpdateLocationService.class));
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(getApplicationContext(), bean.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "Some Error Occur, Try Again", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<LoginResponseBean> call, @NonNull Throwable t) {
                            Toast.makeText(getApplicationContext(), "Check Your Connectivity", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = user_email.getText().toString().trim();
                final String password = user_pwd.getText().toString().trim();
                if(!Validation.isValidEmail(email)){
                    Toast.makeText(getApplicationContext(), "Enter the Email", Toast.LENGTH_SHORT).show();
                } else if (!Validation.isValidPassword(password)){
                    Toast.makeText(getApplicationContext(), "Password Must be Contain Alphabets, Numbers and Special Symbols", Toast.LENGTH_SHORT).show();
                } else {
                    ConnectionMethod method = ConnectionConfig.getApiClient().create(ConnectionMethod.class);
                    Call<ErrorResponseBean> call = method.validateEmail(email);
                    call.enqueue(new Callback<ErrorResponseBean>() {
                        @Override
                        public void onResponse(@NonNull Call<ErrorResponseBean> call, @NonNull Response<ErrorResponseBean> response) {
                            errorBean = response.body();
                            if (errorBean != null){
                                if (!errorBean.getError()) {
                                    Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                                    intent.putExtra("email", email);
                                    intent.putExtra("password", password);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(getApplicationContext(), errorBean.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "Some Error Occur, Try Again", Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(@NonNull Call<ErrorResponseBean> call, @NonNull Throwable t) {
                            Toast.makeText(getApplicationContext(), "Check Your Connectivity", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
        user_forget_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogRegActivity.this,ForgetPwdActivity.class));
            }
        });
    }
}
