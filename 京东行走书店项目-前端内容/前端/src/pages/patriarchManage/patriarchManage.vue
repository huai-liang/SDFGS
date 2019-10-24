<!--Add 家长端信息管理 by zheng_r 20180507-->
<template>
  <div class="patriarchManage">
    <!-- 搜索区域 -->
    <el-card class="searchModule">
      <el-form :model="query" label-width="100px" :inline="true" ref="queryFrom" :rules="rules">
        <el-form-item label="家长账号" prop="user_acct">
          <el-input v-model="query.user_acct" clearable></el-input>
        </el-form-item>
        <el-form-item label="家长电话" prop="patriarch_phone">
          <el-input v-model="query.patriarch_phone" clearable></el-input>
        </el-form-item>
        <el-form-item label="门店编码" prop="storeNum">
          <el-input v-model="query.storeNum" clearable></el-input>
        </el-form-item>
        <el-form-item label="门店名称" prop="store_name">
          <el-input v-model="query.store_name" clearable></el-input>
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
        <!-- <el-button type="primary" icon="el-icon-delete" @click="delAccount()" class="clickBtn">删除</el-button> -->
      </div>
      <!--修改密码 -->
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
      <!--内容-->
      <sirui-table
        class="operateTable"
        :page="page"
        :loading="load"
        :data="tableData"
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
        ></el-table-column>
      </sirui-table>
    </div>
  </div>
</template>
<script>
import { mapState } from "vuex";
import SiruiTable from "@/components/SiruiTable";
import API from "@/api/patriarchManage";

