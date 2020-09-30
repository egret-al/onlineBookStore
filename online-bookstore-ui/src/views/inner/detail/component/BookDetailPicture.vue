<template>
  <div class="main">
    <div class="item-img">
      <img :src="cover" />
    </div>
  </div>
</template>

<script>
export default {
  props: {
    cover: {
      type: String,
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
.item-img
  img
    width 300px
    height 300px
</style>
