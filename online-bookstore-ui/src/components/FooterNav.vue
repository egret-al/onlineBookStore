<template>
  <div class="main">
    <transition :name="transitionName">
      <router-view class="router" />
    </transition>
    <cube-tab-bar v-model="selectedLabelDefault" :data="tabs" @click="clickHandler" @change="changeHandler" class="footerNav">
    </cube-tab-bar>
    <span class="collect-count">{{collectCount}}</span>
  </div>
</template>

<script>
import {mapGetters, mapState} from 'vuex'

export default {
  components: {},
  data() {
    return {
      transitionName: 'slide-right',
      selectedLabelDefault: '首页',
      tabs: [
        {
          label: '首页',
          icon: 'cubeic-home',
        },
        {
          label: '分类',
          icon: 'cubeic-tag',
        },
        {
          label: '搜索',
          icon: 'cubeic-search',
        },
        {
          label: '购物车',
          icon: 'cubeic-mall',
        },
        {
          label: '我的',
          icon: 'cubeic-person',
        },
      ],
    }
  },
  methods: {
    clickHandler(label) {
      // if you clicked home tab, then print 'Home'
    },
    //点击不同的导航才会触发
    changeHandler(label) {
      // if you clicked different tab, this methods can be emitted
      switch (label) {
        case '首页':
          this.$router.replace({path: '/footer/index'})
          break
        case '分类':
          this.$router.replace({path: '/footer/classify'})
          break
        case '搜索':
          this.$router.replace({path: '/footer/search'})
          break
        case '购物车':
          this.$router.replace({path: '/footer/cart'})
          break
        case '我的':
          this.$router.replace({path: '/footer/mine'})
          break
      }
    },
  },

  computed: {
    ...mapGetters({
      //获取到vuex中（getters）的属性
      collectCount: 'collectCount'
    }),
    ...mapState({
      username: (state) => state.username,
    }),
  },

  mounted() {},

  async created() {
    switch (this.$route.path) {
      case '/footer/index':
        this.selectedLabelDefault = '首页'
        break
      case '/footer/list':
        this.selectedLabelDefault = '分类'
        break
      case '/footer/search':
        this.selectedLabelDefault = '搜索'
        break
      case '/footer/cart':
        this.selectedLabelDefault = '购物车'
        break
      case '/footer/mine':
        this.selectedLabelDefault = '我的'
        break
    }
    //获取当前用户的购物车商品数量
    const sumRes = await this.$http.post('/user-server/api/v1/shopping/pri/getBookCountByUsername', { 'username': this.username })
    localStorage.setItem('collectCount', sumRes.data)
    this.$store.commit('setCollectCount', sumRes.data)
    console.log(sumRes)
  },
}
</script>
<style lang="stylus" scoped>
.cube-tab-bar.footerNav
  position fixed
  bottom 0
  left 0
  z-index 1000
  width 100%
  background #fff
  .cube-tab div
    font-size 16px
    padding-top 3px
  i
    font-size 20px
.router
  position absolute
  width 100%
  transition all 0.5s ease
.silde-left-enter, .slide-right-leave-active
  opacity 0
  -webkit-transform translate(-100%, 0)
  transform translate(-100%, 0)
.slide-left-leave-active, .slide-right-enter
  opacity 0
  -webkit-transform translate(-100%, 0)
  transform translate(-100%, 0)
.collect-count
  position fixed
  bottom 33px
  right 23%
  z-index 1001
  width 18px
  height 18px
  line-height 18px
  border-radius 50%
  font-size 14px
  background-color red
  color white
</style>
