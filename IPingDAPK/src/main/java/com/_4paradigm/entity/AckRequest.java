package com._4paradigm.entity;

import lombok.Data;

@Data
public class AckRequest  {
    //private String userId;
    //private String itemId;
   // private String category;
    //private String itemChannel;


    private String userId;

   // private String mc;
    private int count=15;
//    private int start;
//    private int sdonly;
//    private String scene;
//    private String typeid;
//    private String cpid;
//    private  String category;
    private String interestTag="";


}
