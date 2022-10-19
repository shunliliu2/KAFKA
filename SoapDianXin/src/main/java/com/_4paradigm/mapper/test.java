package com._4paradigm.mapper;

import java.util.List;

public class test {
    public static void main(String[] args) {
            CodeMapper codeMapper = new CodeMapper();
       int select = codeMapper.select("0000000100000000050401061");
        System.out.println("select = " + select);



       // codeMapper.update("00000001000000010000000050401061");

        //codeMapper.insert("1","1");
        List<String> selectservercode = codeMapper.selectservercode();
        for (String s : selectservercode) {
            System.out.println("s = " + s);
        }
    }
}
