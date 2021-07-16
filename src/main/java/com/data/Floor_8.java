package com.data;

import com.util.Util;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;

public class Floor_8 {

    //阻尼器排列方式
    public static Integer DAMPER_ARRANGEMENT;
    //楼层总层数
    public static Integer FLOORS;
    //拥有阻尼器的楼层数
    public static  Integer DAMPER_FLOOR_COUNT;

    //楼层高度 从底层到高层
    public static Integer[] FLOOR_HEIGHT;
    //阻尼器数量 X 方向
    public static Integer[] DAMPER_COUNT_X;
    //阻尼器数量 Y方向
    public static Integer[] DAMPER_COUNT_Y;

    /**
     * 楼层信息初始化
     * @param sheet
     */
   public static void initFloorInfo(XSSFSheet sheet){
       XSSFCell cell = sheet.getRow(0).getCell(7);
       DAMPER_ARRANGEMENT = Util.getIntValueFromXssCell(cell);

       cell = sheet.getRow(1).getCell(7);
       FLOORS = Util.getIntValueFromXssCell(cell);

       cell = sheet.getRow(2).getCell(7);
       DAMPER_FLOOR_COUNT = Util.getIntValueFromXssCell(cell);

       FLOOR_HEIGHT = new Integer[FLOORS];
       for (int i = 2; i < FLOORS + 2; i++) {
           cell = sheet.getRow(i).getCell(3);
           FLOOR_HEIGHT[i - 2] = Util.getIntValueFromXssCell(cell);
       }


       DAMPER_COUNT_X = new Integer[DAMPER_FLOOR_COUNT];
       DAMPER_COUNT_Y = new Integer[DAMPER_FLOOR_COUNT];
       for (int i = 2; i < DAMPER_FLOOR_COUNT + 2; i++) {
           XSSFRow row = sheet.getRow(i);
           XSSFCell cell_x = row.getCell(1);
           XSSFCell cell_y = row.getCell(2);
           DAMPER_COUNT_X[i - 2] = Util.getIntValueFromXssCell(cell_x);
           DAMPER_COUNT_Y[i - 2] = Util.getIntValueFromXssCell(cell_y);
       }

       Util.printInfo("楼层信息");
       System.out.println("阻尼器排列方式:"+DAMPER_ARRANGEMENT);
       System.out.println("楼层总数:"+FLOORS);
       System.out.println("拥有阻尼器的楼层数:"+DAMPER_FLOOR_COUNT);
       System.out.print("楼层高度:");
       Util.printArray(FLOOR_HEIGHT);
       System.out.print("阻尼器数量X向:");
       Util.printArray(DAMPER_COUNT_X);
       System.out.print("阻尼器数量Y向:");
       Util.printArray(DAMPER_COUNT_Y);
   }

}
