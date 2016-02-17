package com.elisoft.appstud.api;

import com.elisoft.appstud.model.Contacts;

import retrofit.http.GET;
import rx.Observable;

/**
 * @author phuc.tran
 */
public interface ContactsApi {
    public static final String ENDPOINT = "http://54.72.181.8";

    /**
     * Create a retrofit GET request
     * @return
     */
    @GET("/yolo/contacts.json")
    Observable<Contacts> getContacts();
}
