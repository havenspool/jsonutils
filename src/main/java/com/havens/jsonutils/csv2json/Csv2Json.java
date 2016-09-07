package com.havens.jsonutils.csv2json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvParser;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import java.io.File;
import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * @desc: TODO
 * @author: xuwenwu
 * @date: 2016/9/5 17:53
 */
public class Csv2Json {
    public static void main(String[] args) throws Exception{
        String fileName="E:\\git_code\\jsonutils\\src\\main\\resources\\handbookLv.csv";
        CsvMapper mapper = new CsvMapper();
        mapper.enable(CsvParser.Feature.WRAP_AS_ARRAY);
        File csvFile = new File(fileName); // or from String, URL etc
        MappingIterator<String[]> it = mapper.readerFor(String[].class).readValues(csvFile);
        while (it.hasNext()) {
            String[] row = it.next();
            System.out.println(Arrays.toString(row));
            // and voila, column values in an array. Works with Lists as well
        }
    }


}