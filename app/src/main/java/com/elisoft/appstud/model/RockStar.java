package com.elisoft.appstud.model;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;

/**
 * @author phuc.tran
 */
public class RockStar {

    public RockStar() {

    }

    public RockStar(String firstName, String lastName, String status, String hisFace) {
        this.id = this.firstName + this.lastName + this.status + this.hisFace;
        this.firstName = firstName;
        this.lastName = lastName;
        this.status = status;
        this.hisFace = hisFace;
    }

    /**
     * Get full name from first name and last name
     * @return
     */
    public String getFullName() {
        return firstName + " " + lastName;
    }

    @DatabaseField(id = true)
    public String id;

    @DatabaseField
    @SerializedName("firstname")
    public String firstName;

    @DatabaseField
    @SerializedName("lastname")
    public String lastName;

    @DatabaseField
    @SerializedName("status")
    public String status;

    @DatabaseField
    @SerializedName("hisface")
    public String hisFace;
}
