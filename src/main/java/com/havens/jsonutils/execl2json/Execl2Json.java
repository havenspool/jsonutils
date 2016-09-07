package com.havens.jsonutils.execl2json;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.havens.jsonutils.util.GsonUtil;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Workbook;
import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @desc: TODO
 * @author: xuwenwu
 * @date: 2016/9/1 17:56
 */
public class Execl2Json {
    private String[] execlNames;
    private String execlPath = "";
    private String[] jsonNames;
    private String jsonFilePath = "";

    public static String fileName="E:\\git_code\\jsonutils\\src\\main\\resources\\CardCareer.xlsx";

    public static void main(String[] args) throws IOException {
        Execl2Json execl2Json = new Execl2Json();
        execl2Json.parseConfig();
        System.out.println(execl2Json.execl2Json(fileName));
    }

    public void parseConfig(){

    }

    public String execl2Json(String fileName) throws IOException {
        FileInputStream input = new FileInputStream(fileName);
//        SXSSFWorkbook workbook = new SXSSFWorkbook(100);

        Workbook workbook = new HSSFWorkbook() ;
        // Get the first Sheet.
        Sheet sheet = workbook.getSheetAt( 0 );
        // Start constructing JSON.
        JSONObject json = new JSONObject();
        // Iterate through the rows.
        JSONArray rows = new JSONArray();
        for (Iterator<Row> rowsIT = sheet.rowIterator(); rowsIT.hasNext(); ){
            Row row = rowsIT.next();
            JSONObject jRow = new JSONObject();
            // Iterate through the cells.
            JSONArray cells = new JSONArray();
            for (Iterator<Cell> cellsIT = row.cellIterator(); cellsIT.hasNext(); )
            {
                Cell cell = cellsIT.next();
                cells.put( cell.getStringCellValue() );
            }
            jRow.put( "cell", cells );
            rows.put( jRow );
        }
        // Create the JSON.
        json.put( "rows", rows );
        // Get the JSON text.
        return json.toString();
    }
}
