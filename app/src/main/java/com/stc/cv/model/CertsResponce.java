package com.stc.cv.model;

import com.google.firebase.database.PropertyName;

import java.util.List;

/**
 * Created by artem on 3/24/17.
 */
public class CertsResponce {
	@PropertyName("certifications")
	public List<Cert> certifications;

}
