package com.stc.cv.model;

import com.google.firebase.database.PropertyName;

public class Contacts {

    @PropertyName("name")
    public String name;

    @PropertyName("profession")
    public String profession;

    @PropertyName("phone")
    public String phone;

    @PropertyName("email")
    public String email;

    @PropertyName("github")
    public String gitHub;

    @PropertyName("cv_url")
    public String cv;

    @PropertyName("pic_url")
    public String userPic;

    @PropertyName("cert_url")
    public String certUrl;

    @PropertyName("cert_img_url")
    public String certImgUrl;

    @PropertyName("cv_url_key")
    public String cvKey;

    @PropertyName("name_key")
    public String nameKey;

    @PropertyName("profession_key")
    public String professionKey;

    public Contacts() {
        //do nothing
    }
}
