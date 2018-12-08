package com.example.ramil.myuniversity.utils;

public class XmlParser {

    public static String getImage(String sourceText) {
        String[] splitXml = sourceText.split("<p><img src=\"");
        String imageUrlStart = splitXml[1];
        int indexEnd = imageUrlStart.indexOf("\"");

        return imageUrlStart.substring(0, indexEnd);
    }

    public static String getText(String sourceText) {
        int indexStart = sourceText.indexOf("\n");
        int indexEnd = sourceText.indexOf("{");

        // If text has {gallery} tag in the end
        if (indexEnd > 0) {
            return sourceText.substring(indexStart, indexEnd).trim();
        }

        return sourceText.substring(indexStart).trim();
    }
}
