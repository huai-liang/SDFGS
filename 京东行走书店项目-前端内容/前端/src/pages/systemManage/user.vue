<template>
  <div class="user">
    <!-- 搜索区域 -->
    <el-card class="searchModule">
      <el-form :model="query" label-width="100px" :inline="true" ref="queryFrom">
        <el-form-item label="用户账号" prop="userAcct">
          <el-input v-model="query.userAcct" clearable></el-input>
        </el-form-item>
        <el-form-item label="用户名称" prop="userName">
          <el-input v-model="query.userName" clearable></el-input>
        </el-form-item>
        <div class="searchBtn">
          <el-button type="primary" @click="searchForm()">查询</el-button>
          <el-button @click="resetForm('queryFrom')">重置</el-button>
        </div>
      </el-form>
    </el-card>
    <!--内容区域-->
    <div class="contentModule">
      <!--按钮-->
      <div class="handle-box">
        <el-button
          v-for="(item,i) in menuButtonList"
          class="operateBtn"
          :key="i"
          type="primary"
          :icon="item.buttonStyle"
          @click="chooseClick(item.buttonUrl)"
        >{{item.buttonName}}</el-button>
        <!-- <el-button type="primary" icon="el-icon-plus"    @click="addUser('addInfo')">用户新增</el-button>
        <el-button type="primary" icon="el-icon-edit"    @click="editNews()">修改信息</el-button>
        <el-button type="primary" icon="el-icon-edit"    @click="editPassword('editPwd')">修改密码</el-button>
        <el-button type="primary" icon="el-icon-delete"  @click="delUser()">删除用户</el-button>
        <el-button type="primary" icon="el-icon-check"   @click="assignRole()">指派角色</el-button> -->
      </div>
      <!--内容-->
      <sirui-table
        class="operateTable"
        :page="page"
        :loading="load"
        :data="tableData"
        :tableHeight="450"
        @handleSelectionChange="handleSelectionChange"
        @handleSizeChange="handleSizeChange"
        @handleCurrentChange="handleCurrentChange"
      >
        <el-table-column align="center" type="selection" width="45"></el-table-column>
        <el-table-column
          v-for="(item,index) in columnList"
          :key="index"
          :prop="item.prop"
          :label="item.label"
          :width="item.width"
          :sortable="item.sort"
          :fixed="item.fixed"
          align="center"
          show-overflow-tooltip
        >
          <template slot-scope="scope">
            <span
              v-if="item.prop === 'gmtCreate' || item.prop === 'gmtModified'"
            >{{scope.row[item.prop] | dateTimeFormat}}</span>
            <span v-else-if="item.prop === 'sex'">{{({'0':"女",'1':"男"})[scope.row[item.prop]]}}</span>
            <span v-else>{{scope.row[item.prop]}}</span>
          </template>
        </el-table-column>
      </sirui-table>
    </div>
    <!--新增-->
    <userAdd ref="userAdd" v-on:search="searchForm"></userAdd>
    <!--修改-->
    <userEdit ref="userEdit" v-on:getData="getData"></userEdit>
    <!-- 修改密码 -->
    <el-dialog
      title="修改密码"
      :visible.sync="dialogPwdEdit"
      width="30%"
      :close-on-click-modal="false"
      :before-close="cancelDialog"
    >
      <el-form
        :model="editPwd"
        :rules="rules"
        ref="editPwd"
        label-width="100px"
        label-position="right"
        class="changePassword"
      >
        <el-form-item label="新密码" prop="passWord">
          <el-input ref="password" :type="passwordType" v-model="editPwd.passWord"></el-input>
          <span class="show-pwd" @click="showPwd">
            <svg-icon :icon-class="passwordType === 'password' ? 'eye' : 'eye-open'"/>
          </span>
        </el-form-item>
        <el-form-item label="确认密码" prop="passWordCheck">
          <el-input ref="passWordCheck" :type="passWordCheck" v-model="editPwd.passWordCheck"></el-input>
          <span class="show-pwd" @click="showPwdCheck">
            <svg-icon :icon-class="passWordCheck === 'password' ? 'eye' : 'eye-open'"/>
          </span>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancelDialog()">取 消</el-button>
        <el-button type="primary" @click="editPwdSure()">确 定</el-button>
      </div>
    </el-dialog>
    <!-- 指派角色 -->
    <assignRole ref="assignRole"></assignRole>
  </div>
