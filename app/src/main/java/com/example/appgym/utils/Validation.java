package com.example.appgym.utils;

public class Validation {

    public static boolean validateName(String name){
        if (name.trim().isEmpty()){
            return false;
        }
        return true;
    }
}
