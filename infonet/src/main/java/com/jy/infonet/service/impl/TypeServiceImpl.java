package com.jy.infonet.service.impl;

import com.jy.infonet.dao.TypesMapper;
import com.jy.infonet.entity.Types;
import com.jy.infonet.entity.TypesExample;
import com.jy.infonet.service.TypeService;
import com.jy.infonet.util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TypeServiceImpl implements TypeService {
   @Autowired
    private TypesMapper typeDao;

   @Value("${upload.folder.type}")
   private String TYPE_COVER_FOLDER;

    @Override
    public Types getTypeById(Integer tid) {
        return typeDao.selectByPrimaryKey(tid);
    }

    @Override
    public List<Types> getAllTypes() {
        return typeDao.selectByExample(null);
    }

    @Override
    public String deleteTypeById(int tid,Integer op) {
        Types type = typeDao.selectByPrimaryKey(tid);
        if (type!=null) type.setDeleted(op);
        if (typeDao.updateByPrimaryKey(type)==1){
            return op==1?"删除成功（24小时之后生效）":"撤销删除成功！";
        }else return "操作失败";
    }

    @Override
    public String updateTypeById(Types type,MultipartFile file) {
        if (file!=null && !file.isEmpty()){
            Types types = typeDao.selectByPrimaryKey(type.getTypeId());
            ImageUtil imageUtil = ImageUtil.getInstance();
            Map<String, String> map = imageUtil.uploadImage(TYPE_COVER_FOLDER, file);
            if ( !map.containsKey("error") && imageUtil.deleteImage(  imageUtil.dirImageName(TYPE_COVER_FOLDER,types.getTypeCover())  ) ){
                type.setTypeCover(  imageUtil.noDirImageName(TYPE_COVER_FOLDER,map.get("filename"))  );
            }else return "封面更新失败！";
        }
        return typeDao.updateByPrimaryKeySelective(type)==1?"更新类别成功！":"增加类别异常！";
    }

    @Override
    public String addType(MultipartFile file, Types type) {
        if (type.getTypeName()==null || "".equals(type.getTypeName())) return "类别名儿不能为空";
        Map<String, String> map = ImageUtil.getInstance().uploadImage(TYPE_COVER_FOLDER, file);
        if (!map.containsKey("error")){
            type.setTypeCover(  ImageUtil.getInstance().noDirImageName(TYPE_COVER_FOLDER, map.get("filename") ) );
            return typeDao.insertSelective(type)==1?"增加类别成功！":"增加异常！";
        }else return "封面上传失败！";
    }
}
