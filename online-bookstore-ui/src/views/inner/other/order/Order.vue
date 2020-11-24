<template>
  <div class="main">
    <ul>
      <li class="order-item" v-for="item in orderList" :key="item.serial_number">
        <p>订单号：{{ item.serial_number }}</p>
        <p>购买数量：{{ item.product_count }}</p>
        <p v-if="item.order_payment_status == 1">总计：{{ item.whole_price }}</p>
        <p>订单内容：{{ item.order_content }}</p>
        <p>创建时间：{{ item.create_time }}</p>
        <p>订单状态：
          <span v-if="item.order_payment_status == 1" class="finish">已完成</span>
          <span v-if="item.order_payment_status == 0" class="unpaid">未支付</span>
          <span v-if="item.order_payment_status == -1" class="expire">已过期</span>
          </p>
        <div class="operation">
          <cube-button class="detail" @click="toOrderDetail(item.serial_number)">查看详情</cube-button>
          <cube-button class="delete" @click="deleteOrder(item.serial_number)">删除</cube-button>
        </div>
      </li>
    </ul>
  </div>
</template>

<script>
import { mapState } from "vuex";
export default {
  components: {},
  data() {
    return {
      orderList: [],
    };
  },
  methods: {
    toOrderDetail(serial) {
      console.log(serial);
      this.$router.push({
        path: "/order-detail",
        query: { serialNumber: serial },
      });
    },

    async deleteOrder(serial) {
      //删除服务器的数据
      const result = await this.$http.get(
        `/order-server/api/v1/order/pri/deleteOrder/${serial}/${this.username}`
      );
      if (result.code === 1) {
        //删除本地数据
        for (let i = 0; i < this.orderList.length; i++) {
          if (serial == this.orderList[i].serial_number) {
            this.orderList.splice(i, 1);
          }
        }
        const toast = this.$createToast({
          txt: "删除成功！",
          type: "correct",
          time: 1000,
        });
        toast.show();
      } else if (result.code === 0) {
        //删除失败
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

  computed: {
    ...mapState({
      username: (state) => state.username,
    }),
  },

  async created() {
    //查询该账号的全部订单
    const result = await this.$http.get(
      `/order-server/api/v1/order/pri/selectOrderByUsername/${this.username}`
    );
    if (result.code === 0) {
      const toast = this.$createToast({
        txt: result.message,
        type: "correct",
        time: 1000,
      });
      toast.show();
      return;
    }
    this.orderList = result.data;
    console.log(this.orderList);
  },
};
</script>
<style lang="stylus" scoped>
.main
  margin-top 40px
  width 100%
  height 400px
  .order-item
    margin 10px
    box-shadow 0 4px 11px 0 rgba(43, 51, 59, 0.6)
    padding-top 10px
    font-size 13px
    padding-bottom 5px
    .finish
      color green
    .expire
      color gray
    .unpaid
      color red
    p
      text-align left
      padding-left 20px
      margin-bottom 4px
    .operation
      display flex
      flex-wrap wrap
      button
        font-size 14px
        text-align center
        width 25%
        height 33px
        line-height 0
        margin-left 20px
      .detail
        background-color #26a2ff
      .delete
        background-color #EF4F4F
</style>
