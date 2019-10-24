package com.neusoft.bookstore.customer.service.imp;

import com.alibaba.druid.sql.visitor.functions.If;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.neusoft.bookstore.base.LoginResult;
import com.neusoft.bookstore.base.Response;
import com.neusoft.bookstore.base.VerifyCode;
import com.neusoft.bookstore.customer.mapper.CustomerMapper;
import com.neusoft.bookstore.customer.model.Customer;
import com.neusoft.bookstore.customer.service.CustomerService;
import com.neusoft.bookstore.util.MD5Util;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerMapper customerMapper;

    /**
     * 注册
     * @param customer
     * @return
     */
    @Override
    public Response saveCustomer(Customer customer,VerifyCode verifyCode) {
        //1、非空
        //手机号
        if("".equals(customer.getPhone())||null == customer.getPhone()){
            return Response.error("手机号不能为空");
        }
        //用户名
        if("".equals(customer.getUserAcct())||null == customer.getUserAcct()){
            return Response.error("用户名不能为空");
        }
        //密码
        if("".equals(customer.getUserPwd())||null == customer.getUserPwd()){
            return Response.error("密码不能为空");
        }
        //session获取验证码
        long code=verifyCode.getVerifyCode();
        long customerCode=customer.getVerifyCode();
        //判断验证码是否正确
        if (code!=customerCode){
            return Response.error("验证码错误");
        }
        if (Objects.isNull(code)){
            return Response.error("验证码失效");
        }
        //验证码发送时间
        Date sendDate=verifyCode.getSendDate();
        //当前时间
        Date date=new Date();
        //时间差
        long abs=Math.abs((sendDate.getTime()-date.getTime())/1000);
        long time=300;
        if (abs>time){
            return Response.error("验证码失效，请重新发送");
        }
        //2、用户名是否重复
        int count = customerMapper.listUserAcctCount(customer);
        if(count>0){
            return Response.error("用户账户已经存在");
        }
        //3、手机号是否注册
        int countPhone = customerMapper.listPhoneCount(customer);
        if(countPhone>0){
            return Response.error("手机号已被注册");
        }
        //密码加密
        String pwd = MD5Util.encryption(customer.getUserPwd());
        customer.setUserPwd(pwd);
        //4、保存入库
        int saveCount = customerMapper.saveCustomer(customer);
        if(saveCount<=0){
            return Response.error("注册失败");
        }
        return Response.ok("注册成功");
    }
    /**
     * 验证手机号是否存在
     * @param customer
     * @return
     */
    @Override
    public Response checkPhone(Customer customer){
        //3、手机是否注册
        int countPhone = customerMapper.listPhoneCount(customer);
        if (countPhone>0){
            return Response.error("手机已被注册");
        }
        return Response.error("成功");
    }
    /**
     * 验证用户名是否存在
     * @param customer
     * @return
     */
    @Override
    public Response checkUserAcct(Customer customer){
        //3、手机是否注册
        int countUserAcct = customerMapper.listUserAcctCount(customer);
        if (countUserAcct>0){
            return Response.error("用户名已被注册");
        }
        return Response.error("成功");
    }
    /**
     * 模拟发送验证码
     * @param phoneNum
     * @return
     */
    @Override
    public VerifyCode sendVerifyCode(String phoneNum){
        VerifyCode verifyCode = getVerifyCode(phoneNum);
        return verifyCode;
    }
    @Override
    public Response loginCustomer(Customer customer) {
        //用户名或密码是否为空
        if (null==customer.getUserAcct()||null==customer.getUserPwd()){
            return Response.error("用户名或密码为空");
        }
        //用户账号是否存在
        Customer ct=customerMapper.getCustomerByUserAcct(customer);
        //如果存在判断密码是否正确
        if (null == ct){
            return Response.error("用户账号不存在");
        }
        //密码
        String userPwd = ct.getUserPwd();
        String up=customer.getUserPwd();
        String userP=MD5Util.encryption(up);
        //判断密码是否正确
        if (!userPwd.equals(userP)){
            return Response.error("密码错误");
        }
        LoginResult loginResult = new LoginResult();
        //用户Id
        loginResult.setUserId(ct.getId());
        //用户名称
        loginResult.setUserName(ct.getUserName());
        UUID uuid = UUID.randomUUID();
        String token = uuid.toString();
        loginResult.setToken(token);
        return Response.ok(loginResult,"登录成功");
    }
    /**
     *
     * @param phoneNum
     * @return
     */
    @Override
    public Response sendCodeByResetPassword(String phoneNum) {
        //手机号是否注册
        Customer customer=new Customer();
        customer.setPhone(phoneNum);
        int countPhone=customerMapper.listPhoneCount(customer);
        if (countPhone==0){
            return Response.error("手机号未被注册");
        }
        VerifyCode verifyCode = getVerifyCode(phoneNum);
        return Response.ok(verifyCode,"发送成功");
    }
    /**
     * 发送验证码
     * @param phoneNum
     * @return
     */
    private VerifyCode getVerifyCode(String phoneNum) {
        long random = Math.round(Math.random() * 10000);
        System.out.println("【行走书店】" + "您的验证码是：" + random + "有效期为5分钟");
        VerifyCode verifyCode = new VerifyCode();
        //验证码
        verifyCode.setVerifyCode(random);
        //手机号
        verifyCode.setPhoneNum(phoneNum);
        //发送时间
        verifyCode.setSendDate(new Date());
        return verifyCode;
    }

    @Override
    public Response resetPassword(Customer customer,VerifyCode verifyCode) {
        if(null == verifyCode){
            return Response.error("手机号不正确");
        }
        //验证手机号、验证码、密码不能为空
        if("".equals(customer.getPhone())||null==customer.getPhone()){
            return Response.error("手机号不能为空");
        }
        if (null==customer.getVerifyCode()){
            return Response.error("验证码不能为空");
        }
        if (null==customer.getUserPwd()||"".equals(customer.getUserPwd())){
            return Response.error("密码不能为空");
        }
        //判断验证码是否正确
        long cv=customer.getVerifyCode();
        long vc=verifyCode.getVerifyCode();
        if (cv!=vc){
            return Response.error("验证码错误");
        }
        //将密码加密
        String pwd = MD5Util.encryption(customer.getUserPwd());
        customer.setUserPwd(pwd);
        //修改密码
        customerMapper.updateUserPwd(customer);
        return Response.error("重置成功");
    }
    /**
     * 新增用户
     * @param customer
     * @return
     */
    @Override
    public Response insertCustomer(Customer customer) {
        //1、非空
        //手机号
        if("".equals(customer.getPhone())||null == customer.getPhone()){
            return Response.error("手机号不能为空");
        }
        //用户名
        if("".equals(customer.getUserAcct())||null == customer.getUserAcct()){
            return Response.error("用户名不能为空");
        }
        //密码
        if("".equals(customer.getUserPwd())||null == customer.getUserPwd()){
            return Response.error("密码不能为空");
        }
        //2、用户名是否重复
        int count = customerMapper.listUserAcctCount(customer);
        if(count>0){
            return Response.error("用户账户已经存在");
        }
        //3、手机号是否注册
        int countPhone = customerMapper.listPhoneCount(customer);
        if(countPhone>0){
            return Response.error("手机号已被注册");
        }
        //将密码加密
        String pwd = MD5Util.encryption(customer.getUserPwd());
        customer.setUserPwd(pwd);
        //新增用户
        customerMapper.saveCustomer(customer);
        return Response.ok("新增成功");
    }
    /**
     * 修改用户
     * @param customer
     * @return
     */
    @Override
    public Response updateCustomer(Customer customer) {
        //1、非空
        //手机号
        if("".equals(customer.getPhone())||null == customer.getPhone()){
            return Response.error("手机号不能为空");
        }
        //用户名
        if("".equals(customer.getUserAcct())||null == customer.getUserAcct()){
            return Response.error("用户名不能为空");
        }
        //2、用户名是否重复
        int count = customerMapper.listUserAcctCount(customer);
        if(count>0){
            return Response.error("用户账户已经存在");
        }
        //3、手机号是否注册
        int countPhone = customerMapper.listPhoneCount(customer);
        if(countPhone>0){
            return Response.error("手机号已被注册");
        }
        //将密码加密
        String pwd = MD5Util.encryption(customer.getUserPwd());
        customer.setUserPwd(pwd);
        customerMapper.updateCustomer(customer);
        return Response.ok("修改成功");
    }
    /**
     * 删除用户
     * @param idList
     * @return
     */
    @Override
    public Response deleteCustomer(List<Long> idList) {
        if (null==idList||idList.size()<=0){
            return Response.error("id不能为空");
        }
        customerMapper.deleteCustomer(idList);
        return Response.ok("删除成功");
    }
    /**
     * 查看详情
     * @param customer
     * @return
     */
    @Override
    public Response getCustomerById(Customer customer) {
        if (null == customer.getId()){
            return Response.error("id不能为空");
        }
        Customer customer1 = customerMapper.getCustomerById(customer);
        return Response.ok(customer1,"成功");
    }
    /**
     * 分页查询用户列表
     * @param customer
     * @return
     */
    @Override
    public Response listCustomerByPage(Customer customer) {
        if (null==customer.getPageNum()||null==customer.getPageSize()){
            return Response.error("参数传递错误");
        }
        PageHelper.startPage(customer.getPageNum(),customer.getPageSize());
        List<Customer> customerList = customerMapper.listCustomerByPage(customer);
        PageInfo<Customer> customerPageInfo=new PageInfo<>(customerList);
        return Response.ok(customerPageInfo,"查询成功");
    }
    /**
     * 设置用户积分
     * @param customer
     * @return
     */
    @Override
    public Response updateCustomerScore(Customer customer) {
        //判断参数是否为空
        if (null == customer.getId()||null == customer.getScore()){
            return Response.error("参数传递错误");
        }
        customerMapper.updateCustomerScore(customer);
        return Response.ok("设置积分成功");
    }
}
