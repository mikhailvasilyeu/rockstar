package com.elisoft.appstud.presenter.rockstars;

import com.elisoft.appstud.model.Contacts;

/**
 * @author phuc.tran
 */
public interface IRockStarsPresenter {

    void getContactsFromBackend();
    void filterContacts(Contacts contacts, String keyword);
}
