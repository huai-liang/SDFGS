<!--Add 日志查询 by zheng_r 20180521-->
<template>
  <div class="logQuery">
    <!-- 搜索区域 -->
    <el-card class="searchModule">
      <el-form :model="query" label-width="100px" :inline="true" ref="queryFrom">
        <el-form-item label="用户编码" prop="user_no">
          <el-input v-model="query.user_no" clearable></el-input>
        </el-form-item>
        <el-form-item label="方法名" prop="fun_name">
          <el-input v-model="query.fun_name" clearable></el-input>
        </el-form-item>
        <!-- <el-form-item label="操作类型" prop="op_type">
          <el-input v-model="query.op_type" clearable></el-input>
        </el-form-item>-->
        <el-form-item label="操作类型" prop="op_type">
          <el-select v-model="query.op_type" placeholder="请选择">
            <el-option
              v-for="item in opTypes"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            ></el-option>
          </el-select>
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
          :key="i"
          type="primary"
          :icon="item.buttonStyle"
          @click="chooseClick(item.buttonUrl)"
        >{{item.buttonName}}</el-button>
        <!-- <el-button type="primary" @click="logQueryDetail()">查看详情</el-button> -->
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
    <detailLogQuery ref="detailLogQuery" v-on:detail="getData"></detailLogQuery>
  </div>
</template>
<script>
import { mapState } from "vuex";
import API from "@/api/logQuery";
import SiruiTable from "@/components/SiruiTable";
import detailLogQuery from "@/pages/logQuery/detailLogQuery";
// import districts from '@/pages/shopManage/districts.js'
export default {
  components: {
    SiruiTable,
    detailLogQuery
  },
  name: "logQuery",
  data() {
    return {
      // 必填项
      query: {
        user_no: "",
        fun_name: "",
        op_type: ""
      },
      opTypes: [
        {
          label: "APP端",
          value: "1"
        },
        {
          label: "PC端",
          value: "2"
        }
      ],
      load: false,
      skuLoad: false,
      menuButtonList: [],
      columnList: [
        // {
        //   prop: "countyNbr",
        //   label: "序号",
        //   sort: true,
        //   fixed: true,
        //   width: 200
        // },
        { prop: "userNo", label: "用户编号" },
        { prop: "ip", label: "用户IP" },
        { prop: "funParam", label: "请求参数" },
        { prop: "funDesc", label: "方法描述" },
        { prop: "funName", label: "方法名" },
        { prop: "role", label: "操作类型" },
        { prop: "detail", label: "操作信息" },
        { prop: "gmtCreate", label: "创建时间" }
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
    chooseClick(method) {
      if ("logQueryDetail" == method) {
        this.logQueryDetail();
      } else if ("alterData" == method) {
        this.alterData();
      } else if ("delData" == method) {
        this.delData();
      } else if ("quantityDisplay" == method) {
        this.quantityDisplay();
      }
    },
    // 将时间戳转换成日期
    getFullTime(date) {
      let now = new Date(date);
      let tyear = now.getFullYear();
      let tmonth = now.getMonth() + 1;
      let tday = now.getDate();
      let hours = now.getHours();
      let minutes = now.getMinutes();
      let seconds = now.getSeconds();
      if (tmonth < 10) {
        tmonth = "0" + tmonth;
      }
      if (tday < 10) {
        tday = "0" + tday;
      }
      if (hours < 10) {
        hours = "0" + hours;
      }
      if (minutes < 10) {
        minutes = "0" + minutes;
      }
      if (seconds < 10) {
        seconds = "0" + seconds;
      }
      let time = `${tyear}-${tmonth}-${tday} ${hours}:${minutes}:${seconds}`;
      return time;
    },
    // 初始化数据查表格
    getData() {
      this.query.pageSize = this.page.pageSize;
      this.query.pageNum = this.page.pageNum;
      let params = {
        pageSize: this.query.pageSize,
        pageNum: this.query.pageNum,
        funName: this.query.fun_name,
        role: this.query.op_type,
        userNo: this.query.user_no
      };
      this.load = true;
      this.$axios.post(API.LOG_QUERY_API, params).then(res => {
        this.load = false;
        if (0 == res.data.code) {
          this.tableData = res.data.data.list;
          this.page.total = res.data.data.total;
          for (var i = 0; i < this.tableData.length; i++) {
            this.tableData[i].gmtCreate = this.getFullTime(
              this.tableData[i].gmtCreate
            );
          }
        } else if (5 == res.data.code) {
          this.tableData = [];
          this.page.total = 0;
        } else {
          this.$message({
            showClose: true,
            message: res.data.msg,
            type: "error"
          });
        }
        // 将操作类型转换为文字
        for (var i = 0; i < this.tableData.length; i++) {
          if (this.tableData[i].role == "1") {
            this.tableData[i].role = "APP端";
          } else if (this.tableData[i].role == "2") {
            this.tableData[i].role = "PC端";
          }
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
    //日志查询详情
    logQueryDetail() {
      if (1 == this.multipleSelection.length) {
        //为了防止自组件的值  影响父组件
        this.$refs.detailLogQuery.openDialog(
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
    //选中触发事件
    handleSelectionChange(row) {
      this.multipleSelection = row;
      //alert(JSON.stringify(this.multipleSelection));
    },
    clickSKURow(val) {
      this.$refs.skuTable.toggleRowSelection(val);
    },
    handleSKUSelectionChange(row) {}
  }
};
</script>
<style lang="scss" scoped>
.clickBtn {
  margin-top: 10px;
}
.searchBtn {
  margin-left: 3%;
}
</style>

