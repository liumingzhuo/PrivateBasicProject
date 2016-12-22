package com.lmz.basicdemo.model;

import com.lmz.baselibrary.util.PinYinUtils;

/**
 * 作者：LMZ on 2016/12/22 0022 10:06
 * 城市选择实体类
 */
public class CitysModle implements Comparable<CitysModle> {
    public String name;
    public String pinyin;

    public CitysModle(String name) {
        this.name = name;
        this.pinyin = PinYinUtils.getPinYin(name);
    }

    @Override
    public int compareTo(CitysModle o) {
        return this.pinyin.compareTo(o.pinyin);
    }
}
