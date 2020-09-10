package com.jy.infonet.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

/**
 * 处理资讯内容的工具类
 */
public class ContentUtil {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    private static volatile ContentUtil contentUtil;

    private ContentUtil(){}

    public static ContentUtil getInstance(){
        if (contentUtil==null){
            synchronized(ContentUtil.class){
                if (contentUtil==null){
                    contentUtil = new ContentUtil();
                }
            }
        }
        return contentUtil;
    }

    //上传资讯内容到服务器中
    public String uploadContent(String dir, String content){
        if (dir==null || content==null) return "上传异常";
        try {
            //上传资讯
            byte[] bytes = content.getBytes();
            String filename = UUID.randomUUID().toString();//将资讯内容打包成md文件持久化
            dir = dir+filename;
            Path path = Paths.get(dir);
            Files.write(path, bytes);
            return dir;
        } catch (IOException e) {
            e.printStackTrace();
            return  "上传异常";
        }
    }

    //获取资讯内容
    public String downLoadContent(String dir){
        try {
            File textFile = new File(dir);
            byte[] bytes = new byte[(int)textFile.length()];
            FileInputStream fileInputStream = new FileInputStream(textFile);
            fileInputStream.read(bytes);
            String s = new String(bytes, "UTF-8");
            System.out.println(s);
            return s;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean updateContent(String dir, String content){
        if (dir==null || content==null) return false;
        try {
            //上传资讯
            byte[] bytes = content.getBytes();
            Path path = Paths.get(dir);
            Files.write(path, bytes);
            logger.debug("===资讯内容+"+content.substring(0,10)+"...写入=="+path);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    //处理：路径+文件名
    public String dirContentName(String dir,String contentName){
        return dir + contentName;
    }

    //处理：去掉路径，只保留简单文件名
    public String noDirContentName(String dir,String contentName){
        return contentName.substring(dir.length(),contentName.length());
    }

}
