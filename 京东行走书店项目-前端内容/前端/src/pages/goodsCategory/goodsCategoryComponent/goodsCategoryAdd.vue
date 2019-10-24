<template>
  <div class="userDialog">
    <el-dialog width="32%" title="新增商品分类" :visible.sync="dialogAdd" :close-on-click-modal="false">
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
  name: 'goodsCategoryAdd',
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
      addInfo: {
        cateName: '',
        cateCode: '',
        remark: '',
        level:'1',
        parentCode:''
      },
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
      if(goods.parentCode == undefined){
        this.addInfo.level = '1';     //重置层级，防止下次新增出错
        this.dialogAdd = flag;
        this.addInfo.parentCode = goods.cateCode;
        this.$nextTick(() => {
          this.$refs.addInfo.resetFields();
        });
      }else{
        this.$message({
          showClose: true,
          message: '二级分类下不可以新增',
          type: 'error'
        });
      }
    },
    cancelDialog() {
      this.dialogAdd = false;
    },
    addSure() {
      this.$refs['addInfo'].validate((valid) => {
        if (valid) {
          this.loading.startLoading();
          if(this.addInfo.parentCode != undefined){
            this.addInfo.level = '2';
          }
          this.$axios.post(API.ADD_GOODS_API, this.addInfo).then(res => {
            this.loading.endLoading();
            if (0 == res.data.code) {
              this.$message({
                showClose: true,
                message: '新增成功',
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
