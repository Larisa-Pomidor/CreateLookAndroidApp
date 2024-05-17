package com.example.createlookandroidapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class ClothesAdapter extends RecyclerView.Adapter<ClothesAdapter.MyViewHolder> {
    int[] data;
    //ConstraintLayout mainLayout;
    FrameLayout imageContainer;

    public ClothesAdapter(int[] data, FrameLayout imageContainer) {
        this.data = data;
        this.imageContainer = imageContainer;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_clothes_item_detail, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.imageView.setImageResource(data[position]);
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    class MyViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        ImageView imageView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            imageView = itemView.findViewById(R.id.clothes_image);
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(view.getContext(),
                    "position = " + getLayoutPosition(), Toast.LENGTH_SHORT).show();

            DraggableResizableImageView newImageView = new DraggableResizableImageView(view.getContext());

            newImageView.setImageResource(data[getLayoutPosition()]);

            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            newImageView.setLayoutParams(layoutParams);

            imageContainer.addView(newImageView);
        }
    }
}