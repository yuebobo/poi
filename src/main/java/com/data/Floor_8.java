package com.data;

import com.util.Util;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import java.util.HashMap;
import java.util.Map;

/**
 * 楼层信息
 */
public class Floor_8 {

    //阻尼器排列方式
    public static Integer DAMPER_ARRANGEMENT;
    //楼层总层数
    public static Integer FLOORS;

    //楼层高度 从底层到高层
    public static Integer[] FLOOR_HEIGHT;

    //阻尼器数量 k -> 楼层 ， v -> [x数量，y数量]
    public static Map<Integer,Integer[]> DAMPER_COUNT;


    /**
     * 楼层信息初始化
     * @param sheet
     */
   public static void initFloorInfo(XSSFSheet sheet){
       XSSFCell cell = sheet.getRow(0).getCell(7);
       DAMPER_ARRANGEMENT = Util.getIntValueFromXssCell(cell);

       cell = sheet.getRow(1).getCell(7);
       FLOORS = Util.getIntValueFromXssCell(cell);

       DAMPER_COUNT = new HashMap<>();
       FLOOR_HEIGHT = new Integer[FLOORS];
       for (int i = 2; i < FLOORS + 2; i++) {
           XSSFRow row = sheet.getRow(i);
           cell = row.getCell(3);
           FLOOR_HEIGHT[i - 2] = Util.getIntValueFromXssCell(cell);

           XSSFCell cell_x = row.getCell(1);
           XSSFCell cell_y = row.getCell(2);
           Integer x = Util.getIntValueFromXssCell(cell_x);
           Integer y = Util.getIntValueFromXssCell(cell_y);
           if (x == 0 && y == 0){
               continue;
           }
           DAMPER_COUNT.put(i - 1, new Integer[]{x, y});
       }

       Util.printInfo("楼层信息");
       System.out.println("阻尼器排列方式:"+DAMPER_ARRANGEMENT);
       System.out.println("楼层总数:"+FLOORS);
       System.out.print("楼层高度:");
       Util.printArray(FLOOR_HEIGHT);
       System.out.print("阻尼器数量X向:");
       DAMPER_COUNT.forEach((k,v)-> System.out.print(v[0]+","));
       System.out.println();
       System.out.print("阻尼器数量Y向:");
       DAMPER_COUNT.forEach((k,v)-> System.out.print(v[1]+","));
       System.out.println();
   }

}
