<template>
  <div class="buttonEdit">
    <el-dialog
      title="修改按钮"
      :visible.sync="dialogButtonAdd"
      width="30%"
      :close-on-click-modal="false"
    >
      <el-form
        :model="addButtonList"
        v-if="dialogButtonAdd"
        :rules="rulesAddButton"
        ref="addButtonList"
        label-width="100px"
      >
        <el-form-item label="按钮名称" prop="buttonName">
          <el-input v-model="addButtonList.buttonName" maxlength="40" clearable></el-input>
        </el-form-item>
        <el-form-item label="执行方法" prop="buttonUrl">
          <el-input v-model="addButtonList.buttonUrl" maxlength="40" clearable></el-input>
        </el-form-item>
        <el-form-item label="图标样式" prop="buttonStyle">
          <el-input v-model="addButtonList.buttonStyle" clearable></el-input>
        </el-form-item>
        <el-form-item label="排序" prop="sortNo">
          <el-input v-model="addButtonList.sortNo" clearable></el-input>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="addButtonList.remark" clearable></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancelDialog()">取 消</el-button>
        <el-button type="primary" @click="addButtonSure()">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import API from '@/api/systemManage/menu';
export default {
  name: 'buttonEdit',
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
      dialogButtonAdd: false,
      menu: {},
      addButtonList: {//新增按钮的数据
        buttonName: '',//名称
        buttonUrl: '',//方法
        buttonStyle: '',//图标样式
        sortNo: '',//排序
        remark: ''
      },
      rulesAddButton: {//新增按钮
        buttonName: [
          { required: true, validator: validateName, trigger: 'blur' }
        ],
        sortNo: [
          { required: true, message: '排序不能为空' },
          { pattern: /^([1-9]\d*|[0]{1,1})$/, message: '请输入正确的正整数', trigger: 'blur' }
        ],
        buttonUrl: [
          { required: true, message: '方法不能为空', trigger: 'blur' },
        ]
      },
    }
  },
  methods: {
    openDialog(flag, button) {
      this.dialogButtonAdd = flag;
      this.$nextTick(function () {
        this.addButtonList = button;
      });
    },
    cancelDialog() {
      this.dialogButtonAdd = false;
    },
    addButtonSure() {
      this.$refs["addButtonList"].validate((valid) => {
        if (valid) {
          this.loading.startLoading();
          this.$axios.post(API.UPDATE_MENU_BTN_API, this.addButtonList).then(res => {
            this.loading.endLoading();
            if (0 == res.data.code) {
              this.$message({
                showClose: true,
                message: '修改按钮成功',
                type: 'success'
              });
              this.$emit('findButtonList');
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
.buttonEdit {
  .el-input {
    width: 90%;
  }
  .el-select {
    width: 90%;
  }
}
</style>

