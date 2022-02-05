package com.test.medicarehealth.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateTimeUtil {

    public static String getDateTimeFrom(String given){
        String str = "";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        SimpleDateFormat outPut = new SimpleDateFormat("dd/MM/yyyy hh:mm aa");

        try {
            str = outPut.format(sdf.parse(given));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }
}
