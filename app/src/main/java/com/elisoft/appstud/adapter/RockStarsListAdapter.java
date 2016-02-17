package com.elisoft.appstud.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.elisoft.appstud.R;
import com.elisoft.appstud.api.ContactsApi;
import com.elisoft.appstud.app.App;
import com.elisoft.appstud.database.DatabaseHelper;
import com.elisoft.appstud.model.Contacts;
import com.elisoft.appstud.utils.DeviceManager;
import com.elisoft.appstud.utils.SmartLog;

/**
 * @author phuc.tran
 *
 * The adapter which manages rockstars list
 */
public class RockStarsListAdapter extends RecyclerView.Adapter<RockStarsListAdapter.ViewHolder> {
    private Context context;
    private Contacts contacts;

    public RockStarsListAdapter(Contacts contacts, Context context) {
        this.context = context;
        this.contacts = contacts;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Create a new view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rockstar_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        SmartLog.error(RockStarsListAdapter.class, contacts.rockStars[position].id);
        holder.tvName.setText(contacts.rockStars[position].firstName + " " + contacts.rockStars[position].lastName);
        holder.tvStatus.setText(contacts.rockStars[position].status);

        // Load image
        Glide.with(context).load(ContactsApi.ENDPOINT + "/yolo/" + contacts.rockStars[position].hisFace)
                .crossFade()
                .into(holder.ivFace);

        holder.cbBookmark.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (App.dbHelper == null) {
                    App.dbHelper = new DatabaseHelper(context);
                }

                if (isChecked) {
                    App.dbHelper.bookmarkRockStar(contacts.rockStars[position]);
                } else {
                    App.dbHelper.removeBookmarkedRockStar(contacts.rockStars[position]);
                }
            }
        });

        /**
         * Check bookmark status, if bookmarked -> enable checkbox
         */
        if (App.dbHelper == null) {
            App.dbHelper = new DatabaseHelper(context);
        }

        if (App.dbHelper.isBookmarked(contacts.rockStars[position])) {
            holder.cbBookmark.setChecked(true);
        } else {
            holder.cbBookmark.setChecked(false);
        }

    }

    @Override
    public int getItemCount() {
        if (contacts == null || contacts.rockStars == null) {
            return 0;
        }
        return contacts.rockStars.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView ivFace;
        public TextView tvName;
        public TextView tvStatus;
        public CheckBox cbBookmark;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvStatus = (TextView) itemView.findViewById(R.id.tv_status);
            ivFace = (ImageView) itemView.findViewById(R.id.iv_face);
            cbBookmark = (CheckBox) itemView.findViewById(R.id.checkbox);

            /**
             * Resize image
             */
            ivFace.getLayoutParams().width = DeviceManager.getScreenWidth(context) / 7;
            ivFace.getLayoutParams().height = DeviceManager.getScreenWidth(context) / 7;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }
}