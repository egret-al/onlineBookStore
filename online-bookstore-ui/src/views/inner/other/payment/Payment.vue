<template>
  <div class="main">
    <product-info :book='this.book'></product-info>
    <order-info :order='this.order'></order-info>
  </div>
</template>

<script>
import ProductInfo from './component/ProductInfo'
import OrderInfo from './component/OrderInfo'

export default {
  components: {
    ProductInfo,
    OrderInfo
  },
  data() {
    return {
      order: { },
      book: { }
    }
  },
  methods: {},
  mounted() {},
  async created() {
    let serialNumber = this.$route.query.serialNumber
    if (!serialNumber) {
      return
    }
    //请求订单信息
    const resultOrderInfo = await this.$http.get(`/order-server/api/v1/order/pri/selectOrderBySerialNumber/${serialNumber}`)
    const order = resultOrderInfo.data
    this.order = order
    //请求图书资源
    let bookId = this.order.book_id
    const resultBookInfo = await this.$http.get(`/book-server/api/v1/book/pub/selectAllBookAloneById/${bookId}`)
    const book = resultBookInfo.data
    this.book = book

    console.log('订单信息：', order)
    console.log('商品信息：', book)
  },
}
</script>
<style lang='stylus' scoped>
.main
  margin-top 50px
  width 90%
</style>
