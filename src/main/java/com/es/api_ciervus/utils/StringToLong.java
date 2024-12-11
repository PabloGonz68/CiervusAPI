package com.es.api_ciervus.utils;

public class StringToLong {
    public static Long stringToLong(String id) {
        try {
            return Long.parseLong(id);
        } catch (NumberFormatException e) {
            return null;//Cambiar cuando tenga las excepciones ;)
        }

    }
}
