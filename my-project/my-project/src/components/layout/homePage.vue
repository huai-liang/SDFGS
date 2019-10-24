<template>
  <div>
    <div class="head">
      <Head></Head>
    </div>
    <div class="leftStyle">
      <LeftSilder></LeftSilder>
    </div>
    <div class="content">
      <router-view></router-view>
    </div>
  </div>
</template>

<script>
import Head from './head.vue'
import LeftSilder from './leftSilder.vue'
export default {
  components:{Head,LeftSilder},
  data() {
    return {

    };
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.head{
  height: 50px;
}
.leftStyle{
  position:fixed;
  left: 0;
  width: 20%;
  height:calc(100% - 50px);
  background: #544242
}
.content{
  position: absolute;
  left: 20%;
  width: 80%;
  height: calc(100% - 50px);
  background: rgba(111, 240, 245, 0.534);
}
</style>
