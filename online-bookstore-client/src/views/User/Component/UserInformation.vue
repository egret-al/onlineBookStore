<template>
  <div class="main">
    <div class="header">
      <div class="header-left">
        <img src="../../../assets/images/user-default.png" class="userImg" alt="用户头像"/>
        <p>{{account.user.nickname}}</p>
      </div>
      <div class="header-right">
        <p>积分余额：{{account.score}}</p>
        <p>账户余额：{{account.balance}}</p>
      </div>
    </div>
    <div class="content">
      <div class="content-item" v-for="item in itemList" :key="item.path">
        <router-link :to="{path: item.path}">
          <img class="item-img" :src="item.img" alt=""/>
          <div class="content-word">{{item.label}}</div>
        </router-link>
      </div>
    </div>
    <div class="footer">
      <cube-button @click="signOut">退出</cube-button>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex'

import menu1 from '@/assets/images/menu1.png'
import menu2 from '@/assets/images/menu2.png'
import menu3 from '@/assets/images/menu3.png'
import menu4 from '@/assets/images/menu4.png'
import menu5 from '@/assets/images/menu5.png'
import menu6 from '@/assets/images/menu6.png'

export default {
  components: {},
  data() {
    return {
      itemList: [
        {
          path: 'path1',
          label: '功能1',
          img: menu1
        },
        {
          path: 'path2',
          label: '功能2',
          img: menu2
        },
        {
          path: 'path3',
          label: '功能3',
          img: menu3
        },
        {
          path: 'path4',
          label: '功能4',
          img: menu4
        },
        {
          path: 'path5',
          label: '功能5',
          img: menu5
        },
        {
          path: 'path6',
          label: '功能6',
          img: menu6
        },
      ]
    }
  },
  computed: {
    ...mapState({
      //获取vuex里面存放的用户信息
      account: state => state.user.account
    })
  },
  methods: {
    signOut() {
      //清除token
      this.$store.dispatch('clearToken')
      this.$store.dispatch('clearAccount')
      localStorage.removeItem('token')
      localStorage.removeItem('account')
      this.$router.push({path: '/login'})
    }
  },
  mounted() {},
}
</script>
<style lang='scss' scoped>
.main {
  .header {
    display: flex;
    justify-content: space-around;

    margin-bottom: 15%;
    margin-top: 20%;
    .userImg {
      width: 50px;
      height: 50px;
      border-radius: 50%;
    }
    .header-right {
      p {
        margin-top: 10px;
      }
    }
  }
  .content {
    text-align: center;
    display: flex;
    align-content: flex-start;
    flex-flow: row wrap;
    
    width: 100%;
    height: 400px;
    border-top: gray 1px solid;
    .content-item {
      margin-top: 40px;
      flex: 0 0 25%;
    }
    .item-img {
      width: 60px;
      height: 60px;
    }
  }
}
</style>
