package com.todaynews.ssm2.controller;

import com.todaynews.ssm2.utils.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @Description:
 * @Author: LiBo
 * @Date: 2018/4/20下午 05:36
 */
@Controller
public class FileController {

    //上传文件的相对路径（相对路径是相对于static）
    //如果路径为不加斜杠的相对路径：images/upload，则在回显时请求的url是相对于当前请求url的上一级路径。
    //如果路径为加斜杠的相对路径：/images/upload，则在回显时请求的url是相对于当前应用的url入口。
    private static final String UPLOAD_PATH = "/images/upload/";

    //静态属性会先加载，所以目录static/images/upload/必须存在，否则会报空指针异常
    //上传文件的绝对路径
    private static final String BASE_PATH = FileController.class
            .getClassLoader()
            .getResource("static/images/upload/")
            .getPath();

    @RequestMapping(path = "/uploadImage", method = RequestMethod.POST)
    @ResponseBody
    public Result upload(MultipartFile file){

        Result result = new Result();

        //图片存放的目录为：classpath:static/images/upload/
        File base = new File(BASE_PATH);
        //静态属性会先加载，所以目录static/images/upload/必须存在，否则会报空指针异常
        /*if (!base.exists()){
            base.mkdirs();
        }*/

        //获取文件名
        String originalFilename = file.getOriginalFilename();

        //最终存储时的文件名
        String fileName = UUID.randomUUID() + originalFilename;

        //最终存储位置
        File dest = new File(base, fileName);

        //上传文件
        try {
            file.transferTo(dest);
            result.setCode(0);
            result.setMsg(UPLOAD_PATH + fileName);
        } catch (IOException e) {
            result.setCode(-1);
            result.setMsg("文件上传失败");
            e.printStackTrace();
        }

        return result;
    }
}
