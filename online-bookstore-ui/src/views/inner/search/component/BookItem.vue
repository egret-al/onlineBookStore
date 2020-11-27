<template>
  <div class="main">
    <div class="item-img">
      <img :src="book.main_cover" />
    </div>
    <div class="item-info">
      <div class="item-title">{{book.book_name}}</div>
      <div class="item-price">￥{{book.price}}</div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    book: {
      type: Object,
      required: true
    }
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
