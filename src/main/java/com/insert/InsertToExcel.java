package com.insert;

import com.data.DataInfo;
import com.util.Util;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 读取数据，插入到excel模型中
 * @author 
 *
 */
public class InsertToExcel {


	/**
	 * 插入数据到 excel
	 */
	public static void insert(){
		insertFloorInfo(DataInfo.BASE_PATH + "\\excel\\材料数据.xlsx");
	}

	/**
	 * 对 楼层sheet 进行数据插入
	 * @param path
	 */
	private static void insertFloorInfo(String path) {
		FileInputStream stream = null;
		FileOutputStream out = null;
		try {
			stream = new FileInputStream(path);
			XSSFWorkbook workbook = new XSSFWorkbook(stream);
			insertDrawingNumber(workbook.getSheetAt(8));
			out = new FileOutputStream(path);
			workbook.write(out);
		} catch (Exception e) {
			System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$" + "excel插入编号异常");
			e.printStackTrace();
		} finally {
			if (stream != null) {
				try {
					stream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (out != null){
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 插入 图纸编号
	 * @param sheet
	 */
	private static void insertDrawingNumber(XSSFSheet sheet){
		int title = 2;
		for (int i = 0; i < DataInfo.DRAWING_NUMBER_X.length ; i++) {
			Util.insertValueToCell(sheet,++title,9,DataInfo.DRAWING_NUMBER_X[i]);
			Util.insertValueToCell(sheet,title,10,String.valueOf(title-2));
		}

		for (int i = 0; i < DataInfo.DRAWING_NUMBER_Y.length ; i++) {
			Util.insertValueToCell(sheet,++title,9,DataInfo.DRAWING_NUMBER_Y[i]);
			Util.insertValueToCell(sheet,title,10,String.valueOf(title- 2));
		}
		Util.insertValueToCell(sheet,title,11,"END");
	}



}
