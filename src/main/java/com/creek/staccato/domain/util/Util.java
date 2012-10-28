package com.creek.staccato.domain.util;

/**
 * 
 * @author Andrey Pereverzin
 *
 */
public class Util {
    public static boolean getBoolean(String value) {
        try {
            return Boolean.parseBoolean(value);
        } catch (Exception ex) {
            return false;
        }
    }
}
