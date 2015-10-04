package com.yuuki.utils;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

/**
 * @author Borja
 * @date 26/08/2015
 * @package com.yuuki.blackeye.utils
 * @project ProjectX - Emulator
 */
public class Console {
    public static final String LINE_EQ    = "======================================================";
    public static final String LINE_MINUS = "------------------------------------------------------";

    /**
     * Gets the actual date time formatted by simpleDateFormat
     * @return Formatted date-time string
     */
    public static String getDateTime() {
        return new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss]").format(GregorianCalendar.getInstance().getTime());
    }

    /**
     * Prints an array of messages in the console.
     * @param messages Array of messages to be printed
     */
    public static void out(String... messages) {
        for(String message : messages) {
            System.out.println(getDateTime() + " " + message);
        }
    }

    /**
     * Prints an array of messages in the console as 'error'
     * @param messages Array of messages to be printed
     */
    public static void error(String... messages) {
        for(String message : messages) {
            System.err.println(getDateTime() + "[ERROR] " + message);
        }
    }
}
