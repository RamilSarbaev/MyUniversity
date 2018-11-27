package com.example.ramil.myuniversity.utils;

import org.threeten.bp.format.DateTimeFormatter;
import org.threeten.bp.temporal.TemporalAccessor;

import java.util.Locale;

public class DateUtil {

    public static String getStandardDate(String sourceDate) {
        DateTimeFormatter formatterIn = DateTimeFormatter.RFC_1123_DATE_TIME;
        TemporalAccessor temp = formatterIn.parse(sourceDate);

        DateTimeFormatter formatterOut = DateTimeFormatter
                .ofPattern("dd MMMM y HH:mm", new Locale("ru"));

        return formatterOut.format(temp);
    }
}
