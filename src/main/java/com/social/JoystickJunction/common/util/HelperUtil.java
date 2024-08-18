package com.social.JoystickJunction.common.util;

public class HelperUtil {

    public static String toTitleCase(String givenString) {
        String[] arr = givenString.split(" ");
        StringBuilder sb = new StringBuilder();

        for (String s : arr) {
            sb.append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).append(" ");
        }

        return sb.toString();
    }
}
