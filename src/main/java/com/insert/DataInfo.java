package com.insert;

import com.data.Cantilever_3;
import com.data.Floor_8;
import com.data.Girder_0;
import com.data.Pillar_2;
import com.util.Util;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

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

            //================================= 柱属性 ========================
            Pillar_2.initPillarInfo(excel.getSheetAt(2));
            //================================= 柱属性 ========================

            //================================= 悬臂墙属性 ========================
            Cantilever_3.initCantileverInfo(excel.getSheetAt(3));
            //================================= 悬臂墙属性 ========================

            //================================= 楼层参数 ========================
            Floor_8.initFloorInfo(excel.getSheetAt(8));
            //================================= 楼层参数 ========================

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

        Integer sum_x = Util.getSum(Floor_8.DAMPER_COUNT_X);
        Integer sum_y = Util.getSum(Floor_8.DAMPER_COUNT_Y);

        DRAWING_NUMBER_X = new String[sum_x];
        DRAWING_NUMBER_Y = new String[sum_y];

        int m = 0;
        for (int i = 0; i < Floor_8.DAMPER_COUNT_X.length; i++) {
            for (int j = 0; j < Floor_8.DAMPER_COUNT_X[i]; j++) {
                DRAWING_NUMBER_X[m++] = "X-" + (i + 1) + "-" + (j + 1);
            }
        }
        m = 0;
        for (int i = 0; i < Floor_8.DAMPER_COUNT_Y.length; i++) {
            for (int j = 0; j < Floor_8.DAMPER_COUNT_Y[i]; j++) {
                DRAWING_NUMBER_Y[m++] = "Y-" + (i + 1) + "-" + (j + 1);
            }
        }

        //重新排序
        if (Floor_8.DAMPER_ARRANGEMENT == 2) {
            Arrays.sort(DRAWING_NUMBER_X, Comparator.comparingInt(a -> Integer.valueOf(a.substring(a.length() - 1))));
            Arrays.sort(DRAWING_NUMBER_Y, Comparator.comparingInt(a -> Integer.valueOf(a.substring(a.length() - 1))));
        }

        Util.printInfo("图纸编号");
        Util.printArray(DRAWING_NUMBER_X);
        Util.printArray(DRAWING_NUMBER_Y);
    }


}
