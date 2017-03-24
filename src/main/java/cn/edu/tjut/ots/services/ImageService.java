package cn.edu.tjut.ots.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.OutputStream;
import java.util.List;

/**
 * Created by KINGBOOK on 2017/3/23.
 */
public interface ImageService {
    /**
     * 通过ID查询图片信息
     * @param subjectId
     * @return
     */
    public List queryImageBySubjectId(String subjectId);

    /**
     * 保存图片
     * @param subjectId
     * @param file
     * @param username
     * @param realPath
     */
    public void saveImage(String subjectId, MultipartFile file, String username, String realPath);

    /**
     * 删除图片
     * @param imageId
     */
    public void deleteImage(String imageId);

    /**
     * 下载图片
     * @param imageId
     * @param os
     */
    public void downloadImage(String imageId, OutputStream os);

    /**
     * 获取图片文件名
     * @param imageId
     * @return
     */
    public String getImageName(String imageId);
}
