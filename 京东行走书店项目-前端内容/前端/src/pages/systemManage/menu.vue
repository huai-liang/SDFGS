<template>
  <div class="menu">
    <el-row :gutter="0" class="menuRow">
      <el-col :span="8" class="menuCol">
        <el-card class="menuCard">
          <div slot="header">
            <span>菜单树</span>
            <div class="right">
              <i class="el-icon-plus" @click="addTree"></i>
              <i class="el-icon-edit" @click="editTree"></i>
              <i class="el-icon-delete" @click="delTree"></i>
              <i class="el-icon-refresh" @click="getData"></i>
            </div>
          </div>
          <el-tree
            :data="menuData"
            :props="defaultProps"
            :default-expand-all="true"
            :highlight-current="true"
            @node-click="handleNodeClick"
            class="menuTree"
          ></el-tree>
        </el-card>
      </el-col>
      <el-col :span="16">
        <el-card>
          <div slot="header">
            <span>菜单信息</span>
          </div>
          <el-form ref="menuDetail" :inline="true" :model="menuDetail" label-width="80px">
            <el-form-item label="名称">
              <el-input v-model="menuDetail.menuName" readonly></el-input>
            </el-form-item>
            <el-form-item label="类型">
              <el-input v-model="menuDetail.type" readonly></el-input>
            </el-form-item>
            <el-form-item label="方法">
              <el-input v-model="menuDetail.menuUrl" readonly></el-input>
            </el-form-item>
            <el-form-item label="排序">
              <el-input v-model="menuDetail.sortNo" readonly></el-input>
            </el-form-item>
            <el-form-item label="备注">
              <el-input v-model="menuDetail.remark" readonly></el-input>
            </el-form-item>
          </el-form>
        </el-card>
        <el-card>
          <div slot="header">
            <span>菜单按钮</span>
            <div class="right">
              <i class="el-icon-plus" @click="addButton"></i>
              <i class="el-icon-edit" @click="editButton"></i>
              <i class="el-icon-delete" @click="delButton"></i>
            </div>
          </div>
          <el-table
            :data="buttonList"
            ref="mainTable"
            v-loading="load"
            @row-click="clickRow"
            @selection-change="handleSelectionChange"
            border
            stripe
            height="300"
            style="width: 100%"
          >
            <el-table-column type="selection" width="55"></el-table-column>
            <el-table-column prop="buttonName" label="按钮名称" fixed="left" sortable></el-table-column>
            <el-table-column prop="buttonUrl" label="方法名"></el-table-column>
          </el-table>
        </el-card>
      </el-col>
    </el-row>
    <!--新增菜单-->
    <menuAdd ref="menuAdd" v-on:getData="getData"></menuAdd>
    <!--修改菜单-->
    <menuEdit ref="menuEdit" v-on:getData="getData"></menuEdit>
    <!--新增按钮-->
    <btnAdd ref="btnAdd" v-on:findButtonList="findButtonList"></btnAdd>
    <!--修改按钮-->
    <btnEdit ref="btnEdit" v-on:findButtonList="findButtonList"></btnEdit>
  </div>
</template>
<script>
import { mapState } from 'vuex'
import API from '@/api/systemManage/menu'
import SiruiTable from '@/components/SiruiTable'
import MenuAdd from '@/pages/systemManage/menuComponent/menuAdd'
import MenuEdit from '@/pages/systemManage/menuComponent/menuEdit'
import BtnAdd from '@/pages/systemManage/menuComponent/buttonAdd'
import BtnEdit from '@/pages/systemManage/menuComponent/buttonEdit'

