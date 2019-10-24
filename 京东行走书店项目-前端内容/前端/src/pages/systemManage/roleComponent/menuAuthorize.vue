<template>
  <div class="menuAuthorize">
    <!-- 菜单授权 -->
    <el-dialog
      :title="'菜单授权-'+role.roleName"
      :visible.sync="dialogMenuAuthorize"
      width="60%"
      :close-on-click-modal="false"
    >
      <el-row :gutter="0">
        <el-col :span="10">
          <el-card>
            <div slot="header">
              <span>菜单</span>
            </div>
            <div style="overflow:auto;">
              <el-tree
                :data="menuData"
                ref="tree"
                :default-expand-all="true"
                :highlight-current="true"
                show-checkbox
                @node-click="nodeClick"
                node-key="id"
                :default-checked-keys="checkedIds"
                :props="defaultProps"
                class="menuTree"
              ></el-tree>
            </div>
          </el-card>
        </el-col>
        <el-col :span="14">
          <el-card>
            <div slot="header">
              <span>菜单按钮</span>
            </div>
            <div>
              <el-table
                ref="multipleBtnTable"
                :data="buttonTable"
                v-loading="load"
                highlight-current-row
                @select="handleSelectionChange"
                @select-all="handleSelectionChange"
                border
                stripe
                height="330"
              >
                <el-table-column type="selection" width="55"></el-table-column>
                <el-table-column prop="buttonName" label="按钮名称" sortable></el-table-column>
              </el-table>
            </div>
          </el-card>
        </el-col>
      </el-row>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancelDialog()">取 消</el-button>
        <el-button type="primary" @click="menuAuthorizeSure()">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import API from '@/api/systemManage/role';

