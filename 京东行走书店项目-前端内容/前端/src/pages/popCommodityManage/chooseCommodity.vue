<template>
  <div class="chooseDialog">
    <el-dialog
      width="80%"
      height="800px"
      title
      :visible.sync="dialogAdd"
      :close-on-click-modal="false"
    >
      <!-- 搜索区域 -->
      <h3 style="margin:10px">选择商品</h3>
      <el-card class="searchModule">
        <el-form
          :model="addInfo"
          label-width="100px"
          ref="queryFrom"
          :inline="true"
          label-position="right"
        >
          <el-form-item label="商品编号" prop="commodityNum">
            <el-input v-model="addInfo.commodityNum" clearable></el-input>
          </el-form-item>
          <el-form-item label="商品名称" prop="skuName">
            <el-input v-model="addInfo.skuName" clearable></el-input>
          </el-form-item>
          <div class="searchBtn">
            <el-button type="primary" @click="searchForm()">查询</el-button>
            <el-button @click="resetForm('queryFrom')">重置</el-button>
          </div>
        </el-form>
      </el-card>
      <h3 style="margin:10px">查询结果</h3>
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
      <div class="searchBtn">
        <el-button type="primary" @click="submitInfo()">提交</el-button>
        <el-button @click="cancelDialog()">关闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import API from "@/api/popCommodityManage";
import { mapState } from "vuex";
import SiruiTable from "@/components/SiruiTable";
export default {
  components: {
    SiruiTable
  },
  name: "chooseCommodity",
  data() {
    //表单验证飞控去空格后非空
    // var validateName = (rule, value, callback) => {
    //   if (value.replace(/(^\s*)|(\s*$)/g, "") === '') {//前后去空格
    //     callback(new Error('请输入用户名称'));
    //   } else {
    //     callback();
    //   }
    // };
    // var validateAcct = (rule, value, callback) => {
    //   if (value.replace(/(^\s*)|(\s*$)/g, "") === '') {//前后去空格
    //     callback(new Error('请输入用户账号'));
    //   } else {
    //     callback();
    //   }
    // };
    return {
      dialogAdd: false,
      // 必填项
      addInfo: {
        skuName: "",
        commodityNum: ""
      },
      // isShow: true,
      load: false,
      skuLoad: false,
      menuButtonList: [],
      columnList: [
        { prop: "skuNo", label: "商品编号" },
        { prop: "skuName", label: "商品名字" },
        { prop: "status", label: "商品状态" },
        { prop: "oneLevel", label: "一级分类" },
        { prop: "twoLevel", label: "二级分类" }
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
  //props: ["skuInfo"],
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
    //this.getButton();
    //this.getData();
  },
  methods: {
    openDialog(flag) {
      this.dialogAdd = flag;
      // 父子通信，将查询方法传给父组件
      this.$emit("choose", this.searchForm());
      // this.$nextTick(() => {
      //   this.$refs.addInfo.resetFields();
      // });
    },
    //Dialog框关闭
    cancelDialog() {
      this.dialogAdd = false;
    },
    //获取页面按钮
    // getButton() {
    //   let params = {
    //     USER_NAME: this.userInfo.userInfo.userName,
    //     MENU_CODE: this.$store.state.menuCode,
    //     BUTTON_CODE: ""
    //   };
    //   this.$axios.post(this.$store.state.getButtonUrl, params).then(res => {
    //     if (0 == res.data.code) {
    //       this.menuButtonList = res.data.data;
    //     } else {
    //       this.$message({
    //         showClose: true,
    //         message: res.data.msg,
    //         type: "error"
    //       });
    //     }
    //   });
    // },
    getData() {
      this.addInfo.pageSize = this.page.pageSize;
      this.addInfo.pageNum = this.page.pageNum;
      let params = {
        pageSize: this.addInfo.pageSize,
        pageNum: this.addInfo.pageNum,
        skuName: this.addInfo.skuName,
        skuNo: this.addInfo.commodityNum
        // status: "",
        // advertising: "",
        // author: ""
      };
      this.load = true;
      this.$axios.post(API.GOOD_SKU_QUERY, params).then(res => {
        this.load = false;
        if (0 == res.data.code) {
          this.tableData = res.data.data.list;
          this.page.total = res.data.data.total;
          for (var i = 0; i < this.tableData.length; i++) {
            if (this.tableData[i].status == "0") {
              this.tableData[i].status = "下架";
              // this.isShow = false;
            } else if (this.tableData[i].status == "1") {
              this.tableData[i].status = "上架";
              //this.isShow = true;
            } else if (this.tableData[i].status == "2") {
              this.tableData[i].status = "未发布";
            }
          }
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
    // 选中触发事件
    handleSelectionChange(row) {
      this.multipleSelection = row;
      // alert(JSON.stringify(this.multipleSelection));
    },
    clickSKURow(val) {
      this.$refs.skuTable.toggleRowSelection(val);
    },
    // 提交
    submitInfo() {
      if (1 == this.multipleSelection.length) {
        //为了防止自组件的值  影响父组件
        for (var i = 0; i < this.multipleSelection.length; i++) {
          if (this.multipleSelection[i].status == "下架") {
            this.$message({
              showClose: true,
              message: "下架产品不允许选择",
              type: "error"
            });
            return;
          } else {
          }
        }
        let aaa = [];
        aaa = aaa.join();
        this.$emit("skuInfo", this.multipleSelection[0]);
        this.dialogAdd = false;
      } else {
        this.$message({
          showClose: true,
          message: "请选择一条数据进行提交",
          type: "error"
        });
      }
    }
  }
};
</script>

