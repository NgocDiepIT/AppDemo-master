package com.example.appdemo.adapter;

import android.media.Image;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appdemo.R;
import com.example.appdemo.interf.OnDeleteImageClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class UpdateCoverAdapter extends RecyclerView.Adapter<UpdateCoverAdapter.MyViewHolder> {
    List<Uri> imageList;
    OnDeleteImageClickListener listener;

    public UpdateCoverAdapter(List<Uri> imageList, OnDeleteImageClickListener listener) {
        this.imageList = imageList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public UpdateCoverAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_image, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UpdateCoverAdapter.MyViewHolder holder, int position) {
        holder.bindView(imageList.get(position));
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.iv_image)
        ImageView ivImage;
        @BindView(R.id.iv_delete)
        CircleImageView ivDelete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);


        }

        private void bindView(Uri uri){
            ivImage.setImageURI(uri);
            ivDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onDeleteImage(uri);
                }
            });
        }
    }
}
