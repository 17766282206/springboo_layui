package com.cxwmpt.demo.common.util;

import java.util.ArrayList;
import java.util.List;

public class ArrayUtil {
    /**
     * 显示不同
     * @param t1
     * @param t2
     * @param <T>
     * @return
     */
    public static <T> List<T> compareDifferentReturns(T[] t1, T[] t2) {

        List<T> list2 = new ArrayList<T>();

        for(int i=0;i<t1.length;i++){
            boolean type=false;
            for(int j=0;j<t2.length;j++) {
                if(t1[i]==t2[j]){
                    type=true;
                }
            }
            if(!type){
                list2.add(t1[i]);
            }
        }

        return list2;
    }

    /**
     * 显示相同
     * @param t1
     * @param t2
     * @param <T>
     * @return
     */
    public static <T> List<T> compareSameReturn(T[] t1, T[] t2) {

        List<T> list2 = new ArrayList<T>();

        for(int i=0;i<t1.length;i++){

            for(int j=0;j<t2.length;j++) {
                if(t1[i]==t2[j]){
                    list2.add(t1[i]);
                    break;
                }
            }
        }
        return list2;
    }

    /**
     * 数组去重
     * @param t1
     * @param <T>
     * @return
     */
    public static <T> List<T> arrayWeighting(T[] t1) {
        List<T> result = new ArrayList<T>();
       // List<String> result = new ArrayList<>();
        boolean flag;
        for(int i=0;i<t1.length;i++){
            flag = false;
            for(int j=0;j<result.size();j++){
                if(t1[i].equals(result.get(j))){
                    flag = true;
                    break;
                }
            }
            if(!flag){
                result.add(t1[i]);
            }
        }
     //   String[] arrayResult = (String[]) result.toArray(new String[result.size()]);

        return result;
    }


}
