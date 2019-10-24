<template>
  <div>
    <template v-for="value in menuData">
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
          <i class="el-icon-tickets"></i>
          <span slot="title">{{value.label}}</span>
        </el-menu-item>
      </template>
    </template>
  </div>
</template>

<script>
export default {
  props: ['menuData'],
  name: 'MenuTree',
  methods: {
    //获取菜单code 查询按钮权限时用到
    getMenuCode(item) {
      //给菜单code放到全局变量
      this.$store.state.menuCode = item.id;
    }
  }
}
</script>
