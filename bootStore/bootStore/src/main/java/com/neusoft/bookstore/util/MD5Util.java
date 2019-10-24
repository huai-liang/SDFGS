package com.neusoft.bookstore.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * 加密工具类
 * @Author：qiulx
 * @Date: 2019/9/22
 **/
public class MD5Util {
    /**
     * 加密方法
     * @Author：qiulx
     * @Date: 2019/9/22
     * @param str 要加加密的数据
     * @return java.lang.String
     **/
    public static String encryption(String str) {
        return DigestUtils.md5Hex(str);
    }
}
