<template>
  <div class="header">
    <div class="common-header">
      <div class="leftReturn"></div>
      <div class="centerInfo">{{message}}</div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    message: {
      type: String,
      required: false,
      default: '在线书城'
    }
  },
  components: {},
  data() {
    return {}
  },
  methods: {},
  mounted() {},
}
</script>
<style lang='scss' scoped>
.common-header {
  background-color: #26A2FF;
  height: 40px;
  position: fixed;
  width: 100%;
  top: 0;
  z-index: 999;
  line-height: 40px;
  color: white;
  font-size: 16px;
}
</style>
