package com.havens.jsonutils.json2bean;

import com.google.gson.JsonArray;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Map;

/**
 * @desc: TODO
 * @author: xuwenwu
 * @date: 2016/9/1 18:10
 */
public class JavaBean extends BaseBean{
    public void pushPackName(String packName)
    {
        super.pushPackName(packName);
        this.beanContent.append("package " + packName + ";\n\n");
        this.beanContent.append("import java.util.Map;\n");
        this.beanContent.append("import java.util.ArrayList;\n\n");
    }

    public void pushBeanName(String beanName)
    {
        super.pushBeanName(beanName);
        this.beanContent.append("public class " + this.beanName + " {\n");
    }

    protected void buildField()
    {
        boolean isMainClass = true;
        for (Map.Entry<String, JsonArray> entry : this.relations.entrySet())
        {
            String key = (String)entry.getKey();
            if (this.fields.contains(key))
            {
                JsonArray jsonArray = (JsonArray)entry.getValue();
                String upperFirstCharOnKey = upperFirstChar(key);
                if (isMainClass)
                {
                    isMainClass = false;
                    buildGetSetMethod((String)this.types.get(key), key, this.beanContent, "\t");
                    int size = jsonArray.size();
                    for (int i = 0; i < size; i++)
                    {
                        String field = jsonArray.get(i).getAsString();
                        if (this.relations.containsKey(field)) {
                            buildGetSetMethod("Map<Integer, " + upperFirstChar(field) + "Bean>", field, this.beanContent, "\t");
                        } else {
                            buildGetSetMethod((String)this.types.get(field), field, this.beanContent, "\t");
                        }
                    }
                }
                else
                {
                    buildInterClass(jsonArray, upperFirstCharOnKey);
                }
            }
        }
        for (int i = 0; i < this.interClass.size(); i++) {
            this.beanContent.append("\n" + this.interClass.get(i));
        }
        this.beanContent.append("}");
    }

    protected void buildGetSetMethod(String type, String key, StringBuilder stringBuilder, String prefix)
    {
        stringBuilder.append("\n" + prefix + "private " + type + " " + key + ";\n");
        String upperFirstCharOnKey = upperFirstChar(key);
        if (this.descs.get(key) != null) {
            stringBuilder.append(prefix + "/** " + (String)this.descs.get(key) + " */\n");
        }
        stringBuilder.append(prefix + "public " + type + " get" + upperFirstCharOnKey + "(){\n");
        stringBuilder.append(prefix + "\treturn this." + key + ";\n");
        stringBuilder.append(prefix + "}\n");
        if (this.fieldTypes.containsKey(type)) {
            buildInterFieldClass(upperFirstCharOnKey, (String)this.fieldTypes.get(type), stringBuilder, prefix);
        }
    }

    protected void buildInterClass(JsonArray jsonArray, String interClassName)
    {
        int fieldSize = jsonArray.size();
        StringBuilder classBuilder = new StringBuilder();

        classBuilder.append("\t//内部类\n");
        classBuilder.append("\tpublic class " + interClassName + "Bean {\n");
        for (int i = 0; i < fieldSize; i++)
        {
            String field = jsonArray.get(i).getAsString();
            buildGetSetMethod((String)this.types.get(field), field, classBuilder, "\t\t");
        }
        classBuilder.append("\t}\n");
        this.interClass.add(classBuilder);
    }

