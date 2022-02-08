package com.test.medicarehealth.util;

public class Utils {

    public static int getOtp(){
        int randomPIN = (int)(Math.random()*9000)+1000;
        return randomPIN;
    }
}
