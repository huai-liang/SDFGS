<template>
  <div class="sidebar">
    <el-menu
      class="sidebar-el-menu"
      :default-active="onRoutes"
      :collapse="collapse"
      background-color="#00d1b2"
      text-color="#fff"
      active-text-color="#324157"
      router
    >
      <!--logo-->
      <logo :collapse="isCollapse"/>
      <template v-for="value in items">
        <template v-if="value.children.length > 0">
          <el-submenu :index="value.index" :key="value.id">
            <template slot="title">
              <i class="el-icon-menu"></i>
              <span slot="title">{{value.label}}</span>
            </template>
            <MenuTree :menuData="value.children"></MenuTree>
          </el-submenu>
        </template>
        <template v-else>
          <el-menu-item :index="value.index" :key="value.id" @click="getMenuCode(value)">
            <i class="el-icon-menu"></i>
            <span slot="title">{{value.label}}</span>
          </el-menu-item>
        </template>
      </template>
    </el-menu>
  </div>
</template>
<script>
import API from '@/api/login';
import Logo from '@/components/SideLogo';
import MenuTree from '@/components/MenuTree';
import bus from '../common/js/bus';
export default {
  components: {
    Logo,
    MenuTree
  },
  data() {
    return {
      collapse: false,
      items: [],
      list: []
    }
  },
  created() {
    this.getMenu();
    // 通过 Event Bus 进行组件间通信，来折叠侧边栏
    bus.$on('collapse', msg => {
      this.collapse = msg;
    });
  },
  methods: {
    getMenu() {
      this.$axios.post(API.MENUS_API).then(res => {
        if (0 == res.data.code) {
          this.items = res.data.data;
        } else {
          this.$message({
            showClose: true,
            message: res.data.msg,
            type: 'error'
          });
        }
      });
    },
    //获取菜单code 查询按钮权限时用到
    getMenuCode(item) {
      //给菜单code放到全局变量
      this.$store.state.menuCode = item.id;
    }
  },
  computed: {
    onRoutes() {
      return this.$route.path.replace('/', '');
    },
    isCollapse() {
      return this.collapse;
    }
  }
}
</script>

<style lang="scss" scoped>
.sidebar {
  display: block;
  position: absolute;
  left: 0;
  top: 0;
  bottom: 0;
  overflow-y: scroll;
}
.sidebar::-webkit-scrollbar {
  width: 0;
}
.sidebar-el-menu:not(.el-menu--collapse) {
  width: 220px;
}
.sidebar > ul {
  height: 100%;
}
</style>
