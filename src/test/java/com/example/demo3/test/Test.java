package com.example.demo3.test;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.concurrent.*;

/**
 * @Version 1.0
 * @Author:zengtao
 * @Date:2021-4-19
 * @Content:
 */
public class Test {

    @org.junit.jupiter.api.Test
    public void test(){
        String title = "";
        String arrs[] = title.split(",");
        int num=0;
        for (String p:arrs){
            if(p.equals("15013645789")){
                num++;
            }
        }
        System.out.println(arrs.length);
    }

    @org.junit.jupiter.api.Test
    public void test04(){
        String phones = "13925251127,\n" +
                "13925251537,\n" +
                "13925251540,\n" +
                "13925251542,\n" +
                "13632952649,\n" +
                "13925251548,\n" +
                "13925270976,\n" +
                "13925263809,\n" +
                "13925251570,\n" +
                "13925251587,\n" +
                "13925251590,\n" +
                "13925251591,\n" +
                "13925251595,\n" +
                "13925251596,\n" +
                "13925251754,\n" +
                "13925251605,\n" +
                "13925251608,\n" +
                "13925251626,\n" +
                "13925251615,\n" +
                "13925263895,\n" +
                "13925251637,\n" +
                "13925251636,\n" +
                "13925251630,\n" +
                "13925251206,\n" +
                "13632961143,\n" +
                "13925251642,\n" +
                "13925251646,\n" +
                "13925251649,\n" +
                "13925251651,\n" +
                "13925251654,\n" +
                "13925251667,\n" +
                "13925270984,\n" +
                "13925251664,\n" +
                "13925251656,\n" +
                "13925251684,\n" +
                "13925251679,\n" +
                "13925251676,\n" +
                "13925270982,\n" +
                "13925251687,\n" +
                "13925251691,\n" +
                "13925251692,\n" +
                "13925251693,\n" +
                "13925251697,\n" +
                "13925251702,\n" +
                "13925251703,\n" +
                "13925251639,\n" +
                "13925251714,\n" +
                "13925250213,\n" +
                "13925251746,\n" +
                "13925251734,\n" +
                "13925251735,\n" +
                "13925251739,\n" +
                "13925251742,\n" +
                "13925251740,\n" +
                "13925251744,\n" +
                "13925251745,\n" +
                "13925251732,\n" +
                "13925251748,\n" +
                "13925251750,\n" +
                "13925251753,\n" +
                "13925251779,\n" +
                "13925251762,\n" +
                "13925251760,\n" +
                "13925251769,\n" +
                "13925251772,\n" +
                "13925251774,\n" +
                "13925251776,\n" +
                "13925251627,\n" +
                "13925251780,\n" +
                "13925251786,\n" +
                "13925251792,\n" +
                "13925251798,\n" +
                "13925251801,\n" +
                "13925251803,\n" +
                "13925270897,\n" +
                "13925251809,\n" +
                "13925251812,\n" +
                "13925251824,\n" +
                "13925251821,\n" +
                "13925251814,\n" +
                "13925251831,\n" +
                "13925263905,\n" +
                "13925251827,\n" +
                "13925251832,\n" +
                "13925251834,\n" +
                "13632966471,\n" +
                "13925251252,\n" +
                "13925251854,\n" +
                "13925251857,\n" +
                "13925251871,\n" +
                "13925251879,\n" +
                "13544267608,\n" +
                "13925251904,\n" +
                "13925251907,\n" +
                "13925251924,\n" +
                "13925251940,\n" +
                "13925251942,\n" +
                "13925251950,\n" +
                "13925251954,\n" +
                "13925268439,\n" +
                "13925251970,\n" +
                "13925251971,\n" +
                "13925252179,\n" +
                "13925251973,\n" +
                "13925252048,\n" +
                "13925252053,\n" +
                "13925252064,\n" +
                "13925252120,\n" +
                "13925252124,\n" +
                "13925263848,\n" +
                "13925252142,\n" +
                "13925252147,\n" +
                "13925252149,\n" +
                "13925252150,\n" +
                "13925252154,\n" +
                "13925252156,\n" +
                "13925252163,\n" +
                "13925252172,\n" +
                "13925252175,\n" +
                "13925251603,\n" +
                "13925250965,\n" +
                "13925252190,\n" +
                "13925252247,\n" +
                "13925252194,\n" +
                "13925270954,\n" +
                "13925252219,\n" +
                "13925270950,\n" +
                "13925252234,\n" +
                "13925252243,\n" +
                "13925252245,\n" +
                "13925268453,\n" +
                "13925252249,\n" +
                "13925251706,\n" +
                "13925270995,\n" +
                "13925270964,\n" +
                "13925252294,\n" +
                "13925252306,\n" +
                "13925252307,\n" +
                "13925252317,\n" +
                "13925252319,\n" +
                "13925252320,\n" +
                "13925252327,\n" +
                "13925252334,\n" +
                "13590159648,\n" +
                "13925252340,\n" +
                "13925252351,\n" +
                "13925252358,\n" +
                "13925252362,\n" +
                "13688810064,\n" +
                "13925253740,\n" +
                "13925252376,\n" +
                "13925253644,\n" +
                "13925253694,\n" +
                "13688810724,\n" +
                "13925253734,\n" +
                "13925253744,\n" +
                "13925253745,\n" +
                "13925253764,\n" +
                "13925253784,\n" +
                "13925253874,\n" +
                "13925253914,\n" +
                "13925253948,\n" +
                "13925263767,\n" +
                "13925270992,\n" +
                "13925263769,\n" +
                "13925263770,\n" +
                "13925263778,\n" +
                "13925263772,\n" +
                "13925263781,\n" +
                "13925263793,\n" +
                "13925263795,\n" +
                "13925251164,\n" +
                "13688810014,\n" +
                "13925263810,\n" +
                "13925263812,\n" +
                "13925263819,\n" +
                "13925263820,\n" +
                "13925263831,\n" +
                "13925263840,\n" +
                "13925251182,\n" +
                "13925263890,\n" +
                "13925263850,\n" +
                "13925263862,\n" +
                "13925263865,\n" +
                "13925263872,\n" +
                "13925263873,\n" +
                "13925263875,\n" +
                "13925263879,\n" +
                "13925263884,\n" +
                "13925263885,\n" +
                "13925263887,\n" +
                "13925263849,\n" +
                "13554917608,\n" +
                "13925263893,\n" +
                "13688810694,\n" +
                "13925263897,\n" +
                "13925263903,\n" +
                "13925263768,\n" +
                "13925263919,\n" +
                "13925263920,\n" +
                "13925263929,\n" +
                "13925263924,\n" +
                "13925263931,\n" +
                "13925263932,\n" +
                "13925263940,\n" +
                "13925263944,\n" +
                "13925263950,\n" +
                "13925263956,\n" +
                "13925263967,\n" +
                "13925263973,\n" +
                "13925251536,\n" +
                "13925270903,\n" +
                "13925263983,\n" +
                "13925263984,\n" +
                "13925263976,\n" +
                "13925263985,\n" +
                "13925263997,\n" +
                "13925268007,\n" +
                "13925268016,\n" +
                "13925268017,\n" +
                "13925268026,\n" +
                "13925268021,\n" +
                "13925268034,\n" +
                "13632963945,\n" +
                "13925268039,\n" +
                "13925268040,\n" +
                "13925268042,\n" +
                "13925268048,\n" +
                "13925268049,\n" +
                "13925268053,\n" +
                "13925268054,\n" +
                "13925268059,\n" +
                "13925268060,\n" +
                "13925268070,\n" +
                "13925268076,\n" +
                "13925268418,\n" +
                "13925268419,\n" +
                "13925268428,\n" +
                "13925251964,\n" +
                "13925268440,\n" +
                "13925268449,\n" +
                "13688810704,\n" +
                "13925268456,\n" +
                "13925251235,\n" +
                "13925268460,\n" +
                "13925268459,\n" +
                "13925268481,\n" +
                "13925270871,\n" +
                "13925270872,\n" +
                "13925270874,\n" +
                "18814351249,\n" +
                "13925270879,\n" +
                "13925270884,\n" +
                "13925270885,\n" +
                "13925270893,\n" +
                "13925270894,\n" +
                "13925251245,\n" +
                "13925263982,\n" +
                "13925270905,\n" +
                "13925270908,\n" +
                "13925270913,\n" +
                "13925270917,\n" +
                "13925270923,\n" +
                "13925270924,\n" +
                "13925270928,\n" +
                "13925270929,\n" +
                "13925270934,\n" +
                "13925270936,\n" +
                "13925270941,\n" +
                "13925270942,\n" +
                "13925270943,\n" +
                "13925270944,\n" +
                "13925270945,\n" +
                "13925270946,\n" +
                "13632885548,\n" +
                "13925251828,\n" +
                "15989899719";
        phones = phones.replaceAll(" ", "").replaceAll("\n", "");
        String[] phoneArrs = phones.split(",");
        System.out.println(Arrays.toString(phoneArrs).replaceAll("\\[","").replaceAll("\\]","").replaceAll(" ", ""));
    }


