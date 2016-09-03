package com.havens.jsonutils.json2bean;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @desc: TODO
 * @author: xuwenwu
 * @date: 2016/9/1 18:08
 */

public class BaseBean
{
    protected StringBuilder beanContent;
    protected String packName;
    protected String beanName;
    protected String tableName;
    protected ArrayList<String> fields;
    protected Map<String, JsonArray> relations;
    protected ArrayList<StringBuilder> interClass;
    protected Map<String, String> descs;
    protected Map<String, String> types;
    protected Map<String, String> fieldTypes;

    public BaseBean()
    {
        this.beanContent = new StringBuilder();

        this.fields = new ArrayList();
        this.relations = new LinkedHashMap();

        this.descs = new LinkedHashMap();
        this.types = new LinkedHashMap();

        this.interClass = new ArrayList();

        this.fieldTypes = new LinkedHashMap();
    }

    public void pushPackName(String packName)
    {
        this.packName = packName;
    }

    public void pushBeanName(String beanName)
    {
        this.beanName = upperFirstChar(beanName);
    }

    public void pushTableName(String tableName)
    {
        this.tableName = tableName;
    }

    public void pushType(JsonArray column)
    {
        for (int i = 0; i < column.size(); i++)
        {
            String field = column.get(i).getAsString();
            String[] param = field.split(":");
            this.types.put(param[0].trim(), getTypeFromParam(param));
        }
    }

    public void pushDesc(JsonArray column)
    {
        for (int i = 0; i < column.size(); i++)
        {
            String field = column.get(i).getAsString();
            String[] param = field.split(":");
            if (param.length >= 2) {
                if (param.length == 2)
                {
                    this.descs.put(param[0].trim(), param[1].replaceAll("\r\n", " "));
                }
                else
                {
                    StringBuilder tmp = new StringBuilder(param[1]);
                    for (int k = 2; k < param.length; k++) {
                        tmp.append(":" + param[k].replaceAll("\r\n", " "));
                    }
                    this.descs.put(param[0].trim(), tmp.toString());
                }
            }
        }
    }

    public void pushField(JsonArray column)
    {
        for (int i = 0; i < column.size(); i++)
        {
            String field = column.get(i).getAsString();
            this.fields.add(field);
        }
    }

    public void parseConfig(JsonObject relationData)
            throws Exception
    {
        for (Map.Entry<String, JsonElement> data : relationData.entrySet())
        {
            String key = (String)data.getKey();
            JsonArray value = ((JsonElement)data.getValue()).getAsJsonArray();
            this.relations.put(key, value);
        }
    }

    public void writeToBean(String beanFilePath) {}

    protected String getTypeFromParam(String[] param)
    {
        String type = param[1].trim();
        if (type.equals("int"))
        {
            type = "int";
        }
        else if (type.equals("str"))
        {
            type = "String";
        }
        else if (type.equals("map"))
        {
            type = "Map<Integer, " + upperFirstChar(param[0]) + "Bean>";
            this.fieldTypes.put(type, connectStr(param, 2));
        }
        else if (param[1].trim().equals("arr"))
        {
            if (param[2].trim().equals("int"))
            {
                type = "ArrayList<Integer>";
            }
            else if (param[2].trim().equals("str"))
            {
                type = "ArrayList<String>";
            }
            else
            {
                type = "ArrayList<" + upperFirstChar(param[0]) + "Bean>";
                this.fieldTypes.put(type, connectStr(param, 2));
            }
        }
        return type;
    }

    public String connectStr(String[] param, int index)
    {
        String str = param[index];
        index++;
        while (index < param.length)
        {
            str = str + ":" + param[index];
            index++;
        }
        return str;
    }

    protected void buildField() {}

    protected void buildInterClass(JsonArray jsonArray, String interClassName) {}

    protected String upperFirstChar(String value)
    {
        String retStr = value.substring(0, 1).toUpperCase() + value.substring(1);
        return retStr;
    }
}

