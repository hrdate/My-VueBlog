<template>
<el-card class="main-card">
<div>
    <el-row :gutter="32">
        <el-row :gutter="10" class="mgb20">
            <el-col :span="6">
                <el-card shadow="hover" :body-style="{padding: '0px'}">
                    <div class="grid-content grid-con-1">
                        <i class="el-icon-lx-people grid-con-icon"></i>
                        <div class="grid-cont-right">
                            <div class="grid-num">{{blogInfo.viewsCount}}</div>
                            <div>用户访问量</div>
                        </div>
                    </div>
                </el-card>
            </el-col>
            <el-col :span="6">
                <el-card shadow="hover" :body-style="{padding: '0px'}">
                    <div class="grid-content grid-con-2">
                        <i class="el-icon-document grid-con-icon"></i>
                        <div class="grid-cont-right">
                            <div class="grid-num">2</div>
                            <div>文章数量</div>
                        </div>
                    </div>
                </el-card>
            </el-col>
            <el-col :span="6">
                <el-card shadow="hover" :body-style="{padding: '0px'}">
                    <div class="grid-content grid-con-3">
                        <i class="el-icon-lx-comment grid-con-icon"></i>
                        <div class="grid-cont-right">
                            <div class="grid-num">5</div>
                            <div>留言数量</div>
                        </div>
                    </div>
                </el-card>
            </el-col>
            <el-col :span="6">
                <el-card shadow="hover" :body-style="{padding: '0px'}">
                    <div class="grid-content grid-con-4">
                        <i class="el-icon-lx-comment grid-con-icon"></i>
                        <div class="grid-cont-right">
                            <div class="grid-num">5</div>
                            <div>评论数量</div>
                        </div>
                    </div>
                </el-card>
            </el-col>
        </el-row>
        <!-- 一周访问量展示 -->
        <!-- <el-card style="margin-top:1.25rem">
            <div class="e-title">一周访问量</div>
            <div style="height:350px">
                <v-chart :options="viewCount" v-loading="loading" />
            </div>
        </el-card> -->
        <el-card shadow="hover" style="height:403px;">
            <div slot="header" class="clearfix">
                <span>总图</span>
            </div>
            <div class="schart-box">
                <schart class="schart" canvasId="line" :options="viewCountMonth"></schart>
            </div>
        </el-card>
    </el-row>
    <el-row :gutter="20" style="margin-top:1.25rem">
      <!-- 文章浏览量排行 -->
      <el-col :span="16">
        <el-card>
          <div class="e-title">文章浏览量排行</div>
          <div style="height:350px">
            <v-chart :options="ariticleRank" v-loading="loading" />
          </div>
        </el-card>
      </el-col>
      <!-- 文章标签统计 -->
      <el-col :span="8">
        <el-card>
          <div class="e-title">文章标签统计</div>
          <div style="height:350px;" v-loading="loading">
            <tag-cloud style="margin-top:1.5rem" :data="tagDTOList" />
          </div>
        </el-card>
      </el-col>
    </el-row>
</div>
</el-card>
</template>

