package in.twister.blood_donate;

import android.support.annotation.NonNull;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Validation {
    @NonNull
    static Boolean isValidEmail(String email){
        return email.matches("[(a-zA-Z0-9_.)]+@[(a-z-A-z)]+\\.[(a-zA-z)]{2,3}") && !email.isEmpty();
    }

    @NonNull
    public static Boolean isValidContactNo(String contact){
        return !contact.isEmpty() && contact.length()==10;
    }

    @NonNull
    static Boolean isValidDOB(String dob){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        Date currDate = new Date();
        Date date1;
        try {
            date1 = dateFormat.parse(dob);
        } catch (ParseException e) {
            return false;
        }
        long diff = currDate.getTime() - date1.getTime();
        long diffDays = diff / (1000 * 60 * 60 * 24);
        long diffYear = diffDays / 365;

        return !dob.isEmpty() && diffYear>=18;
    }

    @NonNull
    static Boolean isValidPassword(String password){
        return password.matches("(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=])(?=\\S+$).{6,}$") && !password.isEmpty();
    }
}
