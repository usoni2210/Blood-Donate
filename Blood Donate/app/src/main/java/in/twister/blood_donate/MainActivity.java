package in.twister.blood_donate;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import in.twister.blood_donate.fragment.AboutBloodDonateFragment;
import in.twister.blood_donate.fragment.BloodBankFragment;
import in.twister.blood_donate.fragment.DetailsFragment;
import in.twister.blood_donate.fragment.SettingFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    SharedPreferences sharedPreferences;
    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle(R.string.app_name);
        sharedPreferences = getSharedPreferences("user_db", Context.MODE_PRIVATE);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // Navigation Drawer
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //Navigation Header User Profile Display
        View headerView =  navigationView.getHeaderView(0);
        View profiler = headerView.findViewById(R.id.user_detail);
        ImageView user_image = headerView.findViewById(R.id.user_image);
        TextView user_name = headerView.findViewById(R.id.user_name);

        if(sharedPreferences.getBoolean("user_isLogged",false)){
            if(getResources().getStringArray(R.array.gender_option)[1].equals(sharedPreferences.getString("user_gender", null)))
                user_image.setImageResource(R.drawable.img_guest_female);
            else
                user_image.setImageResource(R.drawable.img_guest_male);

            user_name.setText(sharedPreferences.getString("user_name", null));
            profiler.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                }
            });
        } else {
            user_image.setImageResource(R.drawable.img_guest_male);
            user_name.setText(R.string.txt_guest);
            profiler.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(), LogRegActivity.class));
                }
            });
        }

        if(!locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) &&
                sharedPreferences.getBoolean("user_isLogged",false)){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(false);
            builder.setTitle(R.string.title_require_gps);
            builder.setMessage(R.string.msg_enable_gps);
            builder.setInverseBackgroundForced(true);
            builder.setPositiveButton(R.string.option_enable, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                }
            });
            builder.setNegativeButton(R.string.option_ignore, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    finish();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }

        //Display Fragment of Importance of Blood Donate
        getSupportFragmentManager().beginTransaction().replace(R.id.mainframe, new AboutBloodDonateFragment(),"mainFragment").commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_blood_donor) {
            setTitle(R.string.txt_find_blood_donor);
            getSupportFragmentManager().beginTransaction().replace(R.id.mainframe, new DetailsFragment()).commit();
        }
        else if (id == R.id.nav_blood_bank) {
            setTitle(R.string.txt_find_blood_bank);
            getSupportFragmentManager().beginTransaction().replace(R.id.mainframe, new BloodBankFragment()).commit();
        }
        else if (id == R.id.nav_setting) {
            setTitle(R.string.txt_setting);
            getSupportFragmentManager().beginTransaction().replace(R.id.mainframe, new SettingFragment()).commit();
        }
        else if (id == R.id.nav_share) {
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT,"Blood Donate");
            sharingIntent.putExtra(Intent.EXTRA_TEXT,"Google Play Share Link"); //ToDo
            startActivity(Intent.createChooser(sharingIntent,"Share via"));
        }
        else if (id == R.id.nav_feedback) {
            startActivity(new Intent(MainActivity.this,FeedbackActivity.class));
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
