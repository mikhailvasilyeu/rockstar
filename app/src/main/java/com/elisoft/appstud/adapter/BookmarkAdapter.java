package com.elisoft.appstud.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.elisoft.appstud.R;
import com.elisoft.appstud.model.RockStar;
import com.elisoft.appstud.utils.DeviceManager;

import java.util.List;

/**
 * @author Chuong
 *
 * The adapter which manages bookmarks list
 */
public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkAdapter.ViewHolder> {
    private Context context;
    private List<RockStar> rockStars;

    public BookmarkAdapter(List<RockStar> rockStars, Context context) {
        this.rockStars = rockStars;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.bookmark_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindData(rockStars.get(position));
    }

    @Override
    public int getItemCount() {
        if (rockStars == null) {
            return 0;
        }
        return rockStars.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView ivAvatar, ivDelete;
        private TextView tvName, tvStatus;
        private RockStar clickedItem;

        public ViewHolder(View v) {
            super(v);
            ivAvatar = (ImageView) v.findViewById(R.id.iv_avatar);
            ivDelete = (ImageView) v.findViewById(R.id.iv_delete);
            tvName = (TextView) v.findViewById(R.id.tv_name);
            tvStatus = (TextView) v.findViewById(R.id.tv_status);

            /**
             * Resize image
             */
            ivAvatar.getLayoutParams().width = DeviceManager.getScreenWidth(context) / 7;
            ivAvatar.getLayoutParams().height = DeviceManager.getScreenWidth(context) / 7;
        }

        public void bindData(RockStar rockStar) {
            clickedItem = rockStar;
            tvName.setText(rockStar.getFullName());
            tvStatus.setText(rockStar.status);

            // Load image
            Glide.with(context).load(R.mipmap.ic_launcher)
                    .crossFade()
                    .into(ivAvatar);
        }

        @Override
        public void onClick(View v) {
        }
    }
}
