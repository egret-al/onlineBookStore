//对axios的封装
import axios from 'axios'

const service = axios.create({
  //url = baseURL + requestURL，直接访问后台的网关，由网关进行路由转发，负载均衡
  baseURL: 'http://127.0.0.1:9527',
  //配置请求超时时间为5s
  timeout: 5000 
})

//导出模块
export default service