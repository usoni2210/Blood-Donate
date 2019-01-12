package in.twister.blood_donate.Bean;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import in.twister.blood_donate.R;

public class DonorListBean {
    @SerializedName("error") private Boolean error;
    @SerializedName("message") private String message;
    @SerializedName("donor") private ArrayList<DonorBean> bean;

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<DonorBean> getBean() {
        return bean;
    }

    public void setBean(ArrayList<DonorBean> bean) {
        this.bean = bean;
    }
}