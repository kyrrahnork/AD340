package com.example.user.ad340app;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CameraAdapter extends RecyclerView.Adapter<CameraAdapter.CameraViewHolder> {

    private Context context;
    private ArrayList<CameraItem> cameraItemArrayList;

    public CameraAdapter(Context context, ArrayList<CameraItem> cameraItemArrayList) {
        this.context = context;
        this.cameraItemArrayList = cameraItemArrayList;
    }

    @NonNull
    @Override
    public CameraViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_camera_list_2_items, parent, false);
        return new CameraViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CameraViewHolder holder, int position) {
        CameraItem currentItem =  cameraItemArrayList.get(position);
        String imageURL = currentItem.getImageURL();
        String name = currentItem.getName();

        holder.textView.setText(name);
        Picasso.get()
                .load(imageURL)
                .fit()
                .centerInside()
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return cameraItemArrayList.size();
    }

    public class CameraViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;
        public TextView textView;

        public CameraViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView2);
            textView = itemView.findViewById(R.id.cameraTitleTextView);
        }
    }
}