package org.example.csvutils;

import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CSVWriter
{
    private static String produceCsvData(Object[] data) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, InvocationTargetException {
        if(data.length==0)
        {
            return "";
        }

        Class classType = data[0].getClass();
        StringBuilder builder = new StringBuilder();

        Method[] methods = classType.getDeclaredMethods();

        for(Method m : methods)
        {
            if(m.getParameterTypes().length==0)
            {
                if(m.getName().startsWith("get"))
                {
                    builder.append(m.getName().substring(3)).append(',');
                }
                else if(m.getName().startsWith("is"))
                {
                    builder.append(m.getName().substring(2)).append(',');
                }

            }

        }
        builder.deleteCharAt(builder.length()-1);
        builder.append('\n');
        for(Object d : data)
        {
            for(Method m : methods)
            {
                if(m.getParameterTypes().length==0)
                {
                    if(m.getName().startsWith("get") || m.getName().startsWith("is"))
                    {
                        builder.append(m.invoke(d).toString()).append(',');
                    }
                }
            }
            builder.append('\n');
        }
        builder.deleteCharAt(builder.length()-1);
        return builder.toString();
    }

    public static boolean generateCSV(File csvFileName, Object[] data)
    {
        FileWriter fw = null;
        try
        {
            fw = new FileWriter(csvFileName);
            if(!csvFileName.exists())
                csvFileName.createNewFile();
            fw.write(produceCsvData(data));
            fw.flush();
        }
        catch(Exception e)
        {
            System.out.println("Error while generating csv from data. Error message : " + e.getMessage());
            e.printStackTrace();
            return false;
        }
        finally
        {
            if(fw!=null)
            {
                try
                {
                    fw.close();
                }
                catch(Exception e)
                {
                }
                fw=null;
            }
        }
        return true;
    }
}