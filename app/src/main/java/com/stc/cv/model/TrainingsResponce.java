package com.stc.cv.model;

import com.google.firebase.database.PropertyName;

import java.util.List;

/**
 * Created by artem on 3/24/17.
 */
public class TrainingsResponce {
	@PropertyName("trainings")
	public List<Training> trainings;

}
