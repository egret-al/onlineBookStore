<template>
  <div class="main-comment">
    <div class="content-area" v-if="comments.length > 0">
      <comment-item v-for="item in comments" :key="item.id" :comment="item"></comment-item>
    </div>
    <div class="remind-message" v-if="comments.length == 0">{{showMessage}}</div>
    <div class="footer">
      <cube-input v-model="content" placeholder="请输入..."></cube-input>
      <button class="send-button" v-on:click="sendComment">发送</button>
    </div>
  </div>
</template>

<script>
import { mapState } from "vuex"
import CommentItem from './component/CommentItem.vue'

export default {
  components: {
    CommentItem
  },

  data() {
    return {
      comments: [],
      showMessage: '',
      loading: '请等待...',
      noComment: '暂无评论！',
      content: ''
    }
  },

  computed: {
    ...mapState({
      username: (state) => state.username,
    }),
  },
  
  methods: {
    async sendComment() {
      if (this.content == '') {
        const toast = this.$createToast({
          txt: '请输入内容！',
          type: 'error',
          time: 1000
        })
        toast.show()
      } else {
        const res = await this.$http.post('/user-server/api/v1/comment/pri/insertComment', {
          'account_username': this.username,
          'book_id': this.$route.query.id,
          'content': this.content
        })
        if (res.code === 1) {
          this.content = ''
          this.refreshComments()
        } else {
          const toast = this.$createToast({
            txt: res.message,
            type: 'error',
            time: 1000
          })
          toast.show()
        }
      }
    },

    async refreshComments() {
      this.showMessage = this.loading
      let id = this.$route.query.id
      if (!(parseFloat(1).toString() == 'NaN')) {
        const res = await this.$http.get(`/user-server/api/v1/comment/pri/selectCommentsByBookId/${id}`)
        if (res.code === 1) {
          this.comments = res.data
          this.showMessage = ''
          if (this.comments.length == 0) {
            this.showMessage = this.noComment
          }
        }
      }
    }
  },
  
  mounted() {},

  async created() {
    this.refreshComments()
  }
}
</script>
<style lang='stylus' scoped>
.main-comment
  margin-top 50px
  width 100%
  .footer
    z-index 2
    display flex
    position fixed
    bottom 0px
    width 100%
    height auto
    .cube-input
      width 90%
      border 2px solid gray
      font-size 10px
    .send-button
      width 10%
      border 0px
      background-color #26A2FF
      color white
      font-size 12px
</style>
