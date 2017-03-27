package com.stc.cv.ui.common;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.stc.cv.R;
import com.stc.cv.model.Work;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by artem on 3/24/17.
 */

public class WorkAdapter extends RecyclerView.Adapter{
	List<Work> list;
	private static final String TAG = "WorkAdapter";


	public WorkAdapter() {
		this.list = new ArrayList<>();
	}
	public void addItem(Work work){
		Log.d(TAG, "addItem: "+work);
		list.add(work);
		notifyDataSetChanged();
	}
	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.work_layout, parent, false);
		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
		ViewHolder vh = (ViewHolder) holder;
		Work work = list.get(position);
		vh.textCompany.setText(work.company);
		vh.textPosition.setText(work.position);
		vh.textPeriod.setText(work.period);
		vh.textProject.setText(work.project);
		vh.textTools.setText(work.tools);
		vh.textTasks.setText(work.tasks);
	}

	@Override
	public int getItemCount() {
		return list.size();
	}
	static class ViewHolder extends RecyclerView.ViewHolder {

		View itemView;
		TextView textProject;
		TextView textPosition;
		TextView textCompany;
		TextView textTools;
		TextView textTasks;
		TextView textPeriod;


		ViewHolder(View itemView) {
			super(itemView);
			this.itemView = itemView;
			this.textProject=(TextView)itemView.findViewById(R.id.text_project_info);
			this.textPosition=(TextView)itemView.findViewById(R.id.text_position);
			this.textCompany=(TextView)itemView.findViewById(R.id.text_company);
			this.textTools=(TextView)itemView.findViewById(R.id.text_tools);
			this.textTasks=(TextView)itemView.findViewById(R.id.text_tasks);
			this.textPeriod=(TextView)itemView.findViewById(R.id.text_period);
		}
	}
}
