<!--Add 热门位商品管理 by zheng_r 20180507-->
<template>
  <div class="shopManage">
    <!-- 搜索区域 -->
    <el-card class="searchModule">
      <el-form :model="query" label-width="100px" :inline="true" ref="queryFrom">
        <el-form-item label="商品编号" prop="skuNo">
          <el-input v-model="query.skuNo" clearable></el-input>
        </el-form-item>
        <el-form-item label="商品名称" prop="skuName">
          <el-input v-model="query.skuName" clearable></el-input>
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
        <!-- <el-button type="primary" icon="el-icon-plus" @click="addData()">新增</el-button>
        <el-button type="primary" icon="el-icon-s-goods" @click="alterData()">修改</el-button>
        <el-button type="primary" icon="el-icon-delete" @click="delData()">删除</el-button>
        <el-button type="primary" @click="quantityDisplay()">展示数量设置</el-button>-->
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
        <!-- <template slot-scope="scope">{{scope.row.sortNo.padStart(10,' ')}}</template> -->
      </sirui-table>
    </div>
    <el-dialog width="30%" title :visible.sync="dialogAdd" :close-on-click-modal="false">
      <el-form
        :model="addInfo"
        label-width="100px"
        ref="addInfo"
        :inline="true"
        label-position="right"
        :rules="rules"
      >
        <el-form-item label="原商品展示数" prop="beforeShowNum" label-width="110px">
          <el-input v-model="addInfo.beforeShowNum" disabled></el-input>
        </el-form-item>
        <el-form-item label="现商品展示数" prop="showNum" label-width="110px">
          <el-input v-model="addInfo.showNum"></el-input>
        </el-form-item>
        <div style="margin:10px 32%">
          <el-button type="primary" @click="sure('addInfo')">确认</el-button>
          <el-button @click="cancelDialog()">关闭</el-button>
        </div>
      </el-form>
    </el-dialog>
    <!-- 新增 -->
    <addPop ref="addPop" v-on:search="searchForm"></addPop>
    <!--修改 -->
    <popEdit ref="popEdit" v-on:search="searchForm"></popEdit>
    <!--展示数量设置-->
    <!-- <quantityDisplay ref="quantityDisplay" v-on:getData="getData"></quantityDisplay>-->
  </div>
</template>
<script>
import { mapState } from "vuex";
import API from "@/api/popCommodityManage";
import SiruiTable from "@/components/SiruiTable";
import addPop from "@/pages/popCommodityManage/addPop";
//import quantityDisplay from "@/pages/popCommodityManage/quantityDisplay";
import popEdit from "@/pages/popCommodityManage/popEdit";
// import districts from '@/pages/shopManage/districts.js'
export default {
  components: {
    SiruiTable,
    popEdit,
    addPop
    //  quantityDisplay
  },
  name: "popCommodityManage",
  data() {
    return {
      // 必填项
      query: {
        skuNo: "",
        skuName: ""
      },
      // 原现商品数量展示
      addInfo: {
        showNum: "",
        beforeShowNum: ""
      },
      //原现商品数量展示正则
      rules: {
        showNum: [
          {
            trigger: "blur",
            required: true,
            pattern: /^\d+(\.{0,1}\d+){0,1}$/,
            message: "商品展示数量不能为负数和特殊符号",
            trigger: "blur"
          }
        ]
      },
      load: false,
      beforeShowNum: "",
      skuLoad: false,
      dialogAdd: false, //商品数量展示dialog框
      menuButtonList: [],
      columnList: [
        { prop: "skuNo", sort: true, label: "商品编号" },
        { prop: "skuName", label: "商品名字" },
        { prop: "sellingPrice", label: "商品价格" },
        { prop: "introduction", label: "广告词" },
        { prop: "sortNo", label: "商品排序号" },
        { prop: "createTime", label: "创建时间" }
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
    },
    //点击按钮方法
    chooseClick(method) {
      if ("addData" == method) {
        this.addData();
      } else if ("alterData" == method) {
        this.alterData();
      } else if ("delData" == method) {
        this.delData();
      } else if ("quantityDisplay" == method) {
        this.quantityDisplay();
      }
    },
    clickSKURow(val) {
      this.$refs.skuTable.toggleRowSelection(val);
    },
    handleSKUSelectionChange(row) {},
    //新增
    addData() {
      this.$refs.addPop.openDialog(true);
    },
    //修改
    alterData() {
      if (1 == this.multipleSelection.length) {
        //为了防止自组件的值  影响父组件
        this.$refs.popEdit.openDialog(
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
    //删除
    delData() {
      if (1 == this.multipleSelection.length >= 1) {
        if (0 != this.multipleSelection.length) {
          this.$confirm("此操作将永久删除该数据, 是否继续?", "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning"
          })
            .then(() => {
              let params = {
                idList: []
              };
              for (var i = 0; i < this.multipleSelection.length; i++) {
                params.idList.push(this.multipleSelection[i].id);
              }
              params.idList = params.idList.join(",");
              this.loading.startLoading();
              this.$axios.post(API.HOT_GOOD_API_DEL, params).then(res => {
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
    // 展示数量设置
    quantityDisplay() {
      this.dialogAdd = true;
      this.before();
      this.$nextTick(() => {
        this.$refs.addInfo.resetFields();
      });
    },
    //关闭数量设置Dialog框
    cancelDialog() {
      this.dialogAdd = false;
    },
    //原商品展示数量方法
    before() {
      let before = {};
      //  this.$refs["addInfo"].validate(valid => {
      //  if (valid) {
      // this.loading.startLoading();
      this.$axios.post(API.SHOW_NUM_QUERY, before).then(res => {
        if (0 == res.data.code) {
          // this.loading.startLoading();
          this.addInfo.beforeShowNum = res.data.data;
        } else {
          // this.loading.endLoading();
          this.$message({
            showClose: true,
            message: res.data.msg,
            type: "error"
          });
        }
      });
      // }
      // });
    },
    //现商品数量展示确认方法
    sure() {
      let param = {
        showNum: this.addInfo.showNum
      };
      this.$refs["addInfo"].validate(valid => {
        if (valid) {
          this.loading.startLoading();
          this.$axios.post(API.HOT_SET_SHOW_NUM, param).then(res => {
            this.loading.endLoading();
            if (0 == res.data.code) {
              this.$message({
                showClose: true,
                message: res.data.msg,
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
    //搜索
    searchForm() {
      this.page.pageNum = 1;
      this.getData();
    },
    // 获取数据
    getData() {
      this.query.pageSize = this.page.pageSize;
      this.query.pageNum = this.page.pageNum;
      this.load = true;
      this.$axios.post(API.HOT_GOOD_QUERY, this.query).then(res => {
        this.load = false;
        if (0 == res.data.code) {
          this.tableData = res.data.data.list;
          this.page.total = res.data.data.total;
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
      });
    }
  }
};
</script>
<style lang="scss" scoped>
.clickBtn {
  margin-top: 10px;
}
</style>


