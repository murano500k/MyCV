package com.stc.cv.model;

import com.google.firebase.database.PropertyName;

/**
 * Created by artem on 4/1/17.
 */

public class Training {
    @PropertyName("img_url")
    public String imgUrl;

    @PropertyName("url")
    public String url;

    @PropertyName("description")
    public String description;

    @PropertyName("title")
    public String title;

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

}
