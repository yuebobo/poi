package com.data;

import com.util.Util;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;


public class Girder_0 {

    //横截面属性 b，h
    public static Integer SECTION_B;
    public static Integer SECTION_H;

    //受力状况 v ，m
    public static Integer STRESS_CONDITION_V;
    public static Integer STRESS_CONDITION_M;

    //混泥土等级
    public static String CONCRETE_GRADE;

    //钢筋等级
    public static String STEEL_GRADE;

    //箍筋参数
    public static Integer HOOP_D;
    public static Integer HOOP_L;

    //希腊字母 阿尔法
    public static Double PARAM_af1;
    public static Double PARAM_afS;

    /**
     * 初始化 梁属性
     *
     * @param sheet
     */
    public static void initGirderInfo(XSSFSheet sheet) {
        XSSFRow row = sheet.getRow(0);

        SECTION_B = Util.getIntValueFromXssCell(row.getCell(1));
        SECTION_H = Util.getIntValueFromXssCell(row.getCell(4));

        row = sheet.getRow(2);
        STRESS_CONDITION_V = Util.getIntValueFromXssCell(row.getCell(1));
        STRESS_CONDITION_M = Util.getIntValueFromXssCell(row.getCell(4));

        row = sheet.getRow(4);
        CONCRETE_GRADE = Util.getValueFromXssfcell(row.getCell(1));

        row = sheet.getRow(6);
        STEEL_GRADE = Util.getValueFromXssfcell(row.getCell(1));

        row = sheet.getRow(8);
        HOOP_D = Util.getIntValueFromXssCell(row.getCell(2));
        HOOP_L = Util.getIntValueFromXssCell(row.getCell(5));

        row = sheet.getRow(10);
        PARAM_af1 = Util.getDoubleValueFromXssCell(row.getCell(1), 2);
        PARAM_afS = Util.getDoubleValueFromXssCell(row.getCell(5), 5);

        Util.printInfo("梁属性");
        System.out.println("截面属性：b=" + SECTION_B + ",h=" + SECTION_H);
        System.out.println("受力情况：V=" + STRESS_CONDITION_V + ",M=" + STRESS_CONDITION_M);
        System.out.println("混泥土等级：" + CONCRETE_GRADE);
        System.out.println("钢筋等级：" + STEEL_GRADE);
        System.out.println("箍筋参数：直径=" + HOOP_D + ",间距=" + HOOP_L);
        System.out.println("阿尔法：α1=" + PARAM_af1 + ",αs=" + PARAM_afS);
    }
}
