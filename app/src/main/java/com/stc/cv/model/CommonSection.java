package com.stc.cv.model;

import com.google.firebase.database.PropertyName;

public class CommonSection {

    @PropertyName("title")
    public String title;

    @PropertyName("description")
    public String description;

	@PropertyName("l0")
	public String l0;
	@PropertyName("l1")
	public String l1;
	@PropertyName("l2")
	public String l2;

    public CommonSection() {
        //do nothing
    }

	@Override
	public String toString() {
		return "CommonSection{" +
				"title='" + title + '\'' +
				", description='" + description + '\'' +
				", level0='" + l0 + '\'' +
				", level1='" + l1 + '\'' +
				", level2='" + l2 + '\'' +
				'}';
	}
}
