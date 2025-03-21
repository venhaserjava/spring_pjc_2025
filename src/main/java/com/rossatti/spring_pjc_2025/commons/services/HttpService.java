package com.rossatti.spring_pjc_2025.commons.services;

import java.util.Optional;

public interface HttpService {
    <T>Optional<T> getPathVariable(String name, Class<T> type);
}
