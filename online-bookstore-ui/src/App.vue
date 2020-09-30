<template>
  <div id="app">
    <header-nav :returnIsShow="returnIsShow"></header-nav>
    <router-view />
  </div>
</template>

<script>
import HeaderNav from './components/HeaderNav'

export default {
  components: {
    HeaderNav,
  },
  data() {
    return {
      returnIsShow: false,
    }
  },
  methods: {},
  mounted() {},
  created() {
    this.$router.beforeEach((to, from, next) => {
      if (to.path.indexOf('footer') == -1) {
        //跳转的不是首页的几个页面
        this.returnIsShow = true
      } else {
        //跳转的是其它页面，允许返回到首页的几个页面
        this.returnIsShow = false
      }
      next()
    })
  },
}
</script>
<style lang="stylus">
#app
  font-family Avenir, Helvetica, Arial, sans-serif
  -webkit-font-smoothing antialiased
  -moz-osx-font-smoothing grayscale
  text-align center
  color #2c3e50
  margin-top 20px
</style>
