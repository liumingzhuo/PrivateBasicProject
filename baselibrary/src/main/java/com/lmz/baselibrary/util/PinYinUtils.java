package com.lmz.baselibrary.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * Created by fan on 2016/8/9.
 */
public class PinYinUtils {

    public static String getPinYin(String text){
        char[] chars = text.toCharArray();

        StringBuilder sb = new StringBuilder();

        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();

        //取消音调
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        //大写
        format.setCaseType(HanyuPinyinCaseType.UPPERCASE);

        for ( char ch : chars ) {
            if(Character.isWhitespace(ch)){
                //如果是空格
                continue;
            }

            if(ch > 128 || ch < -127){
                try{
                    //数组是有多音字
                    String[] array = PinyinHelper.toHanyuPinyinStringArray(ch, format);
                    sb.append(array[0]);

                }catch (BadHanyuPinyinOutputFormatCombination e){
                    e.getMessage();
                }
            }else{
                //#$%^
                return "#";
            }
        }

        return sb.toString();

    }

}
