package com.hxocr.idfpserver._40_message;

import lombok.Data;
import lombok.Generated;
import org.springframework.context.annotation.Description;

@Data
public class RequestInfo {
    String fileName;
    String filePath;
    String RequestID;

    public RequestInfo(String RequestID, String fileName, String filePath) {
        this.RequestID = RequestID;
        this.fileName = fileName;
        this.filePath = filePath;
    }
}
