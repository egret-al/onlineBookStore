<template>
  <div>
    <div class="product-info">
      <div class="picture">
        <img class="cover" :src="this.book.main_cover" />
      </div>
      <div class="info">
        <ul>
          <li>书名：{{ this.book.book_name }}</li>
          <li>作者：{{ this.book.author }}</li>
          <li>上架时间：{{ this.book.create_time }}</li>
          <li>ISBN：{{ this.book.isbn }}</li>
          <li>出版社：{{ this.book.publisher }}</li>
          <li>价格：{{ this.book.price }}￥</li>
        </ul>
      </div>
    </div>
    <div class="order-info">
      <div class="info">
        <ul>
          <li>订单号：{{ this.order.serial_number }}</li>
          <li>订单创建时间：{{ this.order.create_time }}</li>
          <li>下单数量：{{ this.order.product_count }}本</li>

          <li>订单状态：
              <span v-if="this.order.order_payment_status == -1" class="expire">订单过期，请重新下单</span>
              <span v-if="this.order.order_payment_status == 0" class="unpaid">未支付</span>
              <span v-if="this.order.order_payment_status == 1" class="finish">已支付</span>
          </li>
          <li style="margin-top: 20px">收货人姓名：{{this.order.receiver_name}}</li>
          <li>联系电话：{{this.order.phone}}</li>
          <li>收货地址：{{this.order.address}}</li>
          <div class="operation">
            <cube-button @click="payment" class="to-pay" v-if="this.order.order_payment_status == 0">去支付</cube-button>
          </div>
          <div v-if="this.order.order_payment_status == 1">
            <li v-if="this.order.send_status == 1 || this.order.send_status == 2">发货时间：{{ this.order.delivery_time }}</li>
            <li v-if="this.order.send_status == 2">签收时间：{{ this.order.end_time }}</li>
            <li>可获积分：{{ this.order.obtain_score }}</li>
            <li>支付时间：{{ this.order.payment_time }}</li>
            <li>
              使用积分抵扣：<span v-if="this.order.use_score == 1">是</span>
              <span v-if="this.order.use_score != 1">否</span>
            </li>
            <li>
              支付账号：{{ this.order.username_id }}
            </li>
            <li>总计：{{ this.order.whole_price }}</li>
          </div>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  async created() {
    //请求订单信息
    const resultOrderInfo = await this.$http.get(
      `/order-server/api/v1/order/pri/selectOrderBySerialNumber/${this.$route.query.serialNumber}`
    );
    const order = resultOrderInfo.data;
    this.order = order;
    //请求图书资源
    let bookId = this.order.book_id;
    const resultBookInfo = await this.$http.get(
      `/book-server/api/v1/book/pub/selectAllBookAloneById/${bookId}`
    );
    const book = resultBookInfo.data;
    this.book = book;
  },

  components: {},
  data() {
    return {
      order: {},
      book: {},
    };
  },
  methods: {
    payment() {
      this.$router.push({ path: '/payment', query: { serialNumber: this.order.serial_number } })
    }
  },
  mounted() {},
};
</script>
<style lang="stylus" scoped>
.product-info
  margin-top 50px
  padding-left 20px
  display flex
  box-shadow 0 4px 12px 0 rgba(43, 51, 59, 0.6)
  border-radius 5px
  .picture
    border 1px solid #C3C5C8
    .cover
      margin-top 10px
      width 100px
      height 100px
  .info
    width 100%
    ul
      margin-top 10px
      li
        font-size 12px
        color grey
        text-align left
        padding-left 10%
        margin-top 5px

.order-info .info
  padding-top 20px
  width 100%
  .expire
    color gray
  .unpaid
    color red
  .finish
    color green
  ul
    margin-top 10px
    li
      font-size 14px
      color grey
      text-align left
      padding-left 10%
      margin-top 3px
  .operation
    display flex
    justify-content center
    .to-pay
      margin-top 30px
      height 40px
      line-height 0px
      width 30%
      font-size 13px
      background-color #fff
      color #26a2ff
      border 1px solid #26a2ff
</style>
