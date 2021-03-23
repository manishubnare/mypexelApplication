package com.example.mypexelapplication;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoViewHolder> {

    private Context context;
    private List<PhotoModel> photoModelList;
    private onPhotoClick photoClick;

    public interface onPhotoClick
    {
        void onItemClick(PhotoModel model);
    }

    public PhotoAdapter(Context context, List<PhotoModel> photoModelList, onPhotoClick onPhotoClick) {
        this.context = context;
        this.photoModelList = photoModelList;
        this.photoClick= onPhotoClick;
    }

    public void setPhotoModelList(List<PhotoModel> photoModelList) {
        this.photoModelList = photoModelList;
    }

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.image_item,parent,false);
        return new PhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, final int position) {

        Glide.with(context).load(photoModelList.get(position).getSmallUrl()).into(holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photoClick.onItemClick(photoModelList.get(position));
            }
        });
//        holder.imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                context.startActivity(new Intent(context, PhotoFragment.class)
//                .putExtra("smallUrl", photoModelList.get(position).getSmallUrl()));
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return photoModelList.size();
    }
}
class PhotoViewHolder extends RecyclerView.ViewHolder{
    ImageView imageView;
    public PhotoViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView=itemView.findViewById(R.id.imageViewItem);
    }
}