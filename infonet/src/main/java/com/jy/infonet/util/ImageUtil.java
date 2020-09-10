package com.jy.infonet.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.UUID;

/**
 * 上传图片的工具类
 * 上传图片不会很频繁，使用单例模式，只需要一个工具类即可
 */

public class ImageUtil {
    private static volatile ImageUtil imageUtil;
    private Logger logger = LoggerFactory.getLogger(ImageUtil.class);

    private ImageUtil(){}

    public static ImageUtil getInstance(){
        if (imageUtil==null){
            synchronized(ImageUtil.class){
                if (imageUtil==null){
                    imageUtil = new ImageUtil();
                }
            }
        }
        return imageUtil;
    }

    public HashMap<String,String> uploadImage(String dir, MultipartFile file){
        HashMap<String,String> map = new HashMap<>();
        if (file.isEmpty()) {
            map.put("error","文件为空！");
            return map;
        }
        try {
            //上传图片
            byte[] bytes = file.getBytes();
            String filename = file.getOriginalFilename();
            long size = file.getSize();
            String ext = filename.substring(filename.lastIndexOf(".")+1);
            if (!isImage(file)){
                map.put("error", "图片格式有误!");
                logger.info("上传图片{}图片格式有误!",filename);
            } else{
                filename = dir + UUID.randomUUID().toString()+"."+ext;//uuid命名图片避免重复命名
                Path path = Paths.get(filename);
                Files.write(path, bytes);
                map.put("filename", filename);
                map.remove("error");
                logger.info("上传图片{}成功!",filename);
            }
        } catch (IOException e) {
            e.printStackTrace();
            map.put("error",e.getMessage());
        }
        return map;
    }

    public boolean deleteImage(String dir){
         Path path = Paths.get(dir);
        try {
            if (Files.exists(path)) {
                Files.delete(path);
                logger.info("删除图片{}成功!",dir);
                return true;
            }else logger.info("删除图片{}路径不存在!",dir);
        } catch (IOException e) {
            return false;
        }
        return false;
    }

    //后缀名判断file是否为图片
    public static boolean isImage(MultipartFile file){
        if (file.isEmpty()) return false;
        String filename = file.getOriginalFilename();
        String ext = filename.substring(filename.lastIndexOf(".")+1);
        return  ext.equals("png") || ext.equals("jpg") || ext.equals("jpeg") || ext.equals("gif") ;
    }

    //处理：路径+文件名
    public String dirImageName(String dir,String imageName){
        return dir + imageName;
    }

    //处理：去掉路径，只保留简单文件名
    public String noDirImageName(String dir,String imageName){
        return imageName.substring(dir.length(),imageName.length());
    }

}