    @Autowired
    private TestService testService;


    public void threadTest(){
        /*PausableThreadPoolExecutor poolExecutor =
                new PausableThreadPoolExecutor(10, 10, 1000L,
                        TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(10),
                        Executors.defaultThreadFactory(), new ThreadPoolExecutor.CallerRunsPolicy());*/
        PausableThreadPoolExecutor poolExecutor = new PausableThreadPoolExecutor(10, 10, 1000L,
                TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(10),
                Executors.defaultThreadFactory(), new ThreadPoolExecutor.CallerRunsPolicy());
        for (int i = 0; i < 50; i++) {
            final int b = i;
            poolExecutor.execute(() -> {

            });
        }
    }

    @org.junit.jupiter.api.Test
    void test02(){
        for(int i=0;i<10;i++){
            for (int j=0;j<10;j++){
                if(j==5||j==8){
                    continue;
                }
                //System.out.println("j:" + j);
            }
            System.out.println("i:" + i);
        }
    }

    @org.junit.jupiter.api.Test
    void test01(){
        String[] arr = {"0","1","2","3","4","5","6","7","8","9","10","11","12"};
        Object[] objects = splitAry(arr, 4);
        for (Object object:objects){
            String[] arrs = (String[]) object;
            System.out.println(Arrays.toString(arrs).replaceAll("\\[","").replaceAll("\\]",""));
        }
    }

