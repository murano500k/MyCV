package com.stc.cv.ui.common;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.stc.cv.R;
import com.stc.cv.model.SkillsGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by artem on 3/24/17.
 */

public class SkillsAdapter extends RecyclerView.Adapter {
	List<SkillsGroup> list;
	private static final String TAG = "SkillsAdapter";

	public SkillsAdapter() {
		this.list = new ArrayList<>();
	}
	public void addItem(SkillsGroup skillsGroup){
		Log.d(TAG, "addItem: "+skillsGroup);
		list.add(skillsGroup);
		notifyDataSetChanged();
	}
	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.skills_layout, parent, false);
		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
		ViewHolder vh = (ViewHolder) holder;
		SkillsGroup skillsGroup = list.get(position);
		if (skillsGroup.title != null) {
			vh.textTitle.setText(skillsGroup.title);
		}
		if (skillsGroup.level1 != null && skillsGroup.level1.length()>0) {
			vh.textLevel1.setText(skillsGroup.level1);
		}
		if (skillsGroup.level2 != null&& skillsGroup.level2.length()>0) {
			vh.textLevel2.setText(skillsGroup.level2);
		}
		if (skillsGroup.level3 != null&& skillsGroup.level3.length()>0) {
			vh.textLevel3.setText(skillsGroup.level3);
		}
	}

	@Override
	public int getItemCount() {
		return list.size();
	}
	static class ViewHolder extends RecyclerView.ViewHolder {

		View itemView;
		TextView textTitle;
		TextView textLevel1;
		TextView textLevel2;
		TextView textLevel3;


		ViewHolder(View itemView) {
			super(itemView);
			this.itemView = itemView;
			this.textTitle=(TextView)itemView.findViewById(R.id.text_title);
			this.textLevel1=(TextView)itemView.findViewById(R.id.text_level1);
			this.textLevel2=(TextView)itemView.findViewById(R.id.text_level2);
			this.textLevel3=(TextView)itemView.findViewById(R.id.text_level3);
		}
	}
}
