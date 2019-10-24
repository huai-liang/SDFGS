package com.neusoft.bookstore.file.controller;

import com.neusoft.bookstore.base.Response;
import com.neusoft.bookstore.file.modle.File;
import com.neusoft.bookstore.file.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * 上传文件
 */
@Api(description = "上传文件相关控制器")
@RequestMapping("file")
@Controller
public class FileController {
    @Autowired
    private FileService fileService;

    /**
     * 上传文件
     */
    @ApiOperation(value = "上传文件")
    @PostMapping("uploadFile")
    @ResponseBody
    public Response uploadFile(MultipartFile file){
        Response response=fileService.uploadFile(file);
        return response;
    }
    /**
     * 删除文件
     */
    @ApiOperation(value = "删除文件")
    @PostMapping("deleteFile")
    @ResponseBody
    public Response deleteFile(@RequestBody File file){
        Response response = fileService.deleteFile(file);
        return response;
    }
}
