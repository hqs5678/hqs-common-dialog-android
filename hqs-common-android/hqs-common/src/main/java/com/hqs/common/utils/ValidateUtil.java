package com.hqs.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by apple on 16/9/26.
 */

public class ValidateUtil {

    public static boolean isPhoneNumber(String phoneNumber){

        if (phoneNumber == null){
            return false;
        }
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher m = p.matcher(phoneNumber);
        return m.matches();
    }

    public static boolean isEmpty(String value){

        if (value != null){
            if (value.length() > 0){
                return false;
            }
        }
        return true;
    }

    public static boolean isUrl(String url){

        if (url != null && url.length() > 4){
            if (url.indexOf("http://") == 0 || url.indexOf("https://") == 0){
                return true;
            }
        }
        return false;
    }
        
}


