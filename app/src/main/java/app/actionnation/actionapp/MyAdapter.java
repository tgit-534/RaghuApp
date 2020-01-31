package app.actionnation.actionapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.ArrayList;
import java.util.List;

import app.actionnation.actionapp.Storage.Upload;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private Context context;
    private List<Upload> uploads;

    public MyAdapter(Context context, List<Upload> uploads) {
        this.uploads = uploads;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_images, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Upload upload = this.uploads.get(position);

        holder.textViewName.setText(upload.getName());

        List<String> x = new ArrayList<>();
        x.add(upload.getUrl());
        String urll = "https://firebasestorage.googleapis.com/v0/b/tgit-4c9c3.appspot.com/o/uploads%2F1573645157379.jpg?alt=media&token=92540079-a8f2-4470-bf88-0f2e52e06de8";


        ImageView imageView = holder.imageFbView;
        Log.d("check image url",upload.getUrl());

        Glide.with(context)
                .asBitmap()
                .load(upload.getUrl())
                .override(180, 180)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        holder.imageFbView.setImageBitmap(resource);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                    }

                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
                        super.onLoadFailed(errorDrawable);
                      //  holder.imageFbView.setImageBitmap(R.drawable.com_facebook_auth_dialog_background);
          //              Log.d("error", errorDrawable.toString());

                    }
                });
    }


    @Override
    public int getItemCount() {
        return uploads.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewName;

        public TextView getTextViewName() {
            return textViewName;
        }

        public void setTextViewName(TextView textViewName) {
            this.textViewName = textViewName;
        }

        public ImageView getImageView() {
            return imageFbView;
        }

        public void setImageView(ImageView imageView) {
            this.imageFbView = imageFbView;
        }

        public ImageView imageFbView;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewName = (TextView) itemView.findViewById(R.id.textViewName);
            imageFbView = (ImageView) itemView.findViewById(R.id.imageFbView);
        }
    }
}