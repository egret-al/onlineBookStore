<template>
  <div class="main">
    <cube-scroll class="leftPanel">
      <ul>
        <li v-for="(item, index) in typeList" @click="selectItem(item.id)" :key="index" :class="item.active ? 'active' : ''">
          {{ item.type }}
        </li>
      </ul>
    </cube-scroll>
    <cube-scroll class="rightPanel">
      <ul>
        <li v-for="(book, index) in showBookList" :key="index">
          <router-link :to="{path: '/detail', query: {id: book.id}}">
            <img :src="book.main_cover" alt="" />
          </router-link>
          <p>{{ book.book_name }}<i class="cubeic-add" @click="addToCart($event, book)"></i></p>
        </li>
      </ul>
    </cube-scroll>
    <div class="ball-warp">
      <transition @before-enter="beforeEnter" @enter="enter" @afterEnter="afterEnter">
        <div class="ball" v-if="ball.show">
          <div class="inner">
            <i class="cubeic-add"></i>
          </div>
        </div>
      </transition>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex'

export default {
  components: {},

  data() {
    return {
      ball: {
        show: false,
        el: '',
      },
      showBookList: [],
      typeList: [ ],
    }
  },

  computed: {
    ...mapState({
      username: (state) => state.username,
    }),
  },

  methods: {
    //点击左侧分类
    selectItem(id) {
      this.typeList.forEach((value, index) => {
        if (value.id === id) {
          value.active = true
          this.showBookList = value.books
        } else {
          value.active = false
        }
      })
    },

    //添加商品到购物车
    async addToCart(e, book) {
      console.log(book)
      //添加book到购物车，并且vuex的collectCount加1
      const res = await this.$http.post('/user-server/api/v1/shopping/pri/insertShoppingTrolley', {
        'book_id': book.id,
        'account_username': this.username,
        'collect_count': 1
      })
      if (res.code === 1) {
        let c = localStorage.getItem('collectCount')
        localStorage.setItem('collectCount', c - '0' + 1)
        this.$store.commit('addCollectCount', 1)
        //显示小球
        this.ball.show = true
        //获取点击的元素
        this.ball.el = e.target
      } else {
        this.$createToast({
          txt: res.message,
          type: 'error',
          time: 1000
        }).show()
      }
    },

    //--------------------------动画效果------------------------

    beforeEnter(el) {
      //让小球移动到点击的位置
      const dom = this.ball.el
      //获取点击的dom位置
      const rect = dom.getBoundingClientRect()
      console.log(rect)
      const x = rect.left - window.innerWidth * 0.7
      const y = -(window.innerHeight - rect.top)
      console.log(x, y)
      el.style.display = 'block'
      el.style.transform = `translate3d(0, ${y}px, 0)`

      const inner = el.querySelector('.inner')
      inner.style.transform = `translate3d(${x}px, 0, 0)`
    },

    enter(el, done) {
      //触发重绘
      document.body.offsetHeight
      //小球移动回到原点，也就是购物车的位置
      el.style.transform = `translate3d(0, 0, 0)`
      const inner = el.querySelector('.inner')
      inner.style.transform = `translate3d(0, 0, 0)`
      //过渡完成后执行的事件
      el.addEventListener('transitionend', done)
    },

    afterEnter(el) {
      //结束隐藏小球
      this.ball.show = false
      el.style.display = 'none'
    }, 
  },

  mounted() {
    //设置滚动盒子的高度
    const leftPanel = document.querySelector('.leftPanel')
    const rightPanel = document.querySelector('.rightPanel')
    const bodyHeight = document.documentElement.clientHeight
    leftPanel.style.height = bodyHeight - 60 + 'px'
    rightPanel.style.height = bodyHeight - 60 + 'px'
  },

  async created() {
    const res = await this.$http.get('/book-server/api/v1/book/pub/selectAllTypeWithBook')
    if (res.code === 1) {
      this.typeList = res.data
      for (let i = 0; i < this.typeList.length; i++) {
        if (i == 0) this.typeList[i].active = true
        else this.typeList[i].active = false 
      }
      this.showBookList = this.typeList[0].books
    }
  },
}
</script>
<style lang="stylus" scoped>
.ball-warp
  .ball
    position fixed
    left 70%
    bottom 10px
    z-index 1003
    color red
    transition all 1s cubic-bezier(0.49, -0.29, 0.75, 0.41)
    .inner
      width 16px
      height 16px
      transition all 1s linear
.main
  margin-top 20px
  display flex
  .leftPanel
    width 30%
    li
      height 50px
      line-height 50px
      border-bottom 1px solid #fff
      color #333
      background #f8f8f8
      font-size 14px
    .active
      background #fff
      color #e93b3d
  .rightPanel
    width 70%
    ul
      display flex
      flex-wrap wrap
      li
        width 50%
        justify-content center
        align-items center
        font-size 15px
        img
          width 80px
          height 80px
        p
          overflow hidden
          text-overflow ellipsis
          font-size 12px
</style>
