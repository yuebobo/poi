package com.test;

import com.insert.DataInfo;

import java.io.IOException;

public class DataInfoTest {
    static String basePath = "C:\\Users\\Administrator\\Desktop\\WENJ\\weige\\testFile\\file";


    public static void main(String[] args) throws IOException {
        DataInfo.initBaseData(basePath);
    }
}
