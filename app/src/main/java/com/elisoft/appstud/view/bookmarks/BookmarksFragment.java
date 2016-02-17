package com.elisoft.appstud.view.bookmarks;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elisoft.appstud.R;
import com.elisoft.appstud.adapter.BookmarkAdapter;
import com.elisoft.appstud.model.RockStar;
import com.elisoft.appstud.view.SimpleDividerItemDecoration;

import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author phuc.tran
 */
public class BookmarksFragment extends Fragment {

    /**
     * Singleton bookmarks fragment
     */
    private static BookmarksFragment instance;

    @Bind(R.id.recycler_view_bookmarks)
    RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private BookmarkAdapter adapter;
    private List<RockStar> rockStars;

    /**
     * Get bookmarks fragment using singleton pattern (Only create new instance when needed)
     * @return
     */
    public static BookmarksFragment getInstance() {
        if (BookmarksFragment.instance == null) {
            BookmarksFragment.instance = new BookmarksFragment();
        }
        return BookmarksFragment.instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_bookmarks, container, false);

        ButterKnife.bind(this, v);

        /**
         * Setup recycler view
         */
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        // Add divider
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));

        generateData();

        return v;
    }

    /**
     * Generate dummy data for testing
     */
    private void generateData() {
        rockStars = new LinkedList<>();
        for (int i = 1; i <= 20; i++) {
            RockStar rockStar = new RockStar("", "this is rockstar name " + i, "this is rockstar status" + i, "");
            rockStars.add(rockStar);
        }

        adapter = new BookmarkAdapter(rockStars, getContext());
        recyclerView.setAdapter(adapter);
    }
}
