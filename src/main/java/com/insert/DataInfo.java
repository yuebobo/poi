package com.insert;

import com.util.Util;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

public class DataInfo {

    /**
     * 基础路径
     */
    public static String BASE_PATH ;

    /**
     * 楼层总层数
     */
    public static int FLOORS;

    /**
     * 楼层高度
     * 从底层到高层
     */
    public static Integer[] FLOOR_HEIGHT;

    /**
     * 阻尼器排列方式
     */
    public static Integer DAMPER_ARRANGEMENT;

    /**
     * 阻尼器数量 X 方向
     */
    public static Integer[] DAMPER_COUNT_X;

    /**
     * 阻尼器数量 Y方向
     */
    public static Integer[] DAMPER_COUNT_Y;

    public static void initBaseData(String basePath) throws IOException {
        BASE_PATH = basePath;

        //初始化材料数据 excel 里的数据
        initMaterialData();


    }

    /**
     * 初始化材料数据 excel 里的数据
     * @throws IOException
     */
    private static void initMaterialData() throws IOException {
        String path = BASE_PATH + "\\excel\\材料数据.xlsx";
        FileInputStream  e = null;

        try {
            e = new FileInputStream(path);
            XSSFWorkbook excel = new XSSFWorkbook(e);

            //初始化 楼层数量，楼层高度
            initFloor(excel.getSheetAt(8));

            //初始化 阻尼器排列方式
            initDamperArrangement(excel.getSheetAt(8));

            //初始化 阻尼器数量
            initDamperCount(excel.getSheetAt(8));

        }finally {
            if (e != null) {
                try {
                    e.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }


    /**
     * 初始化楼层高度，楼层数量
     * @param sheet
     */
    private static void initFloor(XSSFSheet sheet) {

        //获取出楼层高度
        XSSFCell c = sheet.getRow(1).getCell(7);

        FLOORS = Util.getIntValueFromXssCell(c);
        Util.printInfo("楼层层数:" + FLOORS);

        //获取每层楼层的高度
        FLOOR_HEIGHT = new Integer[FLOORS];
        for (int i = 2; i < FLOORS + 2; i++) {
            XSSFRow row = sheet.getRow(i);
            XSSFCell cell = row.getCell(3);
            FLOOR_HEIGHT[i - 2] = Util.getIntValueFromXssCell(cell);
        }
        Util.printInfo("楼层层高");
        Util.printArray(FLOOR_HEIGHT);
    }

    /**
     * 初始化阻尼器的排序方式
     * @param sheet
     */
    private static void initDamperArrangement(XSSFSheet sheet){
        XSSFCell cell = sheet.getRow(0).getCell(7);
        DAMPER_ARRANGEMENT = Util.getIntValueFromXssCell(cell);
        Util.printInfo("阻尼器排列方式:"+DAMPER_ARRANGEMENT);
    }

    /**
     * 初始化阻尼器数量
     * @param sheet
     */
    private static void initDamperCount(XSSFSheet sheet){

        XSSFCell cell = sheet.getRow(2).getCell(7);
        Integer v = Util.getIntValueFromXssCell(cell);
        DAMPER_COUNT_X = new Integer[v];
        DAMPER_COUNT_Y = new Integer[v];

        for (int i = 2; i < v + 2; i++) {
            XSSFRow row = sheet.getRow(i);
            XSSFCell cell_x = row.getCell(1);
            XSSFCell cell_y = row.getCell(2);
            DAMPER_COUNT_X[i-2] = Util.getIntValueFromXssCell(cell_x);
            DAMPER_COUNT_Y[i-2] = Util.getIntValueFromXssCell(cell_y);
        }
        Util.printInfo("阻尼器数量 X-Y");
        Util.printArray(DAMPER_COUNT_X);
        Util.printArray(DAMPER_COUNT_Y);
    }

}
