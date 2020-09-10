package com.jy.infonet.util;

import org.springframework.util.DigestUtils;

/**
 * 密码加密
 * 使用MD5算法
 */
public class PwdEncoderUtil {
    //编码
    public static String encode(String pwd) {
        return DigestUtils.md5DigestAsHex(pwd.getBytes());
    }

    //匹配
    public static boolean matches(CharSequence charSequence, String s) {
        //将密码加密后再比较
        return s.equals(DigestUtils.md5DigestAsHex(charSequence.toString().getBytes()));
    }
}
