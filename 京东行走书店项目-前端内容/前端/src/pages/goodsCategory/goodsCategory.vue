<template>
  <div class="goodsCategory">
    <el-card>
      <div class="handle-box">
        <el-button
          v-for="(item,i) in menuButtonList"
          class="operateBtn"
          :key="i"
          type="primary"
          :icon="item.buttonStyle"
          @click="chooseClick(item.buttonUrl)"
        >{{item.buttonName}}</el-button>
      </div>
    </el-card>
    <el-row :gutter="0" class="menuRow">
      <el-col :span="8" class="menuCol">
        <el-card class="menuCard menuCardTree">
          <el-tree
          :data="menuData"
          :props="defaultProps"
          :default-expand-all="true"
          :highlight-current="true"
          @node-click="handleNodeClick"
          class="menuTree"
          ></el-tree>
        </el-card>
      </el-col>
      <el-col :span="16" class="menuCol">
        <el-card class="menuCard">
          <div slot="header">
            <span>分类信息</span>
          </div>
          <el-form ref="menuDetail" :inline="true" :model="menuDetail" label-width="120px" class="goodsCategoryForm">
            <el-form-item label="分类名称">
              <el-input v-model="menuDetail.cateName" readonly></el-input>
            </el-form-item>
            <el-form-item label="分类编码">
              <el-input v-model="menuDetail.cateCode" readonly></el-input>
            </el-form-item>
            <el-form-item label="分类描述">
              <el-input v-model="menuDetail.remark" readonly></el-input>
            </el-form-item>
          </el-form>
        </el-card>
      </el-col>
    </el-row>
    <!--新增菜单-->
    <GoodsCategoryAdd ref="goodsCategoryAdd" v-on:getData="getData"></GoodsCategoryAdd>
    <!--修改菜单-->
    <GoodsCategoryEdit ref="goodsCategoryEdit" v-on:getData="getData"></GoodsCategoryEdit>
    <!--导入-->
    <GoodsCategoryUpload ref="goodsCategoryUpload" v-on:getData="getData"></GoodsCategoryUpload>
  </div>
</template>
<script>
import { mapState } from 'vuex'
import API from '@/api/goodsCategory/goodsCategory'
import GoodsCategoryAdd from '@/pages/goodsCategory/goodsCategoryComponent/goodsCategoryAdd'
import GoodsCategoryEdit from '@/pages/goodsCategory/goodsCategoryComponent/goodsCategoryEdit'
import GoodsCategoryUpload from '@/pages/goodsCategory/goodsCategoryComponent/goodsCategoryUpload'
//测试环境
import { devUrl } from '@/common/config/dev.env'
//正式环境
import { prodUrl } from '@/common/config/prod.env'

export default {
  components: {
    GoodsCategoryAdd,
    GoodsCategoryEdit,
    GoodsCategoryUpload
  },
  name: 'goodsCategory',
  data() {
    return {
      menuData: [],
      defaultProps: {
        children: 'secondCategory',
        label: 'cateName'
      },
      menuDetail: {},
      selectInfo:{},
      buttonList: [],
      multipleSelection: [],
      menuButtonList:[],
      downDataUrl:process.env.NODE_ENV === 'production'?prodUrl:devUrl,//下载地址
    }
  },
  computed: {
    ...mapState({
      userInfo: state => state.userInfo
    })
  },
  mounted: function () {
    this.getData();
    this.getButton();
  },
  methods: {
    //获取页面按钮
    getButton() {
      let params = {
        USER_NAME: this.userInfo.userInfo.userName,
        MENU_CODE: this.$store.state.menuCode,
        BUTTON_CODE: ''
      };
      this.$axios.post(this.$store.state.getButtonUrl, params).then(res => {
        if (0 == res.data.code) {
          this.menuButtonList = res.data.data;
        } else {
          this.$message({
            showClose: true,
            message: res.data.msg,
            type: 'error'
          });
        }
      })
    },
    //判断点击哪个按钮
    chooseClick(method) {
      if ('addGoods' == method) {
        this.addGoods();
      } else if ('editGoods' == method) {
        this.editGoods();
      } else if ('uploadGoods' == method) {
        this.uploadGoods();
      } else if ('delGoods' == method) {
        this.delGoods();
      } else if ('downloadGoods' == method) {
        this.downloadGoods();
      } else if ('cancelSelect' == method) {
        this.cancelSelect();
      }
    },
    getData() {
      this.menuDetail = {};         //清空选中的数据，防止新增在之前的子级
      this.loading.startLoading();
      this.$axios.post(API.GOODS_LIST_API).then(res => {
      this.loading.endLoading();
        if (0 == res.data.code) {
          this.menuData = res.data.data;
        } else {
          this.$message({
            showClose: true,
            message: res.data.msg,
            type: 'error'
          });
        }
      });
    },
    handleNodeClick(menu) {
      this.menuDetail = {};
      this.menuDetail = menu;
    },
    addGoods() {
      this.$refs.goodsCategoryAdd.openDialog(true, JSON.parse(JSON.stringify(this.menuDetail)));
    },
    editGoods() {
      if (this.menuDetail.cateCode) {
        this.$refs.goodsCategoryEdit.openDialog(true, JSON.parse(JSON.stringify(this.menuDetail)));
      } else {
        this.$message({
          showClose: true,
          message: '请选择要修改的分类',
          type: 'error'
        });
      }
    },
    //重置，清空选中
    cancelSelect(){
      this.getData();
    },
    //导入
    uploadGoods() {
      this.$refs.goodsCategoryUpload.openDialog(true);
    },
    //下载
    downloadGoods(){
        window.location.href=encodeURI(this.downDataUrl+API.DOWNLOAD_GOODS_API+'?access_token=' + this.$store.state.userInfo.userInfo.access_token);
    },
    delGoods() {
      if (this.menuDetail.cateCode) {
        this.$confirm('此操作将永久删除该数据, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.loading.startLoading();
          let params = {
            cateCode: this.menuDetail.cateCode,
            level:this.menuDetail.level
          };
          this.$axios.post(API.DELETE_GOODS_API, params).then(res => {
            this.loading.endLoading();
            if (0 == res.data.code) {
              this.getData();
              this.$message({
                type: 'success',
                message: '删除成功!'
              });
            } else {
              this.$message({
                showClose: true,
                message: res.data.msg,
                type: 'error'
              });
            }
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });
      } else {
        this.$message({
          showClose: true,
          message: "请选择数据进行删除",
          type: 'error'
        });
      }
    },
  }
}
</script>
<style lang="scss">
.goodsCategory{
  height: 100%;
  box-sizing: border-box;
  .menuRow,
  .menuCol {
    height: 100%;
    box-sizing: border-box;
  }
  .menuCard {
    height: 95%;
  }
  .menuCardTree .el-card__body{
    height: 95%;
  }
}
</style>