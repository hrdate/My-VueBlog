package com.vueblog.controller;

import com.vueblog.annotation.OptLog;
import com.vueblog.common.lang.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

import static com.vueblog.common.constant.OptTypeConst.REMOVE;
import static com.vueblog.common.constant.OptTypeConst.SAVE;

@Api(tags = "文件管理模块")
@RestController
@RequestMapping("/file")
public class FileController {

    @OptLog(optType =  SAVE)
    @ApiOperation("上传文件")
    @PostMapping(value = "/upload")
    public Result fileUpload(@RequestParam(value = "file") MultipartFile file) {
        if (file.isEmpty()) {
            System.out.println("文件为空");
        }
        String fileName = file.getOriginalFilename();  // 文件名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀名
        String filePath = "src/main/resources/static/"; // 上传后的路径
        fileName = UUID.randomUUID() + suffixName; // 新文件名
        File dest = new File(filePath + fileName);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String filename = "/static/" + fileName;
        return Result.succ(filename);
    }

}
