<template>
  <div class="main">
    <!--轮播图-->
    <cube-slide ref="slide" :data="BannerItems" @change="changePage">
      <cube-slide-item v-for="(item, index) in BannerItems" :key="index" @click.native="clickHandler(item, index)">
        <a :href="item.resource_url">
          <img class="banner" :src="item.resource_url" />
        </a>
      </cube-slide-item>
    </cube-slide>
    <!--滚动分类-->
    <cube-slide ref="slideClassify" :auto-play="false" :data="classifyList">
      <cube-slide-item v-for="(list, index) in classifyList" :key="index">
        <ul class="listUl">
          <li class="listLi" v-for="(item, index1) in list" :key="index1">
            <router-link :to="{path: '/book-type', query: {id: item.id}}">
              <a>
                <img :src="item.img" :alt="item.img" />
                <p>{{item.type}}</p>
              </a>
            </router-link>
          </li>
        </ul>
      </cube-slide-item>
    </cube-slide>
    <book-list></book-list>
  </div>
</template>

<script>
import BookList from './component/BookList'

export default {
  components: {
    BookList,
  },
  data() {
    return {
      BannerItems: [], //轮播图数组
      classifyList: [[], []], //滚动分类数组
    }
  },
  methods: {
    changePage(current) {
      //console.log('当前轮播图序号：' + current)
    },

    clickHandler(item, index) {
      //console.log(item, index)
    },
  },
  mounted() {},
  async created() {
    try {
      //获取轮播图数据
      const bookBannerList = await this.$http.get(
        '/book-server/api/v1/book/pub/selectAllBanner',
      )
      this.BannerItems = bookBannerList.data
      //console.log(bookBannerList.data)

      //获取列表分类
      //const lists = await this.$http.get('http:localhost:8080/api/rollinglist')
      const lists = await this.$http.get(
        '/book-server/api/v1/book/pub/selectAllType',
      )
      for (let i = 0; i < lists.data.length; i++) {
        if (i < lists.data.length / 2) this.classifyList[0].push(lists.data[i])
        else this.classifyList[1].push(lists.data[i])
      }
    } catch (err) {
      console.log(err)
    }
  },
}
</script>
<style lang="stylus" scoped>
.main
  a
    .banner
      display block
      width 100%
      height 175px
  .listUl
    display flex
    flex-wrap wrap
  .listLi
    width 20%
    justify-content center
    img
      width 35px
      height 35px
      border-radius 50%
      padding 5px 0
    p
      font-size 14px
      padding-bottom 5px
</style>
