package in.twister.blood_donate.Bean;

import com.google.gson.annotations.SerializedName;

public class UserBean {
    @SerializedName("name") private String name;
    @SerializedName("email") private String email_id;
    @SerializedName("contact_no") private String contact_no;
    @SerializedName("dob") private String dob;
    @SerializedName("bloodgrp") private String bloodgrp;
    @SerializedName("gender") private String gender;
    @SerializedName("isdonor") private Boolean isDonor;
    private int image;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail_id() {
        return email_id;
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }

    public String getContact_no() {
        return contact_no;
    }

    public void setContact_no(String contact_no) {
        this.contact_no = contact_no;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getBloodgrp() {
        return bloodgrp;
    }

    public void setBloodgrp(String bloodgrp) {
        this.bloodgrp = bloodgrp;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public Boolean getIsDonor() {
        return isDonor;
    }

    public void setIsDonor(Boolean donor) {
        isDonor = donor;
    }
}
