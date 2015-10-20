package com.yuuki.utils;

/**
 * @author Yuuki
 * @date 21/05/2015
 * @package utils
 * @project S7KBetaServer
 *
 * Credits to SpaceBattles
 */
public class Conversions {

    /**
     * Return int from a given String
     * @param pValue
     * @return
     */
    public static int stringToInt(String pValue) {
        try {
            return Integer.parseInt(pValue);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Can't extract int from given string: " + pValue);
        }
    }

    /**
     * Returns boolean from given String
     * @param pValue
     * @return
     */
    public static boolean stringToBoolean(String pValue) {
        if (pValue.equalsIgnoreCase("true")) {
            return true;
        } else if (pValue.equalsIgnoreCase("false")) {
            return false;
        } else {
            throw new IllegalArgumentException("Can't extract boolean from given string: " + pValue);
        }
    }

    /**
     * Parse a string array packet to plain text
     * @param packet
     * @return
     */
    public static String parsePacket(String[] packet) {
        String returnPacket = "";
        for(String s : packet) {
            returnPacket += s + "|";
        }

        return returnPacket;
    }
}
