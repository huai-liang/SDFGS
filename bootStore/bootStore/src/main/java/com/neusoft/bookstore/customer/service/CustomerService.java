package com.neusoft.bookstore.customer.service;

import com.neusoft.bookstore.base.Response;
import com.neusoft.bookstore.base.VerifyCode;
import com.neusoft.bookstore.customer.model.Customer;

import java.util.List;

public interface CustomerService {
    /**
     * 注册
     * @param customer
     * @return
     */
    Response saveCustomer(Customer customer,VerifyCode verifyCode);

    /**
     * 验证手机号是否存在
     * @param customer
     * @return
     */
    Response checkPhone(Customer customer);

    /**
     * 验证用户名是否存在
     * @param customer
     * @return
     */
    Response checkUserAcct(Customer customer);
    /**
     * 模拟发送验证码
     * @param phoneNum
     * @return
     */
    VerifyCode sendVerifyCode(String phoneNum);

    /**
     * 用户登录
     * @param customer
     * @return
     */
    Response loginCustomer(Customer customer);

    /**
     * 重置密码发送验证码
     * @param phoneNum
     * @return
     */
    Response sendCodeByResetPassword(String phoneNum);
    /**
     * 重置密码
     * @param verifyCode
     * @return
     */
    Response resetPassword(Customer customer,VerifyCode verifyCode);
    /**
     * 新增用户
     * @param customer
     * @return
     */
    Response insertCustomer(Customer customer);
    /**
     * 修改用户
     * @param customer
     * @return
     */
    Response updateCustomer(Customer customer);
    /**
     * 删除用户
     * @param idList
     * @return
     */
    Response deleteCustomer(List<Long> idList);
    /**
     * 查看详情
     * @param customer
     * @return
     */
    Response getCustomerById(Customer customer);
    /**
     * 分页查询用户列表
     * @param customer
     * @return
     */
    Response listCustomerByPage(Customer customer);
    /**
     * 设置用户积分
     * @param customer
     * @return
     */
    Response updateCustomerScore(Customer customer);
}
