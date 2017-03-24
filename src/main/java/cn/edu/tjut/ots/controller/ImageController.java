package cn.edu.tjut.ots.controller;

import cn.edu.tjut.ots.services.ImageService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * Created by KINGBOOK on 2017/3/23.
 */
@Controller
@Scope("prototype")
@RequestMapping("image")
public class ImageController {
    @Resource
    ImageService imageServiceImpl;

    @ResponseBody
    @RequestMapping("queryImageBySubjectId")
    public List queryImageBySubjectId(@RequestParam("subjectId")String subjectId){
        return imageServiceImpl.queryImageBySubjectId(subjectId);
    }

    @ResponseBody
    @RequestMapping("saveImage")
    public boolean saveImage(
            HttpServletRequest req,
            HttpSession session){
        boolean bool = false;
        MultipartHttpServletRequest multReq = (MultipartHttpServletRequest) req;
        imageServiceImpl.saveImage(multReq.getParameter("subjectId"),
                multReq.getFile("subjectImgFile"), (String)session.getAttribute("username"),
                session.getServletContext().getRealPath(""));
        bool = true;
        return bool;
    }

    @ResponseBody
    @RequestMapping("removeImage")
    public boolean removeImage(@RequestParam("imageId")String imageId){
        boolean bool = false;
        imageServiceImpl.deleteImage(imageId);
        bool = true;
        return bool;
    }

    @RequestMapping("downloadImage")
    public void downloadImage(
            @RequestParam("imageId")String imageId,
            HttpServletResponse response){
        String fileName = imageServiceImpl.getImageName(imageId);
        response.setHeader("Content-Disposition", "attachment; filename="+fileName);
        response.setContentType("application/octet-stream;charset=UTF-8");
        OutputStream os = null;
        try {
            os = response.getOutputStream();
            imageServiceImpl.downloadImage(imageId,os);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
