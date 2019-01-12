package in.twister.blood_donate.Bean;


import com.google.gson.annotations.SerializedName;

public class BankBean {
    @SerializedName("pid") private String PlaceID;
    @SerializedName("name") private String Name;
    @SerializedName("address") private String Address;
    @SerializedName("contact")private String  Contact_No;

    public String getPlaceID() {
        return PlaceID;
    }

    public void setPlaceID(String placeID) {
        PlaceID = placeID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getContactNo() {
        return Contact_No;
    }

    public void setContactNo(String contact_No) {
        Contact_No = contact_No;
    }
}
