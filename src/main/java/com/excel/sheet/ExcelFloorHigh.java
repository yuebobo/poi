package com.excel.sheet;

import com.util.Util;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 时间 : 2018/9/2.
 */
public class ExcelFloorHigh {

    public static List<String> getFloorH(XSSFSheet sheet){
        XSSFCell c = sheet.getRow(1).getCell(7);


        Iterator it = sheet.iterator();
        Util.iteratorNext(it,2);
        List<String> list = new ArrayList<>();
        String h;
        XSSFRow row;
        XSSFCell cell;
        while(it.hasNext()) {
            row = (XSSFRow) it.next();
            cell = row.getCell(3);
            try {
                h = Util.getPrecisionString(cell.getNumericCellValue(),0);
            }catch (Exception e){
                try {
                    h = cell.getStringCellValue();
                }catch (Exception e1){
                    h = cell.getRawValue();
                }
            }
            if ("0".equals(h)){
                break;
            }

          list.add(h);
        }
        return list;
    }

}
