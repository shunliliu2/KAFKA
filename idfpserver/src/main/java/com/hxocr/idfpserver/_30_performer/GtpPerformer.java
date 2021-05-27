package com.hxocr.idfpserver._30_performer;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class GtpPerformer {
    private Boolean checkFile(String fileName, String filePath) {
        System.out.println("process file:" + filePath + "/" + fileName);
        return true;
    }

    private Boolean copyFileFromGtp(String fileName, String filePath, String localPath) {
        System.out.println("process file:" + localPath + "/" + fileName);
        return true;
    }

    private Boolean copyFileToGtp(String fileName, String filePath, String gtpPath) {
        System.out.println("process file:" + gtpPath + "/" + fileName);
        return true;
    }
}
