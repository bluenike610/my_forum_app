package com.postingapp.android.common;

import android.app.Activity;
import com.postingapp.android.Application;
import com.postingapp.android.db.DbHelper;
import com.postingapp.android.db.Queries;
import com.postingapp.android.model.Profile;

public class Common {
    public static Common cm = new Common();

    //----------------------------------------------Common Objects-----------------------------------------------------------------------------------------

    public static Application myApp;
    public static Activity currentActivity = null;
    //----------------------------------------------Common Functions-----------------------------------------------------------------------------------------

    public Profile getMyInfo() {
        DbHelper dbHelper = new DbHelper(currentActivity);
        Queries query = new Queries(null, dbHelper);
        Profile me = query.getUserById(Integer.valueOf(3));
        return me;
    }
}
