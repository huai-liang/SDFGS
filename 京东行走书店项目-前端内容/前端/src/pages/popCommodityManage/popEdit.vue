<template>
  <div class="userDialog">
    <el-dialog width="40%" title :visible.sync="dialogAdd" :close-on-click-modal="false">
      <el-form
        :model="addInfo"
        label-width="100px"
        ref="addInfo"
        :rules="rules"
        :inline="true"
        label-position="right"
      >
        <el-form-item label="商品" prop="skuName">
          <el-input v-model="addInfo.skuName" maxlength="40" clearable></el-input>
        </el-form-item>
        <el-button type="primary" @click="choose()">选择</el-button>
        <el-form-item label="商品排序" prop="sortNo">
          <el-input v-model="addInfo.sortNo" maxlength="40" clearable></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="addSure('addInfo')">确 定</el-button>
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
    return {
      dialogAdd: false,
      addInfo: {
        sortNo: "",
        skuName: ""
      },
      id: "", // 商品Id
      skuNo: "", // 商品编码
      oldSkuNo: "",
      oldSkuName: "",
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
    openDialog(flag, val) {
      this.dialogAdd = flag;
      this.$nextTick(() => {
        // this.$refs.addInfo.resetFields();
        this.addInfo = val;
        this.id = val.id;
        this.oldSkuNo = val.skuNo;
        this.oldSkuName = val.skuName;
        console.log(this.oldSkuNo);
      });
    },
    cancelDialog() {
      this.dialogAdd = false;
    },
    choose() {
      this.$refs.chooseCommodity.openDialog(true);
    },
    // 调用方法接收子组件的内容
    skuInfo(value) {
      this.addInfo.skuName = value.skuName;
      this.skuNo = value.skuNo;
      //  console.log(this.skuNo);
    },
    addSure() {
      this.$refs["addInfo"].validate(valid => {
        if (valid) {
          this.loading.startLoading();
          let param = {};
          if (this.addInfo.skuName == this.oldSkuName) {
            param = {
              skuName: this.addInfo.skuName,
              sortNo: this.addInfo.sortNo,
              skuNo: this.oldSkuNo,
              id: this.id
            };
          } else {
            param = {
              skuName: this.addInfo.skuName,
              sortNo: this.addInfo.sortNo,
              skuNo: this.skuNo,
              id: this.id
            };
          }
          this.$axios.post(API.HOT_GOOD_UPDATE, param).then(res => {
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
