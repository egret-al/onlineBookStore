<template>
  <div class="main">
    <p class="title" style="text-align: left; padding-left: 10px; font-size: 18px">
      {{ bookData.book_name }}
    </p>
    <hr />
    <div>
      <p class="price">
        市场价：<del>￥{{ bookData.price + 20 }}</del
        >&nbsp;&nbsp;销售价：
        <span class="now_price"> ￥{{ bookData.price }} </span>
      </p>
      <div class="purchase">
        购买数量：<br />
        <button class="sub" @click="subNumber">-</button>
        <cube-input readonly class="purchase-number" v-model="number"></cube-input>
        <button class="add" @click="addNumber">+</button>
        <cube-validator :model="number" :rules="validatorNumber" />
        <cube-switch class="switch-use-score" v-model="useScore">
          使用积分
        </cube-switch>
      </div>
      <!--各种参数信息-->
      <div class="info">
        <ul>
          <li>库存数量：{{ bookData.bookStorage.residue_count }}</li>
          <li>作者：{{ bookData.author }}</li>
          <li>ISBN：{{ bookData.isbn }}</li>
          <li>出版社：{{ bookData.publisher }}</li>
          <li>上架时间：{{ bookData.create_time }}</li>
        </ul>
      </div>

      <div class="purchase-btn">
        <cube-button class="buy" @click="immediatelyPurchase">立刻购买</cube-button>
        <cube-button class="add-cart" style="margin-left: 5px" @click="addCart">加入购物车</cube-button>
      </div>
      <div class="introduce">
        <cube-button class="picture-word-introduce" @click="pictureAndWordIntroduce">图文介绍</cube-button>
        <cube-button class="show-comment" style="margin-left: 5px" @click="showComment">查看评论</cube-button>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState } from "vuex";

export default {
  props: {
    bookData: {
      type: Object,
      required: true,
    },
  },
  components: {},
  data() {
    return {
      number: 1,
      validatorNumber: {
        type: "number",
        min: 0,
      },
      useScore: false,
    };
  },
  computed: {
    ...mapState({
      username: (state) => state.username,
    }),
  },

  methods: {
    //减少购买数量
    subNumber() {
      if (this.number <= 0) return;
      this.number--;
    },

    //增加购买数量
    addNumber() {
      if (this.number >= this.bookData.bookStorage.residue_count) return;
      this.number++;
    },

    //直接购买
    async immediatelyPurchase() {
      if (typeof this.number === "number") {
        //请求服务器得到默认的收货地址
        const defaultAddressRes = await this.$http.post('/user-server/api/v1/address/pri/selectDefaultAddress', {
          'account_username': this.username
        })
        console.log(defaultAddressRes)
        if (defaultAddressRes.code == 1) {
          //发起创建订单的请求
          const createOrderRes = await this.$http.post('/user-server/api/v1/account/pri/createOrder', {
            'book_id': this.bookData.id,
            'username_id': this.username,
            'order_content': `下单了${this.number}本《${this.bookData.book_name}》`,
            'product_count': this.number,
            'use_score': this.useScore == true ? 1 : 0,
            'book_name': this.bookData.book_name,
            'phone': defaultAddressRes.data.phone,
            'receiver_name': defaultAddressRes.data.receiver_name,
            'address': defaultAddressRes.data.address
          })
          console.log(createOrderRes)
          const toast = this.$createToast({
            txt: createOrderRes.message,
            type: 'correct',
            time: 1000
          })
          toast.show()
          let serialNumber = createOrderRes.data.serial_number
          this.$router.push({
            path: '/payment',
            query: { serialNumber: serialNumber }
          })
        }
      } else {
        this.number = 0
      }
    },

    //加入购物车
    addCart() {},

    //图文信息（详细介绍）
    pictureAndWordIntroduce() {
      this.$router.push({
        path: "/introduce",
        query: { id: this.bookData.id },
      });
    },

    //查看评论
    showComment() {},

    validateNumber(value) {
      return typeof value === "number" && !isNaN(value);
    },
  },
  watch: {
    number(newValue, oldValue) {
      if (this.validateNumber(newValue)) {
        console.log("合法数字");
      } else {
        console.log("非法数字");
      }
    },
  },
  mounted() {},
  created() {},
};
</script>
<style lang="stylus" scoped>
.main
  margin 10px
  box-shadow 0 4px 11px 0 rgba(43, 51, 59, 0.6)
  padding-top 10px
  .price
    padding-top 5px
    padding-left 10px
    text-align left
    color #C0C0C9
    font-size 14px
    .now_price
      color red
      font-size 16px
      font-weight bold
  .purchase
    padding-top 20px
    text-align left
    padding-left 10px
    margin-bottom 20px
    display flex
    .cube-input
      width 40px
      padding 0
      height 20px
      margin-left 5px
    button
      border 0
    .add
      margin-left 5px
  .switch-use-score
    margin-left 10px
    height 20px
    color grey
    font-size 15px

  .purchase-btn
    padding-left 10px
    display flex
    flex-wrap wrap
    padding-bottom 20px
    button
      font-size 14px
      text-align center
      width 25%
      height 33px
      line-height 0
    .buy
      background-color #26a2ff
    .add-cart
      background-color #EF4F4F
  .info
    li
      font-size 14px
      text-align left
      padding-left 10px
      margin-bottom 10px
      color grey
  .introduce
    display flex
    flex-wrap wrap
    padding-left 10px
    padding-bottom 10px
    button
      font-size 14px
      text-align center
      width 25%
      height 33px
      line-height 0
    .picture-word-introduce
      background-color #fff
      color #26a2ff
      border 1px solid #26a2ff
    .show-comment
      background-color #fff
      color #EF4F4F
      border 1px solid #EF4F4F
</style>
