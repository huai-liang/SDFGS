<template>
  <div class="menuAdd">
    <el-dialog title="新增菜单" :visible.sync="dialogMenuAdd" width="30%" :close-on-click-modal="false">
      <el-form :model="addMenuList" :rules="rulesAddMenu" ref="addMenuList" label-width="100px">
        <el-form-item label="名称" prop="menuName">
          <el-input v-model="addMenuList.menuName" maxlength="40" clearable></el-input>
        </el-form-item>
        <el-form-item label="类型" prop="type">
          <el-select v-model="addMenuList.type" placeholder="请选择菜单类型">
            <el-option label="菜单" value="1"></el-option>
            <el-option label="目录" value="2"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="方法" prop="menuUrl" v-if="addMenuList.type==='1'">
          <el-input v-model="addMenuList.menuUrl" maxlength="40" clearable></el-input>
        </el-form-item>
        <el-form-item label="排序" prop="sortNo">
          <el-input v-model="addMenuList.sortNo" clearable></el-input>
        </el-form-item>
        <el-form-item label="备注" prop="remark" class="mar-top-20">
          <el-input v-model="addMenuList.remark" clearable></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancelDialog()">取 消</el-button>
        <el-button type="primary" @click="addMenuSure()">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import API from '@/api/systemManage/menu';
export default {
  name: 'menuAdd',
  data() {
    //表单验证非空去空格后
    var validateName = (rule, value, callback) => {
      if (value.replace(/(^\s*)|(\s*$)/g, "") === '') {//前后去空格
        callback(new Error('请输入名称'));
      } else {
        callback();
      }
    };
    return {
      dialogMenuAdd: false,
      menu: {},
      addMenuList: {//新增菜单的数据
        menuName: '',//名称
        type: '1',//类型 1是目录 2是菜单
        menuUrl: '',//方法
        sortNo: '',//排序
        remark: '',//备注
      },
      rulesAddMenu: {//新增菜单
        menuName: [
          { required: true, validator: validateName, trigger: 'blur' }
        ],
        type: [
          { required: true, message: '请选择所属部门', trigger: 'change' }
        ],
        sortNo: [
          { required: true, message: '排序不能为空' },
          { pattern: /^([1-9]\d*|[0]{1,1})$/, message: '请输入正确的正整数', trigger: 'blur' }
        ],
        menuUrl: [
          { required: true, message: '方法不能为空', trigger: 'blur' }
        ]
      },
    }
  },
  methods: {
    openDialog(flag, menu) {
      this.dialogMenuAdd = flag;
      this.menu = menu;
      this.$nextTick(() => {
        this.$refs.addMenuList.resetFields();
      });
    },
    cancelDialog() {
      this.dialogMenuAdd = false;
    },
    addMenuSure() {
      this.$refs["addMenuList"].validate((valid) => {
        if (valid) {
          this.addMenuList.parentMenuCode = this.menu.menuCode == undefined ? "" : this.menu.menuCode;
          this.addMenuList.parentAuthCode = this.menu.authCode == undefined ? "" : this.menu.authCode;
          this.addMenuList.level = this.menu.level == undefined ? 0 : this.menu.level;
          this.loading.startLoading();
          this.$axios.post(API.ADD_MENU_API, this.addMenuList).then(res => {
            this.loading.endLoading();
            if (0 == res.data.code) {
              this.$message({
                showClose: true,
                message: '新增菜单成功',
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
        }
      });
    }
  }
}
</script>
<style lang="scss" scoped>
.menuAdd {
  .el-input {
    width: 90%;
  }
  .el-select {
    width: 90%;
  }
}
</style>

