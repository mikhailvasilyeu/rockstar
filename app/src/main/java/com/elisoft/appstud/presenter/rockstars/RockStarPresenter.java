package com.elisoft.appstud.presenter.rockstars;

import android.text.TextUtils;

import com.elisoft.appstud.app.App;
import com.elisoft.appstud.model.Contacts;
import com.elisoft.appstud.model.RockStar;
import com.elisoft.appstud.view.rockstars.IRockStarsView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * @author phuc.tran
 *
 * The presenter which handles all logic on Rockstars fragment
 */
public class RockStarPresenter implements IRockStarsPresenter {

    private IRockStarsView rockStarsView;

    public RockStarPresenter(IRockStarsView rockStarsView) {
        this.rockStarsView = rockStarsView;
    }

    /**
     * Get contact from backend, then render it on UI
     */
    public void getContactsFromBackend() {
        App.getContactsApi()
                .getContacts()
                .delay(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Contacts>() {
                    @Override
                    public void call(Contacts contacts) {
                        rockStarsView.updateContacts(contacts);
                        rockStarsView.renderContacts(contacts);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                    }
                });
    }

    @Override
    public void filterContacts(Contacts contacts, String keyword) {
        /**
         * Do nothing if the contacts list is empty
         */
        if (contacts == null ||
                contacts.rockStars == null ||
                contacts.rockStars.length == 0) {
            return;
        }

        /**
         * Show all contacts if the keyword is not entered
         */
        if (TextUtils.isEmpty(keyword)) {
            rockStarsView.renderContacts(contacts);
            return;
        }

        /**
         * If user enters keyword, filter the contact and re-render list of contacts
         */
        Contacts filteredContacts = new Contacts();
        List<RockStar> rockStars = new ArrayList<>();
        int length = contacts.rockStars.length;
        /**
         * Do filter
         */
        for (int i = 0; i < length; i++) {
            if (contacts.rockStars[i].firstName.toLowerCase().contains(keyword)
                    || contacts.rockStars[i].lastName.toLowerCase().contains(keyword)
                    || contacts.rockStars[i].status.toLowerCase().contains(keyword)) {
                RockStar rockStar = new RockStar(contacts.rockStars[i].firstName,
                        contacts.rockStars[i].lastName,
                        contacts.rockStars[i].status,
                        contacts.rockStars[i].hisFace);
                rockStars.add(rockStar);
            }
        }
        filteredContacts.rockStars = rockStars.toArray(new RockStar[rockStars.size()]);
        /**
         * Update UI
         */
        rockStarsView.renderContacts(filteredContacts);
    }

}
