package com.rossatti.spring_pjc_2025.commons.utils;

public class StringUtils {
    
    public static String extractLastPart(String fullpath) {
        if (fullpath == null || fullpath.isEmpty()) {
            return "";
        }
        String[] parts = fullpath.split("\\.");
        return parts[parts.length - 1];
    }

}
