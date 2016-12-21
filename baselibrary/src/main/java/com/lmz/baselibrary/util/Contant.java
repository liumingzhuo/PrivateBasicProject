package com.lmz.baselibrary.util;

/**
 * 作者：LMZ on 2016/12/20 0020 17:30
 */
public class Contant {
    public static final String KEY_CODE = "k+_b}yC2Hx~:uZ/O=a9g-0{6^B|LhfwFlG@I?1MY";
    /**
     * 参数名字的加密
     *
     * @param str
     */
    public static String addMd5(String... str) {
        if (null != str) {
            int length = str.length;
            if ("".equals(str)) {
                return MD5Util.md5("Time" + KEY_CODE);
            } else {
                StringBuilder mStr = new StringBuilder();
                mStr.append("Time");
                for (int i = 0; i < length; i++) {
                    mStr.append(str[i]);
                }
                mStr.append(KEY_CODE);
                return MD5Util.md5(mStr.toString());
            }
        }
        return null;
    }
}
