package com.data;

import com.util.Util;
import org.apache.poi.xssf.usermodel.XSSFSheet;

/**
 * 其他的一些参数
 */
public class OtherData_4_10 {


    /**
     * 阻尼系数
     */
    public static Integer DAMPING_FACTOR;

    /**
     * 阻尼器指数
     */
    public static Double DAMPING_EXPONENT;

    /**
     * 质量
     */
    public static  Double QUALITY;

    /**
     * 初始化其他的数据
     * @param sheet10
     * @param sheet4
     */
    public static void  initOtherData(XSSFSheet sheet4,XSSFSheet sheet10){
        DAMPING_FACTOR = Util.getIntValueFromXssCell(sheet10.getRow(0).getCell(1));
        DAMPING_EXPONENT = Util.getDoubleValueFromXssCell(sheet10.getRow(2).getCell(1),2);
        QUALITY = Util.getDoubleValueFromXssCell(sheet4.getRow(0).getCell(0),3);

        Util.printInfo("其他的信息数据");
        System.out.println("阻尼系数："+DAMPING_FACTOR);
        System.out.println("阻尼器指数："+DAMPING_EXPONENT);
        System.out.println("质量："+QUALITY);
    }


}
