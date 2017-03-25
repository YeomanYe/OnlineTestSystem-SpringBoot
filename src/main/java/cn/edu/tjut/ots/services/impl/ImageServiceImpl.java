package cn.edu.tjut.ots.services.impl;

import cn.edu.tjut.ots.dao.ImageDao;
import cn.edu.tjut.ots.dao.SubjectDao;
import cn.edu.tjut.ots.po.Image;
import cn.edu.tjut.ots.po.Subject;
import cn.edu.tjut.ots.services.ImageService;
import cn.edu.tjut.ots.utils.CreateUserBy;
import cn.edu.tjut.ots.utils.EmptyUtil;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by KINGBOOK on 2017/3/23.
 */
@Service
@Scope("singleton")
public class ImageServiceImpl implements ImageService {
    @Resource
    ImageDao imageDao;

    @Resource
    SubjectDao subjectDao;

    @Override
    public List queryImageBySubjectId(String subjectId) {
        //如果为空字段，则返回空数组
        if(EmptyUtil.isFieldEmpty(subjectId))return new ArrayList();
        Subject subject = subjectDao.querySubjectById(subjectId);
        String imgId = "";
        if (!EmptyUtil.isFieldEmpty(subject.getImgId())) imgId = subject.getImgId();
        return imageDao.queryImageById(imgId);
    }

    @Override
    public void saveImage(String subjectId, MultipartFile file, String username, String realPath) {
        if (EmptyUtil.isObjEmpty(file)) return;
        //查询上个图片是否存在，若果存在则删除
        Subject lastSub = subjectDao.querySubjectById(subjectId);
        String lastImgId = lastSub.getImgId();
        if(!EmptyUtil.isFieldEmpty(lastImgId)){
            deleteImage(lastImgId, realPath);
        }
        //生成新图片的UUID
        String imgId = UUID.randomUUID().toString().replace("-", "");
        String path = new File("src/main/resources/static/images/subjectImages").getAbsolutePath();
        //保存图片到文件中;需要保存到资源文件夹中和部署文件夹中
        OutputStream os = null;
        String suffix = file.getOriginalFilename().split("\\.")[1];
        String presentName = Long.toString(System.currentTimeMillis()) + "." + suffix;
        String absPath = path + File.separator + presentName;
        String relPath = "images" + File.separator + "subjectImages" + File.separator + presentName;

        //保存图片信息到数据库
        Image image = new Image();
        image.setUuid(imgId);
        image.setFileType(suffix);
        image.setFileSize(file.getSize());
        image.setFormerName(file.getOriginalFilename());
        image.setPresentName(presentName);
        image.setAbsPath(absPath);
        image.setRelPath(relPath);
        CreateUserBy.setUser(image, null, username);
        imageDao.addImage(image);
        //更新试题中的imgId
        Subject subject = new Subject();
        subject.setImgId(imgId);
        subject.setUuid(subjectId);
        subjectDao.updateImageId(subject);
        //保存文件
        try {
            //保存到资源文件夹中
            //如果不存在文件夹，则创建
            File pathFile = new File(path);
            if (!pathFile.exists())
                pathFile.mkdirs();
            byte[] fileBytes = file.getBytes();
            os = new FileOutputStream(absPath);
            os.write(fileBytes);
            os.flush();
            os.close();
            //保存到部署文件夹中
            pathFile = new File(realPath + "images" + File.separator + "subjectImages" + File.separator);
            if(!pathFile.exists())
                pathFile.mkdirs();
            os = new FileOutputStream(realPath + relPath);
            os.write(fileBytes);
            os.flush();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteImage(String imageId, String realPath) {
        if(!EmptyUtil.isFieldEmpty(imageId)){
            List<Image> images = imageDao.queryImageById(imageId);
            Image image = images.get(0);
            if(!EmptyUtil.isObjEmpty(image)){
                //删除资源文件夹中的图片
                String filePath = image.getAbsPath();
                File file = new File(filePath);
                if(file.exists()) file.delete();
                //删除部署文件夹中的图片
                filePath = realPath + image.getRelPath();
                file = new File(filePath);
                if(file.exists()) file.delete();
                imageDao.deleteImage(imageId);
            }
        }
    }

    @Override
    public void downloadImage(String imageId, OutputStream os) {
        Image image = imageDao.queryImageById(imageId).get(0);
        String absPath = image.getAbsPath();
        FileInputStream fis = null;
        byte[] b = new byte[1024];
        try {
            fis = new FileInputStream(absPath);
            int len = 0;
            while((len = fis.read(b))!=-1){
                os.write(b,0,len);
            }
            os.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getImageName(String imageId) {
        return imageDao.queryImageById(imageId).get(0).getFormerName();
    }
}
