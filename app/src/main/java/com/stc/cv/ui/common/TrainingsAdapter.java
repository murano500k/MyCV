package com.stc.cv.ui.common;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.stc.cv.R;
import com.stc.cv.model.Training;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by artem on 3/24/17.
 */

public class TrainingsAdapter extends RecyclerView.Adapter{
	List<Training> list;
	private static final String TAG = "WorkAdapter";


	public TrainingsAdapter() {
		this.list = new ArrayList<>();
	}
	public void addItem(Training training){
		Log.d(TAG, "addItem: "+training);
		list.add(training);
		notifyDataSetChanged();
	}
	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trainings_layout, parent, false);
		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
		ViewHolder vh = (ViewHolder) holder;
		Training training = list.get(position);
		Log.d(TAG, "onBindViewHolder: "+training);
		vh.textTitle.setText(training.getTitle());
		vh.textDescription.setText(training.getDescription());
		Picasso.with(vh.imageView.getContext())
				.load(training.imgUrl)
				.placeholder(R.drawable.ic_android)
				.error(R.drawable.ic_android)
				.into(vh.imageView);

		vh.imageView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(training.url));

				v.getContext().startActivity(intent);
			}
		});
	}

	@Override
	public int getItemCount() {
		return list.size();
	}
	static class ViewHolder extends RecyclerView.ViewHolder {

		View itemView;
		TextView textTitle;
		TextView textDescription;
		ImageView imageView;


		ViewHolder(View itemView) {
			super(itemView);
			this.itemView = itemView;
			this.textTitle=(TextView)itemView.findViewById(R.id.text_title);
			this.textDescription=(TextView)itemView.findViewById(R.id.text_description);
			this.imageView=(ImageView)itemView.findViewById(R.id.image_cert);
		}
	}
}
