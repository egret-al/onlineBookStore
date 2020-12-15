<template>
  <div class="main">
    <div class="default-icon">
      <div class="my-icon"></div>
      <div v-if="address.id == defaultAddressId" class="is-default">默认</div>
    </div>
    <div class="address-info">
      <p>
        <span class="receiver-name">{{this.address.receiver_name}}</span>
        <span class="phone">{{this.address.phone}}</span>
      </p>
      <p class="receiver-address">{{this.address.address}}</p>
    </div>
    <div class="address-operation">
      <router-link :to="{path: '/address-edit', query: {id: this.address.id}}">
        <div class="edit-address">编辑</div>
      </router-link>
      <div class="set-default" @click="settingDefault(address.id)">设置默认</div>
    </div>
  </div>
</template>

<script>
import { mapState } from "vuex";

export default {
  props: {
    address: {
      type: Object,
      required: true
    },
    defaultAddressId: {
      type: Number,
      required: true
    }
  },
  components: {},
  data() {
    return {}
  },
  computed: {
    ...mapState({
      username: (state) => state.username,
    }),
  },
  methods: {
    async settingDefault(id) {
      //发起请求设置默认地址
      const res = await this.$http.post('/user-server/api/v1/address/pri/setDefaultAddress', {
        addressId: id,
        username: this.username
      })
      if (res.code === 1) {
        //默认地址设置成功，通知父组件刷新
        this.$emit('refreshAddresses')
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
  mounted() { },
  created() { }
}
</script>
<style lang='stylus' scoped>
.main
  width 100%
  display flex
  .default-icon
    width 10%
    margin-right 20px
    .my-icon
      margin-left 10px
      width 20px
      height 20px
      border-radius 10px
      background-color red
    .is-default
      margin-top 2px
      font-size 10px
      color red
  .address-info
    width 40%
    font-size 12px
    color black
    p
      margin-top 5px
      text-align left 
    .phone
      color gray
      margin-left 20px
  .address-operation
    width 50%
    div
      float right
      color gray
      font-size 12px
    .edit-address
      padding-top 10px
      height 100%
      margin-right 10px
    .set-default
      padding-top 10px
      height 100%
      margin-right 5px
</style>
