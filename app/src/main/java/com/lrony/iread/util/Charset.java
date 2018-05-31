package com.lrony.iread.util;

/**
 * Created by Lrony on 18-5-31.
 */
public enum Charset {

    UTF8("UTF-8"),
    UTF16LE("UTF-16LE"),
    UTF16BE("UTF-16BE"),
    GBK("GBK");

    private String mName;
    public static final byte BLANK = 0x0a;

    private Charset(String name) {
        mName = name;
    }

    public String getName() {
        return mName;
    }
}
