package com.data;

import com.util.Util;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import java.util.ArrayList;
import java.util.List;

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

       List<Integer> x = new ArrayList<>();
       List<Integer> y = new ArrayList<>();
       FLOOR_HEIGHT = new Integer[FLOORS];
       for (int i = 2; i < FLOORS + 2; i++) {
           XSSFRow row = sheet.getRow(i);
           XSSFCell cell_x = row.getCell(1);
           XSSFCell cell_y = row.getCell(2);
           cell = row.getCell(3);
           x.add(Util.getIntValueFromXssCell(cell_x));
           y.add(Util.getIntValueFromXssCell(cell_y));
           FLOOR_HEIGHT[i - 2] = Util.getIntValueFromXssCell(cell);
       }

       x.removeIf(c -> 0 == c);
       y.removeIf(c -> 0 == c);
       DAMPER_COUNT_X = x.toArray(new Integer[x.size()]);
       DAMPER_COUNT_Y = y.toArray(new Integer[y.size()]);

       Util.printInfo("楼层信息");
       System.out.println("阻尼器排列方式:"+DAMPER_ARRANGEMENT);
       System.out.println("楼层总数:"+FLOORS);
       System.out.print("楼层高度:");
       Util.printArray(FLOOR_HEIGHT);
       System.out.print("阻尼器数量X向:");
       Util.printArray(DAMPER_COUNT_X);
       System.out.print("阻尼器数量Y向:");
       Util.printArray(DAMPER_COUNT_Y);
   }

}
