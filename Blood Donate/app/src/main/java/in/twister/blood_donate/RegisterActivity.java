package in.twister.blood_donate;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.GridHolder;
import com.orhanobut.dialogplus.ListHolder;
import com.orhanobut.dialogplus.OnItemClickListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import in.twister.blood_donate.Adapter.BloodTypeAdapter;
import in.twister.blood_donate.Adapter.GenderAdapter;
import in.twister.blood_donate.Bean.ErrorResponseBean;
import in.twister.blood_donate.Connection.ConnectionConfig;
import in.twister.blood_donate.Connection.ConnectionMethod;

public class RegisterActivity extends AppCompatActivity {
    EditText user_name, user_contact_no, user_dob;
    TextView user_gender, user_bloodgrp;
    Button register;

    DialogPlus genderDialog, bloodgrpDialog;
    View genderHeaderView, bloodgrpHeaderView;
    String[] gender_option, bloodgrp_option;
    int[] gender_img;

    Calendar myCalendar;
    ErrorResponseBean bean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        user_name = findViewById(R.id.user_name);
        user_contact_no = findViewById(R.id.user_contact_no);
        user_dob = findViewById(R.id.user_dob);
        user_gender = findViewById(R.id.user_gender);
        user_bloodgrp = findViewById(R.id.user_bloodgrp);
        register = findViewById(R.id.btn_register);

        myCalendar = Calendar.getInstance();
        gender_img = new int[]{
                R.drawable.img_guest_male,
                R.drawable.img_guest_female
        };
        gender_option = getResources().getStringArray(R.array.gender_option);
        bloodgrp_option = getResources().getStringArray(R.array.bloodgrp_option);

        // Date Picker Dialog Box
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                user_dob.setText(dateFormat.format(myCalendar.getTime()));
            }
        };
        user_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(RegisterActivity.this,
                        date,
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)
                ).show();
            }
        });

        // Gender Picker Dialog Box
        genderDialog = DialogPlus.newDialog(this)
                .setAdapter(new GenderAdapter(this, R.layout.activity_register, gender_option, gender_img))
                .setHeader(R.layout.layout_custom_dialog_header)
                .setContentHolder(new GridHolder(2))
                .setMargin(20,0,20,0)
                .setPadding(0,10,0,20)
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
                        user_gender.setText(gender_option[position]);
                        dialog.dismiss();
                    }
                })
                .setGravity(Gravity.CENTER)
                .setCancelable(true)
                .setExpanded(false)
                .create();
        genderHeaderView = genderDialog.getHeaderView();
        TextView genderTitle = genderHeaderView.findViewById(R.id.title);
        genderTitle.setText(R.string.txt_gender);

        user_gender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                genderDialog.show();
            }
        });

        // Blood Group Picker Dialog Box
        bloodgrpDialog = DialogPlus.newDialog(this)
                .setAdapter(new BloodTypeAdapter(this, R.layout.activity_register, bloodgrp_option))
                .setHeader(R.layout.layout_custom_dialog_header)
                .setContentHolder(new ListHolder())
                .setMargin(20,0,20,0)
                .setPadding(0,10,0,10)
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
                        user_bloodgrp.setText(bloodgrp_option[position]);
                        dialog.dismiss();
                    }
                })
                .setGravity(Gravity.CENTER)
                .setCancelable(true)
                .setExpanded(false)
                .create();
        bloodgrpHeaderView = bloodgrpDialog.getHeaderView();
        TextView bloodgrpTitle = bloodgrpHeaderView.findViewById(R.id.title);
        bloodgrpTitle.setText(R.string.txt_bloodgrp);

        user_bloodgrp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bloodgrpDialog.show();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                String name = user_name.getText().toString().trim();
                String email = intent.getStringExtra("email");
                String password = intent.getStringExtra("password");
                String contact_no = user_contact_no.getText().toString().trim();
                String dob = user_dob.getText().toString();
                String gender = user_gender.getText().toString();
                String bloodgrp = user_bloodgrp.getText().toString();

                if (name.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Enter the name", Toast.LENGTH_SHORT).show();
                } else if (!Validation.isValidContactNo(contact_no)){
                    Toast.makeText(getApplicationContext(), "Enter Correct Contact Number", Toast.LENGTH_SHORT).show();
                } else if (!Validation.isValidDOB(dob)){
                    Toast.makeText(getApplicationContext(), "Select Correct Date of Birth\n(Age must be 18 Year Old)", Toast.LENGTH_SHORT).show();
                } else if (gender.equals(getString(R.string.txt_select_one))){
                    Toast.makeText(getApplicationContext(), "Select Gender", Toast.LENGTH_SHORT).show();
                } else if (bloodgrp.equals(getString(R.string.txt_select_one))){
                    Toast.makeText(getApplicationContext(), "Select Blood Group", Toast.LENGTH_SHORT).show();
                } else {
                    ConnectionMethod method = ConnectionConfig.getApiClient().create(ConnectionMethod.class);
                    Call<ErrorResponseBean> call = method.userRegister(name, email, password, contact_no, dob, gender, bloodgrp);

                    call.enqueue(new Callback<ErrorResponseBean>() {
                        @Override
                        public void onResponse(@NonNull Call<ErrorResponseBean> call, @NonNull Response<ErrorResponseBean> response) {
                            bean = response.body();
                            if(bean != null){
                                if(!bean.getError()){
                                    Toast.makeText(getApplicationContext(), bean.getMessage(), Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(RegisterActivity.this, LogRegActivity.class));
                                    finish();
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
    }
}