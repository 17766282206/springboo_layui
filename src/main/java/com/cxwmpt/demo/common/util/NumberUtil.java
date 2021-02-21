package com.cxwmpt.demo.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.*;

/**
 * 2019-3-21  17:32 Create by Administrator
 * NumberUtil
 * 数值工具类
 *
 * @author Administrator
 */
public class NumberUtil {

    /***

     * 判断 String 是否是 int<br>通过正则表达式判断

     *

     * @param input

     * @return

     */

    public static boolean isInteger(String input){

        Matcher mer = compile("^[+-]?[0-9]+$").matcher(input);

        return mer.find();

    }



    /**

     * 判断 String 是否是 double<br>通过正则表达式判断

     * @param input

     * @return

     */

    public static boolean isDouble(String input){

        Matcher mer = compile("^[+-]?[0-9.]+$").matcher(input);

        return mer.find();

    }





    /**

     * 检测字符串是否为 number 类型的数字

     * @param str

     * @return

     */

    public static boolean isNumeric(String str){

        if(str == null ){

            return false;

        }



        String strF = str.replaceAll("-", "");

        String strFormat = strF.replaceAll("\\.", "");

        if("".equals(strFormat)){

            return false;

        }



        for (int i = strFormat.length();--i>=0;){

            if (!Character.isDigit(strFormat.charAt(i))){

                return false;

            }

        }

        return true;

    }


    //获取yyyyMMdd xxxx随机单号
    public static String randomCode() {
        String sjs =  String.valueOf((int)((Math.random()*9+1)*1000));
        //获取时间戳
        String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
        return  date+sjs;
    }

    /**
     * 获取随机数
     * @return
     */
    public static String getVerifyCode() {
        String verifyCode = String.valueOf(new Random().nextInt(899999) + 100000);
        return  verifyCode;
    }
    /**
     * 获取指定位数随机数
     * @return
     */
    public static String getVerifyCode(int len) {
        int rs = (int) ((Math.random() * 9 + 1) * Math.pow(10, len - 1));
        return String.valueOf(rs);
    }


    public static void main(String[] args) {



System.out.println(NumberUtil.isInteger("56A"));

System.out.println(NumberUtil.isDouble("56.92q"));

        System.out.println(NumberUtil.isNumeric("-936.35"));
        System.out.println();



    }
}
