package com.data;

import com.util.Util;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 材料信息
 */
public class MaterialProperty_1 {

    //混凝土等级
    public static Map<String,ConcreteGrade> CONCRETE_GRADE_MAP;

    //钢筋等级
    public static Map<String,SteelGrade> STEEL_GRADE_MAP;


    /**
     * 初始化混泥土，钢筋的材料等级表
     * @param sheet
     */
    public static void initMaterialPropertyInfo(XSSFSheet sheet){

        CONCRETE_GRADE_MAP = new HashMap<>(16);
        STEEL_GRADE_MAP = new HashMap<>(8);

        Iterator<Row> it = sheet.iterator();
        Util.iteratorNext(it,2);
        Row row;
        String key1 = null;
        for (int i = 2; ; i++) {
            row = sheet.getRow(i);
            if (row == null) {
                break;
            }
            String v1 = Util.getValueFromXssfcell(row.getCell(0));
            if (!Util.stringIsBlack(v1) && (key1 == null || v1.compareTo(key1) > 0)) {
                key1 = v1;
                ConcreteGrade concreteGrade = new ConcreteGrade();
                concreteGrade.fck = Util.getDoubleValueFromXssCell(row.getCell(1), 2);
                concreteGrade.fc = Util.getDoubleValueFromXssCell(row.getCell(2), 2);
                concreteGrade.ftk = Util.getDoubleValueFromXssCell(row.getCell(3), 2);
                concreteGrade.ft = Util.getDoubleValueFromXssCell(row.getCell(4), 2);
                CONCRETE_GRADE_MAP.put(v1, concreteGrade);
            }

            String v2 = Util.getValueFromXssfcell(row.getCell(7));
            if (!Util.stringIsBlack(v2)) {
                SteelGrade steelGrade = new SteelGrade();
                steelGrade.fyk = Util.getIntValueFromXssCell(row.getCell(8));
                steelGrade.fstk = Util.getIntValueFromXssCell(row.getCell(9));
                steelGrade.fyvk = Util.getIntValueFromXssCell(row.getCell(10));
                STEEL_GRADE_MAP.put(v2, steelGrade);
            }
        }
        Util.printInfo("材料属性");
        CONCRETE_GRADE_MAP.forEach((k,v)-> System.out.println(k+":"+v));
        STEEL_GRADE_MAP.forEach((k,v)-> System.out.println(k+":"+v));
    }


    public static class ConcreteGrade{
        public Double fck;
        public Double fc;
        public Double ftk;
        public Double ft;

        @Override
        public String toString() {
            return "{" +
                    "fck=" + fck +
                    ", fc=" + fc +
                    ", ftk=" + ftk +
                    ", ft=" + ft +
                    '}';
        }
    }


    public static  class SteelGrade{
        public Integer fyk;
        public Integer fstk;
        public Integer fyvk;

        @Override
        public String toString() {
            return "{" +
                    "fyk=" + fyk +
                    ", fstk=" + fstk +
                    ", fyvk=" + fyvk +
                    '}';
        }
    }

}
