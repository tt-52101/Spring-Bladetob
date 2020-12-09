package org.springblade.auth.utils;

import com.thoughtworks.xstream.XStream;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageUtil {


    /**
     *  接收request对象，读取xml内容，转换成map对象
     * @param request
     * @return
     */
    public static Map<String, String> parseXmlToMap(HttpServletRequest request){
        Map<String, String> map = new HashMap<>();
        SAXReader reader = new SAXReader();
        InputStream ins = null;
        try {
            ins = request.getInputStream();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        Document doc = null;
        try {
            doc = reader.read(ins);
            Element root = doc.getRootElement();
            List<Element> list = root.elements();
            for (Element e : list) {
                map.put(e.getName(), e.getText());
            }
            return map;
        } catch (DocumentException e1) {
            e1.printStackTrace();
        }finally{
            try {
                if (null != ins){
                    ins.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

   
}