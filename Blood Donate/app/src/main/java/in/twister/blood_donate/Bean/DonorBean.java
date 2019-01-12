package in.twister.blood_donate.Bean;

import com.google.gson.annotations.SerializedName;

import in.twister.blood_donate.R;

public class DonorBean {
    @SerializedName("name") private String name;
    @SerializedName("dob") private String dob;
    @SerializedName("contact_no") private String contact_no;
    @SerializedName("gender") private String gender;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getContact_no() {
        return contact_no;
    }

    public void setContact_no(String contact_no) {
        this.contact_no = contact_no;
    }

    public int getImage() {
        if(gender.equals("Male"))
            return R.drawable.img_guest_male;
        else
            return R.drawable.img_guest_male;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}