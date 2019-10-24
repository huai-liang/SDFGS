<template>
  <div class="sidebar-logo-container" @click="toggleClick" :class="{'collapse':collapse}">
    <transition name="sidebarLogoFade">
      <div class="sidebar-logo-link">
        <img :src="logo" class="sidebar-logo">
        <h1 v-if="!collapse" class="sidebar-title">{{ title }}</h1>
      </div>
    </transition>
  </div>
</template>

<script>
import bus from '../../common/js/bus';

export default {
  name: 'SidebarLogo',
  props: {
    collapse: {
      type: Boolean,
      default: true
    }
  },
  data() {
    return {
      title: '行走书店',
      logo: 'https://wpimg.wallstcn.com/69a1c46c-eb1c-4b46-8bd4-e9e686ef5251.png'
    }
  },
  mounted() {

  },
  methods: {
    toggleClick() {
      this.$emit('toggleSideBar', !this.collapse);
    },
    mouseLeave() {
      this.$emit('toggleSideBar', true);
    }
  }
}
</script>

<style lang="scss" scoped>
.sidebarLogoFade-enter-active {
  transition: opacity 1.5s;
}

.sidebarLogoFade-enter,
.sidebarLogoFade-leave-to {
  opacity: 0;
}

.sidebar-logo-container {
  position: relative;
  width: 100%;
  height: 50px;
  line-height: 50px;
  background: #07c4a8;
  text-align: center;
  overflow: hidden;
  z-index: 100;

  & .sidebar-logo-link {
    height: 100%;
    width: 100%;

    & .sidebar-logo {
      width: 32px;
      height: 32px;
      vertical-align: middle;
      margin-right: 12px;
    }

    & .sidebar-title {
      display: inline-block;
      margin: 0;
      color: #fff;
      font-weight: 600;
      line-height: 50px;
      font-size: 14px;
      font-family: Avenir, Helvetica Neue, Arial, Helvetica, sans-serif;
      vertical-align: middle;
    }
  }

  &.collapse {
    .sidebar-logo {
      margin-right: 0px;
    }
  }
}
</style>
