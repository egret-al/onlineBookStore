<template>
<div class="main">
  <el-table max-height="600" style="width: 100%" :data="orderList.slice((currentPage-1)*pageSize,currentPage*pageSize).filter(data => !search || data.book_name.includes(search))">
    <el-table-column type="expand">
      <template slot-scope="props">
        <el-form label-position="left" inline class="demo-table-expand">
          <el-form-item label="订单号">
            <span>{{ props.row.serial_number }}</span>
          </el-form-item>
          <el-form-item label="商品名称">
            <span>{{ props.row.book_name }}</span>
          </el-form-item>
          <el-form-item label="创建时间">
            <span>{{ props.row.create_time }}</span>
          </el-form-item>
          <el-form-item label="购买账号">
            <span>{{ props.row.username_id }}</span>
          </el-form-item>
          <el-form-item label="收件人">
            <span>{{ props.row.receiver_name }}</span>
          </el-form-item>
          <el-form-item label="收货地址">
            <span>{{ props.row.address }}</span>
          </el-form-item>
          <el-form-item label="联系电话">
            <span>{{ props.row.phone }}</span>
          </el-form-item>
          <el-form-item label="商品数量">
            <span>{{ props.row.product_count }}本</span>
          </el-form-item>
          <el-form-item v-if="props.row.order_payment_status === 1" label="总价">
            <span>{{ props.row.whole_price }}</span>
          </el-form-item>
          <el-form-item label="支付状态">
            <span v-if="props.row.order_payment_status === 0">
              <el-tag type="warning">待支付</el-tag>
            </span>
            <span v-if="props.row.order_payment_status === 1">
              <el-tag type="success">已完成</el-tag>
            </span>
            <span v-if="props.row.order_payment_status === -1">
              <el-tag type="info">已过期</el-tag>
            </span>
            <span>{{ props.row.desc }}</span>
          </el-form-item>
        </el-form>
      </template>
    </el-table-column>
    <el-table-column label="订单号" prop="serial_number"></el-table-column>
    <el-table-column label="商品名称" prop="book_name"></el-table-column>
    <el-table-column label="收货地址" prop="address"></el-table-column>
    <el-table-column label="时间" prop="create_time" sortable></el-table-column>
    <el-table-column :filters="[{ text: '已支付', value: 1 }, { text: '待支付', value: 0 }, { text: '已过期', value: -1 }]"
      label="支付状态" prop="order_payment_status" filter-placement="bottom-end" :filter-method="filterTag">
      <template slot-scope="scope">
        <el-tag v-if="scope.row.order_payment_status === 1" type="success" disable-transitions>已支付</el-tag>
        <el-tag v-if="scope.row.order_payment_status === -1" type="info" disable-transitions>已过期</el-tag>
        <el-tag v-if="scope.row.order_payment_status === 0" type="primary" disable-transitions>待支付</el-tag>
      </template>
    </el-table-column>
    <el-table-column label="操作">
      <template slot="header" slot-scope="scope">
        <el-input v-model="search" size="mini" placeholder="输入商品名称搜索"/>
      </template>
      <template slot-scope="scope">
        <span style="color: orange; font-size:10px" v-if="orderList[(currentPage-1) * pageSize + scope.$index].order_payment_status == 0 &&
          orderList[(currentPage-1) * pageSize + scope.$index].send_status == 0" type="warning">待支付</span>

        <el-button v-if="orderList[(currentPage-1) * pageSize + scope.$index].send_status == 0 &&
          orderList[(currentPage-1) * pageSize + scope.$index].order_payment_status == 1" @click.native.prevent="sendBook((currentPage-1) * pageSize + scope.$index)" type="text" size="small">发货</el-button>
        <span style="color: green; font-size:10px" v-if="orderList[(currentPage-1) * pageSize + scope.$index].send_status == 1 && 
          orderList[(currentPage-1) * pageSize + scope.$index].order_payment_status == 1" type="success">已发货</span>
        <span style="color: blue; font-size:10px" v-if="orderList[(currentPage-1) * pageSize + scope.$index].send_status == 2 && orderList[(currentPage-1) * pageSize + scope.$index].order_payment_status == 1" type="success">已签收</span>
        <span style="color: gray; font-size:10px" v-if="orderList[(currentPage-1) * pageSize + scope.$index].order_payment_status == -1" type="info">已过期</span>
      </template>
    </el-table-column>
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
    data() {
      return {
        orderList: [],
        options: [{
          value: 10,
          label: '全部'
        }, {
          value: 1,
          label: '已支付'
        }, {
          value: 0,
          label: '待支付'
        }, {
          value: -1,
          label: '已取消'
        }],
        selectedValue: '',
        search: '',
        currentPage: 1,
        total: 100,
        pageSize: 10
      }
    },
    
    methods: {
      handleSizeChange(size) {
        this.pageSize = size
        console.log('handleSizeChange', size)
      },

      handleCurrentChange(currentPage) {
        this.currentPage = currentPage
        console.log('handleCurrentChange', currentPage)
      },

      filterTag(value, row) {
        return row.order_payment_status === value;
      },

      async sendBook(index) {
        let order = this.orderList[index]
        const sendRes = await this.$http.post('/order-server/api/v1/order/pri/sendBook', order)
        if (sendRes.code === 1) {
          this.$message({
            message: sendRes.message,
            type: 'success'
          })
          this.orderList[index].send_status = 1
        } else {
          this.$message.error(sendRes.message)
        }
      },
    },

    async created() {
      const ordersRes = await this.$http.post('/order-server/api/v1/order/pri/selectAll')
      if (ordersRes.code === 1) {
        this.orderList = ordersRes.data
        this.total = this.orderList.length
      }
      this.value = this.options[0].value
      console.log(this.orderList)
    }
  }
</script>
<style lang="scss" scoped>
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
  .select-pay-status {
    margin-top: 30px;
  }
  .block {
    margin-right: 20px;
    float: right;
  }
</style>