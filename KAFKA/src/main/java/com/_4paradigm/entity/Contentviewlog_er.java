package com._4paradigm.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 点播和点击数据的接收
 */
@Data
public class Contentviewlog_er implements Serializable {

    //公共
   private Long processTime;
   private Long actionTime;
   private String sceneId;
    private int itemSetId;
    private String action;
    private String userId;
    private String lib;
    private String stbIp;
    private String stbId;
    private String stbType;
    private String stbMac;
    private String sysID;
    private String pageId;
    private String referType;
    private String areaCode;
    private String county;
    private String traceId;
    private String requestid;

 //
    private String itemId;
    private String referPageName;
    private String path;
    private String refer_floor_title;
    private String  refer_floor_num ;
    private String referPageID;

    private String duration;
    private String duration_percentage;

    private String productId;
    private int productType;
    private int payType;

    //private String actionTime;
    private String validTime;
    private String expiredTime;
    private int orderContinue;






}
