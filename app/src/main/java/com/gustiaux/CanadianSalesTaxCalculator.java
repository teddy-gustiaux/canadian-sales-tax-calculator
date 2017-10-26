package com.gustiaux;

import android.app.Application;
import android.content.Context;

import com.gustiaux.canadiansalestaxcalculator.R;
import com.gustiaux.canadiansalestaxcalculator.model.Location;
import com.gustiaux.canadiansalestaxcalculator.model.Price;

public class CanadianSalesTaxCalculator extends Application {

    private static Context context;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    private Location location;

    public void onCreate() {
        super.onCreate();
        CanadianSalesTaxCalculator.context = getApplicationContext();
        this.location = new Location(getString(R.string.ON));
    }

    public static Context getAppContext() {
        return CanadianSalesTaxCalculator.context;
    }
}
