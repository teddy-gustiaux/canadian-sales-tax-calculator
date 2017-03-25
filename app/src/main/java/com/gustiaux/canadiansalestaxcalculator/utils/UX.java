package com.gustiaux.canadiansalestaxcalculator.utils;

import android.content.Context;
import android.widget.Toast;

import com.gustiaux.CanadianSalesTaxCalculator;

public class UX {

    public static void displayToast(String text) {
        Context context = CanadianSalesTaxCalculator.getAppContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

}
