<template>
  <div class="motormanDetail">
    <el-dialog width="30%" title="详情" :visible.sync="dialogAdd" :close-on-click-modal="false">
      <el-form
        :model="detailInfo"
        label-width="100px"
        ref="detailInfo"
        :inline="true"
        label-position="right"
      >
        <el-form-item label="司机编码" prop="driverNo">
          <el-input v-model="detailInfo.driverNo" maxlength="40" disabled></el-input>
        </el-form-item>
        <el-form-item label="司机姓名" prop="driverName">
          <el-input v-model="detailInfo.driverName" maxlength="40" disabled></el-input>
        </el-form-item>
        <el-form-item label="手机号" prop="driverPhone">
          <el-input v-model="detailInfo.driverPhone" maxlength="40" disabled></el-input>
        </el-form-item>
        <el-form-item label="身份证号" prop="identityCard">
          <el-input v-model="detailInfo.identityCard" maxlength="40" disabled></el-input>
        </el-form-item>
        <el-form-item label="所在省份" prop="province">
          <el-input v-model="detailInfo.province" maxlength="40" disabled></el-input>
        </el-form-item>
        <!-- <el-form-item label="所在城市" prop="county">
          <el-input v-model="detailInfo.county" maxlength="40" disabled></el-input>
        </el-form-item>-->
        <el-form-item label="所在区" prop="county">
          <el-input v-model="detailInfo.county" maxlength="40" disabled></el-input>
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
      detailInfo: {
        storeName: "",
        phone: "",
        universalAccount: "",
        provinde: "",
        countCity: "",
        countyTown: "",
        address: ""
      }
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
