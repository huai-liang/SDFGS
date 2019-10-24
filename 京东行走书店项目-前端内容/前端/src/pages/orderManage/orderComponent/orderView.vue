<template>
  <div class="userDialog">
    <el-dialog width="55%" title="查看详情" :visible.sync="dialogView" :close-on-click-modal="false">
      <el-table 
        :data="tableViewData" 
        border
        stripe 
        style="width: 100%" 
       >
        <el-table-column prop="skuNo" label="sku商品编号" fixed="left" sortable>
        </el-table-column>
        <el-table-column prop="goodsName" label="sku商品名称">
        </el-table-column>
         <el-table-column prop="goodsCnt" label="数量">
        </el-table-column>
        <el-table-column prop="goodsPrice" label="销售价">
        </el-table-column>
        <el-table-column prop="goodsPrice" label="成交价">
        </el-table-column>
      </el-table>
    </el-dialog>
  </div>
</template>
<script>
import API from '@/api/orderManage/orderManage';

export default {
  name: 'orderView',
  props: {
    user: {
      type: Object,
      default: () => { }
    }
  },
  data() {
    return {
      dialogView: false,
      tableViewData: [],      
    }
  },
  methods: {
    openDialog(flag, order) {
      this.dialogView = flag;
      this.$nextTick(function () {
        let params = {
          id:order.id,
          orderNo: order.orderNo
        };
        this.loading.startLoading();
        this.$axios.post(API.VIEW_ORDER_API, params).then(res => {
          this.loading.endLoading();
          if (0 == res.data.code) {
            this.tableViewData = res.data.data.orderDetailS;
          } else {
            this.$message({
              showClose: true,
              message: res.data.msg,
              type: 'error'
            });
          }
        });

      });
    },
    cancelDialog() {
      this.dialogView = false;
    },
  }
}
</script>
