package com.rossatti.spring_pjc_2025.commons.routes;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiRoutes {
    
    public static final String API = "/api";
    public static final String PEOPLE = "/pessoas";    
    public static final String FIND_PEOPLES = API+PEOPLE;
    public static final String CREATE_PEOPLE = API+PEOPLE;
    public static final String UPDATE_PEOPLE = API+PEOPLE+"/{id}";
    public static final String FIND_PERSON_BY_ID = FIND_PEOPLES +"/{id}";

    public static final String UNITS = "/unidades";
    public static final String FIND_UNITS = API+UNITS;
    public static final String FIND_UNIT_BY_ID = FIND_UNITS+"/{id}";
    public static final String CREATE_UNIT = API+UNITS;
    public static final String UPDATE_UNIT = API+UNITS+"/{id}";

    public static final String CITIES = "/cidades";
    public static final String FIND_CITIES = API+CITIES;
    public static final String FIND_CITY_BY_ID = FIND_CITIES+"/{id}";
    public static final String CREATE_CITY = API+CITIES;
    public static final String UPDATE_CITY = API+CITIES+"/{id}";

    public static final String POSTING = "/lotacoes";
    public static final String FIND_POSTING = API+POSTING;
    public static final String FIND_POSTING_BY_ID = FIND_POSTING+"/{id}";
    public static final String CREATE_POSTING = API+POSTING;
    public static final String UPDATE_POSTING = API+POSTING+"/{id}";

    public static final String ADDRESSES = "/enderecos";
    public static final String FIND_ADDRESSES = API+ADDRESSES;
    public static final String FIND_ADDRESS_BY_ID = FIND_ADDRESSES+"/{id}";
    public static final String CREATE_ADDRESS = API+ADDRESSES;
    public static final String UPDATE_ADDRESS = API+ADDRESSES+"/{id}";



}
