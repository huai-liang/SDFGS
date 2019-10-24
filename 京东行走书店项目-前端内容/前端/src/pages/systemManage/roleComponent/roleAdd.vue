<template>
  <div class="roleAdd">
    <el-dialog title="新增角色" :visible.sync="dialogAdd" width="30%" :close-on-click-modal="false">
      <el-form :model="addInfo" :rules="rules" label-width="100px" ref="addInfo">
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
  name: 'roleAdd',
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
      dialogAdd: false,
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
    openDialog(flag) {
      this.dialogAdd = flag;
      this.$nextTick(() => {
        this.$refs.addInfo.resetFields();
      });
    },
    cancelDialog() {
      this.dialogAdd = false;
    },
    addSure() {
      this.$refs['addInfo'].validate((valid) => {
        if (valid) {
          this.loading.startLoading();
          this.$axios.post(API.ADD_ROLE_API, this.addInfo).then(res => {
            this.loading.endLoading();
            if (0 == res.data.code) {
              this.$message({
                showClose: true,
                message: '新增角色成功',
                type: 'success'
              });
              this.$emit('search');
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
.roleAdd {
  .el-input {
    width: 90%;
  }
}
</style>
