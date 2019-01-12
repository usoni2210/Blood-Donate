package in.twister.blood_donate.Adapter;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import in.twister.blood_donate.Bean.BankBean;
import in.twister.blood_donate.R;

public class BankAdapter extends RecyclerView.Adapter<BankAdapter.ViewHolder>{

    private final ArrayList<BankBean> bank;
    private FragmentActivity activity;

    public BankAdapter(FragmentActivity activity, ArrayList<BankBean> bank) {
        this.bank = bank;
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_bank,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BankAdapter.ViewHolder holder, int position) {
        final BankBean bean = bank.get(position);
        holder.name.setText(bean.getName());
        holder.address.setText(bean.getAddress());

        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bean.getContactNo() != null) {
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:" + bean.getContactNo()));
                    if (ActivityCompat.checkSelfPermission(view.getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(activity.getBaseContext(), R.string.txt_need_permission_to_call, Toast.LENGTH_SHORT).show();
                        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CALL_PHONE}, 1);
                        return;
                    }
                    activity.startActivity(intent);
                } else {
                    Toast.makeText(activity.getBaseContext(), "Contact Number is not Available",Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.gps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + bean.getName() + ", " + bean.getAddress());
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                activity.startActivity(mapIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bank.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, address;
        ImageView call, gps;
        ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.bank_name);
            address = itemView.findViewById(R.id.bank_address);
            call = itemView.findViewById(R.id.bank_call);
            gps = itemView.findViewById(R.id.bank_gps_location);
        }
    }
}