package in.twister.blood_donate;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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

public class FeedbackActivity extends AppCompatActivity {
    EditText e_topic,e_msg;
    Button send;
    SharedPreferences sharedPreferences;
    String user_email;
    ErrorResponseBean bean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Back Button Code
        ActionBar bar = getSupportActionBar();
        if(bar !=null) {
            bar.setHomeButtonEnabled(true);
            bar.setDisplayHomeAsUpEnabled(true);
        }

        //Set Title
        setTitle(R.string.txt_feedback);

        e_topic=findViewById(R.id.feedback_topic);
        e_msg=findViewById(R.id.feedback_message);
        send=findViewById(R.id.btn_send_feedback);

        sharedPreferences=getSharedPreferences("user_db", Context.MODE_PRIVATE);
        user_email=sharedPreferences.getString("user_email","Guest");

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String topic = e_topic.getText().toString().trim();
                String msg = e_msg.getText().toString().trim();

                if(topic.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Enter the Topic", Toast.LENGTH_SHORT).show();
                } else if (msg.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Enter the Message", Toast.LENGTH_SHORT).show();
                } else {
                    ConnectionMethod method = ConnectionConfig.getApiClient().create(ConnectionMethod.class);
                    Call<ErrorResponseBean> call = method.feedback(user_email, topic, msg);

                    call.enqueue(new Callback<ErrorResponseBean>() {
                        @Override
                        public void onResponse(@NonNull Call<ErrorResponseBean> call, @NonNull Response<ErrorResponseBean> response) {
                            bean = response.body();
                            if (bean != null) {
                                if (!bean.getError()) {
                                    Toast.makeText(getApplicationContext(), bean.getMessage(), Toast.LENGTH_SHORT).show();
                                    finish();
                                } else
                                    Toast.makeText(getApplicationContext(), bean.getMessage(), Toast.LENGTH_SHORT).show();
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
