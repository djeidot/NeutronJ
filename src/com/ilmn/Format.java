package com.ilmn;

public class Format {

    public static String center(String text, int length, boolean rightAligned) {
        int spaces = length - text.length();
        if (spaces <= 0) {
            return text.substring(0, length);
        } else {
            int spacesLeft = spaces / 2;
            int spacesRight = spaces - spacesLeft;

            if (rightAligned) {
                return spaces(spacesRight) + text + spaces(spacesLeft);
            } else {
                return spaces(spacesLeft) + text + spaces(spacesRight);
            }
        }
    }

    private static String spaces(int length) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            builder.append(" ");
        }
        return builder.toString();
    }
}
