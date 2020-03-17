package utils;


import org.testng.annotations.Test;

import java.util.Random;

/**
 * Created by zhangyueqiu on 16/9/14.
 */
public class RandomDataPortal {

    private Random ran = new Random();


    @Test
    public static String getRandomSerialNo() {

        Long randomSerialNo = Math.round(Math.random() * 99999999);

        String ran = randomSerialNo.toString();
//        System.out.println(ran);
        return "JD13010896826" + ran;
//        System.out.println("ran="+"jisujie13850108968261705231"+ran);
    }

    @Test
    public static String getRandomNineNo() {

        Long randomSerialNo = Math.round(Math.random() * 999999999);

        String ran = randomSerialNo.toString();
        System.out.println(ran);
        return ran;
    }

    @Test
    public static String getBizNo() {

        Long randomSerialNo = Math.round(Math.random() * 999999999);

        String ran = randomSerialNo.toString();
        System.out.println(ran);
        String num = "35231" + ran;
        return num;
//        System.out.println("ran="+num);
    }

    public String getRandom5No() {

        int random5No = this.ran.nextInt(99999);

//        System.out.println(random5No);
        return random5No + "";
    }

    public String randomPhoneUsual() {
        int a = this.ran.nextInt(899999999) + 100000000;
        int b = this.ran.nextInt(89999999) + 10000000;
        String p13 = "13" + a;
        String p15 = "15" + a;
        String p18 = "18" + a;
        String p176 = "176" + b;
        String p177 = "177" + b;
        String p178 = "178" + b;
        String[] str = {p13, p15, p18, p176, p177, p178};
        int random = (int) (Math.random() * 6);
        System.out.println(str[random]);
        return str[random];
    }

    public String randomPhone() {
        int a = this.ran.nextInt(899999999) + 100000000;
        System.out.println("13" + a);
        return "13" + a;

    }

    public static String randomWord1() {
        String[] str = {"欧阳", "司徒", "元", "袁", "沙", "祁", "高", "刘", "古", "杨", "张", "徐", "于", "王", "陈", "谷", "黄", "胡", "祁", "刘", "陆", "赵", "巍", "秦", "宋", "樊", "袁", "倪", "李", "慕容", "越", "莫", "祖", "周", "赖", "苏", "亭", "郑", "章", "化"};
        int random = (int) (Math.random() * 30);
        // System.out.println (str[random]);
        return str[random];
    }

    public static String randomWord2() {
        String[] str = {"泰德", "文豪", "小小", "小柯", "梁平", "籍", "康达", "余量", "云杰", "东方", "栋梁", "期望", "大力", "可可", "美美", "巧虎", "乐迪", "多多", "古丽", "凯", "鹏", "言", "秀", "修", "余", "青", "哈伦", "嘎", "名", "续", "旭", "顾", "甜甜", "青青", "余伦", "美亚", "田名", "思慕", "木清", "米月", "莫言", "小徐", "再旭", "古丽", "顾里", "京津", "景阳", "蒂亚", "田", "虞城", "雨辰", "禹城", "雨沫", "摩卡", "墨客", "金晶", "鑫鑫", "欣欣", "许行"};
        int random = (int) (Math.random() * 40);
        //  System.out.println (str[random]);
        return str[random];
    }

    @Test
    public static String randomName() {
        String name = randomWord1() + randomWord2();
        return name;
    }
}
