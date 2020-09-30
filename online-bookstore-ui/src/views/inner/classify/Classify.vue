<template>
  <div class="main">
    <cube-scroll class="leftPanel">
      <ul>
        <li v-for="(list, index) in tabsLabel" @click="selectList(index)" :key="index" :class="list.active ? 'active' : ''">
          {{ list.label }}
        </li>
      </ul>
    </cube-scroll>
    <cube-scroll class="rightPanel">
      <ul>
        <li v-for="(tag, index) in tags" :key="index">
          <img :src="tag.image" alt="" />
          <p>{{ tag.label }}<i class="cubeic-add" @click="addToCart($event, tag)"></i></p>
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
export default {
  components: {},
  data() {
    return {
      ball: {
        show: false,
        el: '',
      },
      tags: [],
      tabsLabel: [
        {
          label: '热门推荐',
          active: true,
        },
        {
          label: '手机数码',
          active: false,
        },
        {
          label: '家用电器',
          active: false,
        },
        {
          label: '家用空调',
          active: false,
        },
        {
          label: '电脑产品',
          active: false,
        },
        {
          label: '计生情趣',
          active: false,
        },
        {
          label: '美妆护肤',
          active: false,
        },
        {
          label: '口红',
          active: false,
        },
        {
          label: '手袋',
          active: false,
        },
        {
          label: '金银财宝',
          active: false,
        },
        {
          label: '手机数码',
          active: false,
        },
        {
          label: '手机数码',
          active: false,
        },
      ],
    }
  },
  methods: {
    //点击左侧分类
    selectList(index) {
      this.tabsLabel.forEach((val, ind) => {
        if (ind === index) {
          val.active = true
        } else {
          val.active = false
        }
      })
      this.getClassify(index)
    },
    //获取分类
    async getClassify(index) {
      const result = await this.$http.get('/api/classify', {
        params: {type: index},
      })
      this.tags = result.data
    },

    //添加商品到购物车
    addToCart(e, tag) {
      this.$store.commit('addCart', tag)
      //显示小球
      this.ball.show = true
      //获取点击的元素
      this.ball.el = e.target
    },

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
  created() {
    this.getClassify(0)
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
</style>
