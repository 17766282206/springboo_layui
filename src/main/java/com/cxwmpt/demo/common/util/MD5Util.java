package com.cxwmpt.demo.common.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.DigestUtils;

import java.io.UnsupportedEncodingException;

/**
 * 2018/11/12 Create by 郭文梁
 * MD5Util
 * MD5工具类
 *
 * @author 郭文梁
 * @date 2018/11/12
 */
@Slf4j
@SuppressWarnings("all")    // 压制名命警告
public class MD5Util {
    public static final String DEFAULT_CHARSET = "UTF-8";

    /**
     * md5编码
     *
     * @param str 被编码字符串
     * @return MD5 Hex String
     */
    public static String md5(String str) {
        if (str == null) {
            return null;
        }
        try {
            return DigestUtils.md5DigestAsHex(str.getBytes(DEFAULT_CHARSET));
        } catch (UnsupportedEncodingException e) {
            log.error("Could not get md5 from string [{}], error : [{}]", str, e);
            return null;
        }
    }

    /**
     * 比对str的MD5结果与置顶MD5是否一致
     * 即 返回 [md5(str) == md5]
     *
     * @param str 字符串
     * @param md5 MD5
     * @return 是否一致
     */
    public static boolean md5Equals(String str, String md5) {
        if (str == null || md5 == null) {
            return false;
        }
        final String otherMd5 = md5(str);
        if (otherMd5 == null) {
            return false;
        }
        return md5.equalsIgnoreCase(otherMd5);
    }
}
