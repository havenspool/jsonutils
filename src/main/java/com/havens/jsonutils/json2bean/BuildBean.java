package com.havens.jsonutils.json2bean;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Map;
/**
 * @desc: TODO
 * @author: xuwenwu
 * @date: 2016/9/1 18:09
 */

public class BuildBean{
    private JavaBean javaBean;

    public BuildBean()
    {
        this.javaBean = new JavaBean();
    }

    public void pushPackName(String packName)
    {
        this.javaBean.pushPackName(packName);
    }

    public void pushBeanName(String beanName)
    {
        this.javaBean.pushBeanName(beanName);
    }

    public boolean pushBeanFiled(String jsonFilePath, String tableName){
        try{
            InputStreamReader fileReader = new InputStreamReader(new FileInputStream(jsonFilePath + "/" + tableName + ".json"), "UTF-8");Throwable localThrowable3 = null;
            try{
                Gson gson = new Gson();
                this.javaBean.pushTableName(tableName);
                JsonObject jsonObject = (JsonObject)gson.fromJson(fileReader, JsonObject.class);
                for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
                    switch (((String)entry.getKey()).toLowerCase()){
                        case "type":
                            this.javaBean.pushType(((JsonElement)entry.getValue()).getAsJsonArray());
                            break;
                        case "desc":
                            this.javaBean.pushDesc(((JsonElement)entry.getValue()).getAsJsonArray());
                            break;
                        case "relation":
                            this.javaBean.parseConfig((JsonObject)entry.getValue());
                            break;
                        case "server":
                            this.javaBean.pushField(((JsonElement)entry.getValue()).getAsJsonArray());
                    }
                }
                this.javaBean.buildField();
                fileReader.close();
                return true;
            }catch (Throwable localThrowable1){
                localThrowable3 = localThrowable1;
                throw localThrowable1;
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
            System.out.println(tableName + "文件不存在, 或者是json格式错误");
            return false;
        }
    }

    public void writeToJavaBean(String beanFilePath){
        this.javaBean.writeToBean(beanFilePath);
    }

}
