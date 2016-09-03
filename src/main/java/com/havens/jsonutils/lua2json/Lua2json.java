package com.havens.jsonutils.lua2json;

import java.io.FileReader;
import java.util.Scanner;

/**
 * @desc: TODO
 * @author: xuwenwu
 * @date: 2016/9/2 12:23
 */
public class Lua2json {
    private String[] luaNames;
    private String luaFilePath = "";
    private String[] jsonNames;
    private String jsonFilePath = "";
    private boolean isAllJson;

    public static void main(String[] args) {
        Lua2json lua2json = new Lua2json();
        lua2json.parseConfig();
    }

    public void parseConfig(){
        Scanner scanner = null;
        String[] param;
        try {
            FileReader fileReader = new FileReader("E:\\hg_code\\qlz\\server\\execl2json2bean\\src\\main\\resources\\lua2json_config.txt");
            Throwable localThrowable3 = null;
            try {
                scanner = new Scanner(fileReader);
                while (scanner.hasNextLine()) {
                    String pathParam = scanner.nextLine();
                    if (!pathParam.trim().equals("")) {
                        param = pathParam.split("=");
                        if (param.length != 1) {
                            switch (param[0].trim()){
                                case "luafilePath":
                                    this.luaFilePath = param[1].trim();
                                    break;
                                case "luanames":
                                    String var1 = param[1].trim();
                                    this.luaNames = var1.split(",");
                                    break;
                                case "jsonnames":
                                    if (param[1].equals("*")){
                                        this.isAllJson = true;
                                    }else{
                                        String var2 = param[1].trim();
                                        this.jsonNames = var2.split(",");
                                    }
                                    break;
                                case "jsonfilepath":
                                    this.jsonFilePath = param[1].trim();
                            }
                        }
                    }
                }
            } catch (Throwable localThrowable1){
                localThrowable3 = localThrowable1;throw localThrowable1;
            }finally{
                if (fileReader != null) {
                    if (localThrowable3 != null) {
                        try{
                            fileReader.close();
                        }catch (Throwable localThrowable2){
                            localThrowable3.addSuppressed(localThrowable2);
                        }
                    } else {
                        fileReader.close();
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            return;
        }
        System.out.println(luaNames);
        System.out.println(luaFilePath);
        System.out.println(jsonNames);
        System.out.println(jsonFilePath);
    }
}
