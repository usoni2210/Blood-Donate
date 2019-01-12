package in.twister.blood_donate;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import in.twister.blood_donate.Bean.ErrorResponseBean;
import in.twister.blood_donate.Connection.ConnectionConfig;
import in.twister.blood_donate.Connection.ConnectionMethod;

public class ForgetPwdActivity extends AppCompatActivity {
    private EditText verification_id, verification_code, new_pwd, new_pwd_again;
    private Button send_otp, change_pwd;
    private View block_chage_pwd;
    ErrorResponseBean bean;
    String ver_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pwd);

        //Back Button Code
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Set Title
        setTitle(R.string.title_forget_password);

        //Get EditText and Button
        verification_id = findViewById(R.id.verification_id);
        verification_code = findViewById(R.id.verification_code);
        new_pwd = findViewById(R.id.new_pwd);
        new_pwd_again = findViewById(R.id.new_pwd_again);
        send_otp = findViewById(R.id.btn_send_otp);
        change_pwd = findViewById(R.id.btn_change_pwd);
        block_chage_pwd = findViewById(R.id.block_change_pwd);

        block_chage_pwd.setVisibility(View.GONE);

        send_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ver_id = verification_id.getText().toString().trim();
                if(!Validation.isValidEmail(ver_id)) {
                    Toast.makeText(getApplicationContext(),"Enter Valid Email ID", Toast.LENGTH_SHORT).show();
                } else {
                    ConnectionMethod method = ConnectionConfig.getApiClient().create(ConnectionMethod.class);
                    Call<ErrorResponseBean> call = method.sendOTP(ver_id);

                    call.enqueue(new Callback<ErrorResponseBean>() {
                        @Override
                        public void onResponse(@NonNull Call<ErrorResponseBean> call, @NonNull Response<ErrorResponseBean> response) {
                            bean = response.body();
                            if(bean != null){
                                if(!bean.getError()){
                                    Toast.makeText(getApplicationContext(), bean.getMessage(), Toast.LENGTH_SHORT).show();
                                    block_chage_pwd.setVisibility(View.VISIBLE);

                                } else {
                                    Toast.makeText(getApplicationContext(), bean.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getApplicationContext(),"Some Error Occur, Try Again", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<ErrorResponseBean> call, @NonNull Throwable t) {
                            Toast.makeText(getApplicationContext(),"Check Your Connectivity", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        change_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code = verification_code.getText().toString().trim();
                String pwd = new_pwd.getText().toString().trim();
                String repwd = new_pwd_again.getText().toString().trim();

                if(code.isEmpty() || code.length()!=6){
                    Toast.makeText(getApplicationContext(), "Enter the Valid Code", Toast.LENGTH_SHORT).show();
                } else if(!Validation.isValidPassword(pwd)){
                    Toast.makeText(getApplicationContext(), "Password Must be Contain Alphabets, Numbers and Special Symbols", Toast.LENGTH_SHORT).show();
                } else if(!repwd.equals(pwd)){
                    Toast.makeText(getApplicationContext(), "Passwords are not Same", Toast.LENGTH_SHORT).show();
                } else {
                    ConnectionMethod method = ConnectionConfig.getApiClient().create(ConnectionMethod.class);
                    Call<ErrorResponseBean> call = method.resetPassword(ver_id,code,pwd);

                    call.enqueue(new Callback<ErrorResponseBean>() {
                        @Override
                        public void onResponse(@NonNull Call<ErrorResponseBean> call, @NonNull Response<ErrorResponseBean> response) {
                            bean = response.body();
                            if(bean != null){
                                Toast.makeText(getApplicationContext(), bean.getMessage(), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(),"Some Error Occur, Try Again", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<ErrorResponseBean> call, @NonNull Throwable t) {
                            Toast.makeText(getApplicationContext(),"Check Your Connectivity", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;

            default:
            return super.onOptionsItemSelected(item);
        }
    }
}
