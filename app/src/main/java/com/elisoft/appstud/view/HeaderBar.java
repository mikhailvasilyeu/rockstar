package com.elisoft.appstud.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.elisoft.appstud.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author phuc.tran
 *
 * Header bar which is on top of main screen. This bar could be reused in other screens.
 *
 */
public class HeaderBar extends RelativeLayout {

    @Bind(R.id.tv_header_title)
    TextView tvHeaderTitle;

    /**
     * Constructor which is used when creating Header Bar by Java code
     * @param context The context which uses this header bar
     */
    public HeaderBar(Context context) {
        super(context);

        inflateHeader(context);
    }

    /**
     * Constructor which is used when creating Header Bar in xml
     * @param context The context which uses this header bar
     * @param attrs The attributes of header bar which are defined in xml layout
     */
    public HeaderBar(Context context, AttributeSet attrs) {
        super(context, attrs);

        inflateHeader(context);
    }

    /**
     * Inflate header bar from xml layout (header_bar.xml)
     */
    private void inflateHeader(Context context) {
        View view = inflate(context, R.layout.header_bar, this);

        /**
         * Bind header bar -> This let Butterknife inject views in header bar (Ex: header title)
         */
        ButterKnife.bind(view);
    }

    /**
     * Set title for header
     * @param title The title which is shown on header
     */
    public void setHeaderTitle(String title) {
        tvHeaderTitle.setText(title);
    }
}
