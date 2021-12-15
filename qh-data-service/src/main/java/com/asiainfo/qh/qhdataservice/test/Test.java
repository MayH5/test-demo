package com.asiainfo.qh.qhdataservice.test;

import java.io.*;

public class Test {

    public static void main(String[] args) {
        File file  = new File("C:/Users/24432/Desktop/createSQL1/createSQL1");
        File[] files = file.listFiles();
        BufferedReader bufferedReader = null;
        String content = null;
        StringBuffer contentTotal = new StringBuffer();


        for (File singleFile:files) {
            StringBuffer sb = new StringBuffer();
            String fileNameExceptSuffix = singleFile.getName().replace(".txt","");

            System.out.println(fileNameExceptSuffix+"============="+fileNameExceptSuffix);

            String[] tabNameAttr = fileNameExceptSuffix.split("#");

            try {
                bufferedReader = new BufferedReader(new FileReader(singleFile));
                try {
                    while ((content=bufferedReader.readLine())!=null){
                        //content = content.replace(tabNameAttr[0],tabNameAttr[1]);
                        sb.append(content);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            if(sb!=null && !sb.toString().equals("")) {
                System.out.println("替换前的SQL-----------"+sb.toString());

                //替换表名
                String singleCreateSql = sb.toString();
                singleCreateSql = singleCreateSql.replace(tabNameAttr[0],tabNameAttr[1]);

                //截取有效SQL
                int endIndex = singleCreateSql.indexOf("COMPRESS");
                if (endIndex != -1) {
                    singleCreateSql = singleCreateSql.substring(0, endIndex) + ";\n";
                }else{
                    endIndex = singleCreateSql.indexOf("DISTRIBUTE");
                    if(endIndex!=-1){
                        singleCreateSql = singleCreateSql.substring(0, endIndex) + ";\n";
                    }
                }
                System.out.println("替换后的SQL-----------"+singleCreateSql);
                contentTotal.append(singleCreateSql);
            }
            try {
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }




        //执行完 写入
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("D:/create.sql"));
            bufferedWriter.write(contentTotal.toString());
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
