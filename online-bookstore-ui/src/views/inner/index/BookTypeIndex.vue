<template>
  <div class="list-main">
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
    <div @click="loadData">{{message}}</div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      bookInfoList: [],
      isFirstLoad: true,
      message: '数据加载中...',
      pageSize: 10,
      currentPage: 1,
      type: -1
    }
  },

  methods: {
    async loadMore() {
      console.log('加载更多！')
    },

    async loadData() {
      if (this.pageSize === -1 || this.currentPage === -1 || this.type < 1) return
      const res = await this.$http.get(`/book-server/api/v1/book/pub/selectBookAndStorageByTypePage/${this.type}/${this.currentPage}/${this.pageSize}`)
      console.log(res.data)
      if (res.code === 1) {
        res.data.forEach(v => this.bookInfoList.push(v))
        this.currentPage++
        this.isFirstLoad = false
        this.message = '点击加载更多...'
      } else {
        if (this.isFirstLoad) this.message = '该类商品暂时为空！'
        else this.message = '没有数据了哦！！！'
        this.pageSize = -1
        this.currentPage = -1
      }
    }
  },
  
  async created() {
    this.type = this.$route.query.id - '0'
    this.loadData()
  }
}
</script>
<style lang='stylus' scoped>
.list-main
  margin-top 40px
  margin-bottom 20px
.main
  display flex // 设置flex布局
  flex-wrap wrap // 换⾏排列
  justify-content space-between // 两端对⻬
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
