package com.kaisn.utils.xml;

import com.kaisn.pojo.Employee;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

public class ParseXmlUtil {

    private static Logger logger = Logger.getLogger(ParseXmlUtil.class);

    public static Element useDom4JReadXml(String soucePath){
        try {
            File file = new File(soucePath);
            SAXReader read = new SAXReader();
            org.dom4j.Document doc = read.read(file);
            Element root = doc.getRootElement();
            return root;
        } catch (Exception e) {
            logger.error("xml parse failure",e);
        }
        return null;
    }

    public static void readXML() {
        SAXReader sr = new SAXReader();// 获取读取xml的对象。
        try {
            Document doc = sr.read("src/main/resources/employee.xml");// 得到xml所在位置。然后开始读取。并将数据放入doc中
            Element rootElement = doc.getRootElement();// 向外取数据，获取xml的根节点。
            logger.info("根节点：" + rootElement.getName());
            Iterator<Element> it = rootElement.elementIterator();// 从根节点下依次遍历，获取根节点下所有子节点
            while (it.hasNext()) {// 遍历子节点
                Element next = it.next();
                //String str = next.getText();
                Iterator<Element> iterator = next.elementIterator();
                while (iterator.hasNext()) {
                    Element element = iterator.next();
                    logger.info(element.getName() + "=" + element.getData());
                }
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    public static void writeToXML() {

        Employee employee = new Employee();

        employee.setEmpId("001");
        employee.setEmpName("lijing");
        employee.setGender("男");
        employee.setAddress("江西吉安");
        employee.setBirth("1992-01-30");
        employee.setDescText("ha lou");
        employee.setEmail("1169318609@qq.com");

        Document document = DocumentHelper.createDocument();
        Element root = document.addElement("root");
        Element row = root.addElement("employee");
        row.addElement("id").addText(employee.getEmpId());
        row.addElement("name").addText(employee.getEmpName());
        row.addElement("gender").addText(employee.getGender());
        row.addElement("address").addText(employee.getAddress());
        row.addElement("birth").addText(employee.getBirth());
        row.addElement("desc").addText(employee.getDescText());
        row.addElement("email").addText(employee.getEmail());

        String filePath = "E:/xmlTest/" + getNowDay() + "/";
        String fileName = "employee.xml";

        String xmlStr = "";
        xmlStr = document.asXML();
        try {
            Document dcmt = DocumentHelper.parseText(xmlStr);
            saveDocumentToFile(dcmt, filePath, fileName, true, "UTF-8");
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }


    public static String getNowDay() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        return format.format(new Date());
    }

    /**
     * 方法描述：<b>存储完整XML文件.</b></br>
     * 备 注: </br>
     * 创 建 人: zyl</br>
     * 创建日期:2013-3-18</br>
     *
     * @param document
     * @param xmlFilePath
     * @param xmlFileName
     * @param isTrimText
     * @param xmlEncoding
     */
    public static void saveDocumentToFile(Document document,
            String xmlFilePath, String xmlFileName, boolean isTrimText,
    String xmlEncoding) {
        if (document == null || xmlFilePath == null || "".equals(xmlFileName)) {
            return;
        }

        File file = new File(xmlFilePath);
        // 判断路径是否存在，不存在创建
        if (!file.exists()) {
            file.mkdirs();
        }
        // 保存文件
        OutputFormat format = null;

        if (isTrimText) {
            format = OutputFormat.createPrettyPrint();
        } else {
            format = DomXmlOutputFormat.createPrettyPrint();// 保留xml属性值中的回车换行
        }

        if (xmlEncoding != null) {
            format.setEncoding(xmlEncoding);// GBK
        } else {
            format.setEncoding("UTF-8");// UTF-8
        }

        try {
            XMLWriter xmlWriter = new XMLWriter(
                    new FileOutputStream(xmlFilePath + xmlFileName), format);// FileOutputStream可以使UTF-8生效
            xmlWriter.write(document);
            xmlWriter.close();
        } catch (IOException e) {
            logger.error("保存XML异常：" + e.getMessage());
            logger.error("正在保存的文件名是：" + xmlFileName);
        }
        // 存到文件中结束
    }

    public static void main(String args[]) {
        readXML();
//        writeToXML();
    }

}
