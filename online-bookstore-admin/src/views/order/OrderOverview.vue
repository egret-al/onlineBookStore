<template>
  <div class="main">
    <el-card shadow="hover">
      <e-chart-graph style="height: 280px" :titleText="titlePrice" :chartData="topPriceGraph">图表1</e-chart-graph>
    </el-card>
    <div class="graph">
      <el-card shadow="hover">
        <e-chart-graph style="height: 260px" :titleText="titleCount" :chartData="topCountGraph">图表左</e-chart-graph>
      </el-card>
      <el-card shadow="hover">
        <e-chart-graph style="height: 260px">图表右</e-chart-graph>
      </el-card>
    </div>
  </div>
</template>

<script>
import EChartGraph from "@/views/util/EChartGraph";

export default {
  components: {
    EChartGraph
  },

  data() {
    return {
      topCountGraph: {
        xData: [], //横坐标
        series: [] //{ name: '', data: [], type: ''}
      },
      topPriceGraph: { },
      titleCount: '半年销售量',
      titlePrice: '半年热销额'
    };
  },

  methods: {},

  mounted() {},

  async created() {
    const priceRes = await this.$http.post('/order-server/api/v1/order-graph/pri/getPriceOverviewHalfYearTop6')
    if(priceRes.code === 1) {
      let series = priceRes.data.series
      for (let i = 0; i < series.length; i++) {
        series[i].type = 'line'
      }
      this.topPriceGraph = priceRes.data
    }

    const countRes = await this.$http.post('/order-server/api/v1/order-graph/pri/getCountOverviewHalfYearTop6')
    if(countRes.code === 1) {
      let series = countRes.data.series
      for (let i = 0; i < series.length; i++) {
        series[i].type = 'bar'
      }
      this.topCountGraph = countRes.data
    }
  }
};
</script>
<style lang="scss" scoped>
.graph {
  margin-top: 20px;
  display: flex;
  justify-content: space-between;
  .el-card {
    width: 48%;
  }
}
</style>
