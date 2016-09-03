package com.havens.jsonutils.json2bean;

import java.io.File;
import java.io.FileReader;
import java.util.Scanner;
/**
 * @desc: TODO
 * @author: xuwenwu
 * @date: 2016/9/1 18:18
 */

public class Json2Bean{
    private String packName = "";
    private String[] beanNames;
    private String javaBeanPath = "";
    private String[] jsonNames;
    private boolean isAllJson;
    private String jsonFilePath = "";

    public static void main(String[] args){
        Json2Bean json2Bean = new Json2Bean();
        json2Bean.parseConfig();
    }

    public void parseConfig(){
        Scanner scanner = null;
        String[] param;
        try {
            FileReader fileReader = new FileReader("execl2json_config.txt");
            Throwable localThrowable3 = null;
            try {
                scanner = new Scanner(fileReader);
                while (scanner.hasNextLine()) {
                    String pathParam = scanner.nextLine();
                    if (!pathParam.trim().equals("")) {
                        param = pathParam.split("=");
                        if (param.length != 1) {
                            switch (param[0].trim()){
                                case "packname":
                                    this.packName = param[1].trim();
                                    break;
                                case "beannames":
                                    String var1 = param[1].trim();
                                    this.beanNames = var1.split(",");
                                    break;
                                case "javabeanpath":
                                    this.javaBeanPath = param[1].trim();
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
        if (this.isAllJson){
            File root = new File(this.jsonFilePath);
            File[] files = root.listFiles();
            File[] localThrowable1 = files;
            int param2 = localThrowable1.length;
            for (int arrayOfString1 = 0; arrayOfString1 < param2; arrayOfString1++){
                File file = localThrowable1[arrayOfString1];
                String fileName = file.getName();
                int index = fileName.lastIndexOf(".");
                if (index >= 0){
                    fileName = fileName.substring(0, index);

                    BuildBean buildBean = new BuildBean();
                    buildBean.pushPackName(this.packName);
                    buildBean.pushBeanName(fileName + "Bean");
                    if (buildBean.pushBeanFiled(this.jsonFilePath, fileName)){
                        if (!this.javaBeanPath.equals("")) {
                            buildBean.writeToJavaBean(this.javaBeanPath);
                        }
                    }
                }
            }
        }
        else{
            if (this.jsonNames.length != this.beanNames.length){
                System.out.println("beannames配置的文件书与jsontablenames不一致");
                return;
            }
            for (int i = 0; i < this.beanNames.length; i++){
                BuildBean buildBean = new BuildBean();
                buildBean.pushPackName(this.packName);
                buildBean.pushBeanName(this.beanNames[i]);
                if (buildBean.pushBeanFiled(this.jsonFilePath, this.jsonNames[i])){
                    if (!this.javaBeanPath.equals("")) {
                        buildBean.writeToJavaBean(this.javaBeanPath);
                    }
                }
            }
        }
    }
}
