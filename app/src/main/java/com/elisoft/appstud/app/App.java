package com.elisoft.appstud.app;

import android.app.Application;
import android.util.Log;

import com.elisoft.appstud.api.ContactsApi;
import com.elisoft.appstud.database.DatabaseHelper;

import retrofit.RestAdapter;

/**
 * @author phuc.tran
 */
public class App extends Application {
    /**
     * Global api which is used for all api requests
     */
    private static ContactsApi contactsApi;

    /**
     * Global database helper
     */
    public static DatabaseHelper dbHelper;

    @Override
    public void onCreate() {
        super.onCreate();

        initLocalDatabase();
        getContactsApi();
    }

    private void initLocalDatabase() {
        if (dbHelper == null) {
            dbHelper = new DatabaseHelper(this);
        }
    }

    /**
     * Create global api
     * @return
     */
    public static ContactsApi getContactsApi() {
        if (contactsApi == null) {
            contactsApi = new RestAdapter.Builder()
                    .setEndpoint(ContactsApi.ENDPOINT)
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .setLog(new RestAdapter.Log() {
                        @Override
                        public void log(String message) {
                            Log.v("Retrofit", message);
                        }
                    })
                    .build().create(ContactsApi.class);
        }
        return contactsApi;
    }

}
