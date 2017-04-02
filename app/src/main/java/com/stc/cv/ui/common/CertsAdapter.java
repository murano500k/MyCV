package com.stc.cv.ui.common;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.squareup.picasso.Picasso;
import com.stc.cv.R;
import com.stc.cv.model.Cert;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by artem on 3/24/17.
 */

public class CertsAdapter extends RecyclerView.Adapter{
	List<Cert> list;
	private static final String TAG = "WorkAdapter";
	private FirebaseAnalytics analytics;


	public CertsAdapter(FirebaseAnalytics  analytics) {
		this.list = new ArrayList<>();
		this.analytics = analytics;
	}
	public void addItem(Cert cert){
		Log.d(TAG, "addItem: "+cert);
		list.add(cert);
		notifyDataSetChanged();
	}
	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.certs_layout, parent, false);
		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
		ViewHolder vh = (ViewHolder) holder;
		Cert cert = list.get(position);
		Log.d(TAG, "onBindViewHolder: "+cert);
		vh.textTitle.setText(cert.getTitle());
		vh.textDescription.setText(cert.getDescription());
		vh.textIssuedBy.setText(cert.getIssuedBy());
		Picasso.with(vh.imageView.getContext())
				.load(cert.getCertImgUrl())
				.placeholder(R.drawable.ic_android)
				.error(R.drawable.ic_android)
				.into(vh.imageView);

		vh.imageView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				Bundle bundle = new Bundle();
				bundle.putString("cert", cert.title);
				analytics.logEvent("cert_clicked", bundle);
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(cert.getCertUrl()));

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
		TextView textIssuedBy;
		ImageView imageView;


		ViewHolder(View itemView) {
			super(itemView);
			this.itemView = itemView;
			this.textTitle=(TextView)itemView.findViewById(R.id.text_title);
			this.textDescription=(TextView)itemView.findViewById(R.id.text_description);
			this.textIssuedBy=(TextView)itemView.findViewById(R.id.text_issued_by);
			this.imageView=(ImageView)itemView.findViewById(R.id.image_cert);
		}
	}
}
