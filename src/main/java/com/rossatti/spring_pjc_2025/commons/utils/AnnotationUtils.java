package com.rossatti.spring_pjc_2025.commons.utils;

import java.lang.reflect.Field;

import com.rossatti.spring_pjc_2025.cidade.validators.UniqueCidade;

public class AnnotationUtils {
    public static String getUniqueCidadeMessage(String className) {
        try {
            // Carregar a classe dinamicamente
            Class<?> clazz = Class.forName(className);
            // Percorrer os campos da classe
            for (Field field : clazz.getDeclaredFields()) {
                if (field.isAnnotationPresent(UniqueCidade.class)) {
                    UniqueCidade annotation = field.getAnnotation(UniqueCidade.class);
                    return annotation.message();
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return "Classe não encontrada.";
        }
        try {
            // Carregar a classe dinamicamente
            Class<?> clazz = Class.forName(className);

            // Verificar se a classe tem a anotação @UniqueCidade
            if (clazz.isAnnotationPresent(UniqueCidade.class)) {
                UniqueCidade annotation = clazz.getAnnotation(UniqueCidade.class);
                return annotation.message(); // Retorna a mensagem da anotação
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return "Classe não encontrada.";
        }
        return "Anotação @UniqueCidade não encontrada na classe.";    }

}
