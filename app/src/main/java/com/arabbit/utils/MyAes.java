package com.arabbit.utils;


public class MyAes {
    //定义统一的密钥
//    public static String sKey = Config.AES_KEY;
//    public static String sIv = Config.AES_IV;
//
//    //加密
//    public static String Encrypt(String sSrc) throws Exception {
//        byte[] raw = sKey.getBytes();
//        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
//        Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding"); //"算法/模式/补码方式"
//        IvParameterSpec iv = new IvParameterSpec(sIv.getBytes());//使用CBC模式，需要一个向量iv，可增加加密算法的强度
//        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
//
//        byte[] srawt = sSrc.getBytes();
//        int len = srawt.length;
//        /* 计算补0后的长度 */
//        while (len % 16 != 0) len++;
//        byte[] sraw = new byte[len];
//        /* 在最后补0 */
//        for (int i = 0; i < len; ++i) {
//            if (i < srawt.length) {
//                sraw[i] = srawt[i];
//            } else {
//                sraw[i] = 0;
//            }
//        }
//        byte[] encrypted = cipher.doFinal(sraw);
//        byte[] b = Base64.encodeBase64(encrypted);//此处使用BASE64做转码功
//        String str1 = new String(b);
//        return str1;
//    }
//
//    // 解密
//    public static String Decrypt(String sSrc) throws Exception {
//        try {
//            byte[] raw = sKey.getBytes("ASCII");
//            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
//            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
//            IvParameterSpec iv = new IvParameterSpec(sKey.getBytes());
//            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
//            byte[] encrypted1 = Base64.decodeBase64(sSrc.getBytes());
//            try {
//                byte[] original = cipher.doFinal(encrypted1);
//                String originalString = new String(original);
//                return originalString.trim();
//            } catch (Exception e) {
//                return null;
//            }
//        } catch (Exception ex) {
//            return null;
//        }
//    }
//

}
