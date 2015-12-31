package com.resources.utils;

public class StringUtils {
    
    public static String convertString(String str){
        return str == null ? "":str;
    }
    
    public static boolean isEquals(String str1,String str2){
        if(str1 == null && str2 == null){
            return true;
        }else if(str1 != null && str2 != null){
            return str1.equals(str2);
        }
        return false;
    }
    
    public static boolean isEmpty(String input) {
        return input == null || input.trim().length() == 0;
    }

    public static String escapeHtmlEntity(String value) {
        String[] specialCharacters = {"<", ">", "&", "¢", "£", "¥", "€", "©", "®"};
        String[] entityNames = {"&lt;", "&gt;", "&amp;", "&cent;", "&pound", "&yen;", "&euro;", "&copy;", "&reg;"};
        return value == null ? null : org.apache.commons.lang3.StringUtils.replaceEach(value, specialCharacters, entityNames);
    }
}
