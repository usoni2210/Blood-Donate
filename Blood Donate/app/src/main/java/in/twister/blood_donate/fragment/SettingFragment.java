package in.twister.blood_donate.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ListHolder;
import com.orhanobut.dialogplus.OnItemClickListener;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import in.twister.blood_donate.Adapter.BloodTypeAdapter;
import in.twister.blood_donate.Bean.ErrorResponseBean;
import in.twister.blood_donate.Connection.ConnectionConfig;
import in.twister.blood_donate.Connection.ConnectionMethod;
import in.twister.blood_donate.LogRegActivity;
import in.twister.blood_donate.MainActivity;
import in.twister.blood_donate.R;

public class SettingFragment extends Fragment {
    SharedPreferences sharedPreferences,langPref;
    Switch setting_isdonor;
    ErrorResponseBean bean;

    //TextView setting_language;
    //DialogPlus lang_dialog;
    //View langHeaderView;
    //String[] lang_option;


    public SettingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        setting_isdonor = view.findViewById(R.id.user_isdonor);
        //setting_language = view.findViewById(R.id.language);

        sharedPreferences = getActivity().getSharedPreferences("user_db", Context.MODE_PRIVATE);
        langPref = getActivity().getSharedPreferences("Config", Context.MODE_PRIVATE);

        /*lang_option = getResources().getStringArray(R.array.language_option);
        lang_dialog = DialogPlus.newDialog(container.getContext())
                .setAdapter(new BloodTypeAdapter(container.getContext(), R.layout.fragment_setting,lang_option))
                .setHeader(R.layout.layout_custom_dialog_header)
                .setContentHolder(new ListHolder())
                .setMargin(20,0,20,0)
                .setPadding(0,10,0,10)
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
                        setting_language.setText(lang_option[position]);
                        if(setting_language.getText().toString().equals(lang_option[0]))
                            setLocale("en");
                        else if (setting_language.getText().toString().equals(lang_option[1]))
                            setLocale("hi");
                        dialog.dismiss();
                    }
                })
                .setGravity(Gravity.CENTER)
                .setCancelable(true)
                .setExpanded(false)
                .create();
        langHeaderView = lang_dialog.getHeaderView();
        TextView bloodgrpTitle = langHeaderView.findViewById(R.id.title);
        bloodgrpTitle.setText(R.string.txt_language);

        if (langPref.getString("lang","en").equals("en"))
            setting_language.setText(getResources().getStringArray(R.array.language_option)[0]);
        else if (langPref.getString("lang","en").equals("hi"))
            setting_language.setText(getResources().getStringArray(R.array.language_option)[1]);*/

        if(sharedPreferences.getBoolean("user_isLogged",false)) {
            setting_isdonor.setChecked(sharedPreferences.getBoolean("user_isdonor", false));
            setting_isdonor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ConnectionMethod method = ConnectionConfig.getApiClient().create(ConnectionMethod.class);
                    Call<ErrorResponseBean> call = method.updateIsDonor(
                            sharedPreferences.getString("user_email", null),
                            sharedPreferences.getString("user_password", null),
                            setting_isdonor.isChecked()
                    );
                    call.enqueue(new Callback<ErrorResponseBean>() {
                        @Override
                        public void onResponse(@NonNull Call<ErrorResponseBean> call, @NonNull Response<ErrorResponseBean> response) {
                            bean = response.body();
                            if (bean != null) {
                                if (!bean.getError()) {
                                    Toast.makeText(getContext(), bean.getMessage(), Toast.LENGTH_SHORT).show();
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putBoolean("user_isdonor", setting_isdonor.isChecked());
                                    editor.apply();
                                } else {
                                    Toast.makeText(getContext(), bean.getMessage(), Toast.LENGTH_SHORT).show();
                                    setting_isdonor.setChecked(!setting_isdonor.isChecked());
                                }
                            } else {
                                Toast.makeText(getContext(), "Some Error Occur, Try Again", Toast.LENGTH_SHORT).show();
                                setting_isdonor.setChecked(!setting_isdonor.isChecked());
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<ErrorResponseBean> call, @NonNull Throwable t) {
                            Toast.makeText(getContext(), "Check Your Connectivity", Toast.LENGTH_SHORT).show();
                            setting_isdonor.setChecked(!setting_isdonor.isChecked());
                        }
                    });
                }
            });
        } else {
            setting_isdonor.setChecked(false);
            setting_isdonor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                            .setTitle(R.string.txt_alert)
                            .setCancelable(true)
                            .setMessage(R.string.txt_not_reg_or_log_want_to)
                            .setPositiveButton(R.string.option_yes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    startActivity(new Intent(getContext(), LogRegActivity.class));
                                }
                            })
                            .setNegativeButton(R.string.option_no, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            })
                            .create();
                    alertDialog.show();
                    setting_isdonor.setChecked(false);
                }
            });
        }

        /*setting_language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lang_dialog.show();
            }
        });*/
        return view;
    }

   /* public void setLocale(String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);

        SharedPreferences.Editor editor = langPref.edit();
        editor.putString("lang", lang);
        editor.apply();

        startActivity(new Intent(getActivity().getApplicationContext(), MainActivity.class));
        getActivity().finish();
    }*/
}
