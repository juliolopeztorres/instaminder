package oob.instagramapitest.HomeComponent.Framework.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import oob.instagramapitest.AddPhotoScheduleComponent.Framework.AddPhotoScheduleActivity;
import oob.instagramapitest.HomeComponent.Domain.GetAllPhotosUseCase.Model.Photo;
import oob.instagramapitest.R;

public class PhotoCardAdapter extends RecyclerView.Adapter<PhotoCardAdapter.ViewHolder> {

    private List<Photo> photos = new ArrayList<>();

    public PhotoCardAdapter() {
        this.photos.add(new Photo());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_home_recycler_card_photo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final boolean lastPosition = position == this.photos.size() - 1;
        final Context context = holder.cardView.getContext();

        Photo photo = this.photos.get(position);

        Glide.with(context)
                .load(
                        lastPosition ?
                                R.drawable.ic_add_primary :
                                photo.getBuffer()
                ).into(holder.photoMiniature);

        holder.photoName.setText(photo.getName());
        holder.photoCaption.setText(photo.getCaption());
        holder.photoDate.setText(
                photo.getDate() != null ?
                        SimpleDateFormat.getDateInstance().format(photo.getDate()) + " " + SimpleDateFormat.getTimeInstance().format(photo.getDate()) :
                        ""
        );

        holder.addNewPhotoLabel.setVisibility(lastPosition ? View.VISIBLE : View.GONE);

        holder.photoDataContainer.setVisibility(lastPosition ? View.GONE : View.VISIBLE);
        holder.photoStateIndicator.setVisibility(lastPosition ? View.GONE : View.VISIBLE);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lastPosition) {
                    context.startActivity(new Intent(context, AddPhotoScheduleActivity.class));
                    return;
                }

                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(v.getContext());

                LayoutInflater inflater = LayoutInflater.from(v.getContext());
                @SuppressLint("InflateParams") View dialogView = inflater.inflate(R.layout.activity_home_card_photo_dialog, null);
                dialogBuilder.setView(dialogView);

                AlertDialog alertDialog = dialogBuilder.create();
                alertDialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.photos.size();
    }

    public void setPhotos(List<Photo> photos) {
        photos.add(new Photo());
        this.photos = photos;
        this.notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.cardView)
        View cardView;
        @BindView(R.id.photoStateIndicator)
        View photoStateIndicator;
        @BindView(R.id.photoDataContainer)
        View photoDataContainer;
        @BindView(R.id.addNewPhotoLabel)
        View addNewPhotoLabel;
        @BindView(R.id.photoName)
        TextView photoName;
        @BindView(R.id.photoCaption)
        TextView photoCaption;
        @BindView(R.id.photoDate)
        TextView photoDate;
        @BindView(R.id.photoMiniature)
        ImageView photoMiniature;

        ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
