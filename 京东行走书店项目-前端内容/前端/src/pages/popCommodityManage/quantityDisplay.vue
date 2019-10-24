<!-- 商品数量展示  zheng_r  20190515-->
<!--<template>
  <div class="userDialog">
    <el-dialog width="30%" title :visible.sync="dialogAdd" :close-on-click-modal="false">
      <el-form
        :model="addInfo"
        label-width="100px"
        ref="addInfo"
        :inline="true"
        label-position="right"
        :rules="rules"
      >
        <el-form-item label="原商品展示数" prop="beforeShowNum" label-width="110px">
          <el-input v-model="addInfo.beforeShowNum" disabled></el-input>
        </el-form-item>
        <el-form-item label="现商品展示数" prop="showNum" label-width="110px">
          <el-input v-model="addInfo.showNum"></el-input>
        </el-form-item>
        <div style="margin:10px 32%">
          <el-button type="primary" @click="sure('addInfo')">确认</el-button>
          <el-button @click="cancelDialog()">关闭</el-button>
        </div>
      </el-form>
    </el-dialog>
  </div>
</template>-->
// <script>
// import API from "@/api/popCommodityManage";
// export default {
//   name: "quantityDisplay",
//   data() {
//     //表单验证飞控去空格后非空
//     // var validateName = (rule, value, callback) => {
//     //   if (value.replace(/(^\s*)|(\s*$)/g, "") === '') {//前后去空格
//     //     callback(new Error('请输入用户名称'));
//     //   } else {
//     //     callback();
//     //   }
//     // };
//     // var validateAcct = (rule, value, callback) => {
//     //   if (value.replace(/(^\s*)|(\s*$)/g, "") === '') {//前后去空格
//     //     callback(new Error('请输入用户账号'));
//     //   } else {
//     //     callback();
//     //   }
//     // };
//     return {
//       dialogAdd: false,
//       addInfo: {
//         showNum: "",
//         beforeShowNum: ""
//       },
//       // beforeData: "", // 原商品数量
//       rules: {
//         showNum: [
//           {
//             trigger: "blur",
//             required: true,
//             pattern: /^\d+(\.{0,1}\d+){0,1}$/,
//             message: "商品展示数量不能为负数和特殊符号",
//             trigger: "blur"
//           }
//         ]
//       }
//     };
//   },
//   mounted() {
//     this.before();
//     //this.$emit('childByValue', this.before())
//   },
//   methods: {
//     openDialog(flag) {
//       this.dialogAdd = flag;
//       this.$nextTick(() => {
//         this.$refs.addInfo.resetFields();
//       });
//     },
//     cancelDialog() {
//       this.dialogAdd = false;
//     },
//     before() {
//       let before = {};
//       //  this.$refs["addInfo"].validate(valid => {
//       //  if (valid) {
//       // this.loading.startLoading();
//       this.$axios.post(API.SHOW_NUM_QUERY, before).then(res => {
//         if (0 == res.data.code) {
//           // this.loading.startLoading();
//           this.addInfo.beforeShowNum = res.data.data;
//         } else {
//           // this.loading.endLoading();
//           this.$message({
//             showClose: true,
//             message: res.data.msg,
//             type: "error"
//           });
//         }
//       });
//       // }
//       // });
//     },
//     sure() {
//       let param = {
//         showNum: this.addInfo.showNum
//       };
//       this.$refs["addInfo"].validate(valid => {
//         if (valid) {
//           this.loading.startLoading();
//           this.$axios.post(API.HOT_SET_SHOW_NUM, param).then(res => {
//             this.loading.endLoading();
//             if (0 == res.data.code) {
//               this.$message({
//                 showClose: true,
//                 message: res.data.msg,
//                 type: "success"
//               });
//               this.cancelDialog();
//             } else {
//               this.$message({
//                 showClose: true,
//                 message: res.data.msg,
//                 type: "error"
//               });
//             }
//           });
//         }
//       });
//     }
//   }
// };
//
//
//
</script>

