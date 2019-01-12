package in.twister.blood_donate.fragment;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import in.twister.blood_donate.Adapter.BankAdapter;
import in.twister.blood_donate.Bean.BankListBean;
import in.twister.blood_donate.Connection.ConnectionConfig;
import in.twister.blood_donate.Connection.ConnectionMethod;
import in.twister.blood_donate.R;

public class BloodBankFragment extends Fragment {
    BankListBean bankList;

    public BloodBankFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blood_bank, container, false);

        final RecyclerView recyclerView = view.findViewById(R.id.bank_list);
        final ProgressBar progressBar = view.findViewById(R.id.progress);
        final TextView text = view.findViewById(R.id.txt_not_available);

        ConnectionMethod method = ConnectionConfig.getApiClient().create(ConnectionMethod.class);
        Call<BankListBean> call = method.getBank(getLatLag());

        call.enqueue(new Callback<BankListBean>() {
            @Override
            public void onResponse(@NonNull Call<BankListBean> call, @NonNull Response<BankListBean> response) {
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                bankList = response.body();
                if (bankList != null) {
                    if (!bankList.getError()) {
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(new BankAdapter(getActivity(), bankList.getBankBean()));
                        Toast.makeText(getContext(), bankList.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), bankList.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Some Error Occur, Try Again", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<BankListBean> call, @NonNull Throwable t) {
                progressBar.setVisibility(View.GONE);
                text.setVisibility(View.VISIBLE);
                Toast.makeText(getContext(), "Check Your Connectivity", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    public LatLng getLatLag() {
        LatLng latLng;
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (locationManager != null) {
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},1);
            }
            Location locNet = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (locNet != null) {
                latLng = new LatLng(locNet.getLatitude(), locNet.getLongitude());
                return latLng;
            } else {
                Location locPas =  locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
                latLng = new LatLng(locPas.getLatitude(), locPas.getLongitude());
                return latLng;
            }
        } else {
            return null;
        }
    }
}