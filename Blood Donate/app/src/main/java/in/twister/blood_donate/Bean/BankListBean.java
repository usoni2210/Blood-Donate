package in.twister.blood_donate.Bean;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class BankListBean {
    @SerializedName("error") private Boolean error;
    @SerializedName("message") private String message;
    @SerializedName("bank") private ArrayList<BankBean> bankBean;

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

    public ArrayList<BankBean> getBankBean() {
        return bankBean;
    }

    public void setBankBean(ArrayList<BankBean> bankBean) {
        this.bankBean = bankBean;
    }
}
