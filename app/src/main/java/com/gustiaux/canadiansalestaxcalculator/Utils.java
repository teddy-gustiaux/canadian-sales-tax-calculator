package com.gustiaux.canadiansalestaxcalculator;

import android.content.Context;
import android.widget.Toast;

import com.gustiaux.CanadianSalesTaxCalculator;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class Utils {

    public static void displayToast(String text) {
        Context context = CanadianSalesTaxCalculator.getAppContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    public static String formatNumber(Double input, boolean round) {
        NumberFormat nf = NumberFormat.getInstance(Locale.CANADA);
        if (round) input = Math.round(input * 100.00) / 100.00;
        return nf.format(input);
    }

    public static String cleanNumber(String number) {
        if (number.isEmpty()) return number;
        number = number.replaceAll(",", "");
        return number;
    }
}
