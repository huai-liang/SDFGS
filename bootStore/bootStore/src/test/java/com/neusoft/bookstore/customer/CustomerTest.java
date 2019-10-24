package com.neusoft.bookstore.customer;

import com.neusoft.bookstore.base.BookStoreApplicationTests;
import com.neusoft.bookstore.base.Response;
import com.neusoft.bookstore.base.VerifyCode;
import com.neusoft.bookstore.customer.model.Customer;
import com.neusoft.bookstore.customer.service.CustomerService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class CustomerTest extends BookStoreApplicationTests {

    @Autowired
    private CustomerService customerService;

    /**
     * 测试注册
     */
    @Test
    public void saveCustomerTest(){
        Customer customer = new Customer();
        customer.setUserName("王五");
        customer.setUserAcct("wangwu");
        customer.setPhone("18145874563");
        customer.setUserPwd("abc123");
        customer.setEmail("wangwu@qq.com");
        customer.setIdCard("321456987654789321");
        customer.setRemark("第一次注册");
        customer.setClientId("1236");
        customer.setCreateBy("王五");
        customer.setLastModifiedBy("王五");
        customer.setSex(1);
        VerifyCode verifyCode=new VerifyCode();
        Response response = customerService.saveCustomer(customer,verifyCode);
        System.out.println("cood =" +response.getCode()+"msg ="+response.getMsg());
    }
}
