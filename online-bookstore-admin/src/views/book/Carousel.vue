<template>
  <div class="main">
    <el-upload action="http://localhost:9527/book-server/api/v1/file/pri/upload" list-type="picture-card" :on-preview="handlePictureCardPreview"
      :on-remove="handleRemove" :on-success="handleImgsSuccess" :headers="getToken()" :auto-upload="true" :file-list="fileList">
      <i slot="default" class="el-icon-plus"></i>
      <div slot="file" slot-scope="{file}">
        <img class="el-upload-list__item-thumbnail" :src="file.url" alt="">
        <span class="el-upload-list__item-actions">
          <span class="el-upload-list__item-preview" @click="handlePictureCardPreview(file)">
            <i class="el-icon-zoom-in"></i>
          </span>
          <span v-if="!disabled" class="el-upload-list__item-delete" @click="handleDownload(file)">
            <i class="el-icon-download"></i>
          </span>
          <span v-if="!disabled" class="el-upload-list__item-delete" @click="handleRemove(file)">
            <i class="el-icon-delete"></i>
          </span>
        </span>
      </div>
    </el-upload>
    <el-button plain @click="refreshClient" round>刷新客户端</el-button>
    <el-dialog :visible.sync="dialogVisible">
      <img width="100%" :src="dialogImageUrl" alt="">
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      dialogImageUrl: '',
      dialogVisible: false,
      disabled: false,

      fileList: []
    }
  },

  methods: {
    async refreshClient() {
      //请求删除redis缓存
      const res = await this.$http.post('/book-server/api/v1/book/pri/refreshCache')
      if (res.code === 1) {
        this.$message({
          type: 'success',
          message: res.message
        })
      } else {
        this.$message.error(res.message)
      }
    },

    async handleImgsSuccess(res, file) {
      console.log(res)
      console.log(file)
      this.fileList.push({url: res.data})
      //请求后端，保存到数据库
      const saveRes = await this.$http.post('/book-server/api/v1/book/pri/insertBookBanner', { 'resource_url': res.data })
      if (saveRes.code === 1) {
        this.$message({
          type: 'success',
          message: '上传成功'
        })
      } else {
        //上传失败，删除已经删除服务器的文件
        this.$message.error(saveRes.message)
        this.dealDelete({url: res.data})
      }
    },

    async dealDelete(file) {
      const res = await this.$http.post('/book-server/api/v1/file/pri/delete', { url: file.url })
      console.log(res)
      if (res.code === 1) {
        //发起请求删除数据库的信息
        const deleteRes = await this.$http.post('/book-server/api/v1/book/pri/deleteBookBannerByUrl', { url: file.url })
        //确保删除成功够再删除本地
        if (deleteRes.code === 1) {
          for (let i = 0; i < this.fileList.length; i++) {
            if (this.fileList[i].url === file.url) {
              this.fileList.splice(i, 1)
            }
          }
          this.$message({
            type: 'success',
            message: '删除成功!'
          })
        } else {
          this.$message.error(deleteRes.message)
        }
      } else {
        this.$message.error(res.message)
      }
    },

    async handleRemove(file) {
      this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.dealDelete(file)
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });         
      })
    },

    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url
      this.dialogVisible = true
    },

    getToken() {
      let data = { 'token': localStorage.getItem('token') }
      return data
    },
  },

  async created() {
    const res = await this.$http.get('/book-server/api/v1/book/pub/selectAllBanner')
    if (res.code === 1) {
      res.data.forEach(v => this.fileList.push({url: v.resource_url}))
    }
  }
}
</script>
<style lang='scss' scoped>
.main {
  margin-left: 40px;
  margin-top: 40px;
}
</style>
