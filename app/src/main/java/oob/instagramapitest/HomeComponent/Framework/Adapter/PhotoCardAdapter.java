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

import butterknife.BindView;
import butterknife.ButterKnife;
import oob.instagramapitest.AddPhotoScheduleComponent.Framework.AddPhotoScheduleActivity;
import oob.instagramapitest.R;

public class PhotoCardAdapter extends RecyclerView.Adapter<PhotoCardAdapter.ViewHolder> {

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_home_recycler_card_photo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final boolean lastPosition = position == 0;
        final Context context = holder.cardView.getContext();

        holder.photoMiniature.setBackgroundResource(lastPosition ? R.drawable.ic_add_primary : R.drawable.ic_add_photo_primary);
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
        return 1;
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

        @BindView(R.id.photoMiniature)
        ImageView photoMiniature;

        ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
