package com.tj.skyone.utils;

import java.util.Arrays;
import java.util.List;

/**
 * @describe
 * @anthor wdq
 * @time 2021/4/7 17:53
 * @email wudq@infore.com
 */
public class CheckDatas {

    public static List<String> check(String val){
        String [] name = val.split("\\*");

        return Arrays.asList(name);

    }

}
