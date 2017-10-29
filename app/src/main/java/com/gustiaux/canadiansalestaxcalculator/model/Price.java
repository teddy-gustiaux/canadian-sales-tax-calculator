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

    @Override
    public String toString() {
        return Double.toString(this.price);
    }

    public String formatNumber() {
        NumberFormat nf = NumberFormat.getInstance(Locale.CANADA);
        nf.setMaximumFractionDigits(15);
        if ((this.price == Math.floor(this.price)) && !Double.isInfinite(this.price)) {
            // Integer
        } else {
            nf.setMinimumFractionDigits(1);
        }
        return nf.format(this.price);
    }

    public void roundNumber() {
        this.price = Math.round(this.price * 100.00) / 100.00;
    }

    public void addSalesTax(Double percentage) {
        this.price = this.price * (1 + percentage);
    }

}
