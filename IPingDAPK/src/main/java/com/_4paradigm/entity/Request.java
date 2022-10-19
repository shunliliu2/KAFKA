package com._4paradigm.entity;

import lombok.Data;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;
import java.util.List;

@Data
@Configuration
public class Request implements Serializable{
    private String request_id;

    private List<New_Standard_media_assets> subject;
    //private String request_id;
   // private List<User> data;


}
