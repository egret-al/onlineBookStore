<template>
  <div class="main">
    <div class="head">
      <div class="user">{{comment.account.user.nickname}}</div>
      <div class="time">{{comment.create_time}}</div>
    </div>
    <div class="content">{{comment.content}}</div>
  </div>
</template>

<script>
export default {
  props: {
    comment: {
      type: Object,
      required: true
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
<style lang='stylus' scoped>
.main
  margin-top 40px
.head
  padding-left 20px
  padding-right 20px
  display flex
  justify-content space-between
  .user
    font-size 12px
    color black
  .time
    font-size 8px
    color gray
.content
  padding-left 20px
  text-align left
  font-size 13px
</style>
