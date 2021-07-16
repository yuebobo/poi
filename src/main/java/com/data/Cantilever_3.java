package com.data;

import com.util.Util;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;

public class Cantilever_3 {

    //横截面属性 b，h
    public static Integer SECTION_B;
    public static Integer SECTION_H;

    //受力状况 v ，m
    public static Integer STRESS_CONDITION_V0;
    public static Integer STRESS_CONDITION_V1;
    public static Integer STRESS_CONDITION_M;

    //楼层高度
    public static Integer FLOOR_H;

    //阻尼器高度
    public static Double DAMPER_H;

    //混泥土等级
    public static String CONCRETE_GRADE;

    //钢筋等级
    public static String STEEL_GRADE;

    //箍筋参数
    public static Integer HOOP_D;
    public static Integer HOOP_L;

    //纵筋参数
    public static Integer HOOP_VERTICl_D;
    public static Integer HOOP_VERTICL_COUNT;

    //希腊字母 阿尔法
    public static Double PARAM_afS;


    /**
     * 初始化 柱属性
     *
     * @param sheet
     */
    public static void initCantileverInfo(XSSFSheet sheet) {
        XSSFRow row = sheet.getRow(0);

        SECTION_B = Util.getIntValueFromXssCell(row.getCell(1));
        SECTION_H = Util.getIntValueFromXssCell(row.getCell(4));

        row = sheet.getRow(2);
        STRESS_CONDITION_V0 = Util.getIntValueFromXssCell(row.getCell(1));
        STRESS_CONDITION_V1 = Util.getIntValueFromXssCell(row.getCell(4));
        STRESS_CONDITION_M = Util.getIntValueFromXssCell(row.getCell(7));

        row = sheet.getRow(4);
        FLOOR_H = Util.getIntValueFromXssCell(row.getCell(1));
        DAMPER_H = Util.getDoubleValueFromXssCell(row.getCell(4), 2);


        row = sheet.getRow(6);
        CONCRETE_GRADE = Util.getValueFromXssfcell(row.getCell(1));

        row = sheet.getRow(8);
        STEEL_GRADE = Util.getValueFromXssfcell(row.getCell(1));

        row = sheet.getRow(10);
        HOOP_D = Util.getIntValueFromXssCell(row.getCell(2));
        HOOP_L = Util.getIntValueFromXssCell(row.getCell(5));

        row = sheet.getRow(12);
        HOOP_VERTICl_D = Util.getIntValueFromXssCell(row.getCell(2));
        HOOP_VERTICL_COUNT = Util.getIntValueFromXssCell(row.getCell(5));

        row = sheet.getRow(14);
        PARAM_afS = Util.getDoubleValueFromXssCell(row.getCell(5), 2);

        Util.printInfo("悬臂墙属性");
        System.out.println("截面属性：b=" + SECTION_B + ",h=" + SECTION_H);
        System.out.println("受力情况：V0=" + STRESS_CONDITION_V0 + ",V1=" + STRESS_CONDITION_V1 + ",M=" + STRESS_CONDITION_M);
        System.out.println("楼层高度：" + FLOOR_H);
        System.out.println("阻尼器高度：" + DAMPER_H);
        System.out.println("混泥土等级：" + CONCRETE_GRADE);
        System.out.println("钢筋等级：" + STEEL_GRADE);
        System.out.println("箍筋参数：直径=" + HOOP_D + ",间距=" + HOOP_L);
        System.out.println("纵筋参数：直径=" + HOOP_VERTICl_D + ",根数=" + HOOP_VERTICL_COUNT);
        System.out.println("阿尔法：αs=" + PARAM_afS);
    }
}
