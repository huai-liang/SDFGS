package com.neusoft.bookstore.file.service.impl;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.neusoft.bookstore.base.Response;
import com.neusoft.bookstore.file.mapper.FileMapper;
import com.neusoft.bookstore.file.modle.File;
import com.neusoft.bookstore.file.service.FileService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private FastFileStorageClient fastFileStorageClient;

    @Autowired
    private FileMapper fileMapper;

    @Value("${fdfs.hostPort}")
    private String hostPort;

    /**
     * 上传文件
     * @param file
     * @return
     */
    @Override
    public Response uploadFile(MultipartFile file) {
        try{
            //调上传文件的接口 1、文件输入流 2、文件大小 3、文件拓展名 4、集合元素
            StorePath storePath = fastFileStorageClient.uploadFile(file.getInputStream(),file.getSize(),
                    FilenameUtils.getExtension(file.getOriginalFilename()),null);
            //文件所在服务器的地址
            String filePath = storePath.getFullPath();
            //http文件地址
            String fileUrl = hostPort + storePath.getFullPath();
            File file1 = new File();
            file1.setFilePath(filePath);
            file1.setFileUrl(fileUrl);
            return Response.ok(file1,"上传成功");
        }catch (Exception e){
            return Response.ok("上传失败");
        }
    }
    /**
     * 删除文件
     * @param file
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Response deleteFile(File file) {
        //带文件id
        if (null != file.getId()){
            //删除数据库中的文件
            fileMapper.deleteFileById(file);
        }
        //不带文件id
        if (null != file.getFilePath()){
            //删除服务器文件
            fastFileStorageClient.deleteFile(file.getFilePath());
            return Response.ok("删除成功");
        }else {
            return Response.error("参数传递错误");
        }
    }
}
