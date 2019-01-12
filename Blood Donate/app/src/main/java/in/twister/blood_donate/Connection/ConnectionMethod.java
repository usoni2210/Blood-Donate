package in.twister.blood_donate.Connection;

import com.google.android.gms.maps.model.LatLng;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import in.twister.blood_donate.Bean.ArticleBean;
import in.twister.blood_donate.Bean.BankListBean;
import in.twister.blood_donate.Bean.DonorListBean;
import in.twister.blood_donate.Bean.ErrorResponseBean;
import in.twister.blood_donate.Bean.LoginResponseBean;

public interface ConnectionMethod {

    @POST("getArticle.php")
    Call<ArticleBean> getArticle();

    @FormUrlEncoded
    @POST("getDonor.php")
    Call<DonorListBean> getDonor(
            @Field("user_name") String name,
            @Field("email") String email,
            @Field("contact_no") String contact,
            @Field("bloodgrp") String bloodgrp,
            @Field("latlag") LatLng latlag
    );

    @FormUrlEncoded
    @POST("getBank.php")
    Call<BankListBean> getBank(@Field("latlag") LatLng latLng);

    @FormUrlEncoded
    @POST("validateEmail.php")
    Call<ErrorResponseBean> validateEmail(@Field("email") String email);

    @FormUrlEncoded
    @POST("userLogin.php")
    Call<LoginResponseBean> userLogin(
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("userRegister.php")
    Call<ErrorResponseBean> userRegister(
            @Field("name") String name,
            @Field("email") String email,
            @Field("password") String password,
            @Field("contact_no") String contact_no,
            @Field("dob") String dob,
            @Field("gender") String gender,
            @Field("bloodgrp") String bloodgrp
    );

    @FormUrlEncoded
    @POST("updateIsDonor.php")
    Call<ErrorResponseBean> updateIsDonor(
            @Field("email") String email,
            @Field("password") String password,
            @Field("isdonor") Boolean isdonor
    );

    @FormUrlEncoded
    @POST("updateProfile.php")
    Call<ErrorResponseBean> updateProfile(
            @Field("email") String email,
            @Field("password") String password,
            @Field("name") String name,
            @Field("contact_no") String contact_no,
            @Field("dob") String dob,
            @Field("gender") String gender,
            @Field("bloodgrp") String bloodgrp
    );

    @FormUrlEncoded
    @POST("updateLocation.php")
    Call<ErrorResponseBean> updateLocation(
            @Field("email") String email,
            @Field("password") String password,
            @Field("latlag") LatLng loc
    );

    @FormUrlEncoded
    @POST("changePassword.php")
    Call<ErrorResponseBean> changePassword(
            @Field("email") String email,
            @Field("password") String password,
            @Field("newpwd") String new_password
    );

    @FormUrlEncoded
    @POST("feedback.php")
    Call<ErrorResponseBean> feedback(
            @Field("email") String email,
            @Field("topic") String topic,
            @Field("msg") String msg
    );

    @FormUrlEncoded
    @POST("sendOTP.php")
    Call<ErrorResponseBean> sendOTP(
      @Field("email") String email
    );

    @FormUrlEncoded
    @POST("resetPassword.php")
    Call<ErrorResponseBean> resetPassword(
            @Field("email") String email,
            @Field("otp") String otp,
            @Field("password") String password
    );
}
