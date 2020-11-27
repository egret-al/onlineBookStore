<template>
  <div class="main">
    <div class="input-area">
      <cube-input v-model="keyWord" placeholder="请输入..."></cube-input>
      <button class="search-button"><i>搜索</i></button>
    </div>
    <p class="please-enter-key" v-if="bookInfoList.length == 0">{{this.remindContent}}</p>
    <div v-if="bookInfoList.length != 0" class="content-area">
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
  </div>
</template>

<script>
import BookItem from './component/BookItem'

export default {
  components: {
    BookItem
  },

  data() {
    return {
      bookInfoList: [],
      keyWord: '',
      remindContent: '',
      noKeyWord: '请输入关键字搜索',
      noSearchResult: '没有该物品！'
    };
  },

  watch: {
    //监视keyWord属性
    keyWord: async function(newValue, oldValue) {
      //根据监视的值，查询服务器
      if (newValue !== '') {
        const res = await this.$http.post('/book-server/api/v1/book/pub/selectBookAndResourceLike', {
          'book_name': newValue
        })
        console.log(res)
        if (res.code === 1 && res.data.length != 0) {
          this.bookInfoList = res.data
        } else if (res.code === 0 && res.data.length === 0) {
          this.bookInfoList = []
          this.remindContent = this.noSearchResult
          console.log(res.data)
        }
      } else {
        this.bookInfoList = []
        this.remindContent = this.noKeyWord
      }
    }
  },

  methods: {},
  
  mounted() {},

  created() {
    this.remindContent = this.noKeyWord
  }
};
</script>
<style lang="stylus" scoped>
.main
  width 100%
  height 100px
  margin-top 20px
  .input-area
    display flex
    .cube-input
      line-height 0px
      width 90%
      border 2px solid red
      font-size 10px
    .search-button
      border 0px 
      background-color red
      color white
      font-size 12px
  .please-enter-key
    color gray
    font-size 13px
    margin-top 50%
  .content-area
    margin-top 20px
    display flex
    flex-wrap wrap
    justify-content space-between
    padding-bottom 55px
    .book-link
      width 48%
      margin-bottom 17px
    .item-img
      font-size 0
      box-shadow 0 4px 11px 0 rgba(43, 51, 59, 0.6)
      border-radius 8px
      img
        width 100%
        height 200px
        border-radius 8px
    .item-title
      display -webkit-box
      -webkit-box-orient vertical
      -webkit-line-clamp 2
      overflow hidden
      word-break break-all
      font-size 11px
      height 26px
      line-height 13px
      margin-top 10px
      color #2b333b
    .item-price
      margin-top 0px
      font-size 12px
      color #d93f30
</style>
