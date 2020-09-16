<template>
  <div class="main">
    我是搜索框
    <!-- <cube-input v-model="searchValue" ></cube-input> -->
  </div>
</template>

<script>
export default {
  components: {},
  data() {
    return {
      searchValue: ''
    }
  },
  methods: {},
  mounted() {},
}
</script>
<style lang='scss' scoped>
.main {
  margin-top: 40px;
  display: flex;
  justify-content: center;
  line-height: 30px;
  background-color: rosybrown;
}
</style>
