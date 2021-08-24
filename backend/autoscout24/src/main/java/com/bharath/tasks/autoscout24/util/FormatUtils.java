package com.bharath.tasks.autoscout24.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Comparator;
import java.util.Locale;
import java.util.Map;

public class FormatUtils {

    public static final Comparator<Map.Entry<String, String>> MAKE_PERCENT_COMPARATOR = (makePercentEntry1, makePercentEntry2) -> {
        if (makePercentEntry1.getValue().equals(makePercentEntry2.getValue())) {
            return makePercentEntry1.getKey().compareTo(makePercentEntry2.getKey());
        } else {
            Long percent1 = Long.valueOf(makePercentEntry1.getValue().replaceAll("%", ""));
            Long percent2 = Long.valueOf(makePercentEntry2.getValue().replaceAll("%", ""));
            return percent2.compareTo(percent1);
        }
    };

    public static String formatCurrency(BigDecimal price) {
        DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.GERMANY);
        DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
        symbols.setGroupingSeparator('.');
        formatter.setDecimalFormatSymbols(symbols);

        String priceStr = formatter.format(price);

        return "€ ".concat(priceStr).concat(",-");
    }

    public static String formatPercent(Long percent) {
        return String.format("%s%s", percent, "%");
    }

    public static String formatMileage(int mileage) {
        return String.format("%s KM", mileage);
    }
}
