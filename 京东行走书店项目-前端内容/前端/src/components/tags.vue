<template>
  <div class="tags" v-if="showTags">
    <ul>
      <li
        class="tags-li"
        v-for="(item,index) in tagsList"
        :class="[{'active': isActive(item.path)},{'home-active': item.title == '首页'}]"
        :key="index"
      >
        <span @click="tabClick(item)">{{item.title}}</span>
        <span class="tags-li-icon" v-if="item.title != '首页'" @click="closeTags(index)">
          <i class="el-icon-close"></i>
        </span>
      </li>
    </ul>
  </div>
</template>

<script>
import bus from '../common/js/bus';
export default {
  data() {
    return {
      tagsList: []
    }
  },
  created() {
    //this.setTags(this.$route);
    //刷新浏览器跳转到首页
    this.tagsList = [{
      title: "首页",
      path: "/dashboard",
      name: "Dashboard"
    }];
    this.$store.state.tagsList = this.tagsList;
    this.$router.push({ path: "/dashboard" });
  },
  methods: {
    //tab切换
    tabClick(tab) {
      //this.setTags(tab);
      this.$router.push(tab.path);
    },
    isActive(path) {
      return path === this.$route.fullPath;
    },
    // 关闭单个标签
    closeTags(index) {
      const delItem = this.tagsList.splice(index, 1)[0];
      const item = this.tagsList[index] ? this.tagsList[index] : this.tagsList[index - 1];
      if (item) {
        delItem.path === this.$route.fullPath && this.$router.push(item.path);
      }
    },
    // 设置标签
    setTags(route) {
      const isExist = this.tagsList.some(item => {
        return item.path === route.fullPath;
      });
      if (this.tagsList.length == 0) {
        this.tagsList = [{
          title: "首页",
          path: "/dashboard",
          name: "Dashboard"
        }];
      }
      if (!isExist && route.fullPath != "/dashboard") {
        this.tagsList.push({
          title: route.meta.title,
          path: route.fullPath,
          name: route.matched[1].name
        });
      }
      bus.$emit('tags', this.tagsList);
    }
  },
  computed: {
    showTags() {
      return this.$store.state.tagsList.length > 0;
    }
  },
  watch: {
    $route(newValue, oldValue) {
      this.setTags(newValue);
    }
  }
}

</script>


<style>
.tags {
  position: relative;
  height: 30px;
  overflow: hidden;
  background: #fff;
  padding-right: 120px;
  box-shadow: 0 5px 10px #ddd;
}

.tags ul {
  box-sizing: border-box;
  width: 100%;
  height: 100%;
}

.tags-li {
  float: left;
  margin: 3px 5px 2px 3px;
  border-radius: 3px;
  font-size: 12px;
  overflow: hidden;
  cursor: pointer;
  height: 23px;
  line-height: 23px;
  border: 1px solid #e9eaec;
  background: #fff;
  padding: 0 5px 0 12px;
  vertical-align: middle;
  color: #666;
  -webkit-transition: all 0.3s ease-in;
  -moz-transition: all 0.3s ease-in;
  transition: all 0.3s ease-in;
}
.home-active {
  padding: 0 5px 0 5px;
}

.tags-li:not(.active):hover {
  background: #f8f8f8;
}

.tags-li.active {
  color: #fff;
}

.tags-li-title {
  float: left;
  max-width: 80px;
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
  margin-right: 5px;
  color: #666;
}

.tags-li.active .tags-li-title {
  color: #fff;
}

.tags-close-box {
  position: absolute;
  right: 0;
  top: 0;
  box-sizing: border-box;
  padding-top: 1px;
  text-align: center;
  width: 110px;
  height: 30px;
  background: #fff;
  box-shadow: -3px 0 15px 3px rgba(0, 0, 0, 0.1);
  z-index: 10;
}
</style>
