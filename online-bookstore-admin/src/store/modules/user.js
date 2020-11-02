
const state = {
  //token持久化
  token: localStorage.getItem('token') || '',
  username: localStorage.getItem('username') || ''
}

const mutations = {
  //设置token
  setToken(state, token) {
    state.token = token
  },

  //设置登录账号
  setUsername(state, username) {
    state.username = username
  },
}

const actions = { }

export default {
  namespaced: true,
  state,
  mutations,
  actions
}

