package com.gustiaux;

import android.app.Application;
import android.content.Context;

public class CanadianSalesTaxCalculator extends Application {

    private static Context context;

    public void onCreate() {
        super.onCreate();
        CanadianSalesTaxCalculator.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return CanadianSalesTaxCalculator.context;
    }
}
