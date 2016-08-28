package com.example.freda.easyeatclient.Utils;

import java.text.DecimalFormat;

/**
 * Created by lq on 12/05/16.
 */

public class NumberUtils {
    public static int toInt(Object value) {
        try {
            return Integer.parseInt(value.toString());
        } catch (Exception e) {
        }
        return 0;
    }

    public static double toDouble(Object value) {
        try {
            return Double.parseDouble(value.toString());
        } catch (Exception e) {
        }
        return 0;
    }

    /**
     * format,  2 decimal places, #.##
     *
     * @param value
     * @return
     */
    public static String format(double value) {

        return format(value, "######0.00");
    }

    /**
     * @param value
     * @return
     */
    public static String format(double value, String format) {

        DecimalFormat deFormat = new DecimalFormat(format);
        return deFormat.format(value);
    }

    public static String priceFormat(double price) {
        return format(price, "######0.00") + "EURO";
    }


}
