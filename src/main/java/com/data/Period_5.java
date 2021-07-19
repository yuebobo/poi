package com.data;

import com.util.Util;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import java.util.ArrayList;
import java.util.List;

/**
 * 周期信息
 */
public class Period_5 {

    /**
     * 振型号
     */
    public String number;

    /**
     * 周期
     */
    public Double period;

    /**
     * 转角
     */
    public Double corner;

    /**
     * 平动系数(X+Y)
     */
    public String translation_coefficient;

    /**
     * 扭转系数(Z)(强制刚性楼板模型)
     */
    public Double reverse_coefficient;

    public static List<Period_5> PERIODS;


    @Override
    public String toString() {
        return "{" +
                "number='" + number + '\'' +
                ", period=" + period +
                ", corner=" + corner +
                ", translation_coefficient='" + translation_coefficient + '\'' +
                ", reverse_coefficient=" + reverse_coefficient +
                '}';
    }

    /**
     * 周期信息初始化
     *
     * @param sheet
     */
    public static void initPeriodInfo(XSSFSheet sheet) {
        PERIODS = new ArrayList<>();
        XSSFRow row;
        for (int i = 2; ; i++) {
            row = sheet.getRow(i);
            if (row == null || row.getCell(0) == null) {
                break;
            }
            Period_5 period_5 = new Period_5();
            period_5.number = Util.getValueFromXssfcell(row.getCell(0));
            period_5.period = Util.getDoubleValueFromXssCell(row.getCell(1), 4);
            period_5.corner = Util.getDoubleValueFromXssCell(row.getCell(2), 2);
            period_5.translation_coefficient = Util.getValueFromXssfcell(row.getCell(3));
            period_5.reverse_coefficient = Util.getDoubleValueFromXssCell(row.getCell(4), 2);
            PERIODS.add(period_5);
        }

        Util.printInfo("周期");
        PERIODS.forEach(System.out::println);

    }


}
