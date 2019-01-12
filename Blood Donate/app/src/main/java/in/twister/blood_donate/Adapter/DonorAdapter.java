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
import java.util.List;

import in.twister.blood_donate.Bean.DonorBean;
import in.twister.blood_donate.R;

public class DonorAdapter extends RecyclerView.Adapter<DonorAdapter.ViewHolder>{
    private ArrayList<DonorBean> donor;
    private FragmentActivity activity;

    public DonorAdapter(FragmentActivity activity, ArrayList<DonorBean> donor) {
        this.donor = donor;
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_donor,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.name.setText(donor.get(position).getName());
        holder.dob.setText(donor.get(position).getDob());
        holder.image.setImageResource(donor.get(position).getImage());
        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + donor.get(holder.getAdapterPosition()).getContact_no()));
                if(ActivityCompat.checkSelfPermission(view.getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(activity.getBaseContext(), R.string.txt_need_permission_to_call, Toast.LENGTH_SHORT).show();
                    ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CALL_PHONE},1);
                }
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return donor.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, dob;
        ImageView image, call;
        ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.donor_name);
            dob = itemView.findViewById(R.id.donor_dob);
            image = itemView.findViewById(R.id.donor_image);
            call = itemView.findViewById(R.id.donor_call);
        }
    }
}