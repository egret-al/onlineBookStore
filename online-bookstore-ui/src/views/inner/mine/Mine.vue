<template>
  <div>
    <img
      class="headerimg"
      src="https://cdn.pixabay.com/photo/2020/01/14/02/09/vapor-4763904_1280.jpg"
      alt=""
    />
    <ul>
      <li
        v-for="item in mineArray"
        class="mineitem"
        @click="itemClick(item)"
        :key="item.label"
      >
        <router-link :to="{ path: item.path }">
          <i class="icon-class" :class="item.icon"></i>
          <span class="minetitle">{{ item.label }}</span>
          <i class="cubeic-arrow"></i>
        </router-link>
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  components: {},
  data() {
    return {
      mineArray: [
        {
          label: "我的信息",
          icon: "cubeic-person",
          path: "/infomation",
        },
        {
          label: "商品收藏",
          icon: "cubeic-search",
          path: "/collection",
        },
        {
          label: "我的足迹",
          icon: "cubeic-navigation",
          path: "/footprint",
        },
        {
          label: "我的订单",
          icon: "cubeic-square-right",
          path: "/order",
        },
        {
          label: "退出",
          type: "exit",
          icon: "cubeic-close",
        },
      ],
    };
  },
  methods: {
    itemClick(item) {
      if (item.type === "exit") {
        //退出操作，清除token，跳转登录
        this.$store.commit("setToken", "");
        localStorage.removeItem("token");
        this.$router.push({ path: "/login" });
      }
    },
  },
  mounted() {},
};
</script>
<style lang="stylus" scoped>
.mineitem
  font-size 14px
  text-align left
  height 50px
  line-height 50px
  padding-left 5%
  border-bottom 1px solid #eee
  .icon-class
    margin-right 10px
  .minetitle
    display inline-block
    width 85%
.headerimg
  height 150px
  width 100%
</style>
