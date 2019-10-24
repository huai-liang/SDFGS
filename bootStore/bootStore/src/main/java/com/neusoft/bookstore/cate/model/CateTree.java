package com.neusoft.bookstore.cate.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CateTree {
    private Long id;
    private String name;
    private Long pid;
    private Integer level;
    private String remark;
    private List<CateTree> children = new ArrayList<>();
}
