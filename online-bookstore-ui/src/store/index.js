import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

let store = new Vuex.Store({
  state: {
    //token持久化
    token: localStorage.getItem('token') || '',
    username: localStorage.getItem('username') || '',
    collectCount: localStorage.getItem('collectCount') || 0
  },
  mutations: {
    //设置token
    setToken(state, token) {
      state.token = token
    },

    //设置登录账号
    setUsername(state, username) {
      state.username = username
    },

    setCollectCount (state, count) {
      state.collectCount = count
    },

    clearCollectCount (state) {
      state.collectCount = 0
    },

    addCollectCount (state, count) {
      state.collectCount += count
    },

    subCollectCount (state, count) {
      state.collectCount -= count
    },
  },
  actions: {},
  //相当于vue的computed
  getters: {
    collectCount: state => {
      return state.collectCount
    },
  },
  modules: {}
})

//监听每次调用mutations的时候，都会进入到这个方法
/*
store.subscribe((mutations, state) => {
  //localStorage.setItem('collectCount', JSON.stringify(state.collectCount))
  console.log('getItem', localStorage.getItem('collectCount'))
  state.collectCount = localStorage.getItem('collectCount')
})
*/
export default store