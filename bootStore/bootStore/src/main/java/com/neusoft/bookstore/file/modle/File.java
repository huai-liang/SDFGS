package com.neusoft.bookstore.file.modle;

import lombok.Data;

@Data
public class File {
    private Long id;
    private String fileName;
    private String fileUrl;
    private String filePath;
    private Long businessId;
    private Integer businessType;
}
