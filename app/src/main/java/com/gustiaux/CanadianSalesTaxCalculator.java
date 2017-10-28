package com.gustiaux;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatDelegate;

import com.gustiaux.canadiansalestaxcalculator.R;
import com.gustiaux.canadiansalestaxcalculator.model.Location;

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

    public static void applyCorrectTheme() {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);

        Boolean darkTheme = sharedPref.getBoolean(context.getString(R.string.dark_theme_switch), false);
        if (darkTheme) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }
}