    protected void buildInterFieldClass(String className, String typeStr, StringBuilder stringBuilder, String prefix)
    {
        StringBuilder classBuilder = new StringBuilder();
        classBuilder.append(prefix + "//内部类\n");
        classBuilder.append(prefix + "public class " + className + "Bean {\n");
        typeStr = typeStr.substring(1, typeStr.length() - 1);
        String[] fieldTypes = typeStr.split(",");
        if ((typeStr.contains("]")) || (typeStr.contains("[")))
        {
            ArrayList<String> newFieldTypes = spliteStrToArray(fieldTypes);
            for (int i = 0; i < newFieldTypes.size(); i++)
            {
                String newTypeStr = (String)newFieldTypes.get(i);
                if ((!typeStr.contains("]")) && (!typeStr.contains("[")))
                {
                    writeJsont(fieldTypes, classBuilder, prefix + "\t");
                }
                else
                {
                    String[] tmp = newTypeStr.split(":");
                    String field1 = tmp[0];
                    String type1 = "";
                    if (tmp[1].equals("arr")) {
                        type1 = "ArrayList<" + upperFirstChar(tmp[0]) + "Bean>";
                    } else if (tmp[1].equals("map")) {
                        type1 = "Map<Integer, " + upperFirstChar(tmp[0]) + "Bean>";
                    }
                    if (!type1.equals(""))
                    {
                        classBuilder.append("\n" + prefix + "\tprivate " + type1 + " " + field1 + ";\n");
                        String upperFirstCharOnKey = upperFirstChar(field1);

                        classBuilder.append(prefix + "\tpublic " + type1 + " get" + upperFirstCharOnKey + "(){\n");
                        classBuilder.append(prefix + "\t\treturn this." + type1 + ";\n");
                        classBuilder.append(prefix + "\t}\n");
                        buildInterFieldClass(upperFirstCharOnKey, connectStr(tmp, 2), classBuilder, prefix + "\t");
                    }
                }
            }
        }
        else
        {
            writeJsont(fieldTypes, classBuilder, prefix + "\t");
        }
        classBuilder.append(prefix + "}\n");

        stringBuilder.append(classBuilder.toString());
    }

    private ArrayList<String> spliteStrToArray(String[] fieldTypes)
    {
        ArrayList<String> newFieldTypes = new ArrayList();
        String newType = fieldTypes[0];
        int mark = 0;
        for (int i = 0; i < fieldTypes.length; i++)
        {
            mark += containCharNum(fieldTypes[i], '[');
            mark -= containCharNum(fieldTypes[i], ']');
            if (!newType.equals(fieldTypes[i])) {
                newType = newType + "," + fieldTypes[i];
            }
            if (mark == 0) {
                newFieldTypes.add(newType);
            }
        }
        return newFieldTypes;
    }

    private void writeJsont(String[] fieldTypes, StringBuilder stringBuilder, String prefix)
    {
        for (int i = 0; i < fieldTypes.length; i++)
        {
            String[] fieldType = fieldTypes[i].split(":");
            String type = "String";
            if (fieldType.equals("int")) {
                type = "int";
            }
            buildGetSetMethod(type, fieldType[0], stringBuilder, prefix);
        }
    }

    private int containCharNum(String value, char ch)
    {
        int num = 0;
        for (int i = 0; i < value.length(); i++) {
            if (value.charAt(i) == ch) {
                num++;
            }
        }
        return num;
    }

    public void writeToBean(String beanFilePath)
    {
        String filePath = beanFilePath + "/" + this.packName.replace(".", "/");
        File file = new File(filePath);
        if ((!file.exists()) && (!file.isDirectory())) {
            file.mkdirs();
        }
        System.out.println("正在生成bean文件:");
        try
        {
            OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(filePath + "/" + this.beanName + ".java"), "UTF-8");Throwable localThrowable3 = null;
            try
            {
                osw.write(this.beanContent.toString());
                this.beanContent.delete(0, this.beanContent.length());
                osw.flush();
                osw.close();
                System.out.println(this.beanName + ".java");
            }
            catch (Throwable localThrowable1)
            {
                localThrowable3 = localThrowable1;throw localThrowable1;
            }
            finally
            {
                if (osw != null) {
                    if (localThrowable3 != null) {
                        try
                        {
                            osw.close();
                        }
                        catch (Throwable localThrowable2)
                        {
                            localThrowable3.addSuppressed(localThrowable2);
                        }
                    } else {
                        osw.close();
                    }
                }
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getStackTrace());
        }
    }
}

