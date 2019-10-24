<!--Add 门店信息管理 by zheng_r 20180507-->
<template>
  <div class="shopManage">
    <!-- 搜索区域 -->
    <el-card class="searchModule">
      <el-form :model="query" label-width="100px" :inline="true" ref="queryFrom" :rules="rules">
        <el-form-item label="校边店编号" prop="schoolStoreNum">
          <el-input v-model="query.schoolStoreNum" clearable></el-input>
        </el-form-item>
        <el-form-item label="店名" prop="storeName">
          <el-input v-model="query.storeName" clearable></el-input>
        </el-form-item>
        <el-form-item label="所在省份" prop="province">
          <el-select v-model="query.province" filterable placeholder="请选择" @change="provinceChange">
            <el-option
              v-for="item in optionProvince"
              :key="item.value"
              :label="item.provinceName"
              :value="item"
            ></el-option>
          </el-select>
        </el-form-item>
        <!--
        <el-form-item label="所在城市" prop="city">
          <el-select v-model="query.city" placeholder="请选择" @change="cityChange">
            <el-option
              v-for="item in optionsCity"
              :key="item.value"
              :label="item.value"
              :value="item.code"
            >{{item.value}}</el-option>
          </el-select>
        </el-form-item>
        -->
        <el-form-item label="所在区" prop="county">
          <el-select v-model="query.county" filterable placeholder="请选择">
            <el-option
              v-for="item in optionsTown"
              :key="item.value"
              :label="item.countyName"
              :value="item.countyName"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="店长姓名" prop="managerName">
          <el-input v-model="query.managerName" clearable></el-input>
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
      <div class="clickBtn">
        <el-button
          v-for="(item,i) in menuButtonList"
          class="operateBtn"
          :key="i"
          type="primary"
          :icon="item.buttonStyle"
          @click="chooseClick(item.buttonUrl)"
        >{{item.buttonName}}</el-button>
        <!-- <el-button type="primary" icon="el-icon-share" @click="detailData()">查看详情</el-button>
        <el-button type="primary" icon="el-icon-plus" @click="addData()">新增</el-button>
        <el-button type="primary" icon="el-icon-s-goods" @click="alterData()">修改</el-button>
        <el-button type="primary" icon="el-icon-delete" @click="delData()">删除</el-button>
        <el-button type="primary" icon="el-icon-upload2" @click="importData()">导入</el-button>
        <el-button type="primary" icon="el-icon-download" @click="download()">下载模板</el-button>-->
      </div>
      <!--内容-->
      <sirui-table
        class="operateTable"
        :page="page"
        :loading="load"
        :data="tableData"
        @handleSelectionChange="handleSelectionChange"
        @handleSizeChange="handleSizeChange"
        @handleCurrentChange="handleCurrentChange"
        height="100"
      >
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
    <!--详情-->
    <detailData ref="detailData" v-on:detail="getData"></detailData>
    <!--新增 -->
    <addData ref="addData" v-on:search="searchForm"></addData>
    <!--修改-->
    <shopEdit ref="alterData" v-on:getData="getData"></shopEdit>
    <!--导入数据-->
    <shopUpLoad ref="shopUpLoad" v-on:getData="getData"></shopUpLoad>
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
  </div>
