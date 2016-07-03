package com.fans.becomebeaut.common.util;

import java.text.NumberFormat;

/**
 * Created by lu on 2016/7/3.
 */
public class Utils extends com.zitech.framework.utils.Utils {

    public static String formarttDistance(int distance) {
        if (distance < 100) {
            return "<100m";
        } else if (distance < 1000) {
            return distance + "m";
        } else {
            float value = distance / 1000f;
            return String.format("%.2f", value) + "km";

        }
    }
}
