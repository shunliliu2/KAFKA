package com.hxocr.idfpserver._30_performer;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class PdfPerformer {

    private Boolean convertPdfFile(String fileName, String filePath) {
        System.out.println("process file:" + filePath + "/" + fileName);
        return true;
    }

    private Boolean ocrImgFiles(String fileName, String filePath) {
        System.out.println("process file:" + filePath + "/" + fileName);
        return true;
    }

}
