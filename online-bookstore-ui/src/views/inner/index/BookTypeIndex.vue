<template>
  <div class="main">
    <book-list v-if="bookInfoList.length > 0" :bookInfoList="bookInfoList"></book-list>
    <p v-if="bookInfoList.length == 0">{{showMessage}}</p>
  </div>
</template>

<script>
import BookList from './component/BookList'

export default {
  components: {
    BookList
  },

  data() {
    return {
      bookInfoList: [],
      bookIsNull: '该类商品为空！',
      bookRemindMessage: '请稍等...',
      netError: '网络错误，请稍后再试！',
      showMessage: ''
    }
  },

  methods: {},
  
  mounted() {},

  async created() {
    this.showMessage = this.bookRemindMessage
    let typeId = this.$route.query.id
    const res = await this.$http.get(`/book-server/api/v1/book/pub/selectAllInfoByType/${typeId}`)
    if (res.code === 1) {
      this.bookInfoList = res.data
      this.showMessage = ''
    } else {
      this.showMessage = this.bookIsNull
    }
  }
}
</script>
<style lang='stylus' scoped>
.main
  margin-top 50px
  p
    color red
    margin-top 50%
    font-size 13px
</style>
