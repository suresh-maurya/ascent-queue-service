package com.ascent.message.util;

public class QueueUtil {

    public static boolean checkNullAndEmpty(String data) {
        if(data == null) {
            return true;
        }else if(data.isEmpty()) {
            return true;
        }
        return false;
    }
}
