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
    public static void main(String[] args) throws Exception{
        String fileName="E:\\git_code\\jsonutils\\src\\main\\resources\\CardType.xml";
        ObjectMapper m = new ObjectMapper();
        // can either use mapper.readTree(source), or mapper.readValue(source, JsonNode.class);
        JsonNode rootNode = m.readTree(new File(fileName));
        // ensure that "last name" isn't "Xmler"; if is, change to "Jsoner"
        JsonNode nameNode = rootNode.path("name");
        String lastName = nameNode.path("last").asText();
        System.out.println(lastName);
        if ("xmler".equalsIgnoreCase(lastName)){
            ((ObjectNode) nameNode).put("last", "Jsoner");
        }
        // and write it out:
        m.writeValue(new File("user-modified.json"), rootNode);
    }
}