export default {
  components: {
    SiruiTable,
    MenuAdd,
    MenuEdit,
    BtnAdd,
    BtnEdit
  },
  name: 'menuList',
  data() {
    return {
      load: false,
      menuData: [],
      defaultProps: {
        children: 'children',
        label: 'label'
      },
      menuDetail: {},
      chooseMenuId: "",
      buttonList: [],
      multipleSelection: []
    }
  },
  computed: {
    ...mapState({
      userInfo: state => state.userInfo
    })
  },
  mounted: function () {
    this.getData();
  },
  methods: {
    getData() {
      this.menuDetail = {};
      this.chooseMenuId = "";
      this.buttonList = [];
      this.loading.startLoading();
      this.$axios.post(API.MENU_TREE_API).then(res => {
        this.loading.endLoading();
        if (0 == res.data.code) {
          this.menuData = res.data.data.rootTree;
        } else {
          this.$message({
            showClose: true,
            message: res.data.msg,
            type: 'error'
          });
        }
      })
    },
    handleNodeClick(menu) {
      this.menuDetail = {};
      this.menuDetail = menu.attributes;
      if (1 == this.menuDetail.type || "菜单" == this.menuDetail.type) {
        this.menuDetail.type = "菜单"
      } else {
        this.menuDetail.type = "目录"
      }
      this.chooseMenuId = menu.id;//记住选中的菜单树id
      this.findButtonList();
    },
    findButtonList() {
      let params = {
        menuCode: this.chooseMenuId
      };
      this.load = true;
      this.$axios.post(API.MENU_BTN_API, params).then(res => {
        this.load = false;
        if (0 == res.data.code) {
          this.buttonList = res.data.data.all;
        } else {
          this.$message({
            showClose: true,
            message: res.data.msg,
            type: 'error'
          });
        }
      })
    },
    addTree() {
      this.$refs.menuAdd.openDialog(true, this.menuDetail);
    },
    editTree() {
      if (this.menuDetail.menuName) {
        this.$refs.menuEdit.openDialog(true, JSON.parse(JSON.stringify(this.menuDetail)));
      } else {
        this.$message({
          showClose: true,
          message: '请选择要修改的菜单',
          type: 'error'
        });
      }
    },
    delTree() {
      if (this.menuDetail.menuName) {
        this.$confirm('此操作将永久删除该数据, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.loading.startLoading();
          this.$axios.post(API.DELETE_MENU_API, this.menuDetail).then(res => {
            this.loading.endLoading();
            if (0 == res.data.code) {
              this.getData();
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
          message: "请选择一条数据进行删除",
          type: 'error'
        });
      }
    },
    addButton() {
      if (this.menuDetail.menuName) {
        if ("菜单" == this.menuDetail.type) {
          this.$refs.btnAdd.openDialog(true, this.menuDetail);
        } else {
          this.$message({
            showClose: true,
            message: '目录下禁止新增按钮',
            type: 'error'
          });
        }
      } else {
        this.$message({
          showClose: true,
          message: '请选择一个菜单',
          type: 'error'
        });
      }
    },
    editButton() {
      if (this.multipleSelection.length == 1) {
        this.$refs.btnEdit.openDialog(true, JSON.parse(JSON.stringify(this.multipleSelection[0])));
      } else {
        this.$message({
          showClose: true,
          message: '请选择一条数据进行修改操作',
          type: 'error'
        });
      }
    },
    delButton() {
      if (this.multipleSelection.length == 1) {
        this.$confirm('此操作将永久删除该按钮, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.loading.startLoading();
          let params = {
            buttonCode: this.multipleSelection[0].buttonCode
          };
          this.$axios.post(API.DELETE_MENU_BTN_API, params).then(res => {
            this.loading.endLoading();
            if (0 == res.data.code) {
              this.findButtonList();
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
          message: "请选择一个按钮进行删除操作",
          type: 'error'
        });
      }
    },
    handleSelectionChange(row) {
      this.multipleSelection = row;
    },
    clickRow(val) {
      this.$refs.mainTable.toggleRowSelection(val);
    }
  }
}
</script>
<style lang="scss" scoped>
.menu {
  height: 100%;
  box-sizing: border-box;
  .right {
    float: right;
    i {
      cursor: pointer;
    }
  }
  .menuRow,
  .menuCol {
    height: 100%;
    box-sizing: border-box;
  }
  .menuCard {
    height: 95%;
  }
}
</style>


