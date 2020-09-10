<template>
  <div class="tab">
    <cube-tab-bar v-model="selectedLabelSlots" @click="clickHandler">
      <cube-tab v-for="item in tabs" :icon="item.icon" :label="item.label" :key="item.path" :value="item.path">
      </cube-tab>
    </cube-tab-bar>
  </div>
</template>

<script>
export default {
  components: {},
  data() {
    return {
      selectedLabelSlots: '/',
      tabs: [{
        label: '首页',
        path: '/',
        icon: 'cubeic-home'
      }, {
        label: '搜索',
        path: '/search',
        icon: 'cubeic-search'
      }, {
        label: '购物车',
        path: '/shopping-cart',
        icon: 'cubeic-like'
      }, {
        label: '订单',
        path: '/order',
        icon: 'cubeic-vip'
      }, {
        label: '我的',
        path: '/user-info',
        icon: 'cubeic-person'
      }]
    }
  },
  methods: {
    clickHandler (path) {
      //如果不是当前的路由，则进行对应的跳转
      console.log(path);
      if (path != this.$router.path) {
        this.$router.push(path)
      }
    }
  },
  mounted() {},
  created() {
    //默认路由选择器，比如刷新页面，需要重新进到当前路由
    this.selectedLabelSlots = this.$route.path
  },
}
</script>
<style lang='scss' scoped></style>
