<template>
  <div class="main">
    <address-item @refreshAddresses='refreshAddresses' v-for="item in addresses" :key="item.id" :address="item" :defaultAddressId="defaultAddressId"></address-item>
    <div class="operation">
      <cube-button class="add-address" @click="toAddressAdd">添加地址</cube-button>
    </div>
  </div>
</template>

<script>
import { mapState } from "vuex";
import AddressItem from './component/AddressItem'

export default {
  components: {
    AddressItem
  },
  data() {
    return {
      addresses: [],
      defaultAddressId: -1
    }
  },
  methods: {
    async refreshAddresses() {
      const addressListRes = await this.$http.get(`/user-server/api/v1/address/pri/selectByAccount/${this.username}`)
      if (addressListRes.code === 1) {
        this.addresses = addressListRes.data
      }
      const userRes = await this.$http.post('/user-server/api/v1/user/pri/getUserInfo', {
        'username': this.username
      })
      if (userRes.code === 1) {
        this.defaultAddressId = userRes.data.default_address_id
      }
    },

    toAddressAdd() {
      this.$router.push({ path: "/address-add" });
    }
  },
  mounted() {},
  computed: {
    ...mapState({
      username: (state) => state.username,
    }),
  },
  async created() {
    this.refreshAddresses()
  }
}
</script>
<style lang='stylus' scoped>
.main
  padding-top 20px
  width 100%
  .operation
    margin-top 30px
    display flex
    justify-content center
    .add-address
      width 35%
      height 40px
      font-size 13px
      line-height 0px
      background-color #fff
      color #26a2ff
      border 1px solid #26a2ff
</style>
