package com.stc.cv.model;

import com.google.firebase.database.PropertyName;

/**
 * Created by artem on 3/24/17.
 */

public class Education {
	@PropertyName("university")
	public String university;

	@PropertyName("period")
	public String period;

	@PropertyName("faculty")
	public String faculty;

	@PropertyName("degree")
	public String degree;
}
