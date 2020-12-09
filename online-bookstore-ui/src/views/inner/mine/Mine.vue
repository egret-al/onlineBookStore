<template>
  <div>
    <div class="head">
      <img src="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1605784182040&di=ffd7c359f16e02a55265319741d57f6c&imgtype=0&src=http%3A%2F%2Fblog.gqylpy.com%2Fmedia%2Fai%2F2019-09%2F9a6d0d9d-1477-453d-843f-f3b7442390cb.png">
      <div>
        <p>{{this.user.nickname}}</p>
        <p>{{this.username}}</p>
      </div>
    </div>
    <ul>
      <li v-for="item in mineArray" class="mineitem" @click="itemClick(item)" :key="item.label">
        <router-link :to="{ path: item.path }">
          <i class="icon-class" :class="item.icon"></i>
          <span class="minetitle">{{ item.label }}</span>
          <i class="cubeic-arrow"></i>
        </router-link>
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
      user: {},
      mineArray: [
        {
          label: '我的信息',
          icon: 'cubeic-person',
          path: '/infomation',
        },
        {
          label: '我的订单',
          icon: 'cubeic-square-right',
          path: '/order',
        },
        {
          label: '收货地址',
          icon: 'cubeic-square-right',
          path: '/address'
        },
        {
          label: '退出',
          type: 'exit',
          icon: 'cubeic-close',
        },
      ],
    };
  },
  methods: {
    itemClick(item) {
      if (item.type === 'exit') {
        //退出操作，清除token，跳转登录
        this.$store.commit('setToken', '');
        this.$store.commit('clearCollectCount')
        localStorage.removeItem('token');
        localStorage.removeItem('collectCount')
        localStorage.removeItem('username')
        this.$router.push({ path: '/login' });
      }
    },
  },
  computed: {
    ...mapState({
      username: (state) => state.username,
    }),
  },
  mounted() {},
  async created() {
    //根据账号获取用户信息
    const userRes = await this.$http.post('/user-server/api/v1/user/pri/getUserInfo', {
      'username': this.username
    })
    if (userRes.code === 1) {
      this.user = userRes.data
    }
  }
};
</script>
<style lang="stylus" scoped>
.mineitem
  font-size 14px
  text-align left
  height 50px
  line-height 50px
  padding-left 5%
  border-bottom 1px solid #eee
  .icon-class
    margin-right 10px
  .minetitle
    display inline-block
    width 85%
.head
  margin-top 20px
  height 200px
  width 100%
  img
    margin-top 10%
    width 80px
    height 40px
  p
    color #7E8C8D
    margin-top 10px
    font-size 14px
</style>
