import axios from '@/request'

//登录接口
export const loginApi = (username, password) => axios.post('/api/v1/account/pub/login', {
  username,
  password
})

//注册接口
export const registryApi = (account, user) => axios.post('/api/v1/account/pub/registry', {
  account,
  user
})