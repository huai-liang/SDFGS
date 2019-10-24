<template>
  <div class="exportOrderDialog">
    <el-dialog width="55%" title="代购订单详情" :visible.sync="dialogEdit" :close-on-click-modal="false">
      <el-form
        :model="addInfo"
        label-width="100px"
        ref="addInfo"
        :inline="true"
        label-position="right"
      >
        <el-form-item label="代购订单号" prop="exportNo">
          <el-input v-model="addInfo.exportNo" maxlength="40" readonly></el-input>
        </el-form-item>
        <el-form-item label="导出时间" prop="exportTime">
          <el-input v-model="addInfo.exportTime" maxlength="40" readonly></el-input>
        </el-form-item>
        <el-form-item label="京东订单号" prop="jdOrderNo">
          <el-input v-model="addInfo.jdOrderNo" readonly></el-input>
        </el-form-item>
        <el-form-item label="批次号" prop="batchNo">
          <el-input v-model="addInfo.batchNo" readonly></el-input>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input
            type="textarea"
            :rows="3"
            maxlength="200"
            v-model="addInfo.remark"
            readonly
            style="width:500px;"
          ></el-input>
        </el-form-item>
      </el-form>
      <div>
          <el-table
            :data="tableData"
            border
            style="width: 100%">
            <el-table-column prop="orderNo" label="订单号"></el-table-column>
            <el-table-column prop="buyer" label="下单人"></el-table-column>
            <el-table-column prop="buyerPhone" label="下单人手机号"></el-table-column>
            <el-table-column prop="goodsName" label="商品名称"></el-table-column>
        </el-table>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="cancelDialog()">确认</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import API from '@/api/exportOrder/exportOrder';

export default {
  name: 'exportOrderDetail',
  props: {
    user: {
      type: Object,
      default: () => { }
    }
  },
  data() {
    return {
      dialogEdit:false,
      addInfo: {},
      tableData: [],
    }
  },
  methods: {
    openDialog(flag, user) {
      let params = {
        id: user.id,
      };
      this.loading.startLoading();
      this.$axios.post(API.DETAIL_EXPORTORDER_API, params).then(res => {
        this.loading.endLoading();
        if (0 == res.data.code) {
            this.addInfo = res.data.data;
            this.tableData = res.data.data.orderList;
            this.dialogEdit = flag;
        } else {
            this.$message({
            showClose: true,
            message: res.data.msg,
            type: 'error'
            });
        }
      });
      
    },
    cancelDialog() {
      this.dialogEdit = false;
    },
  }
}
</script>