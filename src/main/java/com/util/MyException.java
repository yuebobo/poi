package com.util;

/**
 * 自定义异常类
 */
public class MyException extends Exception{

    private String functionName;

    private Exception e;

    public MyException() {
    }

    public MyException(String functionName, Exception e) {
        this.functionName = functionName;
        this.e = e;
    }

    public String getFunctionName() {
        return functionName;
    }

    public Exception getE() {
        return e;
    }

    @Override
    public void printStackTrace() {
        if (e != null){
            e.printStackTrace();
        }
    }

    public static MyException build(String functionName, Exception e){
        if (e instanceof MyException){
            return (MyException) e;
        }
        return new MyException(functionName, e);
    }
}
