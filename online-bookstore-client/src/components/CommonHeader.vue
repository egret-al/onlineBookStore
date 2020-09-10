<template>
  <div class="common-header">
    {{message}}
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
  height: 30px;
  display: flex;
  line-height: 30px;
  justify-content: center;
  align-content: center;
  color: white;
  font-size: 14px;
}
</style>