export default {
  name: 'menuAuthorize',
  data() {
    return {
      dialogMenuAuthorize: false,
      role: {},
      menuData: [],
      checkedIds: [],
      //菜单树配置选项
      defaultProps: {
        children: 'children',
        label: 'label'
      },
      load: false,
      chooseMenuId: "",
      buttonTable: [],
      defalutChoosenBtn: [],
      multipleSelection: [],
      changeMenuBtn: []
    }
  },
  methods: {
    openDialog(flag, role) {
      this.dialogMenuAuthorize = flag;
      this.role = role;
      this.buttonTable = [];
      this.getMenuData();
      this.getRoleAuthorize();
    },
    //获取菜单数据
    getMenuData() {
      let params = {
        roleCode: this.role.roleCode
      };
      this.checkedIds = [];
      this.loading.startLoading();
      this.$axios.post(API.MENU_LIST_API, params).then(res => {
        this.loading.endLoading();
        if (0 == res.data.code) {
          this.checkedIds = [];
          this.menuData = res.data.data.rootTree;
          for (var i = 0; i < res.data.data.menuCodeList.length; i++) {
            this.checkedIds.push(res.data.data.menuCodeList[i].menuCode)
          }
        } else {
          this.$message({
            showClose: true,
            message: res.data.msg,
            type: 'error'
          });
        }
      })
    },
    //获取该角色默认权限
    getRoleAuthorize() {
      let params = {
        roleCode: this.role.roleCode
      };
      this.defalutChoosenBtn = [];
      this.$axios.post(API.MENU_BTN_ROLE_API, params).then(res => {
        if (0 == res.data.code) {
          for (var i = 0; i < res.data.data.checked.length; i++) {
            let obj = {
              menuCode: res.data.data.checked[i].menuCode,
              buttonCode: res.data.data.checked[i].buttonCode
            };
            this.defalutChoosenBtn.push(obj);
          };
        }
      })
    },
    //获取按钮
    findButtonList() {
      let params = {
        roleCode: this.role.roleCode,
        menuCode: this.chooseMenuId
      };
      this.load = true;
      this.$axios.post(API.MENU_LIST_BTN_API, params).then(res => {
        this.load = false;
        if (0 == res.data.code) {
          this.buttonTable = res.data.data.all;
          let hasChooseBotton = [];
          for (var i = 0; i < res.data.data.checked.length; i++) {
            hasChooseBotton.push(res.data.data.checked[i].buttonCode);
          }
          this.$nextTick(function () {
            this.setChoose(hasChooseBotton);
          });
          //将查出来值存起来 下次点击菜单时不用再次请求
          let list = {
            id: this.chooseMenuId,
            buttons: this.buttonTable,
            select: hasChooseBotton
          }
          this.changeMenuBtn.push(list);
        } else {
          this.$message({
            showClose: true,
            message: res.data.msg,
            type: 'error'
          });
        }
      })
    },
    cancelDialog() {
      this.dialogMenuAuthorize = false;
    },
    nodeClick(menu) {
      this.chooseMenuId = menu.id;
      for (var i = 0; i < this.changeMenuBtn.length; i++) {
        if (this.chooseMenuId == this.changeMenuBtn[i].id) {
          this.buttonTable = this.changeMenuBtn[i].buttons;
          this.$nextTick(function () {
            this.setChoose(this.changeMenuBtn[i].select);
          });
          return;
        }
      }
      this.findButtonList();//查询按钮信息
    },
    //选中触发事件
    handleSelectionChange(row) {
      this.multipleSelection = row;
      let btnCode = [];//选中的按钮code的数组
      let tableBtn = [];//选中的按钮对象
      for (var m = 0; m < this.multipleSelection.length; m++) {
        let obj = {
          menuCode: this.multipleSelection[m].menuCode,
          buttonCode: this.multipleSelection[m].buttonCode
        }
        tableBtn.push(obj);
        btnCode.push(this.multipleSelection[m].buttonCode);
      }
      //将选中的按钮id 按照menuid对应关系存起来
      for (var k = 0; k < this.changeMenuBtn.length; k++) {
        if (this.chooseMenuId == this.changeMenuBtn[k].id) {
          this.changeMenuBtn[k].select = btnCode;
        }
      }
      for (var i = 0; i < this.defalutChoosenBtn.length; i++) {
        if (this.defalutChoosenBtn[i].menuCode == this.chooseMenuId) {
          this.defalutChoosenBtn.splice(i, 1);
          i--;
        }
      }
      this.defalutChoosenBtn = this.defalutChoosenBtn.concat(tableBtn);
    },
    //设置默认选中
    setChoose(vlaue) {
      for (var i = 0; i < vlaue.length; i++) {
        for (var j = 0; j < this.buttonTable.length; j++) {
          if (vlaue[i] == this.buttonTable[j].buttonCode) {
            this.$refs.multipleBtnTable.toggleRowSelection(this.buttonTable[j], true);
          }
        }
      }
    },
    //确认 菜单授权
    menuAuthorizeSure() {
      let select = this.$refs.tree.getCheckedKeys();//选中的菜单
      let halfSelect = this.$refs.tree.getHalfCheckedNodes()//半选中的菜单
      for (var i = 0; i < halfSelect.length; i++) {
        select.push(halfSelect[i].id)
      }
      let selectBut = [];//选中的按钮集合
      let newChoosenBtn = [];//最终选中的按钮对象
      for (var k = 0; k < this.defalutChoosenBtn.length; k++) {
        for (var j = 0; j < select.length; j++) {
          if (this.defalutChoosenBtn[k].menuCode == select[j]) {
            let btnStr = this.defalutChoosenBtn[k].menuCode + ":" + this.defalutChoosenBtn[k].buttonCode;
            newChoosenBtn.push(btnStr);
          }
        }
      }
      let params = {
        roleCode: this.role.roleCode,
        menuCodes: select,
        menuBtn: newChoosenBtn
      };
      this.loading.startLoading();
      this.$axios.post(API.SAVE_ROLE_MENU_BTN_API, params).then(res => {
        this.loading.endLoading();
        if (0 == res.data.code) {
          this.$message({
            showClose: true,
            message: '菜单授权成功',
            type: 'success'
          });
          this.$emit('getData');
          this.cancelDialog();
        } else {
          this.$message({
            showClose: true,
            message: res.data.msg,
            type: 'error'
          });
        }
      })
    },
  }
}
</script>
<style lang="scss" scoped>
.menuAuthorize {
  .menuTree {
    height: 330px;
    overflow: auto;
  }
}
</style>


