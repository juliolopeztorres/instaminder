package oob.instagramapitest.HomeComponent.Framework.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
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
import oob.instagramapitest.HomeComponent.Domain.GetAllPhotosUseCase.Model.Photo;
import oob.instagramapitest.R;

public class PhotoCardAdapter extends RecyclerView.Adapter<PhotoCardAdapter.ViewHolder> {

    private Context context;
    private OnPhotoCardEvent callback;
    private List<Photo> photos = new ArrayList<>();

    public PhotoCardAdapter(Context context, OnPhotoCardEvent callback) {
        this.context = context;
        this.callback = callback;
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

        final Photo photo = this.photos.get(position);

        Glide.with(this.context)
                .load(
                        lastPosition ?
                                R.drawable.ic_add_primary :
                                photo.getBuffer()
                ).into(holder.photoMiniature);

        holder.addNewPhotoLabel.setVisibility(lastPosition ? View.VISIBLE : View.GONE);

        holder.photoDataContainer.setVisibility(lastPosition ? View.GONE : View.VISIBLE);
        holder.photoStateIndicator.setVisibility(lastPosition ? View.GONE : View.VISIBLE);

        if (photo != null) {
            holder.photoName.setText(photo.getName());
            holder.photoCaption.setText(photo.getCaption());
            holder.photoDate.setText(
                    String.format(PhotoCardAdapter.this.context.getString(R.string.home_component_card_photo_date_time_format), SimpleDateFormat.getDateInstance().format(photo.getDate()), SimpleDateFormat.getTimeInstance().format(photo.getDate()))
            );

            holder.photoStateIndicator.setBackgroundResource(photo.getState().equals(oob.instagramapitest.Util.Database.Photo.PENDING) ? R.drawable.ic_waiting_primary : R.drawable.ic_warning_primary);
        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (lastPosition) {
                    PhotoCardAdapter.this.callback.onAddNewPhotoClicked();
                    return;
                }

                PhotoCardAdapter.this.callback.onPhotoClicked(photo);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.photos.size();
    }

    public void setPhotos(List<Photo> photos) {
        photos.add(null);
        this.photos = photos;
        this.notifyDataSetChanged();
    }

    public void notifyItemChanged(Photo photo) {
        this.notifyItemChanged(this.photos.indexOf(photo));
    }

    public void notifyItemRemoved(Photo photo) {
        int position = this.photos.indexOf(photo);
        this.photos.remove(position);
        this.notifyItemRemoved(position);
    }

    public interface OnPhotoCardEvent {
        void onPhotoClicked(Photo photo);

        void onAddNewPhotoClicked();
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
