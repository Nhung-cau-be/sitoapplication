package com.example.sitoapplication.common;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

public class NumberSupport {
    private static NumberSupport single_instance = null;

    public String asCurrency(long value) {
        DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.getDefault());
        DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
        symbols.setGroupingSeparator('.');
        formatter.setDecimalFormatSymbols(symbols);
        return formatter.format(value) + " VNĐ";
    }

    public static synchronized NumberSupport getInstance()
    {
        if (single_instance == null)
            single_instance = new NumberSupport();

        return single_instance;
    }
}
