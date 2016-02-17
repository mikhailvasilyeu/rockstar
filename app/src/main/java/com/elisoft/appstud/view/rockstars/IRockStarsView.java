package com.elisoft.appstud.view.rockstars;

import com.elisoft.appstud.model.Contacts;

/**
 * @author phuc.tran
 */
public interface IRockStarsView {
    void updateContacts(Contacts contacts);
    void renderContacts(Contacts contacts);
}
