package com.txt;


import java.io.*;

public class TxtGetValue {



	/**
	 * 获取地震波持时表 里的数据
	 *
	 * @param txtPath
	 * @return
	 */
	public static String[] earthquakeWave(String txtPath) {
		double dt = 0;
		FileInputStream fileIn = null;
		BufferedReader br = null;
		String line;
		int count = 0;
		double min = 0;
		double max = 0;
		boolean flag = false;
		try {
			fileIn = new FileInputStream(txtPath);
			br = new BufferedReader(new InputStreamReader(fileIn, "GBK"));
			//获取地震波时间间隔
			if ((line = br.readLine()) != null) {
				dt = Double.valueOf(line.substring(line.lastIndexOf("=") + 1, line.lastIndexOf(",")));
			}
			while ((line = br.readLine()) != null) {
				count++;
				if (isLessThen(line)) continue;
				max = count * dt;
				if (flag) continue;
				min = count * dt;
				flag = true;
			}
			return new String[]{String.valueOf(dt), String.valueOf(min), String.valueOf(max)};
		} catch (FileNotFoundException e) {
			System.out.println("$$$$$$$$$$$$" + txtPath + "没有找到");
			return null;
		} catch (IOException e) {
			System.out.println("$$$$$$$$$$$$" + txtPath + "处理异常");
			return null;
		} finally {
			if (fileIn != null) {
				try {
					fileIn.close();
				} catch (IOException e) {
				}
			}
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
				}
			}
		}
	}

	/**
	 * 获取地震波信息里的第一行的两个数据
	 * @param txtPath
	 * @return
	 */
	public static String[] eathquakeWave1(String txtPath){
		String dt = null;
		String bt = null;
		FileInputStream fileIn = null;
		BufferedReader br = null;
		String line;
		try {
			fileIn = new FileInputStream(txtPath);
			br = new BufferedReader(new InputStreamReader(fileIn, "GBK"));
			//获取地震波时间间隔
			if ((line = br.readLine()) != null) {
				dt = line.substring(line.lastIndexOf("=") + 1, line.lastIndexOf(","));
				bt = line.split(",")[1].trim();
			}
			return new String[]{dt,bt};
		} catch (FileNotFoundException e) {
			System.out.println("$$$$$$$$$$$$" + txtPath + "没有找到");
			return null;
		} catch (IOException e) {
			System.out.println("$$$$$$$$$$$$" + txtPath + "处理异常");
			return null;
		} finally {
			if (fileIn != null) {
				try {
					fileIn.close();
				} catch (IOException e) {
				}
			}
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
				}
			}
		}
	}

	// 对于  2.86E-07 类型的数进行判断 大小是否小于 0.1  小于返回true
	private static boolean isLessThen(String value) {
		value = value.trim().replaceAll(" +", "");
		if (value.contains("E-")) {
			String[] datas = value.split("E-");
			Double basic = Math.abs(Double.valueOf(datas[0]));
			int index = Integer.valueOf(datas[1]);
			if (index == 1) return 1 > basic;
			if (index > 1) return true;
			if (basic != 0) return false;
			return true;
		} else if (value.contains("E+")) {
			String[] datas = value.split("E+");
			Double basic = Math.abs(Double.valueOf(datas[0]));
			if (basic == 0) return true;
			return false;
		} else {
			return 0.1 > Double.valueOf(value);
		}
	}

}

