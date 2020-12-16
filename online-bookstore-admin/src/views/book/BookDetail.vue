<template>
  <div class="main">
    <el-row>
      <el-col :span="8">
        <img class="main-cover" :src="this.book.main_cover" />
      </el-col>
      <el-col :span="16">
        <ul>
          <li>
            图书名称：《{{ this.book.book_name }}》
            <el-button class="book-operation" type="text" @click="openDialogBookName">修改</el-button>
          </li>
          <el-dialog title="提示" :visible.sync="dialogBookNameVisible" width="30%" :before-close="handleClose">
            <el-input type="text" placeholder="请输入图书名称" v-model="newBookName" maxlength="50" show-word-limit></el-input>
            <span slot="footer" class="dialog-footer">
              <el-button @click="dialogBookNameVisible = false">取 消</el-button>
              <el-button type="primary" @click="modifyBookName">确 定</el-button>
            </span>
          </el-dialog>
          <li>ISBN：{{ this.book.isbn }}</li>
          <li>
            定价：{{ this.book.price }}
            <el-button class="book-operation" type="text" @click="openDialogPrice">修改</el-button>
          </li>
          <el-dialog title="提示" :visible.sync="dialogPriceVisible" width="30%" :before-close="handleClose">
            <el-input type="number" placeholder="请输入价格" v-model="newPrice" maxlength="30" show-word-limit></el-input>
            <span slot="footer" class="dialog-footer">
              <el-button @click="dialogPriceVisible = false">取 消</el-button>
              <el-button type="primary" @click="modifyPrice">确 定</el-button>
            </span>
          </el-dialog>
          <li>
            库存：{{this.book.bookStorage.residue_count}}
            <el-button class="book-operation" type="text" @click="openDialogStorage">修改</el-button>
            <span class="storage-note">
              上次修改时间：{{this.book.bookStorage.last_add_time}}
            </span>
          </li>
          <el-dialog title="提示" :visible.sync="dialogStorageVisible" width="30%" :before-close="handleClose">
            <el-input type="number" placeholder="请输入价格" v-model="newStorage" maxlength="30" show-word-limit></el-input>
            <span slot="footer" class="dialog-footer">
              <el-button @click="dialogStorageVisible = false">取 消</el-button>
              <el-button type="primary" @click="modifyStorage">确 定</el-button>
            </span>
          </el-dialog>
          <li>上架时间：{{ this.book.create_time }}</li>
          <li>出版社：{{ this.book.publisher }}</li>
          <li>
            描述：{{ this.book.description }}
            <el-button class="book-operation" type="text" @click="openDialogDescription">修改</el-button>
          </li>
          <el-dialog title="提示" :visible.sync="dialogDescriptionVisible" width="50%" :before-close="handleClose">
            <el-input type="textarea" :autosize="{ minRows: 1, maxRows: 20}" placeholder="请输入内容" v-model="newDescription"></el-input>
            <span slot="footer" class="dialog-footer">
              <el-button @click="dialogDescriptionVisible = false">取 消</el-button>
              <el-button type="primary" @click="modifyDescription">确 定</el-button>
            </span>
          </el-dialog>
        </ul>
      </el-col>
    </el-row>
    <div class="down">
      <el-tabs type="border-card">
        <el-tab-pane label="图片">
          <div class="picture-list" v-if="book.bookResources.length > 0">
            <el-row v-for="item in book.bookResources" :key="item.id">
              <img class="img-list-left-img" :src="item.resource_url"/>
            </el-row>
          </div>
        </el-tab-pane>
        <el-tab-pane label="评论">
          <el-card v-if="comments.length === 0" style="color: red; text-align: center">暂无评论</el-card>
          <el-card v-else class="comment" v-for="item in comments" :key="item.id">
            <div class="top">
              <div class="comment-nickname">{{item.account.user.nickname}}</div>
              <div class="comment-time">{{item.create_time}}</div>
            </div>
            <div class="content">{{item.content}}</div>
          </el-card>
        </el-tab-pane>
      </el-tabs>
    </div>
  </div>
</template>

