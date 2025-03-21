package com.rossatti.spring_pjc_2025.commons.utils;

public class StringUtils {
    
    public static String extrairUltimaParte(String caminhoCompleto) {
        if (caminhoCompleto == null || caminhoCompleto.isEmpty()) {
            return "";
        }
        String[] partes = caminhoCompleto.split("\\.");
        return partes[partes.length - 1];
    }

}
