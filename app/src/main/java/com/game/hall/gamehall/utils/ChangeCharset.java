package com.game.hall.gamehall.utils;

import java.io.UnsupportedEncodingException;

/**
 * Created by hezhiyong on 2016/9/12.
 */
public class ChangeCharset {
    /**
     * 8 位 UTF转换格式
     */
    public static final String UTF_8 = "UTF-8";
    /**
     * 中文超大字符集
     **/
    public static final String GBK = "GBK";
    public static final String Gb2312 = "Gb2312";
    /**
     * 16 位 UTF转换格式，Big Endian(最低地址存放高位字节）字节顺序
     */
    public static final String UTF_16BE = "UTF-16BE";

    /**
     * 将字符编码转换成UTF-8
     */
    public String toUTF_8(String str) throws UnsupportedEncodingException {
        return this.changeCharset(str, UTF_8);
    }

    /**
     * 将字符编码转换成UTF-16BE
     */
    public String toUTF_16BE(String str) throws UnsupportedEncodingException {
        return this.changeCharset(str, UTF_16BE);
    }

    /*
     * 字符串编码转换的实现方法
        * @param str    待转换的字符串
   * @param newCharset    目标编码
     */
    public static String changeCharset(String str, String newCharset) throws UnsupportedEncodingException {
        if (str != null) {
//用默认字符编码解码字符串。与系统相关，中文默认为GBK
            byte[] bs = str.getBytes();
            return new String(bs, newCharset);    //用新的字符编码生成字符串
        }
        return null;
    }

    /**
     * 字符串编码转换的实现方法
     *
     * @param str        待转换的字符串
     * @param oldCharset 源字符集
     * @param newCharset 目标字符集
     */
    public static String changeCharset(String str, String oldCharset, String newCharset)
            throws UnsupportedEncodingException {
        if (str != null) {
            //用源字符编码解码字符串
            byte[] bs = str.getBytes(oldCharset);
            return new String(bs, newCharset);
        }
        return null;
    }
}
