<template>
  <div class="main">
    <el-row>
      <el-col :span="4" v-for="item in bookList" :key="item.id">
        <el-card :body-style="{ padding: '0px' }">
          <img :src="item.main_cover" class="image" />
          <div style="padding: 14px;">
            <span class="book-name">《{{ item.book_name }}》</span>
            <div class="bottom clearfix">
              <time class="time">上架时间：<br />{{ item.create_time }}</time><br/>
              <router-link :to="{ path: '/home/detail', query: { id: item.id } }">
                <el-button type="text" class="button">查看详情</el-button>
              </router-link>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  props: {
    bookList: {
      type: Array,
      required: true
    }
  },
  components: {},
  data() {
    return {
      currentDate: new Date()
    }
  },
  methods: {},
  created() {},
  mounted() {}
};
</script>
<style lang="scss" scoped>
.main {
  margin-top: 20px;
  .el-card {
    margin: 20px;
    height: 330px;
  }
  .book-name {
    font-size: 13px;
  }
}
.time {
  font-size: 10px;
  color: #999;
}

.bottom {
  margin-top: 13px;
  line-height: 12px;
}

.button {
  padding: 0;
  float: right;
}

.image {
  width: 100%;
  height: 200px;
  display: block;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}

.clearfix:after {
  clear: both;
}
</style>
