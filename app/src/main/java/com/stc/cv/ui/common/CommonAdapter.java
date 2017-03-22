package com.stc.cv.ui.common;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.stc.cv.R;
import com.stc.cv.databinding.ItemSectionBinding;
import com.stc.cv.model.CommonSection;
import com.stc.cv.ui.ArrayAdapter;

class CommonAdapter extends ArrayAdapter<CommonSection, CommonAdapter.ViewHolder> {

    CommonAdapter(Context context) {
        super(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemSectionBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context),
                R.layout.item_section, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        CommonSection section = data.get(position);

        holder.binding.title.setText(section.title);
        holder.binding.description.setText(Html.fromHtml(section.description));

    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ItemSectionBinding binding;

        ViewHolder(ItemSectionBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}