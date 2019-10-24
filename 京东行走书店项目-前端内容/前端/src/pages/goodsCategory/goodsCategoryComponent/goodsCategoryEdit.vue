<template>
  <div class="userDialog">
    <el-dialog width="32%" title="修改商品分类" :visible.sync="dialogAdd" :close-on-click-modal="false">
      <el-form
        :model="addInfo"
        label-width="100px"
        :rules="rules"
        ref="addInfo"
        :inline="true"
        label-position="right"
      >
        <el-form-item label="分类名称" prop="cateName">
          <el-input v-model="addInfo.cateName" maxlength="40" clearable></el-input>
        </el-form-item>
        <el-form-item label="分类编码" prop="cateCode">
          <el-input v-model="addInfo.cateCode" maxlength="40" clearable></el-input>
        </el-form-item>
        <el-form-item label="分类描述" prop="remark">
          <el-input v-model="addInfo.remark" clearable></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancelDialog()">取 消</el-button>
        <el-button type="primary" @click="addSure('addInfo')">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import API from '@/api/goodsCategory/goodsCategory';

export default {
  name: 'goodsCategoryEdit',
  data() {
    //表单验证飞控去空格后非空
    var validateName = (rule, value, callback) => {
      if (value.replace(/(^\s*)|(\s*$)/g, "") === '') {//前后去空格
        callback(new Error('请输入分类名称'));
      } else {
        callback();
      }
    };
    return {
      dialogAdd: false,
      addInfo: {},
      selectCode:'',
      rules: {
        cateCode: [
          {
            required: true,
            pattern: /^\w{1,}$/,
            trigger: "blur",
            message: "分类编码只能为数字或字母"
          }
        ],
        cateName: [
          { required: true, validator: validateName, trigger: 'blur' }
        ],
      }
    }
  },
  methods: {
    openDialog(flag,goods) {
      this.selectCode = goods.cateCode;
      this.detail();    //先调详情的接口
    },
    cancelDialog() {
      this.dialogAdd = false;
    },
    detail(){
        let params = {
            cateCode: this.selectCode,
        };
        this.loading.startLoading();
        this.$axios.post(API.DETAIL_GOODS_API, params).then(res => {
            this.loading.endLoading();
            if (0 == res.data.code) {
              this.addInfo = res.data.data;
              this.dialogAdd = true;
            } else {
              this.$message({
                showClose: true,
                message: res.data.msg,
                type: 'error'
              });
            }
        });
    },
    addSure() {
      this.$refs['addInfo'].validate((valid) => {
        if (valid) {
          this.loading.startLoading();
          this.addInfo.level = '1';     //添加层级，否则报错
          if(this.addInfo.parentCode != undefined){
            this.addInfo.level = '2';
          }
          this.$axios.post(API.UPDATE_GOODS_API, this.addInfo).then(res => {
            this.loading.endLoading();
            if (0 == res.data.code) {
              this.$message({
                showClose: true,
                message: '修改成功',
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