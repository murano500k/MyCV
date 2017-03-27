package com.stc.cv.model;

import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.PropertyName;

/**
 * Created by artem on 3/24/17.
 */
@IgnoreExtraProperties
public class EducationResponce {
	@PropertyName("education")
	public Education education;

}
