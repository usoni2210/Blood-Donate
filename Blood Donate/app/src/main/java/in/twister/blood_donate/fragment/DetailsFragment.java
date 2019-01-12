package in.twister.blood_donate.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ListHolder;
import com.orhanobut.dialogplus.OnItemClickListener;

import javax.xml.validation.Validator;

import in.twister.blood_donate.Adapter.BloodTypeAdapter;
import in.twister.blood_donate.R;
import in.twister.blood_donate.Validation;

public class DetailsFragment extends Fragment {
    TextView receiver_name, receiver_contact_no, receiver_bloodgrp;
    Button find_donor;

    DialogPlus bloodgrpDialog;
    View bloodgrpHeaderView;
    String[] bloodgrp_option;


    public DetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);

        receiver_name = view.findViewById(R.id.receiver_name);
        receiver_contact_no = view.findViewById(R.id.receiver_contact_no);
        receiver_bloodgrp = view.findViewById(R.id.receiver_bloodgrp);

        bloodgrp_option = getResources().getStringArray(R.array.bloodgrp_option);

        final SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user_db", Context.MODE_PRIVATE);
        if(sharedPreferences.getBoolean("user_isLogged", false)){
            receiver_name.setText(sharedPreferences.getString("user_name",""));
            receiver_contact_no.setText(sharedPreferences.getString("user_contact_no",""));
        }

        // Blood Group Picker Dialog Box
        bloodgrpDialog = DialogPlus.newDialog(container.getContext())
                .setAdapter(new BloodTypeAdapter(container.getContext(), R.layout.activity_register, bloodgrp_option))
                .setHeader(R.layout.layout_custom_dialog_header)
                .setContentHolder(new ListHolder())
                .setMargin(20,0,20,0)
                .setPadding(0,10,0,10)
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
                        receiver_bloodgrp.setText(bloodgrp_option[position]);
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

        receiver_bloodgrp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bloodgrpDialog.show();
            }
        });

        find_donor = view.findViewById(R.id.btn_find_donor);
        find_donor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = receiver_name.getText().toString().trim();
                String contact_no = receiver_contact_no.getText().toString().trim();
                String bloodgrp = receiver_bloodgrp.getText().toString();

                if(name.isEmpty()){
                    Toast.makeText(getContext(), "Enter the Name", Toast.LENGTH_SHORT).show();
                } else if (!Validation.isValidContactNo(contact_no)) {
                    Toast.makeText(getContext(), "Enter the Contact Number", Toast.LENGTH_SHORT).show();
                } else if (bloodgrp.equals(getString(R.string.txt_select_one))) {
                    Toast.makeText(getContext(), "Select Blood Group", Toast.LENGTH_SHORT).show();
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString("name", name);
                    bundle.putString("contact_no", contact_no);
                    bundle.putString("bloodgrp", bloodgrp);
                    bundle.putString("email",sharedPreferences.getString("user_email","Guest"));
                    BloodDonorFragment fragment = new BloodDonorFragment();
                    fragment.setArguments(bundle);
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.mainframe, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            }
        });

        return view;
    }
}
