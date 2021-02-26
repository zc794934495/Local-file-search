package com.daimao.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class PinYinUtil {

    public static String getPinYin(String name) {
        return toHanYuPinYin(name,0);
    }

    public static String getPinYinFirst(String name) {
        return toHanYuPinYin(name,1);
    }

    /**
     * 根据传入的i来转换为汉语拼音或拼音首字母
     * @param name 需要转换的字符串
     * @param i 0代表获取拼音，1代表获取拼音首字母
     */
    private static String toHanYuPinYin(String name,Integer i) {
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setVCharType(HanyuPinyinVCharType.WITH_V);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        StringBuilder sb = new StringBuilder();
        for(char c : name.toLowerCase().toCharArray()) {
            try {
                String[] pinyinStringArray = PinyinHelper.toHanyuPinyinStringArray(c, format);
                if(pinyinStringArray == null || pinyinStringArray.length == 0) {
                    continue;
                }
                if(i == 0) {
                    sb.append(pinyinStringArray[0]);
                }else {
                    sb.append(pinyinStringArray[0].charAt(0));
                }
            } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}
