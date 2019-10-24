<template>
  <div class="assignRole">
    <el-dialog title="指派角色" width="35%" :visible.sync="dialogRole" :close-on-click-modal="false">
      <el-table
        ref="multipleTable"
        border
        stripe
        v-loading="load"
        :data="roleFormData"
        max-height="250"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="60"></el-table-column>
        <el-table-column prop="roleName" label="角色名称"></el-table-column>
        <el-table-column prop="remark" label="备注"></el-table-column>
      </el-table>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancelDialog()">取 消</el-button>
        <el-button type="primary" @click="roleSure()">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import API from '@/api/systemManage/user';
export default {
  name: 'assignRole',
  data() {
    return {
      load: false,
      userCode: '',
      dialogRole: false,
      roleFormData: [],
      multipleSelection: []
    }
  },
  methods: {
    getRole() {
      let params = {
        userCode: this.userCode
      };
      this.load = true;
      this.$axios.post(API.ROLE_LIST_CHECKED_API, params).then(res => {
        this.load = false;
        if (0 == res.data.code) {
          this.roleFormData = res.data.data.all;
          let hasChoose = res.data.data.checked;
          //解决因为没渲染完，无法默认选中的问题
          this.$nextTick(() => {
            for (var i = 0; i < hasChoose.length; i++) {
              for (var j = 0; j < this.roleFormData.length; j++) {
                if (hasChoose[i].roleCode == this.roleFormData[j].roleCode) {
                  this.$refs.multipleTable.toggleRowSelection(this.roleFormData[j], true);
                }
              }
            }
          });
        } else {
          this.$message({
            showClose: true,
            message: res.data.msg,
            type: 'error'
          });
        }
      })
    },
    openDialog(flag, userCode) {
      this.dialogRole = flag;
      this.userCode = userCode;
      this.getRole();
    },
    cancelDialog() {
      this.dialogRole = false;
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    roleSure() {
      let params = {
        userCode: this.userCode,
        roleList: []
      };
      this.multipleSelection.forEach((item, index) => {
        params.roleList.push(item.roleCode);
      });
      this.$axios.post(API.SAVE_ROLE_API, params).then(res => {
        if (0 == res.data.code) {
          this.dialogRole = false;
          this.$message({
            showClose: true,
            message: '指派角色成功',
            type: 'success'
          });
        } else {
          this.$message({
            showClose: true,
            message: res.data.msg,
            type: 'error'
          });
        }
      })
    }
  }
}
</script>
<style lang="scss" scoped>
.assignRole {
  .roleStyle {
    width: 250px;
    height: 35px;
    line-height: 35px;
    float: left;
    text-align: center;
    border: 1px solid #d1dbe5;
  }
  .topNoStyle {
    border-top: none;
  }
  .rightNoStyle {
    border-right: none;
  }
}
</style>
