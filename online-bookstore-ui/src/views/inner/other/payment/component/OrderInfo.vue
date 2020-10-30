<template>
  <div>
    <div class="info">
      <ul>
        <li>订单号：{{ this.order.serial_number }}</li>
        <li>订单创建时间：{{ this.order.create_time }}</li>
        <li>下单数量：{{ this.order.product_count }}本</li>
        <li v-if="this.order.order_payment_status == -1">
          订单状态：<span class="expire">订单过期，请重新下单</span>
        </li>
        <li v-if="this.order.order_payment_status == 0">
          订单状态：<span class="unpayment">未支付</span>
        </li>
      </ul>
    </div>
    <div class="footer">
      <cube-button class="cancel-button" @click="cancelOrder"
        >取消订单</cube-button
      >
      <cube-button class="pay-button" @click="pay">购买</cube-button>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    order: {
      type: Object,
      required: true,
    },
  },

  components: {},
  data() {
    return {};
  },
  methods: {
    //取消订单
    async cancelOrder() {
      const result = await this.$http.get(
        `/order-server/api/v1/order/pri/cancelOrder/${this.order.serial_number}/${this.order.username_id}`
      );
      if (result.code === 1) {
        const toast = this.$createToast({
          txt: "取消成功！",
          type: "correct",
          time: 1000,
        });
        toast.show();
        this.$router.push({ path: "/footer/index" });
      } else if (result.code === 0) {
        const toast = this.$createToast({
          txt: result.message,
          type: "correct",
          time: 1000,
        });
        toast.show();
      }
    },

    //购买
    async pay() {
      const result = await this.$http.get(
        `/user-server/api/v1/account/pri/purchaseBook/${this.order.serial_number}`
      );
      if (result.code === 1) {
        const toast = this.$createToast({
          txt: "购买成功！",
          type: "correct",
          time: 1000,
        });
        toast.show();
        this.$router.push({ path: "/footer/index" });
      } else if (result.code === 0) {
        const toast = this.$createToast({
          txt: result.message,
          type: "correct",
          time: 1000,
        });
        toast.show();
      }
    },
  },
  mounted() {},
};
</script>
<style lang="stylus" scoped>
.info
  padding-top 20px
  width 100%
  .expire
    color red
  .unpayment
    color green
  ul
    margin-top 10px
    li
      font-size 14px
      color grey
      text-align left
      padding-left 10%
      margin-top 3px
.footer
  display flex
  flex-wrap wrap
  margin-top 30px
  button
    width 20%
    font-size 14px
    text-align center
    margin-left 40px
    height 40px
    line-height 0px
  .cancel-button
    background-color #fff
    color #EF4F4F
    border 1px solid #EF4F4F
  .pay-button
    background-color #fff
    color #26a2ff
    border 1px solid #26a2ff
</style>
