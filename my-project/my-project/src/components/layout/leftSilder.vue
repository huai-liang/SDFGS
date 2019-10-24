<template>
  <div>
    <el-row>
      <el-col :span="24">
        <el-menu
          :default-active="onRoutes"
          background-color="#544242"
          text-color="#fff"
          active-text-color="#ffd04b"
          router
        >
        <template v-for="x in dataList">
          <template v-if="x.children.length>0">
            <el-submenu index="x.url" :key="x.id">
              <template slot="title">
                <!-- <i :class="x.icon"></i> -->
                <span>{{x.name}}</span>
              </template>
              <el-menu-item v-for="y in x.children" :key="y.id" :index="y.url">
                {{y.name}}
              </el-menu-item>
            </el-submenu>
          </template>
          <template v-else>
            <el-menu-item :index="x.url" :key="x.id" >
              <!-- <i :class="x.icon"></i> -->
              <span slot="title">{{x.name}}</span>
            </el-menu-item>
          </template>
        </template>
        </el-menu>
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  data() {
    return {
      dataList:[],
      //   {id:'1',icon:'el-icon-s-tools',name:'系统管理',children:[
      //       {id:'1-1',name:'菜单管理',route:'/Menu'},
      //       {id:'1-2',name:'用户管理',route:'/User'},
      //   ]},
      //   {id:'2',icon:'el-icon-present',name:'商品管理',route:'/Goods',children:[]},
      //   {id:'3',icon:'el-icon-s-custom',name:'客户管理',route:'/Customer',children:[]},
      //   {id:'4',icon:'el-icon-picture',name:'轮播图管理',route:'/BannerImg',children:[]},
      //   {id:'5',icon:'el-icon-s-grid',name:'商品分类管理',route:'/GoodsKind',children:[]},
      // ],
      getMenuUrl:this.$store.state.url+'menu/listMenuTree',
    };
  },
  computed:{
    onRoutes(){
      return this.$route.path;
    },
  },
  mounted(){
    this.getData()
  },
  methods:{
    getData(){
      this.$axios.post(this.getMenuUrl).then(res =>{
        if(0 == res.data.code){
          this.dataList= res.data.data
          //查询成功
        }else{
          this.$message({
            showClose:true,
            message:res.data.msg,
            type:'error'
          });
        }
      });
    },
    //点击跳转菜单页面
    chooseOneMenu(val){
      // console.log(val)
      // if(val.route!=this.$route.path){
      //   this.$router.push({
      //     path:val.route
      //   })
      // }
    }
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.el-submenu .el-menu-item{
  padding-left: 60px !important
}
</style>
