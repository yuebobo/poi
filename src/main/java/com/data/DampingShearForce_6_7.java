package com.data;

import com.util.Util;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import java.util.ArrayList;
import java.util.List;

/**
 * 减震剪力
 */
public class DampingShearForce_6_7 {

    public Double vx;

    public Double vy;

    /**
     * 减震剪力集合
     */
    public static List<DampingShearForce_6_7> DAMPING ;

    /**
     * 非减震剪力
     */
    public static List<DampingShearForce_6_7> DAMPING_NOT ;

    @Override
    public String toString() {
        return "{" +
                "vx=" + vx +
                ", vy=" + vy +
                '}';
    }

    /**
     * 初始化 减震剪力信息， 和非减震剪力
     * @param sheet6
     * @param sheet7
     */
    public static void initDampingShearForce(XSSFSheet sheet6,XSSFSheet sheet7){
        DAMPING= new ArrayList<>();
        DAMPING_NOT = new ArrayList<>();

        dsf(sheet6,DAMPING_NOT);
        dsf(sheet7,DAMPING);

        Util.printInfo("减震剪力");
        DAMPING.forEach(System.out::println);

        Util.printInfo("非减震剪力");
        DAMPING_NOT.forEach(System.out::println);

    }

    private static  void dsf(XSSFSheet sheet,List<DampingShearForce_6_7> list){
        XSSFRow row;
        XSSFCell cell;
        for (int i = 2;; i++) {
             row = sheet.getRow(i);
            if (row == null){
                break;
            }
             cell = row.getCell(3);
            if (cell == null){
                break;
            }
            DampingShearForce_6_7 d = new DampingShearForce_6_7();
            d.vx = Util.getDoubleValueFromXssCell(row.getCell(3), 2);
            d.vy = Util.getDoubleValueFromXssCell(row.getCell(10), 2);
            list.add(d);
        }
    }





}
