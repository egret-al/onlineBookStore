<template>
  <div class="common-header">
    <div class="leftReturn">
      <span v-if="returnIsShow" @click="$router.back(-1)"><i class="cubeic-back"></i></span>
    </div>
    <div class="centerInfo">图书</div>
  </div>
</template>

<script>
export default {
  props: {
    returnIsShow: {
      type: Boolean,
      required: true,
    },
  },
  components: {},
  data() {
    return {}
  },
  methods: {},
  mounted() {},
  created() {},
}
</script>
<style lang='stylus' scoped>
.common-header
  background-color #26a2ff
  height 40px
  position fixed
  width 100%
  top 0
  z-index 999
  line-height 40px
  color white
  font-size 16px
  display flex
  flex-wrap wrap
  .leftReturn
    width 10%
  .centerInfo
    width 80%
</style>