    public static Object[] splitAry(String[] ary, int subSize) {
//用数组的长度除以每个数据的内容数 能除尽就是商是数组个数，除不尽就是商+1个
        int count = ary.length % subSize == 0 ? ary.length / subSize : ary.length / subSize + 1;
//建立一个list里面的object是list。将原来的大数组分成小的装在list里面
        List<List<String>> subAryList = new ArrayList<List<String>>();
//按照获取的数组数循环
        for (int i = 0; i < count; i++) {
//定位每组循环的最大下标
            int index = i * subSize;
            List<String> list = new ArrayList<String>();
            int j = 0;
//j小于这一组的最大下标 而且小于整个大数组的长度  将这个符合条件的元素加入list.
            while (j < subSize && index < ary.length) {
                list.add(ary[index++]);
                j++;
            }
//然后再将list加入到大的list里面
            subAryList.add(list);
        }
//新建一个对象数组
        Object[] subAry = new Object[subAryList.size()];
//循环遍历大List
        for (int i = 0; i < subAryList.size(); i++) {
            List<String> subList = subAryList.get(i);
            String[] subAryItem = new String[subList.size()];
//将小list里面的值遍历添加到小数组里面
            for (int j = 0; j < subList.size(); j++) {
                subAryItem[j] = subList.get(j);
            }
//将小数组添加到大数组里
            subAry[i] = subAryItem;
        }

        return subAry;
    }




    @org.junit.jupiter.api.Test
    void test03(){
        ThreadPoolExecutor fixedThreadPool = new ThreadPoolExecutor(10, 10,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(1000));

        System.out.println("执行次数：" + 10000);
        for (int i=0;i<10000;i++) {
            Runnable miss = new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName());
                    System.out.println("执行你的方法");
                }
            };
            if(fixedThreadPool.getActiveCount()<10){
                fixedThreadPool.execute(miss);
            }
        }

    }
    public Boolean doYouMethod() {
        System.out.println(Thread.currentThread().getName());
        System.out.println("执行你的方法");
        return true;
    }


}
