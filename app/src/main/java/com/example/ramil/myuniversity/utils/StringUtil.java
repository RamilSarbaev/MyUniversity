package com.example.ramil.myuniversity.utils;

public class StringUtil {

    public static String getAboutMe(String text) {
        if (!text.equals("")) {
            return "\"" + text + "\"";
        } else {
            return null;
        }
    }

    public static String get(String text) {
        if (!text.equals("")) {
            return text;
        } else {
            return null;
        }
    }
}
