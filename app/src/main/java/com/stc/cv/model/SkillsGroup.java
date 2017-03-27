package com.stc.cv.model;

import com.google.firebase.database.PropertyName;

/**
 * Created by artem on 3/24/17.
 */

public class SkillsGroup {
	@PropertyName("title")
	public String title;

	@PropertyName("description")
	public String description;

	@PropertyName("l0")
	public String level1;

	@PropertyName("l1")
	public String level2;

	@PropertyName("l2")
	public String level3;

	@Override
	public String toString() {
		return "SkillsGroup{" +
				"title='" + title + '\'' +
				", description='" + description + '\'' +
				", level1='" + level1 + '\'' +
				", level2='" + level2 + '\'' +
				", level3='" + level3 + '\'' +
				'}';
	}
}
