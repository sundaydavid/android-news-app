package com.example.apexonnewsupdate.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.apexonnewsupdate.R;
import com.example.apexonnewsupdate.activities.YoutubeVideoActivity;
import com.example.apexonnewsupdate.models.YtModel;

import java.util.ArrayList;
import java.util.List;

public class YoutubeAdapter extends RecyclerView.Adapter<YoutubeAdapter.ViewHolder> {

    Context context;
    List<YtModel.Item> items = new ArrayList<>();

    public YoutubeAdapter(Context context, List<YtModel.Item> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        YtModel.Item singleVideoItem = items.get(holder.getAdapterPosition());

        if (singleVideoItem.getSnippet().getThumbnails().getStandard() != null){
            Glide.with(context)
                    .load(singleVideoItem.getSnippet().getThumbnails().getStandard().getUrl())
                    .into(holder.videoThumbnail);
        }else {
            Glide.with(context)
                    .load(singleVideoItem.getSnippet().getThumbnails().getDefault().getUrl())
                    .into(holder.videoThumbnail);
        }

    holder.videoTitle.setText(singleVideoItem.getSnippet().getTitle());

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView videoThumbnail;
        TextView videoTitle;
        View view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;

            videoThumbnail = view.findViewById(R.id.thumbnail);
            videoTitle = view.findViewById(R.id.video_title);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            YtModel.Item clickedVideo = items.get(getAdapterPosition());

            Uri uri = Uri.parse(clickedVideo.getSnippet().getThumbnails()
                    .getDefault().getUrl());

            Intent i = new Intent(context, YoutubeVideoActivity.class);
            i.putExtra("vid", uri.getPathSegments().get(1));
            i.putExtra("cid", clickedVideo.getSnippet().getChannelId());
            i.putExtra("cname", clickedVideo.getSnippet().getChannelTitle());
            i.putExtra("title", clickedVideo.getSnippet().getTitle());

            context.startActivity(i);
        }
    }
}
