<template>
  <div class="main">
    <address-item v-for="item in addresses" :key="item.id" :address="item"></address-item>
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
      addresses: []
    }
  },
  methods: {},
  mounted() {},
  computed: {
    ...mapState({
      username: (state) => state.username,
    }),
  },
  async created() {
    const addressListRes = await this.$http.get(`/user-server/api/v1/address/pri/selectByAccount/${this.username}`)
    if (addressListRes.code === 1) {
      this.addresses = addressListRes.data
      console.log(this.addresses)
    }
  }
}
</script>
<style lang='stylus' scoped>
.main
  padding-top 20px
  width 100px
  height 100px
</style>
