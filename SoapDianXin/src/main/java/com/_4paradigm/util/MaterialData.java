package com._4paradigm.util;

import com._4paradigm.log.CommonLogger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;


public class MaterialData {
    public Document getDocument(String xmlPath) {
        File file = new File(xmlPath);
        SAXReader reader = new SAXReader();
        Document document=null;
        try {
            document = reader.read(file);
            Element root = document.getRootElement();
        } catch (DocumentException e) {
            e.printStackTrace();
        }


        return document;
    }
}
