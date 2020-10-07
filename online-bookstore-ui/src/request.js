import axios from 'axios'
import store from './store/index'
import router from './router/index'

axios.defaults.baseURL = 'http://127.0.0.1:9527'
axios.defaults.timeout = 5000

//http全局拦截，将token放在header上面到后端
//请求拦截
axios.interceptors.request.use(config => {
  if (store.state.token) {
    config.headers.token = store.state.token
  }
  return config
})
//响应拦截
axios.interceptors.response.use(response => {
  if (response.status === 200) {
    const data = response.data
    //约定如果返回状态码是-1则为token过期
    if (data.code === -1) {
      //需要重新登录
      store.commit('setToken', '')
      localStorage.removeItem('token')
      //跳转到login页面
      router.replace({
        path: '/login'
      })
    }
    return data
  }
  return response
})

export default axios