package com.neusoft.bookstore.shuffling.model;

import com.neusoft.bookstore.base.PageRequest;
import com.neusoft.bookstore.file.modle.File;
import lombok.Data;

import java.util.Date;

@Data
public class Shuffling extends PageRequest {
    private Long id;
    private String url;
    private String filePath;
    private String status;
    private Integer isDeleted;
    private Integer sortNo;
    private Date gmtCreate;
    private String createBy;
    private Date gmtModified;
    private String lastModifiedBy;
    private Integer version;
}
