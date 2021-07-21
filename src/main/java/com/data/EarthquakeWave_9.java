package com.data;

import com.util.Util;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;

/**
 * 地震波
 */
public class EarthquakeWave_9 {


    /**
     * 多遇地震采用地震加速度最大值
     */
    public static Integer COMMON_MAX;

    /**
     * 罕遇地震采用地震加速度最大值
     */
    public static Integer RARE_MAX;

    /**
     * 地震波编号
     */
    public static String[] EARTHQUAKE_WAVE_NUMBER;

    /**
     * 初始化地震波信息
     *
     * @param sheet
     */
    public static void initEarthquakeWave(XSSFSheet sheet) {

        XSSFCell cell = sheet.getRow(0).getCell(3);
        COMMON_MAX = Util.getIntValueFromXssCell(cell);

        cell = sheet.getRow(2).getCell(3);
        RARE_MAX = Util.getIntValueFromXssCell(cell);


        EARTHQUAKE_WAVE_NUMBER = new String[7];
        for (int i = 1; i < 8; i++) {
            cell = sheet.getRow(i).getCell(0);
            EARTHQUAKE_WAVE_NUMBER[i - 1] = Util.getValueFromXssfcell(cell);
            //如果获取出浮点数，则取整
            if (EARTHQUAKE_WAVE_NUMBER[i - 1].contains(".")){
                EARTHQUAKE_WAVE_NUMBER[i - 1] = EARTHQUAKE_WAVE_NUMBER[i - 1].substring(0,EARTHQUAKE_WAVE_NUMBER[i - 1].lastIndexOf("."));
            }
        }

        Util.printInfo("地震波信息");
        System.out.println("多遇地震采用地震加速度最大值:" + COMMON_MAX);
        System.out.println("罕遇地震采用地震加速度最大值:" + RARE_MAX);
        System.out.print("地震波编号:");
        Util.printArray(EARTHQUAKE_WAVE_NUMBER);
    }

}
