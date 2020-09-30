<template>
  <div class="main">
    <!--点击跳转到图书详情页面-->
    <router-link class="book-link" v-for="item in bookInfoList" :key="item.id" :to="{path: '/detail', query: {id: item.id}}">
      <div class="item-img">
        <img :src="item.main_cover" />
      </div>
      <div class="item-info">
        <div class="item-title">{{item.book_name}}</div>
        <div class="item-price">￥{{item.price}}</div>
      </div>
    </router-link>
  </div>
</template>

<script>
export default {
  props: {
    bookInfoList: {
      type: Array,
      required: true,
    },
  },
  components: {},
  data() {
    return {}
  },
  methods: {},
  mounted() {},
}
</script>
<style lang='stylus' scoped>
.main
  margin-top 20px
  display flex // 设置flex布局
  flex-wrap wrap // 换⾏排列
  justify-content space-between // 两端对⻬
  padding-bottom 55px
.book-link
  width 48%
  margin-bottom 17px
.item-img
  font-size 0 // 消除图⽚元素产⽣的间隙
  box-shadow 0 4px 11px 0 rgba(43, 51, 59, 0.6) // 设置图⽚阴影，rgba前三个参数是颜⾊编码，最后⼀个是透明度
  border-radius 8px // 设置图⽚圆⻆
  img
    width 100%
    height 200px
    border-radius 8px
.item-title
  // 设置超过两⾏隐藏 start
  display -webkit-box
  -webkit-box-orient vertical
  -webkit-line-clamp 2
  overflow hidden
  word-break break-all
  // 设置超过两⾏隐藏 end
  font-size 11px
  height 26px
  line-height 13px
  margin-top 10px
  color #2b333b
// 价格
.item-price
  margin-top 0px
  font-size 12px
  color #d93f30
</style>
