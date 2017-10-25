package com.gustiaux.canadiansalestaxcalculator.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Location {

    private static Map<String, Integer> locationTaxes;
    static {
        Map<String, Integer> aMap = new HashMap<String, Integer>();
        aMap.put("AL",5);
        aMap.put("BC",5);
        aMap.put("MB",5);
        aMap.put("NB",15);
        aMap.put("NL",15);
        aMap.put("NT",5);
        aMap.put("NS",15);
        aMap.put("NU",5);
        aMap.put("ON",13);
        aMap.put("QC",5);
        aMap.put("PE",15);
        aMap.put("SK",5);
        aMap.put("YT",5);
        locationTaxes = Collections.unmodifiableMap(aMap);
    }

    private Integer taxPercentage;

    public Location(String code) {
        this.taxPercentage = this.locationTaxes.get(code);
    }

    public Double getTaxPercentage() {
        return this.taxPercentage.doubleValue() / 100;
    }

    public String getPercentage() {
        return Integer.toString(this.taxPercentage);
    }

}
