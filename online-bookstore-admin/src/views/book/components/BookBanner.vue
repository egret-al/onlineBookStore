<template>
  <div class="main">
    <el-carousel :height="imgHeight + 'px'" :interval="3000" arrow="always">
      <el-carousel-item v-for="item in bannerItems" :key="item.resource_url">
        <el-image
          ref="image"
          style="width: 100%;"
          :src="item.resource_url"
        ></el-image>
      </el-carousel-item>
    </el-carousel>
  </div>
</template>

<script>
export default {
  props: {
    bannerItems: {
      type: Array,
      required: true
    }
  },
  components: {},
  data() {
    return {
      imgHeight: ""
    };
  },
  methods: {
    imgLoad() {
      this.$nextTick(function() {
        // 获取窗口宽度*图片的比例，定义页面初始的轮播图高度
        this.imgHeight = (document.body.clientWidth * 1) / 4;
      });
    }
  },
  mounted() {
    this.imgLoad();
    // 监听窗口变化，使得轮播图高度自适应图片高度
    window.addEventListener("resize", () => {
      this.imgHeight = this.$refs.image[0].height;
    });
  }
};
</script>
<style lang="scss" scoped></style>
