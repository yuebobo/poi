package com.insert;

import com.data.*;
import com.util.Util;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;

public class DataInfo {

    /**
     * 基础路径
     */
    public static String BASE_PATH;

    //图纸编号 X 方向
    public static String[] DRAWING_NUMBER_X;
    //图纸编号 Y 方向
    public static String[] DRAWING_NUMBER_Y;


    public static void initBaseData(String basePath) throws IOException {
        BASE_PATH = basePath;

        //初始化材料数据 excel 里的数据
        initMaterialData();

        initDrawingNumber();
    }

    /**
     * 初始化材料数据 excel 里的数据
     *
     * @throws IOException
     */
    private static void initMaterialData() throws IOException {
        String path = BASE_PATH + "\\excel\\材料数据.xlsx";
        FileInputStream e = null;

        try {
            e = new FileInputStream(path);
            XSSFWorkbook excel = new XSSFWorkbook(e);

            //================================= 梁属性 ========================
            Girder_0.initGirderInfo(excel.getSheetAt(0));
            //================================= 梁属性 ========================

            //================================= 材料属性 ========================
            MaterialProperty_1.initMaterialPropertyInfo(excel.getSheetAt(1));
            //================================= 材料属性 ========================

            //================================= 柱属性 ========================
            Pillar_2.initPillarInfo(excel.getSheetAt(2));
            //================================= 柱属性 ========================

            //================================= 悬臂墙属性 ========================
            Cantilever_3.initCantileverInfo(excel.getSheetAt(3));
            //================================= 悬臂墙属性 ========================

            //================================= 周期信息 ========================
            Period_5.initPeriodInfo(excel.getSheetAt(5));
            //================================= 周期信息 ========================

            //================================= 减震剪力，非减震剪力 ========================
            DampingShearForce_6_7.initDampingShearForce(excel.getSheetAt(6),excel.getSheetAt(7));
            //================================= 减震剪力，非减震剪力 ========================

            //================================= 楼层参数 ========================
            Floor_8.initFloorInfo(excel.getSheetAt(8));
            //================================= 楼层参数 ========================

            //================================= 地震波信息 ========================
            EarthquakeWave_9.initEarthquakeWave(excel.getSheetAt(9));
            //================================= 地震波信息 ========================

            //================================= 其他的一些数据 ========================
            OtherData_4_10.initOtherData(excel.getSheetAt(4), excel.getSheetAt(10));
            //================================= 其他的一些数据 ========================


        } finally {
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
     * 生成图纸编号
     */
    private static void initDrawingNumber() {


        int size_x = Floor_8.DAMPER_COUNT.entrySet().stream().mapToInt(e -> e.getValue()[0]).sum();
        int size_y = Floor_8.DAMPER_COUNT.entrySet().stream().mapToInt(e -> e.getValue()[1]).sum();

        DRAWING_NUMBER_X = new String[size_x];
        DRAWING_NUMBER_Y = new String[size_y];

        int m = 0;
        int n = 0;
        for (Map.Entry<Integer, Integer[]> e : Floor_8.DAMPER_COUNT.entrySet()) {
            Integer x = e.getValue()[0];
            for (Integer i = 0; i < x; i++) {
                DRAWING_NUMBER_X[m++] = "X-" + (e.getKey()) + "-" + (i + 1);
            }

            Integer y = e.getValue()[1];
            for (Integer i = 0; i < y; i++) {
                DRAWING_NUMBER_Y[n++] = "Y-" + (e.getKey()) + "-" + (i + 1);
            }
        }

        //重新排序
        if (Floor_8.DAMPER_ARRANGEMENT == 1) {
            Arrays.sort(DRAWING_NUMBER_X, Comparator.comparingInt(a -> Integer.valueOf(a.substring(a.length() - 1))));
            Arrays.sort(DRAWING_NUMBER_Y, Comparator.comparingInt(a -> Integer.valueOf(a.substring(a.length() - 1))));
        }

        Util.printInfo("图纸编号");
        Util.printArray(DRAWING_NUMBER_X);
        Util.printArray(DRAWING_NUMBER_Y);
    }


}
