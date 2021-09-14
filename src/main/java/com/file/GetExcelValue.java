package com.file;

import com.entity.ValueNote;
import com.excel.sheet.*;
import com.util.MyException;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 该类主要用来处理读取excel文件，进行流的操作
 * @author 
 *
 */
public class GetExcelValue {

	/**
	 * 1.模型对比
	 * @throws IOException
	 */
	public static Map<Integer, Object> getModel(String path) throws Exception {
		FileInputStream e = null;
		try {
			e = new FileInputStream(path);
			XSSFWorkbook excel = new XSSFWorkbook(e);
			
			String value1 = ExcleModel.get_DEAD_LIVE(excel.getSheetAt(0));
			System.out.println("==========================================");
			System.out.println("结构质量对比：");
			System.out.println(value1);
			
			String[] value2 = ExcleModel.getMODAL(excel.getSheetAt(1));
			System.out.println("==========================================");
			System.out.println("周期对比：");
			arrayToString(value2);
			
			String[][] value3 = ExcleModel.getExEy(excel.getSheetAt(2));
			System.out.println("==========================================");
			System.out.println("地震剪力对比:");
			arrayToString(value3[0]);
			arrayToString(value3[1]);
			
			Map<Integer, Object> map = new HashMap<>();
			map.put(1, value1);
			map.put(2, value2);
			map.put(3, value3);
			return map;
			
		} catch (Exception e1) {
			throw MyException.build("工作簿1",e1);
		}
		finally {
			if(e != null){
				try {
					e.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}


	/**
	 * 基低剪力对比
	 * @param path
	 * @return
	 */
	public static String[][] getE2_T5_R2(String path) throws Exception {
		FileInputStream e = null;
		try {
			e = new FileInputStream(path);
			XSSFWorkbook excel = new XSSFWorkbook(e);
			String[][] value =  ExcelBaseShear.getE2_T5_R2(excel.getSheetAt(0));
			arrayToString(value[0]);
			arrayToString(value[1]);
			return value;
		} catch (Exception e1) {
			throw MyException.build("工作簿2",e1);
		}
		finally {
			if(e != null){
				try {
					e.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}


	/**
	 * 地震波信息
	 * @param path
	 * @return
	 */
	public static Map<String,String[]> getEarthquakeWaveInfo(String path) throws Exception {
		FileInputStream e = null;
		try {
			System.out.println("\n"+path);
			e = new FileInputStream(path);
			XSSFWorkbook excel = new XSSFWorkbook(e);
			Map<String,String[]> value =  ExcelEarthquakeWave.getEarthquakeWaveInfo(excel.getSheetAt(0));
			return value;
		} catch (Exception e1) {
			throw MyException.build("地震波信息",e1);
		}
		finally {
			if(e != null){
				try {
					e.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}


	/**
	 * 层间剪力
	 * @param path
	 * @return
	 */
	public static String[][][] getShear(String path,int sheet) throws Exception {
		FileInputStream e = null;
		try {
			e = new FileInputStream(path);
			XSSFWorkbook excel = new XSSFWorkbook(e);
			//原来sheet为0
			String[][][] value = ExcelFloorDisplaceShear.getShear(excel.getSheetAt(sheet),0);
			System.out.println("\n"+path);
			System.out.println("=================== 层间剪力 =========================");
			printArrayDisplace(value);
			return value;
		} catch (Exception e1) {
			throw MyException.build("处理层间剪力",e1);
		}
		finally {
			if(e != null){
				try {
					e.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	
	/**
	 * 层间位移
	 * @param path
	 * @return
	 */
	public static String[][][] getDisplace(String path,int sheet) throws Exception {
		FileInputStream e = null;
		try {
			e = new FileInputStream(path);
			XSSFWorkbook excel = new XSSFWorkbook(e);
			//原来sheet为0
			String[][][] value = ExcelFloorDisplaceShear.getDisplace(excel.getSheetAt(sheet),5);
			System.out.println("\n"+path);
			System.out.println("=================== 层间位移 =========================");
			printArrayDisplace(value);
			return value;
		} catch (Exception e1) {
			throw MyException.build("层间位移",e1);
		}
		finally {
			if(e != null){
				try {
					e.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}


	/**
	 * X方向各地震波下X方向阻尼器耗能
	 * @param path
	 * @return
	 */
	public static Double[][][] getEarthquakeDamperDisEnergyX(String path) throws Exception {
		System.out.println("================================================================");
		System.out.println("\n"+path);
		System.out.println("X方向各地震波下X方向阻尼器耗能");
		Double[][][][] value = getEarthquakeDamperDisEnergy(path,6,7);
		Double[][] shape = value[0][0];
		Double[][] force = value[1][0];
//		return  new Double[][][]{shape,force};
		return  new Double[][][]{getHarf(shape,"X"),getHarf(force,"X")};
	}

	/**
	 * Y方向各地震波下Y方向阻尼器耗能
	 * @param path
	 * @return
	 */
	public static Double[][][] getEarthquakeDamperDisEnergyY(String path) throws Exception {
		System.out.println("================================================================");
		System.out.println("\n"+path);
		System.out.println("Y方向各地震波下Y方向阻尼器耗能");
		Double[][][][] value = getEarthquakeDamperDisEnergy(path,7,8);
		Double[][] shape = value[0][1];
		Double[][] force = value[1][1];
//		return  new Double[][][]{shape,force};
		return  new Double[][][]{getHarf(shape,"Y"),getHarf(force,"Y")};
	}

	//原来是x和y是分开在两个文件里，现在合并在一个文件里边
	//x取前一半，y取后一半
	private static Double[][] getHarf(Double[][] array,String xOy){
		int length = array.length;
		Double[][] data = new Double[length/2][];
		if (length % 2 == 0){
			int start = 0;
			if ("X".equals(xOy)) start = 0;
			if ("Y".equals(xOy)) start = length/2;
			for (int i = 0; i < length/2 ; i++){
				data[i] = array[start++];
			}
		}else {
			System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ X方向和Y方向的总数不是偶数 $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
			return null;
		}
		return data;
	}

	/**
 	 * 各地震波下X/Y方向阻尼器耗能
	 * @param path
	 * @return
	 */
	private static Double[][][][] getEarthquakeDamperDisEnergy(String path, int valuePositionShape, int valuePositionForce) throws Exception {
		FileInputStream e = null;
		try {
			e = new FileInputStream(path);
			XSSFWorkbook excel = new XSSFWorkbook(e);
			//阻尼器变形
			Double[][][] shape = ExcelDamper.getDamperValue(excel.getSheetAt(0),2,valuePositionShape);
			//阻尼器内力
			Double[][][] force = ExcelDamper.getDamperValue(excel.getSheetAt(1),3,valuePositionForce);
			System.out.println("=================== 阻尼器变形  =========================");
			printArrayShear1(shape);
			System.out.println("=================== 阻尼器内力  =========================");
			printArrayDisplace1(force);
			Double[][][][] value = {shape,force};
			return value;
		}catch (Exception e1) {
			throw MyException.build("阻尼器耗能",e1);
		}
		finally {
			if(e != null){
				try {
					e.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	/**
	 * 各地震波下X/Y方向阻尼器耗能
	 * @param path
	 * @return
	 */
	public static Map<String, ValueNote>[] getEarthquakeDamperDisEnergy__(String path, int valuePositionShape, int valuePositionForce,String direct) throws Exception {
		FileInputStream e = null;
		try {
			e = new FileInputStream(path);
			XSSFWorkbook excel = new XSSFWorkbook(e);
			//阻尼器变形
			Map<String, ValueNote> shape = ExcelDamper.getValueNote(excel.getSheetAt(0), 2, valuePositionShape, direct);
			//阻尼器内力
			Map<String, ValueNote> force = ExcelDamper.getValueNote(excel.getSheetAt(1), 3, valuePositionForce, direct);
			return new Map[]{shape,force};
		}catch (Exception e1) {
			throw MyException.build("阻尼器耗能",e1);
		}
		finally {
			if(e != null){
				try {
					e.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 层间位移角
	 * @param path
	 * @return
	 */
	public static String[][][] getDisplaceAngle(String path,int sheet) throws Exception {
		FileInputStream e = null;
		try {
			e = new FileInputStream(path);
			XSSFWorkbook excel = new XSSFWorkbook(e);
			//原来sheet为0
			String[][][] value = ExcelFloorDisplaceShear.getDisplace(excel.getSheetAt(sheet),3);
			System.out.println("\n"+path);
			System.out.println("=================== 层间位移角 =========================");
			printArrayShear(value);
			return value;
		} catch (Exception e1) {
			throw MyException.build("层间角位移",e1);
		}
		finally {
			if(e != null){
				try {
					e.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}


	private static void printArrayDisplace(String[][][] array){
		String[][] x = array[0];
		String[][] y = array[1];
		System.out.println("     T1X   T2X   T3X    T4X   T5X    R1X   R2X");
		for (int i = 0; i < x.length; i++) {
			System.out.print("第"+ (i + 1) + "层    ");
			arrayToString(x[i]);
		}
		System.out.println();
		System.out.println("     T1Y   T2Y   T3Y    T4Y   T5Y    R1Y   R2Y");
		for (int i = 0; i < y.length; i++) {
			System.out.print("第"+ (i + 1) + "层    ");
			arrayToString(y[i]);
		}
	}
	
	private static void printArrayShear(String[][][] array){
		String[][] x = array[0];
		String[][] y = array[1];
		System.out.println("     T1X     T2X      T3X    T4X     T5X      R1X    R2X");
		for (int i = 0; i < x.length; i++) {
			System.out.print("第"+ (i + 1) + "层    ");
			arrayToString(x[i]);
		}
		System.out.println();
		System.out.println("     T1X     T2X      T3X    T4X     T5X      R1X    R2X");
		for (int i = 0; i < y.length; i++) {
			System.out.print("第"+ (i + 1) + "层    ");
			arrayToString(y[i]);
		}
	}
	
	private static void printArrayShear1(Double[][][] array){
		Double[][] x = array[0];
		Double[][] y = array[1];
		System.out.println("         T1X    T2X     T3X    T4X    T5X    R1X   R2X");
		for (int i = 0; i < x.length; i++) {
			System.out.print("第"+ (i + 1) + "层    ");
			arrayToString1(x[i]);
		}
		System.out.println();
		System.out.println("         T1X    T2X     T3X    T4X    T5X    R1X   R2X");
		for (int i = 0; i < y.length; i++) {
			System.out.print("第"+ (i + 1) + "层    ");
			arrayToString1(y[i]);
		}
	}

	private static void printArrayDisplace1(Double[][][] array){
		Double[][] x = array[0];
		Double[][] y = array[1];
		System.out.println("     T1X   T2X   T3X    T4X   T5X    R1X   R2X");
		for (int i = 0; i < x.length; i++) {
			System.out.print("第"+ (i + 1) + "层    ");
			arrayToString1(x[i]);
		}
		System.out.println();
		System.out.println("     T1Y   T2Y   T3Y    T4Y   T5Y    R1Y   R2Y");
		for (int i = 0; i < y.length; i++) {
			System.out.print("第"+ (i + 1) + "层    ");
			arrayToString1(y[i]);
		}
	}
	
	private static void arrayToString(String[] array){
		for (int i = 0; i < array.length; i++) {
			System.out.print(array[i] + ",  ");
		}
		System.out.println();
	}

	private static void arrayToString1(Double[] array){
		for (int i = 0; i < array.length; i++) {
			System.out.print(array[i] + ",  ");
		}
		System.out.println();
	}




	public static void p(Double[][] x){
		for (Double[] doubles : x) {
			System.out.println(Arrays.asList(doubles));
		}
	}
}

