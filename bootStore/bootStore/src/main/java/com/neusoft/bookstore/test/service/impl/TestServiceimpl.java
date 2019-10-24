package com.neusoft.bookstore.test.service.impl;

import com.neusoft.bookstore.test.mapper.TestMapper;
import com.neusoft.bookstore.test.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TestServiceimpl implements TestService {

    @Autowired
    private TestMapper testMapper;

    @Override
    public List<String> listAccount() {
        List<String> list = testMapper.listAccount();
        return list;
    }
}
