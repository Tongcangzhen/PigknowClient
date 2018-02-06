package com.example.ldjg.pigknowclient.Adapter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ldjg.pigknowclient.AddRecordActivity;
import com.example.ldjg.pigknowclient.DB.Media;
import com.example.ldjg.pigknowclient.R;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

/**
 * Created by Administrator on 2018/2/6.
 */

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {
    private List<Media> mediaList;

    public class ViewHolder extends RecyclerView.ViewHolder {
        View videoView;
        ImageView imageView;
        TextView date;

        public ViewHolder(View view) {
            super(view);
            videoView = view;
            imageView = (ImageView) view.findViewById(R.id.video_image);
            date = (TextView) view.findViewById(R.id.textview_video_date);
        }
    }

    @Override
    public VideoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.media_item, null);
        final ViewHolder holder = new ViewHolder(view);
        holder.videoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int i = holder.getAdapterPosition();
                Media media = mediaList.get(i);
                Intent intent = new Intent(view.getContext(), AddRecordActivity.class);
                intent.putExtra("media_data", media);
                view.getContext().startActivity(intent);
            }
        });
        return holder;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        try {
            Media media = mediaList.get(position);
            holder.date.setText(media.getDate());
            FileInputStream fileInputStream=new FileInputStream(media.getPhotoPath());
            Bitmap bitmap  = BitmapFactory.decodeStream(fileInputStream);
            holder.imageView.setImageBitmap(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return mediaList.size();
    }

    public VideoAdapter(List<Media> mediaList) {
        this.mediaList = mediaList;
    }


}
