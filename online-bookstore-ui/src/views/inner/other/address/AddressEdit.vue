<template>
  <div class="main">
    <div class="receiver-name">
      <span>收货人：</span><cube-input v-model="address.receiver_name"></cube-input>
    </div>
    <div class="receiver-phone">
      <span>手机号码：</span><cube-input v-model="address.phone"></cube-input>
    </div>
    <div class="address-detail">
      <span>详细地址：</span><cube-textarea v-model="address.address"></cube-textarea>
    </div>
    <div class="address-operation">
      <cube-button @click="saveAddress">保存</cube-button>
    </div>
  </div>
</template>

<script>
export default {
  components: {},
  data() {
    return {
      address: {
        phone: '',
        receiver_name: '',
        address: '',
        id: -1
      }
    }
  },
  methods: {
    validatePhone(phone) {
      var myreg=/^[1][3,4,5,7,8][0-9]{9}$/
      if (!myreg.test(phone)) {
        return false;
      } else {
        return true;
      }
    },

    showToast(type, msg) {
      if (type === 1) {
        const toast = this.$createToast({
          txt: msg,
          type: 'correct',
          time: 1000
        })
        toast.show()
      } else if (type === -1) {
        const toast = this.$createToast({
          txt: msg,
          type: 'error',
          time: 1000
        })
        toast.show()
      }
    },

    async saveAddress() {
      if (this.validatePhone(this.address.phone)) {
        if (this.address.receiver_name === '' || this.address.address === '') {
          this.showToast(-1, '请填写正确的数据！')
        } else {
          const res = await this.$http.post('/user-server/api/v1/address/pri/updateAddress', this.address)
          if (res.code === 1) {
            this.showToast(1, '修改成功!')
            this.$router.replace({path: '/address'})
          } else {
            this.showToast(-1, res.message)
          }
        }
      } else {
        this.showToast(-1, '手机号码格式错误！')
      }
    }
  },
  mounted() { },
  async created() {
    let addressId = this.$route.query.id
    const res = await this.$http.get(`/user-server/api/v1/address/pri/selectOne/${addressId}`)
    if (res.code === 1) {
      this.address = res.data
    }
  }
}
</script>
<style lang='stylus' scoped>
.main
  margin-top 40px
  .receiver-name, .receiver-phone, .address-detail
    width 100%
    display flex
    font-size 13px
    padding-top 10px
    padding-bottom 10px
    border-bottom 1px solid gray
    span
      width 90px
      margin-top 8px
      margin-left 20px
  .cube-input
    font-size 13px
    height 30px
  .address-operation
    margin-top 50px
    display flex
    justify-content center
    .cube-btn
      width 20%
      height 40px
      font-size 12px
      line-height 0px
      background-color #fff
      color #26a2ff
      border 1px solid #26a2ff
</style>
