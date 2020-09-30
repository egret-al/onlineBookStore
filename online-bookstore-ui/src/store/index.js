import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

let store = new Vuex.Store({
  state: {
    //token持久化
    token: localStorage.getItem('token') || '',
    cartArray: JSON.parse(localStorage.getItem('cartArray')) || []
  },
  mutations: {
    //设置token
    setToken(state, token) {
      state.token = token
    },
    //添加商品到购物车，如果有数据则数量加1，否则加入到数组中
    addCart(state, tag) {
      let goods = state.cartArray.find(v => v.title == tag.label)
      if (goods) {
        goods.cartCount += 1
      } else {
        state.cartArray.push({
          title: tag.label,
          cartCount: 1
        })
      }
    },
    //购物车数量加1
    addNumber(state, index) {
      state.cartArray[index].cartCount++
    },
    //购物车数量减1
    subNumber(state, index) {
      //如果减少到了0就移出
      if (state.cartArray[index].cartCount > 1) {
        state.cartArray[index].cartCount--
      } else {
        if (window.confirm('确定移出商品吗？')) {
          state.cartArray.splice(index, 1)
        }
      }
    },
    //清空购物车
    clearAllCart(state) {
      state.cartArray = []
    }
  },
  actions: {},
  //相当于vue的computed
  getters: {
    //得到购物车商品数量
    countSum: state => {
      let num = 0
      state.cartArray.forEach(v => {
        num += v.cartCount
      })
      return num
    }
  },
  modules: {}
})

//监听每次调用mutations的时候，都会进入到这个方法
store.subscribe((mutations, state) => {
  localStorage.setItem('cartArray', JSON.stringify(state.cartArray))
})
export default store