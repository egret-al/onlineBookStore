<template>
  <div class="library-main">
    <el-table v-loading="loading" :data="books.slice((currentPage-1)*pageSize,currentPage*pageSize)" height="600" style="width: 100%">
      <el-table-column fixed type="expand">
        <template slot-scope="props">
          <el-form label-position="left" inline class="demo-table-expand">
            <el-form-item label="书名">
              <span>{{ props.row.book_name }}</span>
            </el-form-item>
            <el-form-item label="作者">
              <span>{{ props.row.author }}</span>
            </el-form-item>
            <el-form-item label="出版社">
              <span>{{ props.row.publisher }}</span>
            </el-form-item>
            <el-form-item label="上架时间">
              <span>{{ props.row.create_time }}</span>
            </el-form-item>
            <el-form-item label="价格">
              <span>{{ props.row.price }}</span>
            </el-form-item>
            <el-form-item label="库存">
              <span>{{ props.row.bookStorage.residue_count }}</span>
            </el-form-item>
            <el-form-item label="ISBN">
              <span>{{ props.row.isbn }}</span>
            </el-form-item>
            <el-form-item style="width: 100%" label="描述">
              <span>{{ props.row.description }}</span>
            </el-form-item>
            
          </el-form>
        </template>
      </el-table-column>
      <el-table-column align="center">
        <template slot-scope="scope">
    　　　<img :src="scope.row.main_cover" width="40" height="40" class="head_pic"/>
    　　</template>
      </el-table-column>
      <el-table-column align="center" label="书名" prop="book_name"></el-table-column>
      <el-table-column align="center" label="作者" prop="author"></el-table-column>
      <el-table-column align="center" label="出版社" prop="publisher"></el-table-column>
      <el-table-column align="center" sortable label="上架时间" prop="create_time"></el-table-column>
      <el-table-column :filters="typeFilters" :filter-method="filterHandler" align="center" label="类型" prop="bookType.type"></el-table-column>
      <el-table-column align="center" sortable label="价格" prop="price"></el-table-column>
    </el-table>
    <div class="block">
      <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="currentPage"
        :page-sizes="[10, 20, 30]" :page-size="pageSize" layout="total, sizes, prev, pager, next, jumper" :total="total">
      </el-pagination>
    </div>
  </div>
</template>

<script>
export default {
  components: {},

  data() {
    return {
      books: [],
      typeFilters: [],
      loading: true,
      currentPage: 1,
      total: 100,
      pageSize: 10,
    }
  },

  methods: {
    filterHandler(value, row, column) {
      return value === row.bookType.id
    },

    handleSizeChange(size) {
      this.pageSize = size
    },

    handleCurrentChange(currentPage) {
      this.currentPage = currentPage
    },
  },
  
  async created() {
    const bookRes = await this.$http.get('/book-server/api/v1/book/pri/selectBookAndType')
    if (bookRes.code === 1) {
      this.books = bookRes.data
      this.total = this.books.length
    }
    const typeRes = await this.$http.get('/book-server/api/v1/book/pub/selectAllType')
    if (typeRes.code === 1) {
      typeRes.data.forEach(v => {
        this.typeFilters.push({text: v.type, value: v.id})
      })
    }
    this.loading = false
  }
}
</script>
<style lang='scss' scoped>
.demo-table-expand {
  font-size: 0;
}
.demo-table-expand label {
  width: 90px;
  color: #99a9bf;
}
.demo-table-expand .el-form-item {
  margin-right: 0;
  margin-bottom: 0;
  width: 50%;
}
.block {
  margin-right: 20px;
  float: right;
}
</style>
