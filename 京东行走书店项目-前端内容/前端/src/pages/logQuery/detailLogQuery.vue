<template>
  <div class="detailData">
    <el-dialog width="52%" title="详情" :visible.sync="dialogAdd" :close-on-click-modal="false">
      <el-form
        :model="detailInfo"
        label-width="100px"
        :rules="rules"
        ref="detailInfo"
        :inline="true"
        label-position="right"
      >
        <el-form-item label="用户编码" prop="userNo">
          <el-input v-model="detailInfo.userNo" disabled></el-input>
        </el-form-item>
        <el-form-item label="请求IP" prop="ip">
          <el-input v-model="detailInfo.ip" disabled></el-input>
        </el-form-item>
        <el-form-item label="请求参数" prop="funParam">
          <el-input v-model="detailInfo.funParam" disabled></el-input>
        </el-form-item>
        <el-form-item label="方法描述" prop="funDesc">
          <el-input v-model="detailInfo.funDesc" disabled></el-input>
        </el-form-item>
        <el-form-item label="方法名" prop="funName">
          <el-input v-model="detailInfo.funName" disabled></el-input>
        </el-form-item>
        <el-form-item label="操作类型" prop="role">
          <el-input v-model="detailInfo.role" disabled></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancelDialog()">取 消</el-button>
        <el-button type="primary" @click="detailSure('detailInfo')">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
//import API from '@/api/systemManage/user';

export default {
  name: "detailDate",
  data() {
    return {
      dialogAdd: false,
      detailInfo: {},
      rules: {}
    };
  },
  methods: {
    openDialog(flag, val) {
      this.dialogAdd = flag;
      this.$nextTick(() => {
        this.detailInfo = val;
      });
    },
    cancelDialog() {
      this.dialogAdd = false;
    },
    detailSure() {
      this.dialogAdd = false;
      // this.$refs['addInfo'].validate((valid) => {
      //   if (valid) {
      //     this.loading.startLoading();
      //     this.$axios.post(API.ADD_USER_API, this.addInfo).then(res => {
      //       this.loading.endLoading();
      //       if (0 == res.data.code) {
      //         this.$message({
      //           showClose: true,
      //           message: '新增用户成功',
      //           type: 'success'
      //         });
      //          this.$emit('detail');
      //         this.cancelDialog();
      //       } else {
      //         this.$message({
      //           showClose: true,
      //           message: res.data.msg,
      //           type: 'error'
      //         });
      //       }
      //     });
      //   }
      // });
    }
  }
};
</script>
