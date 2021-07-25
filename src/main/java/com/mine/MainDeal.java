package com.mine;

import com.data.DataInfo;
import com.insert.InsertToWord;
import com.ui.WordTab;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Gui获取文件基本路径成功后，调用此类的方法
 *
 * @author
 */
public class MainDeal {

    /**
     * 获取基本路径
     * 拼接出其余文件的路径
     *
     * @param wordPath
     * @throws FileNotFoundException
     */
    public static void getBasePath(String wordPath) throws Exception {
        String basePath;
        System.out.println(wordPath);
        if (wordPath.contains("\\")) {
            basePath = wordPath.substring(0, wordPath.lastIndexOf("\\"));
        } else if (wordPath.contains("/")) {
            basePath = wordPath.substring(0, wordPath.lastIndexOf("/"));
        } else {
            throw new FileNotFoundException();
        }
        System.out.println("基本路径：" + basePath);

        boolean excelDirectoryIsExist = false;
        boolean txtDirectoryIsExist = false;

        File file = new File(basePath);
        File[] fileList = file.listFiles();
        System.out.println("基本路径下的文件有：");
        for (File file2 : fileList) {
            System.out.println(file2.getPath());
            //将值直接插入到excel里，暂时不做
            if (file2.isDirectory()) {
                if ((basePath + "\\excel").equals(file2.getPath())) {
                    excelDirectoryIsExist = true;
                }
                if ((basePath + "\\txt").equals(file2.getPath())) {
                    txtDirectoryIsExist = true;
                }
            }
        }

        if (excelDirectoryIsExist) {
            File file3 = new File(basePath);
            File[] file3s = file3.listFiles();
            if (file3s.length < 1) {
                throw new RuntimeException("excel文件夹里没有文件");
            }
        } else {
            throw new RuntimeException("缺少excel的文件夹");
        }

        if (txtDirectoryIsExist) {
            File file3 = new File(basePath);
            File[] file3s = file3.listFiles();
            if (file3s.length < 1) {
                throw new RuntimeException("txt文件夹里没有文件");
            }
        }else {
            throw new RuntimeException("缺少txt文件夹");
        }

        //初始化数据
         DataInfo.initBaseData(basePath);


        //==================================================  第一套模板  (云南) ======================================================
        if (WordTab.FunctionType.YUNNAN_WORD.equals(WordTab.FUNCTION_TYPE)) {
            InsertToWord.getValueInsertWord1(basePath, wordPath);
        }
        //==================================================  第一套模板 （云南）======================================================


        //==================================================  第二套模板  (江苏）======================================================
        if (WordTab.FunctionType.JIANGSU_WORD.equals(WordTab.FUNCTION_TYPE)) {
            InsertToWord.getValueInsertWord2(basePath, wordPath);
        }
        //==================================================  第二套模板  （江苏）======================================================

//		if(excel0IsExist){
//			try {
//				InsertToExcel.getValueInsertExcel(basePath);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
    }
}
