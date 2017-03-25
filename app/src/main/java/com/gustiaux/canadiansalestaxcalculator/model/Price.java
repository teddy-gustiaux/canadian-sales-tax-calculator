package com.gustiaux.canadiansalestaxcalculator.model;

import java.text.NumberFormat;
import java.util.Locale;

public class Price {

    private String defaultPrice = "0";
    private Double price = 0.0;

    public Price(String input) {
        if (input == null || input.isEmpty()) input = this.defaultPrice;
        this.price = Double.parseDouble(cleanNumber(input));
    }

    private String cleanNumber(String number) {
        number = number.replaceAll(",", "");
        return number;
    }

    public String formatNumber(boolean round) {
        NumberFormat nf = NumberFormat.getInstance(Locale.CANADA);
        if (round) this.price = Math.round(this.price * 100.00) / 100.00;
        return nf.format(this.price);
    }

    public void addSalesTax() {
        this.price = this.price * 1.13;
    }

}
