package com.example.demo3.test.util;

/**
 * @Version 1.0
 * @Author:zengtao
 * @Date:2021-7-7
 * @Content:
 */
public class Test {

    public static void main(String args[]) {
        String str = "(md5(random()::text),'edc44fbdad26445a9a08fae00356a528','-1','1'),";
        StringBuffer ort_str = new StringBuffer("");
        System.out.println(ort_str);
        for(int i=0;i<990;i++) {
            ort_str.append(str);
        }
        System.out.println(ort_str);
    }
}
