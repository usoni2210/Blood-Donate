package in.twister.blood_donate;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

import java.util.ArrayList;
import java.util.List;


class PermissionManagers {

    private static String[] arrayPermission = new String[]{
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.CALL_PHONE
    };

    static boolean checkPermission(Activity activity){
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p:arrayPermission) {
            if (ActivityCompat.checkSelfPermission(activity,p) != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(activity, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),1 );
            return false;
        }
        return true;
    }
}
