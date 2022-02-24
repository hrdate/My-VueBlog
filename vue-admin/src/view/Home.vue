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
                            <div class="grid-num">{{blogInfo.userCount}}</div>
                            <div>用户数量</div>
                        </div>
                    </div>
                </el-card>
            </el-col>
            <el-col :span="6">
                <el-card shadow="hover" :body-style="{padding: '0px'}">
                    <div class="grid-content grid-con-2">
                        <i class="el-icon-document grid-con-icon"></i>
                        <div class="grid-cont-right">
                            <div class="grid-num">{{blogInfo.articleCount}}</div>
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
                            <div class="grid-num">{{blogInfo.messageCount}}</div>
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
                            <div class="grid-num">{{blogInfo.commentsCount}}</div>
                            <div>评论数量</div>
                        </div>
                    </div>
                </el-card>
            </el-col>
        </el-row>
        <!-- 一周访问量展示 -->
        <el-card style="margin-top:1.25rem">
            <!-- <div class="e-title">一周访问量</div>
            <div style="height:350px">
                <v-chart :options="viewCount" v-loading="loading" />
            </div> -->
        </el-card>
        <el-card shadow="hover" style="height:403px;">
            <div slot="header" class="clearfix">
                <span>用户访问量：{{blogInfo.viewsCount}}</span>
            </div>
            <div class="schart-box">
                <schart class="schart" canvasId="line" :options="viewCountMonth"></schart>
            </div>
        </el-card>
    </el-row>
    <el-row :gutter="20" style="margin-top:1.5rem;height:403px;">
      <!-- 文章浏览量排行 -->
      <el-col :span="16">
        <el-card shadow="hover">
            <div class="schart-box" >
                <schart class="schart" canvasId="bar" :options="ariticleRankChart"></schart>
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
import TagCloud from '../components/tagCloud.vue';
export default {
    userName: 'Home',
    created() {
        this.getBlogInfo();
        this.getTagDTOList();
        this.getAriticleRank();
    },
    data() {
        return {
            userName: this.$store.state.userName,
            email: this.$store.state.email,
            loading: true,
            tagDTOList: [],
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
                        type: "bar",
                        data: [],
                        smooth: true
                    }
                ]
            },

            ariticleRankChart:{
                type: 'bar',
                title: {
                    text: '文章浏览量排行'
                },
                bgColor: '#fbfbfb',
                labels: [],
                datasets: [
                    {
                        label: '文章标题',
                        data: []
                    }
                ]
            }
        };
    },
    components: {
        Schart,
        TagCloud,
    },
    computed: {
        role() {
            return this.userName === 'admin' ? '超级管理员' : '普通用户';
        }
    },
    methods: {
        getBlogInfo() {
            var _this = this;
            this.axios.get("/admin/bloginfo").then(({ data }) => {
                _this.blogInfo = data.data;
                _this.$store.commit("checkBlogInfo", data.data);
            });
        },
        getTagDTOList(){
            var tagList = this.$store.state.tagList
            tagList.forEach(item => {
                this.tagDTOList.push({
                    "id": item.id,
                    "name": item.tagName
                });
            });
            
        },
        getAriticleRank(){
            var _this = this;
            this.axios.get("/article/admin/articleRankList").then(({ data }) => {
                var res = data.data;
                res.forEach(item => {
                    _this.ariticleRankChart.datasets[0].data.push(item.viewsCount)
                    if(item.title.length > 7){
                        item.title = item.title.substring(0,10) + '...'
                    }
                    _this.ariticleRankChart.labels.push(item.title)
                    
                });
            });
            this.loading = false;
        },
        
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
