package com.ssatyacc.ixigo.ixigoflights.model;

import java.io.Serializable;

/**
 * Created by vijay on 4/4/17.
 */

public class Airlines implements Serializable {
    public final String id;
    public final String name;
    public final int photoURL;

    public Airlines(String id, String name, int photoURL) {
        this.id = id;
        this.name = name;
        this.photoURL = photoURL;
    }
}
