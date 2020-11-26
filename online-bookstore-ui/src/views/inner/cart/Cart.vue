<template>
  <div class="main">
    <div v-if="this.shoppingTrolleys.length == 0" class="no-shopping">
      <span class="des">购物车为空！</span>
      <router-link :to="{path: '/footer/index'}">
        <span class="to-index">去购物</span>
      </router-link>
    </div>
    <div class="shoppingList" v-if="this.shoppingTrolleys.length != 0">
      <cart-item @refreshCart="refreshCart" v-for="item in shoppingTrolleys" :key="item.id" :shoppingTrolley="item"></cart-item>
    </div>
  </div>
</template>

<script>
import {mapState} from 'vuex'
import CartItem from './component/CartItem'

export default {
  components: {
    CartItem
  },

  data() {
    return {
      shoppingTrolleys: []
    }
  },

  computed: {
    ...mapState({
      username: (state) => state.username,
    }),
  },

  methods: {
    async refreshCart() {
      const res = await this.$http.post('/user-server/api/v1/shopping/pri/selectCompleteProductByAccount', {
        'username': this.username
      })
      if (res.code === 1) {
        this.shoppingTrolleys = res.data
      }
    }
  },

  mounted() {

  },

  async created() {
    this.refreshCart()
  }
}
</script>
<style lang="stylus" scoped>
.main
  margin-top 20px
.no-shopping
  padding-top 100px
  width 100%
  height 300px
  .to-index
    color blue
</style>
