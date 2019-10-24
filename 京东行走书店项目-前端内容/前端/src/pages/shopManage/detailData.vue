<template>
  <div class="detailData">
    <el-dialog width="52%" title="门店详情" :visible.sync="dialogAdd" :close-on-click-modal="false">
      <el-form
        :model="detailInfo"
        label-width="100px"
        :rules="rules"
        ref="detailInfo"
        :inline="true"
        label-position="right"
      >
        <el-form-item label="店名" prop="storeName">
          <el-input v-model="detailInfo.storeName" maxlength="40" disabled></el-input>
        </el-form-item>
        <el-form-item label="联系电话" prop="storePhone">
          <el-input v-model="detailInfo.storePhone" maxlength="40" disabled></el-input>
        </el-form-item>
        <el-form-item label="万家账号" prop="wanjiaAccount">
          <el-input v-model="detailInfo.wanjiaAccount" maxlength="40" disabled></el-input>
        </el-form-item>
        <el-form-item label="店长姓名" prop="userName">
          <el-input v-model="detailInfo.userName" maxlength="40" disabled></el-input>
        </el-form-item>
        <el-form-item label="店长身份证" prop="identityCard">
          <el-input v-model="detailInfo.identityCard" maxlength="40" disabled></el-input>
        </el-form-item>
        <el-form-item label="营业执照编码" prop="businessLicense">
          <el-input v-model="detailInfo.businessLicense" maxlength="40" disabled></el-input>
        </el-form-item>
        <el-form-item label="门店账号" prop="userAcct">
          <el-input v-model="detailInfo.userAcct" maxlength="40" disabled></el-input>
        </el-form-item>
        <el-form-item label="所在省份" prop="province">
          <el-input v-model="detailInfo.province" maxlength="40" disabled></el-input>
        </el-form-item>
        <!--
        <el-form-item label="所在城市" prop="countCity">
          <el-input v-model="detailInfo.countCity" maxlength="40" disabled></el-input>
        </el-form-item>
        -->
        <el-form-item label="所在区" prop="county">
          <el-input v-model="detailInfo.county" maxlength="40" disabled></el-input>
        </el-form-item>
        <el-form-item label="详细地址" prop="storeAddress">
          <el-input v-model="detailInfo.storeAddress" maxlength="40" disabled></el-input>
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
    }
  }
};
</script>
