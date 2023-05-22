package com.example.apexonnewsupdate.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.apexonnewsupdate.R;
import com.example.apexonnewsupdate.models.HomepageModel;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class GridCategoryAdaptor extends BaseAdapter {

    LayoutInflater layoutInflater;
    Context context;
    List<HomepageModel.CategoryBotton> categoryBottons;

    public GridCategoryAdaptor(Context context,List<HomepageModel.CategoryBotton> categoryBottons) {
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

        this.categoryBottons = categoryBottons;
    }

    @Override
    public int getCount() {
        return categoryBottons.size();
    }

    @Override
    public Object getItem(int i) {
        return categoryBottons.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHoldery holdery = null;

        if (view == null){
            view = layoutInflater.inflate(R.layout.item_category_layout,null);
            holdery = new ViewHoldery();

            holdery.circleImageView = view.findViewById(R.id.category_image);
            holdery.textView = view.findViewById(R.id.text_category);
            view.setTag(holdery);
        }else {
            holdery = (ViewHoldery) view.getTag();
        }

        holdery.textView.setText(categoryBottons.get(i).getName());
        Glide.with(context)
                .load(categoryBottons.get(i).getImage())
                .into(holdery.circleImageView);

        if (categoryBottons.get(i).getColor() != null){
        holdery.circleImageView.setCircleBackgroundColor(Color.parseColor(categoryBottons.get(i).getColor()));
        holdery.circleImageView.setBorderColor(Color.parseColor(categoryBottons.get(i).getColor()));
        }
        return view;
    }

    static class ViewHoldery{
        CircleImageView circleImageView;
        TextView textView;
    }
}
