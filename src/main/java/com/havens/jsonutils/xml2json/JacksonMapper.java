package com.havens.jsonutils.xml2json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

/**
 * @desc: TODO
 * @author: xuwenwu
 * @date: 2016/9/6 10:43
 */
public class JacksonMapper {
    /** * can reuse, share globally */
    private static final ObjectMapper object = new ObjectMapper();

    /** * can reuse, share globally */
    private static final XmlMapper xml = new XmlMapper();

    /** * private constructor */
    private JacksonMapper() {
    }

    /** * return a ObjectMapper that is singleton * @return */
    public static ObjectMapper getObjectMapper() {
        return object;
    }

    /** * return a XmlMapper that is singleton * @return */
    public static XmlMapper getXmlMapper() {
        return xml;
    }
}