</template>
<script>
import { mapState } from 'vuex'
import API from '@/api/systemManage/user';
import SiruiTable from '@/components/SiruiTable'
import UserAdd from '@/pages/systemManage/userComponent/userAdd'
import UserEdit from '@/pages/systemManage/userComponent/userEdit'
import AssignRole from '@/pages/systemManage/userComponent/assignRole'

export default {
  components: {
    SiruiTable,
    UserAdd,
    UserEdit,
    AssignRole
  },
  name: 'user',
  data() {
    //密码
    var validatePassWord = (rule, value, callback) => {
      if (!(/^[a-zA-Z0-9]{6,16}$/.test(value))) {
        callback(new Error('请输入6-16个数字、字母'));
      } else {
        if (this.editPwd.passWordCheck !== '') {
          this.$refs.editPwd.validateField('passWordCheck');
        }
        callback();
      }
    };
    //密码确认
    var validatePassWordCheck = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'));
      } else if (value !== this.editPwd.passWord) {
        callback(new Error('两次输入密码不一致!'));
      } else {
        callback();
      }
    };
    return {
      query: {
        userAcct: "",
        userName: ""
      },
      //表格loading
      load: false,
      menuButtonList: [],
      columnList: [
        { prop: 'userAcct', label: '用户账号', sort: true, fixed: true, width: 120 },
        { prop: 'userName', label: '用户名称' },
        { prop: 'sex', label: '性别' },
        { prop: 'phone', label: '手机号码' },
        { prop: 'gmtCreate', label: '创建时间' },
        { prop: 'gmtModified', label: '修改时间' },
        { prop: 'remark', label: '备注', width: 120 }
      ],
      tableData: [],
      page: {
        pages: 0,//总共多少页
        pageNum: 1,//第几页
        pageSize: 10,//一页多少条数据
        total: 0//总共多少条
      },
      multipleSelection: [],//存放选中的值
      userCode: "",//指派角色
      editPwd: {
        passWord: '',
        passWordCheck: ''
      },
      dialogPwdEdit: false,
      passwordType: 'password',
      passWordCheck: 'password',
      //修改密码
      rules: {
        passWord: [
          { required: true, validator: validatePassWord, trigger: 'blur' }
        ],
        passWordCheck: [
          { required: true, validator: validatePassWordCheck, trigger: 'blur' }
        ]
      }
    }
  },
  computed: {
    ...mapState({
      userInfo: state => state.userInfo
    })
  },
  /**页面必要代码Start */
  watch: {
    pageNum: function () {
      this.getData();
    },
    pageSize: function () {
      this.getData();
    }
  },
  mounted() {
    this.getButton();
    this.getData();
  },
  methods: {
    //获取页面按钮
    getButton() {
      let params = {
        USER_NAME: this.userInfo.userInfo.userName,
        MENU_CODE: this.$store.state.menuCode,
        BUTTON_CODE: ''
      };
      this.$axios.post(this.$store.state.getButtonUrl, params).then(res => {
        if (0 == res.data.code) {
          this.menuButtonList = res.data.data;
        } else {
          this.$message({
            showClose: true,
            message: res.data.msg,
            type: 'error'
          });
        }
      })
    },
    //搜索
    searchForm() {
      this.page.pageNum = 1;
      this.getData()
    },
    //获取数据
    getData() {
      this.query.pageSize = this.page.pageSize;
      this.query.pageNum = this.page.pageNum;
      this.load = true;
      this.$axios.post(API.USER_LIST_API, this.query).then(res => {
        this.load = false;
        if (0 == res.data.code) {
          this.tableData = res.data.data.list;
          this.page.total = res.data.data.total;
        } else {
          this.$message({
            showClose: true,
            message: res.data.msg,
            type: 'error'
          });
        }
      })
    },
    //重置
    resetForm(from) {
      this.$refs[from].resetFields();
    },
    //每页条数
    handleSizeChange(val) {
      this.page.pageSize = val;
      this.getData();
    },
    //第几页
    handleCurrentChange(val) {
      this.page.pageNum = val;
      this.getData();
    },
    //选中触发事件
    handleSelectionChange(row) {
      this.multipleSelection = row;
    },
    /**页面必要代码End */
    //判断点击哪个按钮
    chooseClick(method) {
      if ('addUser' == method) {
        this.addUser();
      } else if ('editPassword' == method) {
        this.editPassword();
      } else if ('editNews' == method) {
        this.editUser();
      } else if ('delUser' == method) {
        this.delUser();
      } else if ('assignRole' == method) {
        this.assignRole();
      }
    },
    //新增
    addUser() {
      this.$refs.userAdd.openDialog(true);
    },
    //修改
    editUser() {
      if (1 == this.multipleSelection.length) {
        //为了防止自组件的值  影响父组件
        this.$refs.userEdit.openDialog(true, JSON.parse(JSON.stringify(this.multipleSelection[0])));
      } else {
        this.$message({
          showClose: true,
          message: "请选择一条数据进行修改",
          type: 'error'
        });
      }
    },
    //修改密码
    editPassword() {
      if (1 == this.multipleSelection.length) {
        this.dialogPwdEdit = true;
      } else {
        this.$message({
          showClose: true,
          message: "请选择一条数据进行修改",
          type: 'error'
        });
      }
    },
    showPwd() {
      if (this.passwordType === 'password') {
        this.passwordType = '';
      } else {
        this.passwordType = 'password';
      }
      this.$nextTick(() => {
        this.$refs.password.focus();
      })
    },
    showPwdCheck() {
      if (this.passWordCheck === 'password') {
        this.passWordCheck = '';
      } else {
        this.passWordCheck = 'password';
      }
      this.$nextTick(() => {
        this.$refs.passWordCheck.focus();
      })
    },
    //取消
    cancelDialog() {
      this.passwordType = 'password';
      this.passWordCheck = 'password';
      this.dialogPwdEdit = false;
      this.$refs["editPwd"].resetFields();
    },
    //修改密码
    editPwdSure() {
      this.$refs["editPwd"].validate((valid) => {
        if (valid) {
          let params = {
            userCode: this.multipleSelection[0].userCode,
            newPwd: this.editPwd.passWord
          };
          this.loading.startLoading();
          this.$axios.post(API.UPDATE_USER_PWD_API, params).then(res => {
            this.loading.endLoading();
            if (0 == res.data.code) {
              this.getData();
              this.$message({
                showClose: true,
                message: '修改密码成功',
                type: 'success'
              });
              this.cancelDialog();
            } else {
              this.$message({
                showClose: true,
                message: res.data.msg,
                type: 'error'
              });
            }
          })
        }
      })
    },
    //删除
    delUser() {
      if (0 != this.multipleSelection.length) {
        this.$confirm('此操作将永久删除该数据, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          let params = {
            userList: []
          };
          for (var i = 0; i < this.multipleSelection.length; i++) {
            params.userList.push(this.multipleSelection[i].userCode);
          }
          this.loading.startLoading();
          this.$axios.post(API.DELETE_USER_API, params).then(res => {
            this.loading.endLoading();
            if (0 == res.data.code) {
              this.searchForm();
              this.$message({
                type: 'success',
                message: '删除成功!'
              });
            } else {
              this.$message({
                showClose: true,
                message: res.data.msg,
                type: 'error'
              });
            }
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });
      } else {
        this.$message({
          showClose: true,
          message: "请选择数据进行删除",
          type: 'error'
        });
      }
    },
    //指派角色
    assignRole() {
      if (1 == this.multipleSelection.length) {
        this.$refs.assignRole.openDialog(true, this.multipleSelection[0].userCode);
      } else {
        this.$message({
          showClose: true,
          message: "请选择一条数据进行指派",
          type: 'error'
        });
      }
    },
    //设置岗位
    setPost() {
      if (1 == this.multipleSelection.length) {
        this.$refs.assignPost.openDialog(true, this.multipleSelection[0]);
      } else {
        this.$message({
          showClose: true,
          message: "请选择一条数据进行设置",
          type: 'error'
        });
      }
    },
    //设置部门
    setDepartment() {
      if (1 == this.multipleSelection.length) {
        this.$refs.assignDept.openDialog(true, this.multipleSelection[0]);
      } else {
        this.$message({
          showClose: true,
          message: "请选择一条数据进行设置",
          type: 'error'
        });
      }
    }
  }
}
</script>
<style lang="scss" scoped>
.user {
  .changePassword {
    .el-form-item {
      position: relative;
    }
    .el-input {
      width: 90%;
    }
    .show-pwd {
      position: absolute;
      right: 13%;
      top: 2px;
      font-size: 16px;
      cursor: pointer;
      user-select: none;
    }
  }
  .handle-box{
    margin-top:10px; 
  }
}
</style>