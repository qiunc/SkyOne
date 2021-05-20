package com.tj.skyone.utils;


public class HexUtils {

    private final static String hexString = "0123456789ABCDEF";

//    public static byte[] decode64(String str) {
//        byte[] bt = null;
//        try {
//            BASE64Decoder decoder = new BASE64Decoder();
//            bt = decoder.decodeBuffer(str);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return bt;
//    }

    public static byte[] getDynamicpassword(int randomNumber) {
        byte[] dynamicpassword = new byte[4];
        byte[] randomNumberBytes = HexUtils.intTo4Bytes(randomNumber);
        dynamicpassword[0] = randomNumberBytes[0] ^= randomNumberBytes[1];
        dynamicpassword[1] = randomNumberBytes[1] ^= randomNumberBytes[2];
        dynamicpassword[2] = randomNumberBytes[2] ^= randomNumberBytes[3];
        dynamicpassword[3] = randomNumberBytes[3] ^= randomNumberBytes[0];
        return dynamicpassword;
    }

    /**
     * @param checkBytes
     * @return
     */
    public static byte getCheckCode(int start, byte[] checkBytes) {
        byte checkCode = checkBytes[start];
        for (int i = start + 1; i < checkBytes.length; i++) {
            checkCode ^= checkBytes[i];
        }
        return checkCode;
    }

    /**
     * 将int数值转换为占6个字节的byte数组，本方法适用于(高位在前，低位在后)的顺序。 和bytesToInt（）配套使用
     */
    public static byte[] intTo6Bytes(int value) {
        byte[] src = new byte[6];
        src[0] = (byte) (0);
        src[1] = (byte) (0);
        src[2] = (byte) ((value >> 24) & 0xFF);
        src[3] = (byte) ((value >> 16) & 0xFF);
        src[4] = (byte) ((value >> 8) & 0xFF);
        src[5] = (byte) (value & 0xFF);
        return src;
    }

    /**
     * 将int数值转换为占4个字节的byte数组，本方法适用于(高位在前，低位在后)的顺序。 和bytesToInt（）配套使用
     */
    public static byte[] intTo4Bytes(int value) {
        byte[] src = new byte[4];
        src[0] = (byte) ((value >> 24) & 0xFF);
        src[1] = (byte) ((value >> 16) & 0xFF);
        src[2] = (byte) ((value >> 8) & 0xFF);
        src[3] = (byte) (value & 0xFF);
        return src;
    }

    /**
     * 将int数值转换为占2个字节的byte数组，本方法适用于(高位在前，低位在后)的顺序。 和bytesToInt（）配套使用
     */
    public static byte[] intTo2Bytes(int value) {
        byte[] src = new byte[2];
        src[0] = (byte) ((value >> 8) & 0xFF);
        src[1] = (byte) (value & 0xFF);
        return src;
    }

    /**
     * 将int数值转换为占1个字节的byte数组，本方法适用于(高位在前，低位在后)的顺序。 和bytesToInt（）配套使用
     */
    public static byte[] intTo1Bytes(int value) {
        byte[] src = new byte[1];
        src[0] = (byte) (value & 0xFF);
        return src;
    }

    public static int byteArrayToInt(byte[] b) {
        int value = 0;
        for (int i = 0; i < b.length; i++) {
            value = value
                    + (int) ((b[i] & 0xFF) * Math
                    .pow(16 * 16, b.length - i - 1));
        }
        return value;
    }

    /**
     * 合并两个byte数组
     *
     * @return 结果
     */
    public static byte[] byteMerger(byte[] byte_1, byte[] byte_2) {
        byte[] byte_3 = new byte[byte_1.length + byte_2.length];
        System.arraycopy(byte_1, 0, byte_3, 0, byte_1.length);
        System.arraycopy(byte_2, 0, byte_3, byte_1.length, byte_2.length);
        return byte_3;
    }

    /* 将16进制字符串转化为bytes数组 */
    public static byte[] hexStringToByte(String hex) {
        int len = (hex.length() / 2);
        byte[] result = new byte[len];
        char[] achar = hex.toUpperCase().toCharArray();
        for (int i = 0; i < len; i++) {
            int pos = i * 2;
            result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
        }
        return result;
    }

    /* 将Short Int类型转化为16进制再转化为byte数组 */
    public static byte toByte(char c) {
        byte b = (byte) hexString.indexOf(c);
        return b;
    }

    /**
     * byte数组转换成16进制字符串
     *
     * @param src
     * @return
     */
    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }


    public static String queryStr3des(){
        String KEY_3DESDECODE = "5690dddfa28ae085d23518a035707282babd4a84418fb04015ac773f7459727ce9bc0e13a8a16cbb07b175d92a1131269554fadfa56c4e61a49d8edfdc6466e6";
        String queryStr3des = "";
        String account = "15866666666";
        String password = "cf9d0f0e051c4d2af15e7af9d7067a8c";


        try {
             queryStr3des = HexUtils.bytesToHexString(DESUtils.des3EncodeECB(KEY_3DESDECODE.getBytes(),(account+","
                    +password+","+System.currentTimeMillis()).toString().getBytes("utf-8")));
            return queryStr3des;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }



    public static Integer checkValues(String s){

        if(s.indexOf(".") > 0){

            //正则表达
           s = s.substring(0,s.indexOf("."));

        }
        return Integer.valueOf(s);

    }


}