<script>
import Schart from 'vue-schart';
import bus from '../components/bus';
export default {
    userName: 'Home',
    data() {
        return {
            userName: this.$store.state.userName,
            email: this.$store.state.email,
            blogInfo: {
                about: '',
                avatar: '',
                motto: '',
                name: '',
                notice: '',
                viewsCount: 0
            },
            viewCountMonth: {
                type: 'line',
                title: {
                    text: '博客分析总图'
                },
                bgColor: '#fbfbfb',
                labels: ['6月', '7月', '8月', '9月', '10月'],
                datasets: [
                    {
                        label: '用户访问量',
                        data: [234, 278, 270, 190, 230]
                    },
                    {
                        label: '评论数量',
                        data: [114, 138, 200, 235, 190]
                    }
                ]
            },
            viewCount: {
                tooltip: {
                trigger: "axis",
                axisPointer: {
                    type: "cross"
                }
                },
                color: ["#3888fa"],
                legend: {
                data: ["访问量"]
                },
                grid: {
                left: "0%",
                right: "0%",
                bottom: "0%",
                top: "10%",
                containLabel: true
                },
                xAxis: {
                data: [],
                axisLine: {
                    lineStyle: {
                    // 设置x轴颜色
                    color: "#666"
                    }
                }
                },
                yAxis: {
                axisLine: {
                    lineStyle: {
                    // 设置y轴颜色
                    color: "#048CCE"
                    }
                }
                },
                series: [
                    {
                        name: "访问量",
                        type: "line",
                        data: [],
                        smooth: true
                    }
                ]
            },
        };
    },
    components: {
        Schart,
    },
    computed: {
        role() {
            return this.userName === 'admin' ? '超级管理员' : '普通用户';
        }
    },
    created() {
        this.getBlogInfo();
        // this.handleListener();
        // this.changeDate();
    },
    // activated() {
    //     this.handleListener();
    // },
    // deactivated() {
    //     window.removeEventListener('resize', this.renderChart);
    //     bus.$off('collapse', this.handleBus);
    // },
    methods: {
        changeDate() {
            const now = new Date().getTime();
            this.data.forEach((item, index) => {
                const date = new Date(now - (6 - index) * 86400000);
                item.userName = `${date.getFullYear()}/${date.getMonth() + 1}/${date.getDate()}`;
            });
        },
        getBlogInfo() {
            var _this = this;
            this.axios.get("/bloginfo").then(({ data }) => {
                _this.blogInfo = data.data[0];
                _this.$store.commit("checkBlogInfo", data.data[0]);
            });
        }
        // handleListener() {
        //     bus.$on('collapse', this.handleBus);
        //     // 调用renderChart方法对图表进行重新渲染
        //     window.addEventListener('resize', this.renderChart);
        // },
        // handleBus(msg) {
        //     setTimeout(() => {
        //         this.renderChart();
        //     }, 200);
        // },
        // renderChart() {
        //     this.$refs.bar.renderChart();
        //     this.$refs.line.renderChart();
        // }
    }
};
</script>


<style scoped>
.el-row {
    margin-bottom: 20px;
}

.grid-content {
    display: flex;
    align-items: center;
    height: 100px;
}

.grid-cont-right {
    flex: 1;
    text-align: center;
    font-size: 14px;
    color: #999;
}

.grid-num {
    font-size: 30px;
    font-weight: bold;
}

.grid-con-icon {
    font-size: 50px;
    width: 100px;
    height: 100px;
    text-align: center;
    line-height: 100px;
    color: #fff;
}

.grid-con-1 .grid-con-icon {
    background: rgb(45, 140, 240);
}

.grid-con-1 .grid-num {
    color: rgb(45, 140, 240);
}

.grid-con-2 .grid-con-icon {
    background: rgb(100, 213, 114);
}

.grid-con-2 .grid-num {
    color: rgb(45, 140, 240);
}

.grid-con-3 .grid-con-icon {
    background: rgb(242, 94, 67);
}

.grid-con-3 .grid-num {
    color: rgb(242, 94, 67);
}

.grid-con-4 .grid-con-icon {
    background: rgb(208, 221, 28);
}

.grid-con-4 .grid-num {
    color: rgb(208, 221, 28);
}

.user-info {
    display: flex;
    align-items: center;
    padding-bottom: 20px;
    border-bottom: 2px solid #ccc;
    margin-bottom: 20px;
}

.user-avator {
    width: 120px;
    height: 120px;
    border-radius: 50%;
}

.user-info-cont {
    padding-left: 50px;
    flex: 1;
    font-size: 14px;
    color: #999;
}

.user-info-cont div:first-child {
    font-size: 30px;
    color: #222;
}

.user-info-list {
    font-size: 14px;
    color: #999;
    line-height: 25px;
}

.user-info-list span {
    margin-left: 70px;
}

.mgb20 {
    margin-bottom: 20px;
}

.todo-item {
    font-size: 14px;
}

.todo-item-del {
    text-decoration: line-through;
    color: #999;
}

.schart {
    width: 100%;
    height: 300px;
}
</style>
