<template>
  <!--封装的EChart组件-->
  <div style="height: 100%" ref="echart">
    echart
  </div>
</template>

<script>
import ECharts from "echarts";

export default {
  props: {
    chartData: {
      type: Object,
      default() {
        return {
          xData: [],
          series: []
        };
      }
    },
    //是否是有坐标的
    isAxisChart: {
      type: Boolean,
      default: true
    }
  },
  computed: {
    options() {
      return this.isAxisChart ? this.axisOption : this.normalOption;
    },
    isCollapsed() {
      return this.$store.state.tab.isCollapsed;
    }
  },
  data() {
    return {
      echart: null,
      axisOption: {
        //图例
        legend: {
          //图例文本颜色
          textStyle: {
            color: "#333"
          }
        },
        //偏移
        grid: {
          left: "20%"
        },
        //悬浮提示
        tooltip: {
          trigger: "axis"
        },
        xAxis: {
          type: "category",
          data: [],
          //x轴样式
          axisLine: {
            lineStyle: {
              color: "#17b3a3"
            }
          },
          axisLabel: {
            color: "#333"
          }
        },
        yAxis: [
          {
            type: "value",
            axisLine: {
              lineStyle: {
                color: "#17b3a3"
              }
            }
          }
        ],
        color: [
          "#2ec7c9",
          "#b6a2de",
          "#5ab1ef",
          "#ffb980",
          "#d87a80",
          "#8d98b3",
          "#e5cf0d",
          "#97b552",
          "#95706d",
          "#dc69aa",
          "#07a2a4",
          "#9a7fd1",
          "#588dd5",
          "#f5994e",
          "#c05050",
          "#59678c",
          "#c9ab00",
          "#7eb00a",
          "#6f5553",
          "#c14089"
        ],
        series: []
      },
      normalOption: {
        tooltip: {
          trigger: "item"
        },
        color: [
          "#0f78f4",
          "#dd536b",
          "#9462e5",
          "#a6a6a6",
          "#e1bb22",
          "#39c362",
          "#3ed1cf"
        ],
        series: []
      }
    };
  },
  methods: {
    initChart() {
      this.initChartData();
      if (this.echart) {
        this.echart.setOption(this.options);
      } else {
        this.echart = ECharts.init(this.$refs.echart);
        this.echart.setOption(this.options);
      }
    },
    initChartData() {
      if (this.isAxisChart) {
        this.axisOption.xAxis.data = this.chartData.xData;
        this.axisOption.series = this.chartData.series;
      } else {
        this.normalOption.series = this.chartData.series;
      }
    },
    resizeChart() {
      //自适应
      this.echart ? this.echart.resize() : "";
    }
  },
  watch: {
    chartData: {
      handler: function() {
        this.initChart();
      },
      deep: true
    },
    isCollapsed() {
      setTimeout(() => {
        this.resizeChart();
      }, 300);
    }
  },
  mounted() {
    //注册事件监听
    window.addEventListener("resize", this.resizeChart);
  },
  destroyed() {
    //移出事件监听，避免内存泄漏
    window.removeEventListener("resize", this.resizeChart);
  }
};
</script>
<style lang="scss" scoped></style>