</template>
<script>
import { mapState } from "vuex";
import API from "@/api/shopManage";
//测试环境
import { devUrl } from "@/common/config/dev.env";
//正式环境
import { prodUrl } from "@/common/config/prod.env";
import SiruiTable from "@/components/SiruiTable";
import detailData from "@/pages/shopManage/detailData";
import addData from "@/pages/shopManage/addData";
import shopEdit from "@/pages/shopManage/shopEdit";
import shopUpLoad from "@/pages/shopManage/shopUpLoad";
// import districts from '@/pages/shopManage/districts.js'
export default {
  components: {
    SiruiTable,
    detailData,
    shopEdit,
    addData,
    shopUpLoad
  },
  name: "shop",
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
      optionProvince: [
        //  {
        //   provinceName: "北京省",
        //   provinceCode: "10"
        // },
        // {
        //   provinceName: "辽宁省",
        //   provinceCode: "11"
        // }
      ],
      optionsCity: [],
      optionsTown: [],
      provinceCodes: [],
      provinceNo: [],
      fileList: [],
      aaa: [],
      provinceNo: "", // 省份编码
      countyNo: "", // 县区编码
      // county: "", // 所在区县名称
      editPwd: {
        passWord: "",
        passWordCheck: ""
      },
      dialogPwdEdit: false,
      passwordType: "password",
      passWordCheck: "password",
      // 必填项
      rules: {
        // province: [{ required: true, trigger: "blur" }],
        // city: [{ required: true, trigger: "blur" }]
        passWord: [
          {
            required: true,
            trigger: "blur",
            validator: validatePassWord
          }
        ],
        passWordCheck: [
          {
            required: true,
            validator: validatePassWordCheck,
            trigger: "blur"
          }
        ]
      },
      query: {
        schoolStoreNum: "",
        //province: "",
        county: "",
        storeName: "",
        managerName: ""
        //town: ""
      },
      // downloadUrl:
      //   "/group1/M00/00/00/CmQMFFzjrAeAKOFBAAAhsI8rrqw68.xlsx?filePath=CmQMFFzjrAeAKOFBAAAhsI8rrqw68.xlsx",
      downloadUrl:
        "http://10.100.12.20:8001/group1/M00/00/01/CmQMFFzo0lmAG5Q2AAAhqgFirPQ50.xlsx?filename=门店信息批量导入模板.xlsx",
      load: false,
      skuLoad: false,
      menuButtonList: [],
      columnList: [
        {
          prop: "storeNo",
          label: "校边店编码(邀请码)",
          sort: true,
          fixed: true,
          width: 200
        },
        { prop: "storeName", label: "店名" },
        { prop: "storePhone", label: "电话" },
        { prop: "county", label: "所属地区" },
        { prop: "storeAddress", label: "详细地址" },
        { prop: "wanjiaAccount", label: "万家账号" },
        { prop: "userName", label: "店长姓名" },
        { prop: "userAcct", label: "门店账号" }
      ],
      tableData: [],
      tableHeight: document.body.scrollHeight - 460,
      page: {
        pages: 0, //总共多少页
        pageNum: 1, //第几页
        pageSize: 10, //一页多少条数据
        total: 0 //总共多少条
      },
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
    this.initData();
    this.getButton();
    this.getData();
  },
  methods: {
    //初始化数据 查省市区
    initData() {
      let params = {};
      this.$axios.post(API.PROVEINCE_QUERY).then(res => {
        if (0 == res.data.code) {
          this.optionProvince = res.data.data;
          //alert(this.optionProvince);
        } else {
          this.$message({
            showClose: true,
            message: res.data.msg,
            type: "error"
          });
        }
      });
    },
    //省的change事件
    provinceChange(value) {
      this.query.county = "";
      this.provinceNo = value.provinceCode;
      // 通过省份code作为入参查询;
      let paramsCode = {
        provinceNo: value.provinceCode
      };
      this.$axios.post(API.COUNTY_QUERY, paramsCode).then(res => {
        if (0 == res.data.code) {
          this.optionsTown = res.data.data;
        } else {
          this.$message({
            showClose: true,
            message: res.data.msg,
            type: "error"
          });
        }
      });
      this.query.province = value.provinceName;
    },
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
    //获取数据
    getData() {
      this.query.pageSize = this.page.pageSize;
      this.query.pageNum = this.page.pageNum;
      this.load = true;
      let params = {
        pageSize: this.query.pageSize,
        pageNum: this.query.pageNum,
        storeNo: this.query.schoolStoreNum,
        storeName: this.query.storeName,
        province: this.query.province,
        county: this.query.county,
        userName: this.query.managerName
      };
      this.$axios.post(API.STOTE_INFO_QUERY, params).then(res => {
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
    //搜索查询
    searchForm() {
      this.page.pageNum = 1;
      this.getData();
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
      // alert(JSON.stringify(this.multipleSelection));
    },
    chooseClick(method) {
      if ("detailData" == method) {
        this.detailData();
      } else if ("addData" == method) {
        this.addData();
      } else if ("alterData" == method) {
        this.alterData();
      } else if ("delData" == method) {
        this.delData();
      } else if ("importData" == method) {
        this.importData();
      } else if ("download" == method) {
        this.download();
      } else if ("editPassword" == method) {
        this.editPassword();
      }
    },
    clickSKURow(val) {
      this.$refs.skuTable.toggleRowSelection(val);
    },
    handleSKUSelectionChange(row) {},
    //门店详情
    detailData() {
      if (1 == this.multipleSelection.length) {
        //为了防止自组件的值  影响父组件
        this.$refs.detailData.openDialog(
          true,
          JSON.parse(JSON.stringify(this.multipleSelection[0]))
        );
      } else {
        this.$message({
          showClose: true,
          message: "请选择一条数据进行查看详情",
          type: "error"
        });
      }
    },
    //新增
    addData() {
      this.$refs.addData.openDialog(true);
    },
    //修改
    alterData() {
      if (1 == this.multipleSelection.length) {
        //为了防止自组件的值  影响父组件
        this.$refs.alterData.openDialog(
          true,
          JSON.parse(JSON.stringify(this.multipleSelection[0]))
        );
      } else {
        this.$message({
          showClose: true,
          message: "请选择一条数据进行修改",
          type: "error"
        });
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
            userCode: this.multipleSelection[0].userCode,
            newPwd: this.editPwd.passWord
          };
          this.loading.startLoading();
          this.$axios.post(API.STORE_UPDATE_PWD_API, params).then(res => {
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
    //删除
    delData() {
      if (this.multipleSelection.length >= 1) {
        if (0 != this.multipleSelection.length) {
          this.$confirm("此操作将永久删除该数据, 是否继续?", "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning"
          })
            .then(() => {
              let params = {
                user_code_list: []
              };
              for (var i = 0; i < this.multipleSelection.length; i++) {
                params.user_code_list.push(this.multipleSelection[i].userCode);
              }
              params.user_code_list = params.user_code_list.join(",");
              this.loading.startLoading();
              this.$axios.post(API.STORE_INFO_REMOVE, params).then(res => {
                this.loading.endLoading();
                if (0 == res.data.code) {
                  this.searchForm();
                  this.$message({
                    type: "success",
                    message: "删除成功!"
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
    },
    //导入
    importData() {
      this.$refs.shopUpLoad.openDialog(true);
    },
    // 下载模板
    download() {
      // window.location.href = encodeURI(
      //   (process.env.NODE_ENV === "production" ? prodUrl : devUrl) +
      //     API.STORE_INFO_DOWNLOAD +
      //     this.downloadUrl +
      //     "?access_token=" +
      //     this.$store.state.userInfo.userInfo.access_token
      // );
      window.location.href = encodeURI(this.downloadUrl);
    }
  }
};
</script>
<style lang="scss" scoped>
.clickBtn {
  margin-top: 10px;
}
.el-upload--text {
  border: none;
  width: 100px;
  height: 30px;
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


