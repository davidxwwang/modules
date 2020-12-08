package com.david.module.util.vendors;

public class StringUtil {

    public static final String isBlankRegern = "^\\s*$";


    /**
     * 是否为null 或者空
     *
     * @param string
     * @return
     */
    public boolean isNullOrEmpty(String string) {
        return (string == null || string.length() == 0);
    }

    /**
     * 是否为null 或者空白字符串
     *
     * @param string
     * @return
     */
    public boolean isNullOrBlank(String string) {
        boolean isNullOrEmpty = isNullOrEmpty(string);
        boolean isNullOrEmpty2 = isNullOrEmpty(string.trim());
        return isNullOrEmpty || isNullOrEmpty2;
    }
}