export default {
  components: {
    SiruiTable
  },
  name: "patriarch",
  data() {
    //密码
    let num = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$/;
    let validatePassWord = (rule, value, callback) => {
      if (value === "") {
        return callback(new Error("请输入密码"));
      }
      if (!num.test(value)) {
        return callback(new Error("请输入6-16位字母+数字"));
      } else {
        if (this.editPwd.passWordCheck !== "") {
          this.$refs.editPwd.validateField("passWordCheck");
        }
        callback();
      }
    };
    //密码确认
    var validatePassWordCheck = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请再次输入密码"));
      } else if (value !== this.editPwd.passWord) {
        callback(new Error("两次输入密码不一致!"));
      } else {
        callback();
      }
    };
    return {
      query: {
        user_acct: "",
        store_name: "",
        patriarch_phone: "",
        storeNum: ""
      },
      load: false,
      skuLoad: false,
      menuButtonList: [],
      columnList: [
        { prop: "patriarch_no", label: "家长编号" },
        { prop: "user_acct", label: "家长账号" },
        { prop: "patriarch_phone", label: "家长电话" },
        { prop: "store_name", label: "所属门店" },
        { prop: "gmt_create", label: "注册时间" },
        { prop: "store_no", label: "门店编码" }
      ],
      editPwd: {
        passWord: "",
        passWordCheck: ""
      },
      dialogPwdEdit: false,
      passwordType: "password",
      passWordCheck: "password",
      rules: {
        //表单验证
        patriarch_name: [
          // {
          //   min: 1,
          //   max: 40,
          //   message: "长度在 1 到 40 个字符",
          //   trigger: "blur"
          // }
          {
            trigger: "blur",
            required: false,
            pattern: /^[^0-9`!@#$]{1,20}$/,
            message: "特殊字符和数字无效,长度不超过20字符",
            trigger: "blur"
          }
        ],
        patriarch_phone: [
          // {
          //   trigger: "blur",
          //   required: false,
          //   pattern: /^((0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/,
          //   message: "请输入正确的固定电话",
          //   trigger: "blur"
          // },
          // {
          //   trigger: "blur",
          //   required: false,
          //   pattern: /^((0\d{2,3}-\d{7,8})|(1[3584]\d{9}))$/,
          //   message: "请输入正确的联系方式",
          //   trigger: "blur"
          // }
        ],
        passWord: [
          { required: true, validator: validatePassWord, trigger: "blur" }
        ],
        passWordCheck: [
          { required: true, validator: validatePassWordCheck, trigger: "blur" }
        ]
      },

      tableData: [],
      tableHeight: document.body.scrollHeight - 460,
      page: {
        pages: 0, //总共多少页
        pageNum: 1, //第几页
        pageSize: 10, //一页多少条数据
        total: 0 //总共多少条
      },
      dialogPwdEdit: false,
      multipleSelection: [] //存放选中的值
    };
  },
  computed: {
    ...mapState({
      userInfo: state => state.userInfo
    })
  },
  /**页面必要代码Start */
  watch: {
    pageNum: function() {
      this.getData();
    },
    pageSize: function() {
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
        BUTTON_CODE: ""
      };
      this.$axios.post(this.$store.state.getButtonUrl, params).then(res => {
        if (0 == res.data.code) {
          this.menuButtonList = res.data.data;
        } else {
          this.$message({
            showClose: true,
            message: res.data.msg,
            type: "error"
          });
        }
      });
    },
    // 选择按钮方法
    chooseClick(method) {
      if ("delAccount" == method) {
        this.delAccount();
      } else if ("editPassword" == method) {
        this.editPassword();
      }
    },
    //修改密码按钮
    editPassword() {
      if (1 == this.multipleSelection.length) {
        this.dialogPwdEdit = true;
      } else {
        this.$message({
          showClose: true,
          message: "请选择一条数据进行修改",
          type: "error"
        });
      }
    },
    showPwd() {
      if (this.passwordType === "password") {
        this.passwordType = "";
      } else {
        this.passwordType = "password";
      }
      this.$nextTick(() => {
        this.$refs.password.focus();
      });
    },
    showPwdCheck() {
      if (this.passWordCheck === "password") {
        this.passWordCheck = "";
      } else {
        this.passWordCheck = "password";
      }
      this.$nextTick(() => {
        this.$refs.passWordCheck.focus();
      });
    },
    //修改密码
    editPwdSure() {
      this.$refs["editPwd"].validate(valid => {
        if (valid) {
          let params = {
            userCode: this.multipleSelection[0].user_no,
            newPwd: this.editPwd.passWord
          };
          this.loading.startLoading();
          this.$axios.post(API.PATRIARCH_UPDATE_PWD_API, params).then(res => {
            this.loading.endLoading();
            if (0 == res.data.code) {
              this.getData();
              this.$message({
                showClose: true,
                message: "修改密码成功",
                type: "success"
              });
              this.cancelDialog();
            } else {
              this.$message({
                showClose: true,
                message: res.data.msg,
                type: "error"
              });
            }
          });
        }
      });
    },
    //取消
    cancelDialog() {
      this.passwordType = "password";
      this.passWordCheck = "password";
      this.dialogPwdEdit = false;
      this.$refs["editPwd"].resetFields();
    },
    //搜索查询
    searchForm() {
      this.page.pageNum = 1;
      this.getData();
    },
    //获取数据
    getData() {
      this.query.pageSize = this.page.pageSize;
      this.query.pageNum = this.page.pageNum;
      let params = {
        pageSize: this.query.pageSize,
        pageNum: this.query.pageNum,
        user_acct: this.query.user_acct,
        store_name: this.query.store_name,
        patriarch_phone: this.query.patriarch_phone,
        store_no: this.query.storeNum
      };
      //this.query.pageSize = this.page.pageSize;
      // this.query.currentPage = this.page.pageNum;
      this.load = true;
      this.$axios.post(API.PATRIARCH_QUERY_API, params).then(res => {
        this.load = false;
        if (0 == res.data.code) {
          this.tableData = res.data.data.records;
          this.page.total = res.data.data.totalRecord;
        } else if (res.data.code == 5) {
          this.tableData = [];
          this.page.total = 0;
        } else {
          this.$message({
            showClose: true,
            message: res.data.msg,
            type: "error"
          });
        }
      });
    },
    // 重置
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
      //alert(JSON.stringify(this.multipleSelection));
    },
    clickSKURow(val) {
      this.$refs.skuTable.toggleRowSelection(val);
    },
    handleSKUSelectionChange(row) {},
    //删除
    delAccount() {
      if (this.multipleSelection.length >= 1) {
        if (0 != this.multipleSelection.length) {
          this.$confirm("此操作将永久删除该数据, 是否继续?", "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning"
          })
            .then(() => {
              let params = {
                user_no_list: []
              };
              for (var i = 0; i < this.multipleSelection.length; i++) {
                params.user_no_list.push(this.multipleSelection[i].user_no);
              }
              params.user_no_list = params.user_no_list.join(",");
              this.loading.startLoading();
              this.$axios.post(API.PATRIARCH_REMOVE_API, params).then(res => {
                this.loading.endLoading();
                if (0 == res.data.code) {
                  this.searchForm();
                  this.$message({
                    type: "success",
                    message: res.data.msg
                  });
                } else {
                  this.$message({
                    showClose: true,
                    message: res.data.msg,
                    type: "error"
                  });
                }
              });
            })
            .catch(() => {
              this.$message({
                type: "info",
                message: "已取消删除"
              });
            });
        } else {
          this.$message({
            showClose: true,
            message: "请选择数据进行删除",
            type: "error"
          });
        }
      } else {
        this.$message({
          showClose: true,
          message: "请选择一条数据进行修改",
          type: "error"
        });
      }
    }
  }
};
</script>
<style lang="scss" scoped>
// .searchBtn {
//   margin-top: 20px;
//   margin-left: 35%;
// }
.handle-box {
  margin-top: 10px;
}
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
</style>


