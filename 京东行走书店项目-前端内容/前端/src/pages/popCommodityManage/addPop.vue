<template>
  <div class="userDialog">
    <el-dialog width="40%" title :visible.sync="dialogAdd" :close-on-click-modal="false">
      <el-form
        :model="info"
        label-width="100px"
        ref="info"
        :rules="rules"
        :inline="true"
        label-position="right"
      >
        <el-form-item label="商品" prop="skuName">
          <el-input v-model="info.skuName" maxlength="40" clearable></el-input>
        </el-form-item>
        <el-button type="primary" @click="choose">选择</el-button>
        <el-form-item label="商品排序" prop="sortNo">
          <el-input v-model="info.sortNo" maxlength="40" clearable></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="addSure('info')">确 定</el-button>
        <el-button @click="cancelDialog()">关闭</el-button>
      </div>
    </el-dialog>
    <chooseCommodity ref="chooseCommodity" @skuInfo="skuInfo"></chooseCommodity>
  </div>
</template>
<script>
import API from "@/api/popCommodityManage";
import chooseCommodity from "@/pages/popCommodityManage/chooseCommodity";
export default {
  components: {
    chooseCommodity
  },
  name: "addPop",
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
      //addInfo: {},
      info: {
        sortNo: "",
        skuName: ""
      },
      skuNo: "", // 商品编码
      //userCode: "" // 用户编码
      rules: {
        sortNo: [
          {
            trigger: "blur",
            required: true,
            pattern: /^\d+(\.{0,1}\d+){0,1}$/,
            message: "商品排序不能为负数和特殊符号"
          }
        ]
      }
    };
  },
  mounted() {},
  methods: {
    openDialog(flag) {
      this.dialogAdd = flag;
      this.$nextTick(() => {
        this.$refs.info.resetFields();
      });
    },
    cancelDialog() {
      this.dialogAdd = false;
    },
    // 选择按钮
    choose(data) {
      this.$refs["info"].validate(valid => {
        this.$refs.chooseCommodity.openDialog(true);
      });
    },
    skuInfo(value) {
      this.info.skuName = value.skuName;
      this.skuNo = value.skuNo;
    },
    // 新增
    addSure() {
      this.$refs["info"].validate(valid => {
        if (valid) {
          this.loading.startLoading();
          let param = {
            skuName: this.info.skuName,
            sortNo: this.info.sortNo,
            skuNo: this.skuNo
          };
          this.$axios.post(API.HOT_GOODS_ADD, param).then(res => {
            this.loading.endLoading();
            if (0 == res.data.code) {
              this.$message({
                showClose: true,
                message: res.data.msg,
                type: "success"
              });
              this.$emit("search");
              this.cancelDialog();
            } else {
              this.$message({
                showClose: true,
                message: res.data.msg,
                type: "error"
              });
            }
          });
        }
      });
    }
  }
};
</script>
