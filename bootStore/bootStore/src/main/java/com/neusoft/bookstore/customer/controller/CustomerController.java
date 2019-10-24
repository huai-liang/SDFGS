package com.neusoft.bookstore.customer.controller;

import com.neusoft.bookstore.base.LoginResult;
import com.neusoft.bookstore.base.Response;
import com.neusoft.bookstore.base.VerifyCode;
import com.neusoft.bookstore.customer.model.Customer;
import com.neusoft.bookstore.customer.service.CustomerService;
import com.neusoft.bookstore.util.SessionUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;

@Api(description = "用户相关controller")
@RequestMapping("customer")
@Controller
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    /**
     * 注册
     * @param customer
     * @return
     */
    @ApiOperation(value = "用户注册")
    @ResponseBody
    @PostMapping("/saveCustomer")
    public Response saveCustomer(@RequestBody Customer customer,HttpServletRequest request){
        HttpSession session=request.getSession();
        VerifyCode verifyCode = (VerifyCode) session.getAttribute(customer.getPhone());
        Response response = customerService.saveCustomer(customer,verifyCode);
        return response;
    }
    /**
     * 验证手机号
     */
    @ApiOperation(value = "验证手机号")
    @ResponseBody
    @PostMapping("/checkPhone")
    public Response checkPhone(@RequestBody Customer customer){
        Response response=customerService.checkPhone(customer);
        return response;
    }
    /**
     * 验证用户名
     */
    @ApiOperation(value = "验证用户名")
    @ResponseBody
    @PostMapping("/checkUserAcct")
    public Response checkUserAcct(@RequestBody Customer customer){
        Response response=customerService.checkUserAcct(customer);
        return response;
    }
    @ApiOperation(value = "模拟发送验证码")
    @ResponseBody
    @PostMapping("/sendVerifyCode")
    public Response sendVerifyCode(String phoneNum, HttpServletRequest request){
        VerifyCode verifyCode=customerService.sendVerifyCode(phoneNum);
        HttpSession session=request.getSession();
        session.setAttribute(verifyCode.getPhoneNum(),verifyCode);
        VerifyCode vc=(VerifyCode)session.getAttribute(verifyCode.getPhoneNum());
        System.out.println(vc);
        return Response.ok("发送成功");
    }
    /**
     * 登入
     * @param customer
     * @return
     */
    @ApiOperation(value = "用户登录")
    @PostMapping("/loginCustomer")
    @ResponseBody
    public Response loginCustomer(HttpServletRequest request,@RequestBody Customer customer){
        Response response=customerService.loginCustomer(customer);
        LoginResult loginResult = (LoginResult)response.getData();
        if(Objects.nonNull(loginResult)){
            SessionUtil.setSession(request,loginResult);
        }
        return response;
    }
    /**
     * 重置密码发送验证码
     * @param phoneNum
     * @return
     */
    @ApiOperation(value = "重置密码发送验证码")
    @PostMapping("/sendCodeByResetPassword")
    @ResponseBody
    public Response sendCodeByResetPassword(String phoneNum,HttpServletRequest request){
        HttpSession session=request.getSession();
        Response response = customerService.sendCodeByResetPassword(phoneNum);
        VerifyCode verifyCode=(VerifyCode) response.getData();
        session.setAttribute(phoneNum,verifyCode);
        return response;
    }
    /**
     * 重置密码
     * @param customer
     * @return
     */
    @ApiOperation(value = "重置密码")
    @PostMapping("/resetPassword")
    @ResponseBody
    public Response resetPassword(@RequestBody Customer customer,HttpServletRequest request){
        HttpSession session=request.getSession();
        VerifyCode verifyCode = (VerifyCode) session.getAttribute(customer.getPhone());
        Response response=customerService.resetPassword(customer,verifyCode);
        return response;
    }
    /**
     * 新增用户
     * @param customer
     * @return
     */
    @ApiOperation(value = "新增用户")
    @PostMapping("/insertCustomer")
    @ResponseBody
    public Response insertCustomer(HttpServletRequest request, @RequestBody Customer customer){
        LoginResult loginResult = SessionUtil.getSession(request,customer.getToken());
//        customer.setCreateBy(String.valueOf(loginResult.getUserId()));
        customer.setCreateBy("1");
        customer.setLastModifiedBy("1");
        Response response = customerService.insertCustomer(customer);
        return response;
    }
    /**
     * 修改用户
     * @param customer
     * @return
     */
    @ApiOperation(value = "修改用户")
    @PostMapping("/updateCustomer")
    @ResponseBody
    public Response updateCustomer(@RequestBody Customer customer){
        Response response = customerService.updateCustomer(customer);
        return response;
    }
    /**
     * 删除用户
     * @param customer
     * @return
     */
    @ApiOperation(value = "删除用户")
    @PostMapping("/deleteCustomer")
    @ResponseBody
    public Response deleteCustomer(@RequestBody Customer customer){
        Response response = customerService.deleteCustomer(customer.getIdList());
        return response;
    }
    /**
     * 查看详情
     * @param customer
     * @return
     */
    @ApiOperation(value = "查看详情")
    @PostMapping("/getCustomerById")
    @ResponseBody
    public Response getCustomerById(@RequestBody Customer customer){
        Response response = customerService.getCustomerById(customer);
        return response;
    }
    /**
     * 分页查询用户列表
     * @param customer
     * @return
     */
    @ApiOperation(value = "分页查询用户列表")
    @PostMapping("/listCustomerByPage")
    @ResponseBody
    public Response listCustomerByPage(@RequestBody Customer customer){
        Response response = customerService.listCustomerByPage(customer);
        return response;
    }
    /**
     * 设置用户积分
     * @param customer
     * @return
     */
    @ApiOperation(value = "设置用户积分")
    @PostMapping("/updateCustomerScore")
    @ResponseBody
    public Response updateCustomerScore(@RequestBody Customer customer){
        Response response = customerService.updateCustomerScore(customer);
        return response;
    }
}
