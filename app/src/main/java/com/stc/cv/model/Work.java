package com.stc.cv.model;

import com.google.firebase.database.PropertyName;

/**
 * Created by artem on 3/24/17.
 */

public class Work {
	@PropertyName("position")
	public String position;

	@PropertyName("company")
	public String company;

	@PropertyName("period")
	public String period;

	@PropertyName("project")
	public String project;

	@PropertyName("tasks")
	public String tasks;

	@PropertyName("tools")
	public String tools;

	@Override
	public String toString() {
		return "Work{" +
				"position='" + position + '\'' +
				", company='" + company + '\'' +
				", period='" + period + '\'' +
				", project='" + project + '\'' +
				", tasks='" + tasks + '\'' +
				", tools='" + tools + '\'' +
				'}';
	}
}
