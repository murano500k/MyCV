package com.stc.cv.model;

import com.google.firebase.database.PropertyName;

/**
 * Created by artem on 4/1/17.
 */

public class Cert {
    @PropertyName("cert_img_url")
    public String certImgUrl;

    @PropertyName("cert_url")
    public String certUrl;

    @PropertyName("description")
    public String description;

    @PropertyName("issued_by")
    public String issuedBy;

    @PropertyName("title")
    public String title;

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getCertImgUrl() {
        return certImgUrl;
    }

    public String getIssuedBy() {
        return issuedBy;
    }

    public String getCertUrl() {
        return certUrl;
    }

    @Override
    public String toString() {
        return "Cert{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", certImgUrl='" + certImgUrl + '\'' +
                ", issuedBy='" + issuedBy + '\'' +
                ", certUrl='" + certUrl + '\'' +
                '}';
    }
}
