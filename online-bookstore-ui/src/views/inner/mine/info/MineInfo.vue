<template>
  <div class="main">
    <div class="head-district">
      <img src="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1605784182040&di=ffd7c359f16e02a55265319741d57f6c&imgtype=0&src=http%3A%2F%2Fblog.gqylpy.com%2Fmedia%2Fai%2F2019-09%2F9a6d0d9d-1477-453d-843f-f3b7442390cb.png">
    </div>
    <div class="content-district">
      <div class="group" style="border-top: 1px solid gray">
        <div class="des-label">我的账号：</div>
        <div class="value-content">{{this.account.username}}</div>
      </div>
      <router-link :to="{path: '/modify-nickname'}">
        <div class="group">
          <div class="des-label">我的昵称：</div>
          <div class="value-content">{{this.account.user.nickname}}</div>
          <i class="cubeic-arrow"></i>
        </div>
      </router-link>
      <div class="group">
        <div class="des-label">性别：</div>
        <div class="value-content">
          <label><input id="male" @click="modifySex('男')" v-model="selected" type="radio" name="sex" value="0">男</label>
		      <label><input id="female" @click="modifySex('女')" v-model="selected" style="margin-left: 15px" type="radio" name="sex" value="1">女</label>
        </div>
      </div>
      <router-link :to="{path: '/modify-phone'}">
        <div class="group">
          <div class="des-label">我的手机：</div>
          <div class="value-content">{{this.account.user.phone}}</div>
          <i class="cubeic-arrow"></i>
        </div>
      </router-link>
      <div class="group">
        <div class="des-label">我的积分：</div>
        <div class="value-content">{{this.account.score}}</div>
      </div>
      <router-link :to="{path: '/top-up'}">
        <div class="group">
          <div class="des-label">账户余额：</div>
          <div class="value-content">{{this.account.balance}}</div>
          <i class="cubeic-arrow"></i>
        </div>
      </router-link>
    </div>
    <div class="footer">
      <router-link :to="{path: '/modify-password'}">
        <span>修改密码</span>
      </router-link>
    </div>
  </div>
</template>

<script>
import { mapState } from "vuex";

export default {
  components: {},
  data() {
    return {
      selected: '1',
      account: {
        user: {
          'nickname': ''
        }
      }
    }
  },
  computed: {
    ...mapState({
      username: (state) => state.username,
    }),
  },
  methods: {
    async modifySex(sex) {
      const res = this.$http.post(`/user-server/api/v1/user/pri/modifySex`, {
        'account_username': this.username,
        'sex': sex
      })
    }
  },
  mounted() {},
  async created() {
    const res = await this.$http.get(`/user-server/api/v1/account/pub/getAccountWithUser/${this.username}`)
    if (res.code === 1) {
      this.account = res.data
      if (this.account.user.sex === '女') this.selected = '1'
      else this.selected = '0'
    }
  }
}
</script>
<style lang='stylus' scoped>
.head-district
  margin-top 40px
  width 100%
  height 180px
  img
    margin-top 10%
    width 80px
    height 40px
.content-district
  width 100%
  color black
  a
    color black
  .group
    display flex
    font-size 13px
    height 40px
    line-height 40px
    border-bottom 1px solid gray
    .des-label
      width 40%
    .value-content
      width 50%
      text-align left 
.footer
  margin-top 50px
  font-size 10px
  color gray
</style>
