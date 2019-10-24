package com.neusoft.bookstore.customer.mapper;

import com.neusoft.bookstore.customer.model.Customer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Mapper
@RequestMapping("customer")
public interface CustomerMapper {

    /**
     * 查询用户账户是否存在
     * @param customer
     * @return
     */
    int listUserAcctCount(Customer customer);
    /**
     * 验证手机号是否注册
     * @param customer
     * @return
     */
    int listPhoneCount(Customer customer);
    /**
     * 注册
     * @param customer
     */
    int saveCustomer(Customer customer);
    /**
     * 根据用户账号查询用户信息
     * @param customer
     * @return
     */
    Customer getCustomerByUserAcct(Customer customer);
    /**
     * 重置密码
     * @param customer
     */
    void updateUserPwd(Customer customer);
    /**
     * 修改用户
     */
    void updateCustomer(Customer customer);
    /**
     * 删除用户
     * @param idList
     */
    void deleteCustomer(@Param("idList") List<Long> idList);
    /**
     * 查看详情
     * @param customer
     */
    Customer getCustomerById(Customer customer);
    /**
     * 分页查询用户列表
     * @param customer
     */
    List<Customer> listCustomerByPage(Customer customer);
    /**
     * 更新积分
     * @param customer
     */
    void updateCustomerScore(Customer customer);
}
