package com.example.mahmoud.carpoolingv1.utils;

public class Validator {

    public static boolean stringNotNull(String input) {
        return !(input == null || input.equalsIgnoreCase(""));
    }
}