<script>
export default {
  components: {},
  
  data() {
    return {
      book: {
        bookResources: [],
        bookStorage: {}
      },
      dialogBookNameVisible: false,
      dialogPriceVisible: false,
      dialogDescriptionVisible: false,
      dialogStorageVisible: false,
      newBookName: '',
      newPrice: 1,
      newDescription: '',
      newStorage: 1,
      comments: []
    };
  },

  methods: {
    async modifyBook() {
      const res = await this.$http.post('/book-server/api/v1/book/pri/updateBook', this.book)
      if (res.code === 1) {
        this.$message({
          message: `${res.message}修改将会在稍后生效`,
          type: 'success'
        })
        return true
      } else {
        this.$message.error(res.message)
        return false
      }
    },

    openDialogStorage() {
      this.newStorage = this.book.bookStorage.residue_count
      this.dialogStorageVisible = true
    },

    openDialogDescription() {
      this.newDescription = this.book.description
      this.dialogDescriptionVisible = true
    },

    openDialogPrice() {
      this.newPrice = this.book.price
      this.dialogPriceVisible = true
    },
    
    openDialogBookName() {
      this.dialogBookNameVisible = true
      this.newBookName = this.book.book_name
    },

    async modifyStorage() {
      console.log(this.newStorage)
      if (this.newStorage < this.book.bookStorage.residue_count) {
        this.$message.error('不能手动减少库存！')
        return
      }
      this.dialogStorageVisible = false
      let oldValue = this.book.bookStorage.residue_count
      this.book.bookStorage.residue_count = this.newStorage
      const storageRes = await this.$http.post('/book-server/api/v1/book/pri/updateBookStorage', this.book.bookStorage)
      console.log(storageRes)
      if (storageRes.code === 1) {
         this.$message({
          message: `${storageRes.message}修改将会在稍后生效`,
          type: 'success'
        })
        this.book.bookStorage.last_add_time = this.dateFormat("YYYY-mm-dd HH:MM:SS", new Date())
      } else {
        this.$message.error(storageRes.message)
        this.book.bookStorage.residue_count = oldValue
      }
    },

    modifyDescription() {
      console.log(this.newDescription)
      this.dialogDescriptionVisible = false
      if (this.modifyBook()) {
        this.book.description = this.newDescription
      }
    },

    modifyPrice() {
      console.log(this.newPrice)
      this.dialogPriceVisible = false
      if (this.modifyBook()) {
        this.book.price = this.newPrice
      }
    },

    modifyBookName() {
      console.log(this.newBookName)
      this.dialogBookNameVisible = false
      if (this.modifyBook()) {
        this.book.book_name = this.newBookName
      }
    },

    handleClose(done) {
      this.$confirm('关闭将不会保存修改，是否继续？')
        .then(_ => {
          done();
        })
        .catch(_ => {});
    },

    dateFormat(fmt, date) {
      let ret
      const opt = {
          "Y+": date.getFullYear().toString(),        // 年
          "m+": (date.getMonth() + 1).toString(),     // 月
          "d+": date.getDate().toString(),            // 日
          "H+": date.getHours().toString(),           // 时
          "M+": date.getMinutes().toString(),         // 分
          "S+": date.getSeconds().toString()          // 秒
          // 有其他格式化字符需求可以继续添加，必须转化成字符串
      };
      for (let k in opt) {
          ret = new RegExp("(" + k + ")").exec(fmt);
          if (ret) {
              fmt = fmt.replace(ret[1], (ret[1].length == 1) ? (opt[k]) : (opt[k].padStart(ret[1].length, "0")))
          }
      }
      return fmt;
    }
  },
  
  async created() {
    const result = await this.$http.get(`/book-server/api/v1/book/pub/selectBookContainAllInfoById/${this.$route.query.id}`);
    if (result.code === 1) {
      this.book = result.data;
    }
    let id = this.$route.query.id
    const res = await this.$http.get(`/user-server/api/v1/comment/pri/selectCommentsByBookId/${id}`)
    if (res.code === 1) {
      res.data.forEach(v => this.comments.push(v))
    }
  }
};
</script>
<style lang="scss" scoped>
.main {
  padding-top: 40px;
  padding-left: 20px;
  padding-right: 30px;
  .main-cover {
    margin-left: 100px;
    width: 300px;
    height: 300px;
  }
  .down {
    margin-left: 5%;
    margin-right: 5%;
    margin-bottom: 30px;
    .comment {
      margin-bottom: 10px;
      .top {
        display: flex;
        justify-content: space-between;
        .comment-nickname {
          font-size: 14px;
          color: gray;
        }
        .comment-time {
          color: gray;
          font-size: 13px;
        }
      }
      .content {
        margin-top: 10px;
        font-size: 14px;
      }
    }
  }
}
li {
  margin-right: 100px;
  list-style: none;
  color: #999;
  font-size: 12px;
  line-height: 25px;
}
.book-operation {
  font-size: 12px;
}
.storage-note {
  margin-left: 50px;
}
</style>
