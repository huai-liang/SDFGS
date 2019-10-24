package com.neusoft.bookstore.test.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TestMapper {

    List<String> listAccount();
}
