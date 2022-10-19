package com._4paradigm.entity;


import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {
    private String userId;
    private String user_group;
    private String business_group;
    private String dateCreated;
    private String dateActivated;
    private String datecancelled;
    private int status;
    private String service;
    private String STBID;
    private String areacode;
    private String epGgroup;
    private String userName;
    private String telephone;
    private String address;
    private String postcode;
    private String email;
    private String idCode;
    private String birthday;
    private String sex;
    private String reserved1;
    private String reserved2;
    private String reserved3;
    private String operator;


}
