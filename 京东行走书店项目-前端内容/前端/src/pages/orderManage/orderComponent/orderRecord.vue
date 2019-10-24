<template>
  <div class="userDialog">
    <el-dialog width="55%" title="查看日志" :visible.sync="dialogRecord" :close-on-click-modal="false">
      <el-table 
        :data="tableRecordData" 
        border
        stripe 
        style="width: 100%" 
       >
        <el-table-column prop="userName" label="操作人">
        </el-table-column>
         <el-table-column prop="detail" label="操作内容">
        </el-table-column>
        <el-table-column prop="gmtCreate" label="操作时间">
        </el-table-column>
      </el-table>
    </el-dialog>
  </div>
</template>
<script>
import API from '@/api/orderManage/orderManage';

export default {
  name: 'orderRecord',
  props: {
    user: {
      type: Object,
      default: () => { }
    }
  },
  data() {
    return {
      dialogRecord: false,
      tableRecordData: [],      
    }
  },
  methods: {
    openDialog(flag, order) {
      this.dialogRecord = flag;
      this.$nextTick(function () {
        let params = {
          orderId:order.id,
        };
        this.loading.startLoading();
        this.$axios.post(API.LISTORDERLOG_ORDER_API,params).then(res => {
          this.loading.endLoading();
          //console.log(res);
          if (0 == res.data.code) {
            this.tableRecordData = res.data.data;
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
      this.dialogRecord = false;
    },
  }
}
</script>
