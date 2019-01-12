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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import in.twister.blood_donate.Bean.ErrorResponseBean;
import in.twister.blood_donate.Connection.ConnectionConfig;
import in.twister.blood_donate.Connection.ConnectionMethod;

public class ProfileActivity extends AppCompatActivity {
    TextView user_name, user_email_id, user_contact_no, user_dob, user_bloodgrp, user_gender;
    ImageView user_image, user_isdonor;

    TextView change_pwd,forget_pwd;
    EditText current_pwd, new_pwd, new_pwd_again;
    Button btn_change_pwd;
    LinearLayout block_change_pwd;

    ImageView edit_profile, btn_back;
    TextView logout;

    Boolean change_pwd_flag=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Get Layout Element
        user_image = findViewById(R.id.user_image);
        user_name = findViewById(R.id.user_name);
        user_email_id = findViewById(R.id.user_email_id);
        user_contact_no = findViewById(R.id.user_contact_no);
        user_dob = findViewById(R.id.user_dob);
        user_bloodgrp = findViewById(R.id.user_bloodgrp);
        user_gender = findViewById(R.id.user_gender);
        user_isdonor = findViewById(R.id.user_isdonor);

        change_pwd = findViewById(R.id.txt_change_pwd);
        block_change_pwd = findViewById(R.id.block_change_pwd);
        current_pwd = findViewById(R.id.current_pwd);
        new_pwd = findViewById(R.id.new_pwd);
        new_pwd_again = findViewById(R.id.new_pwd_again);
        btn_change_pwd = findViewById(R.id.btn_change_pwd);
        forget_pwd = findViewById(R.id.user_forget_pwd);

        edit_profile = findViewById(R.id.edit_profile);
        btn_back = findViewById(R.id.profile_back);
        logout = findViewById(R.id.user_logout);

        // Put user data in Activity
        final SharedPreferences sharedPreferences = getSharedPreferences("user_db", Context.MODE_PRIVATE);

        user_name.setText(sharedPreferences.getString("user_name",null));
        user_email_id.setText(sharedPreferences.getString("user_email",null));
        user_contact_no.setText(sharedPreferences.getString("user_contact_no",null));
        user_dob.setText(sharedPreferences.getString("user_dob",null));
        user_bloodgrp.setText(sharedPreferences.getString("user_bloodgrp",null));
        user_gender.setText(sharedPreferences.getString("user_gender",null));

        if(getResources().getStringArray(R.array.gender_option)[1].equals(sharedPreferences.getString("user_gender", null)))
            user_image.setImageResource(R.drawable.img_guest_female);
        else
            user_image.setImageResource(R.drawable.img_guest_male);

        if(sharedPreferences.getBoolean("user_isdonor",false))
            user_isdonor.setImageResource(R.drawable.isdonor_true);
        else
            user_isdonor.setImageResource(R.drawable.isdonor_false);

        // Change Password
        btn_change_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String oldpwd, newpwd, repwd;
                oldpwd = current_pwd.getText().toString().trim();
                newpwd = new_pwd.getText().toString().trim();
                repwd = new_pwd_again.getText().toString().trim();

                if(oldpwd.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Enter the Old Password", Toast.LENGTH_SHORT).show();
                } else if (!Validation.isValidPassword(newpwd)) {
                    Toast.makeText(getApplicationContext(), "Password Must be Contain Alphabets, Numbers and Special Symbols", Toast.LENGTH_SHORT).show();
                } else if (!newpwd.equals(repwd)) {
                    Toast.makeText(getApplicationContext(), "Passwords are not Same", Toast.LENGTH_SHORT).show();
                } else {
                    ConnectionMethod method = ConnectionConfig.getApiClient().create(ConnectionMethod.class);
                    Call<ErrorResponseBean> call = method.changePassword(
                            sharedPreferences.getString("user_email",null),
                            oldpwd,
                            newpwd
                    );

                    call.enqueue(new Callback<ErrorResponseBean>() {
                        @Override
                        public void onResponse(@NonNull Call<ErrorResponseBean> call, @NonNull Response<ErrorResponseBean> response) {
                            ErrorResponseBean bean = response.body();
                            if(bean != null){
                                if(!bean.getError()){
                                    Toast.makeText(getApplicationContext(), bean.getMessage(), Toast.LENGTH_SHORT).show();
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("user_password", newpwd);
                                    editor.apply();
                                } else {
                                    Toast.makeText(getApplicationContext(), bean.getMessage(), Toast.LENGTH_SHORT).show();
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

        // Listener
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                finish();
            }
        });
        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileActivity.this, EditProfileActivity.class));
            }
        });

        change_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(change_pwd_flag){
                    block_change_pwd.setVisibility(View.GONE);
                    change_pwd_flag = false;
                }
                else{
                    block_change_pwd.setVisibility(View.VISIBLE);
                    change_pwd_flag = true;
                }
            }
        });

        forget_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this,ForgetPwdActivity.class));
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("user_db", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_CLEAR_TASK |
                        Intent.FLAG_ACTIVITY_NEW_TASK
                );
                startActivity(intent);
                stopService(new Intent(getApplicationContext(), UpdateLocationService.class));
                finish();
            }
        });
    }
}