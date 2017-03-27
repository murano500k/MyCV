package com.stc.cv.model;

import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.PropertyName;

import java.util.List;

/**
 * Created by artem on 3/24/17.
 */
@IgnoreExtraProperties
public class SkillsResponce {
	@PropertyName("skills")
	public List<SkillsGroup> skillGroups;

}
