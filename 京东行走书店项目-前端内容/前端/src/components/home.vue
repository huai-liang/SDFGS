<template>
  <div class="wrapper">
    <v-sidebar></v-sidebar>
    <div class="content-box" :class="{'content-collapse':collapse}">
      <v-head></v-head>
      <div class="app-wrap">
        <v-tags></v-tags>
        <div class="content">
          <transition name="move" mode="out-in">
            <keep-alive :include="$store.state.keepAlive">
              <router-view></router-view>
            </keep-alive>
          </transition>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import vHead from './header.vue';
import vSidebar from './sidebar.vue';
import vTags from './tags.vue';
import bus from '../common/js/bus';
export default {
  components: {
    vHead, vSidebar, vTags
  },
  data() {
    return {
      collapse: true
    }
  },
  computed: {

  },
  created() {
    bus.$on('collapse', msg => {
      this.collapse = msg;
    });
    // 只有在标签页列表里的页面才使用keep-alive，即关闭标签之后就不保存到内存中了。
    bus.$on('tags', msg => {
      let arr = [];
      for (let i = 0, len = msg.length; i < len; i++) {
        msg[i].name && arr.push(msg[i].name);
      }
      this.$store.state.tagsList = arr;
      this.$store.commit('SET_KEEP_ALIVE', this.$store.state.tagsList);
    });
  }
}
</script>
<style lang="scss" scoped>
.app-wrap {
  height: 96%;
}
</style>

