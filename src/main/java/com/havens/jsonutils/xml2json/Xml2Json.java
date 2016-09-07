package com.havens.jsonutils.xml2json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @desc: TODO
 * @author: xuwenwu
 * @date: 2016/9/6 10:37
 */
public class Xml2Json {
    public static String fileName="E:\\git_code\\jsonutils\\src\\main\\resources\\CardCareer.xml";

    public static void main(String[] args) throws IOException, XMLStreamException {
        // First create Stax components we need
        Xml2Json.xmlToBean(fileName,Object.class);
    }

    /** * XML To Object * * @param xmlPath * @param cls * @param <T> * @return * @throws IOException */
    public static <T> T xmlToBean(String xmlPath, Class<T> cls) throws IOException {
        XmlMapper xml = JacksonMapper.getXmlMapper();
        T obj = xml.readValue(new File(xmlPath), cls);
        return obj;
    }

    /** * @param xmlFile * @param cls * @param <T> * @return * @throws IOException */
    public static <T> T xmlToBean(File xmlFile, Class<T> cls) throws IOException {
        XmlMapper xml = JacksonMapper.getXmlMapper();
        T obj = xml.readValue(xmlFile, cls);
        return obj;
    }

    /** * @param xmlInputStream * @param cls * @param <T> * @return * @throws IOException */
    public static <T> T xmlToBean(InputStream xmlInputStream, Class<T> cls) throws IOException {
        XmlMapper xml = JacksonMapper.getXmlMapper();
        T obj = xml.readValue(xmlInputStream, cls);
        return obj;
    }

    public static <T> String beanToXml(T bean) throws JsonProcessingException {
        XmlMapper xml = JacksonMapper.getXmlMapper();
        String string = xml.writeValueAsString(bean);
        return string;
    }
}
