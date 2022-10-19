package com._4paradigm.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 点播和点击数据的接收
 */
@Data
public class Contentviewlog implements Serializable {
    private String  sequence;
    private String status;
    private String areaCode;
    private String userId;
    private String mediaCode;
    private String beginTime;
    private String endTime;
    private String viewSeconds;
    private String referParentID;
    private String referPageID;
    private String referPosID;
    private String referMediaCode;
    private String hDFlag;
    private String refType ;
    private String  seriesFlag ;
    private String  parentObject;
    private String refPageName;
    private String  refPosName ;
    private String   mediaName;
    private String sysID;
    private String   todayonlineseconds;
    private String   pageName;
    private String mediaduration;
    private String pageId;
    private String mediaCodeId;
    private String tryView;
    private String terminaltype;
    private String userGroupID;
    private String epgGroupID;
    private String county;
    private String firstbegintime;
    private String refer_page_type;
    private String refer_floor_code;
    private String refer_floor_title;
    private String  refer_floor_num ;
    private String  refer_position_num;
    private String current_page_type;
    private String  service_id ;
    private String   tactic_id;
    private String requestid;

}
