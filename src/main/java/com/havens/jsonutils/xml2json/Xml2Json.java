package com.havens.jsonutils.xml2json;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
/**
 * @desc: TODO
 * @author: xuwenwu
 * @date: 2016/9/3 11:45
 */
public class Xml2Json {
//    private static final String STR_JSON = "{\"name\":\"Michael\",\"address\":{\"city\":\"Suzou\",\"street\":\" Changjiang Road \",\"postcode\":100025},\"blog\":\"http://www.ij2ee.com\"}";
        String fileName="E:\\git_code\\jsonutils\\src\\main\\resources\\CardType.xml";
//        List entries = xmlMapper.readValue(new File("input.xml"), List.class);
//    }
//    public static String json2XML(String json){
        ObjectMapper m = new ObjectMapper();
        // can either use mapper.readTree(source), or mapper.readValue(source, JsonNode.class);
        JsonNode rootNode = m.readTree(new File(fileName));
//        String json = jsonMapper.writeValueAsString(entries);
        JsonNode nameNode = rootNode.path("name");
        String lastName = nameNode.path("last").asText();
        System.out.println(lastName);
//    }
        if ("xmler".equalsIgnoreCase(lastName)){
        }
        m.writeValue(new File("user-modified.json"), rootNode);
//        System.out.println("json="+json);
    }
}
