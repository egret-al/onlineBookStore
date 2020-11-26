<template>
  <div class="main">
    <div class="main-cover">
      <img :src="this.shoppingTrolley.book.main_cover">
    </div>
    <div class="info">
      <p>{{this.shoppingTrolley.book.book_name}}</p>
      <p>数量：{{this.shoppingTrolley.collect_count}}</p>
      <p>作者：{{this.shoppingTrolley.book.author}}</p>
      <p>ISBN：{{this.shoppingTrolley.book.isbn}}</p>
      <p>添加时间：{{this.shoppingTrolley.create_time}}</p>
      <div class="operation">
        <span class="delete-operation" @click="deleteCart">删除</span>
        <router-link :to="{path: '/detail', query: {id: this.shoppingTrolley.book.id}}">
          <span class="detail-operation">查看详情</span>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    shoppingTrolley: {
      type: Object,
      required: true
    }
  },

  components: {},
  
  data() {
    return {}
  },

  methods: {
    async deleteCart() {
      const res = await this.$http.post('/user-server/api/v1/shopping/pri/deleteTrolleyById', {
        'id': this.shoppingTrolley.id
      })
      if (res.code === 1) {
        //删除成功，通知刷新
        this.$emit('refreshCart')
      } else {
        const toast = this.$createToast({
          txt: res.message,
          type: 'error',
          time: 1500,
        })
        toast.show()
      }
    }
  },
  
  mounted() {},
}
</script>
<style lang='stylus' scoped>
.main
  width 100%
  height 150px
  box-shadow 0 4px 11px 0 rgba(43, 51, 59, 0.6)
  display flex
  .main-cover
    width 30%
    height 100%
    margin-left 15px
    img
      width 120px
      height 120px
      margin-top 15px
  .info
    width 70%
    height 100%
    margin-top 15px
    p
      font-size 12px
      color gray
      text-align left 
      padding-left 30px
      margin-top 5px
    .operation
      color gray
      margin-left 30px
      font-size 12px
      text-align left
      margin-top 20px 
      .delete-operation
        margin-right 10px
        color red
</style>
