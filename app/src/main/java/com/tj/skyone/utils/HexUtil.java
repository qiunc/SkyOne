package com.tj.skyone.utils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @describe
 * @anthor wdq
 * @time 2020/6/8 08:27
 * @email wudq@infore.com
 */
public class HexUtil {

    private static final char[] DIGITS_LOWER = {'0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    private static final char[] DIGITS_UPPER = {'0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    public static char[] encodeHex(byte[] data) {
        return encodeHex(data, true);
    }

    public static char[] encodeHex(byte[] data, boolean toLowerCase) {
        return encodeHex(data, toLowerCase ? DIGITS_LOWER : DIGITS_UPPER);
    }

    protected static char[] encodeHex(byte[] data, char[] toDigits) {
        if (data == null)
            return null;
        int l = data.length;
        char[] out = new char[l << 1];
        for (int i = 0, j = 0; i < l; i++) {
            out[j++] = toDigits[(0xF0 & data[i]) >>> 4];
            out[j++] = toDigits[0x0F & data[i]];
        }
        return out;
    }


    public static String encodeHexStr(byte[] data) {
        return encodeHexStr(data, true);
    }

    public static String encodeHexStr(byte[] data, boolean toLowerCase) {
        return encodeHexStr(data, toLowerCase ? DIGITS_LOWER : DIGITS_UPPER);
    }


    protected static String encodeHexStr(byte[] data, char[] toDigits) {
        return new String(encodeHex(data, toDigits));
    }

    public static String formatHexString(byte[] data) {
        return formatHexString(data, false);
    }

    public static String formatHexString(byte[] data, boolean addSpace) {
        if (data == null || data.length < 1)
            return null;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < data.length; i++) {
            String hex = Integer.toHexString(data[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex);
            if (addSpace)
                sb.append(" ");
        }
        return sb.toString().trim();
    }

    public static byte[] decodeHex(char[] data) {

        int len = data.length;

        if ((len & 0x01) != 0) {
            throw new RuntimeException("Odd number of characters.");
        }

        byte[] out = new byte[len >> 1];

        // two characters form the hex value.
        for (int i = 0, j = 0; j < len; i++) {
            int f = toDigit(data[j], j) << 4;
            j++;
            f = f | toDigit(data[j], j);
            j++;
            out[i] = (byte) (f & 0xFF);
        }

        return out;
    }


    protected static int toDigit(char ch, int index) {
        int digit = Character.digit(ch, 16);
        if (digit == -1) {
            throw new RuntimeException("Illegal hexadecimal character " + ch
                    + " at index " + index);
        }
        return digit;
    }


    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    public static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    public static String extractData(byte[] data, int position) {
        return HexUtil.formatHexString(new byte[]{data[position]});
    }

    public static String byte2hex(byte[] b) {
        StringBuilder hs = new StringBuilder();
        String stmp;
        for (int i = 0; i < b.length; i++) {
            stmp = Integer.toHexString(b[i] & 0xFF).toUpperCase();
            if (stmp.length() == 1) {
                hs.append("0").append(stmp);
            } else {
                hs.append(stmp);
            }
        }
        return hs.toString();
    }

    public static int covert(String content) {

        int number = 0;

        String[] HighLetter = {"A", "B", "C", "D", "E", "F"};

        Map<String, Integer> map = new HashMap<>();

        for (int i = 0; i <= 9; i++) {
            map.put(i + "", i);
        }

        for (int j = 10; j < HighLetter.length + 10; j++) {
            map.put(HighLetter[j - 10], j);
        }

        String[] str = new String[content.length()];

        for (int i = 0; i < str.length; i++) {
            str[i] = content.substring(i, i + 1);
        }

        for (int i = 0; i < str.length; i++) {

            number += map.get(str[i]) * Math.pow(16, str.length - 1 - i);

        }

        return number;
    }

    public static Long hexNum(String values){

        return Long.parseLong(values,  16);

    }

    public static String formatNumber(String f){
        Double h;
        DecimalFormat df = new DecimalFormat("0.0");

        if (hexNum(f) == 0){

            return "0";

        }else{

            h  = Double.valueOf(hexNum(f).toString())/10;
            return df.format(h);
        }


    }


    public static List<Integer> statusList(String status){

        List<Integer> list = new ArrayList<>();
        for (int i = 0;i<status.length();i=i+2){

            list.add(Integer.valueOf(status.substring(i,i+2)));
        }

        return list;
    }

    public static int statusValue(List<Integer> list){

        int number = 0;

        for (Integer integer : list){

            if (integer == 0){

                number++;

            }

        }

        return number;
    }

}
