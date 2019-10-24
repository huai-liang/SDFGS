<template>
  <div class="roleEdit">
    <el-dialog title="修改角色" :visible.sync="dialogEdit" width="30%" :close-on-click-modal="false">
      <el-form v-if="dialogEdit" :model="addInfo" :rules="rules" label-width="100px" ref="addInfo">
        <el-form-item label="角色名称" prop="roleName">
          <el-input v-model="addInfo.roleName" maxlength="40" clearable></el-input>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="addInfo.remark" maxlength="200" clearable></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancelDialog()">取 消</el-button>
        <el-button type="primary" @click="addSure('form')">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import API from '@/api/systemManage/role';

export default {
  name: 'roleEdit',
  data() {
    //表单验证
    var validateName = (rule, value, callback) => {
      if (value.replace(/(^\s*)|(\s*$)/g, "") === '') {//前后去空格
        callback(new Error('请输入角色名称'));
      } else {
        callback();
      }
    };
    return {
      dialogEdit: false,
      addInfo: {
        roleName: '',
        remark: ''
      },
      rules: {
        roleName: [
          { required: true, validator: validateName, trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    openDialog(flag, role) {
      this.dialogEdit = flag;
      this.$nextTick(function () {
        this.addInfo = role;
      });
    },
    cancelDialog() {
      this.dialogEdit = false;
    },
    addSure() {
      this.$refs['addInfo'].validate((valid) => {
        if (valid) {
          this.loading.startLoading();
          //删除创建和修改日期 否则会报错
          delete this.addInfo.gmtCreate;
          delete this.addInfo.gmtModified;
          this.$axios.post(API.UPDATE_ROLE_API, this.addInfo).then(res => {
            this.loading.endLoading();
            if (0 == res.data.code) {
              this.$message({
                showClose: true,
                message: '修改角色成功',
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
          });
        }
      });
    }
  }
}
</script>
<style lang="scss" scoped>
.roleEdit {
  .el-input {
    width: 90%;
  }
}
</style>
