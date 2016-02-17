package com.elisoft.appstud.view.rockstars;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.elisoft.appstud.R;
import com.elisoft.appstud.adapter.RockStarsListAdapter;
import com.elisoft.appstud.model.Contacts;
import com.elisoft.appstud.presenter.rockstars.IRockStarsPresenter;
import com.elisoft.appstud.presenter.rockstars.RockStarPresenter;
import com.elisoft.appstud.view.SimpleDividerItemDecoration;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author phuc.tran
 */
public class RockStarsFragment extends Fragment implements IRockStarsView, SwipeRefreshLayout.OnRefreshListener {
    /**
     * Singleton bookmarks fragment
     */
    private static RockStarsFragment instance;

    @Bind(R.id.edt_keyword)
    EditText edtKeyword;

    // Contact list which is used for searching
    private Contacts contacts;

    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @Bind(R.id.recycler_view_rockstars)
    RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    RockStarsListAdapter adapter;

    // Presenter which handle all logic
    IRockStarsPresenter presenter;

    /**
     * Get bookmarks fragment using singleton pattern (Only create new instance when needed)
     * @return
     */
    public static RockStarsFragment getInstance() {
        if (RockStarsFragment.instance == null) {
            RockStarsFragment.instance = new RockStarsFragment();
        }
        return RockStarsFragment.instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_rockstars, container, false);

        ButterKnife.bind(this, v);

        /**
         * Setup search bar
         */
        setupSearchBar();

        /**
         * Setup recycler view
          */
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        // Add divider
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));

        swipeRefreshLayout.setOnRefreshListener(this);

        // Create presenter which will handle all logic on this fragment
        presenter = new RockStarPresenter(this);
        presenter.getContactsFromBackend();

        return v;
    }

    /**
     * Setup search bar: when keyword is being typed -> update list of contact
     */
    private void setupSearchBar() {
        edtKeyword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                presenter.filterContacts(contacts, s.toString().toLowerCase());
            }
        });
    }

    @Override
    public void updateContacts(Contacts contacts) {
        this.contacts = contacts;
    }

    /**
     * Render list of contacts to list
     * @param contacts The contacts which will be rendered
     */
    @Override
    public void renderContacts(Contacts contacts) {
        if (adapter != null) {
            adapter = null;
        }
        adapter = new RockStarsListAdapter(contacts, this.getActivity());
        recyclerView.setAdapter(adapter);

        // Hide progress bar
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        // Clear list
        recyclerView.setAdapter(null);

        // Show progress bar
        swipeRefreshLayout.setRefreshing(true);

        // Reload contacts
        presenter.getContactsFromBackend();
    }
}