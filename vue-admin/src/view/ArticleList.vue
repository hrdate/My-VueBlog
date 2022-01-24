<template>
<el-card class="main-card">
<div>
    
    <div class="crumbs">
        <el-breadcrumb separator="/">
            <el-breadcrumb-item>
                <i class="el-icon-lx-cascades"/> 文章管理
            </el-breadcrumb-item>
        </el-breadcrumb>
    </div>
    <div>
        <div class="handle-box">
            <el-button icon="el-icon-edit-outline" type="primary" class="handle-del mr10"><router-link :to="{name: 'ArticleAdd',params: {articleId: 0}}">新增文章</router-link></el-button>
            <el-select v-model="selectArticleType" placeholder="类型" class="handle-select mr10">
                <el-option key="1" label="原创" value="原创"/>
                <el-option key="2" label="转载" value="转载"/>
                <el-option key="2" label="翻译" value="翻译"/>
            </el-select>
            <el-select v-model="selectArticleTag" placeholder="标签" class="handle-select mr10">
                <el-option
                    v-for="item in tagList"
                    :key="item.tagName"
                    :label="item.tagName"
                    :value="item.tagName">
                </el-option>
            </el-select>
            <el-input v-model="name" placeholder="关键字" class="handle-input mr10"/>
            <el-button type="primary" icon="el-icon-search" >搜索</el-button>
            
        </div>
        <el-table
            :data="articles"
            border highlight-current-row fit
            class="table"
            ref="multipleTable"
            header-cell-class-name="table-header">
            <el-table-column prop="id" label="ID" width="55px" align="center"/>
            <el-table-column prop="title" label="文章标题"/>
            <!-- <el-table-column prop="description" :show-overflow-tooltip="true" label="文章简述"/> -->
            <!-- <el-table-column prop="author" label="作者"/> -->
            <el-table-column prop="content" :show-overflow-tooltip="true" label="文章原文"/>
            <el-table-column prop="viewsCount" width="80px" label="阅读量"/>
            <el-table-column prop="commentNum" width="80px" label="评论量"/>
            <el-table-column prop="likeNum" width="80px" label="点赞量"/>
            <!-- 文章置顶 -->
            <el-table-column prop="isTop" label="置顶" width="80px" align="center">
                <template slot-scope="scope">
                <el-switch
                    v-model="scope.row.isTop"
                    active-color="#13ce66"
                    inactive-color="#F4F4F5"
                    :disabled="scope.row.isDelete == 1"
                    :active-value="1"
                    :inactive-value="0"
                    @change="changeTop(scope.row)"
                />
                </template>
            </el-table-column>
            <el-table-column label="标签" show-overflow-tooltip align="center" width="110px">
                <template slot-scope="scope">
                    <el-tag type="success" >{{ tagList[scope.row.tagId-1].tagName }}</el-tag>
                    <!-- <el-tag type="warning" v-if="scope.row.type==='3'">翻译</el-tag> -->
                </template>
            </el-table-column>
            <el-table-column prop="type" label="类型" align="center" width="80px">
                <template slot-scope="scope" >
                    <el-tag type="danger">{{scope.row.type}}</el-tag>
                </template>
            </el-table-column>
            <el-table-column prop="created" width="130px" align="center" label="创建时间">
                <template slot-scope="scope" >
                    {{ scope.row.created | date}}
                </template>
            </el-table-column>
            <el-table-column label="操作" width="200px" align="center">
                <template slot-scope="scope">
                    <el-button
                        type="text"
                        icon="el-icon-view">
                        <router-link :to="{name: 'ArticleDetail', params: {articleId: scope.row.id}}">查看</router-link>
                    </el-button>
                    <el-button
                        type="text"
                        icon="el-icon-edit">
                        <router-link :to="{name: 'ArticleEdit', params: {articleId: scope.row.id}}">编辑</router-link>
                    </el-button>
                    <el-button
                        type="text"
                        class="red"
                        icon="el-icon-delete"
                        @click="handleDelete(scope.row.id,scope.row.title)"
                    >删除</el-button>
                    
                </template>
            </el-table-column>
        </el-table>
        <!-- 分页 -->
        <el-pagination align="right"
            background
            class="pagination-container"
            @current-change="currentChange"
            :current-page="currentPage"
            @size-change="sizeChange"
            :page-size="size"
            :total="total"
            :page-sizes="[5,10]"
            layout="total, sizes, prev, pager, next, jumper"
        />
    </div>
</div>
</el-card>
</template>

<script>
export default {
    name: 'article',
    created(){
        this.listArticles();
        this.loadTags();
    },
    mounted () {
    },
    /**
    // 触发搜索按钮
    handleSearch() {
        this.$set(this.query, 'pageIndex', 1);
        this.getData();
    },*/
    data() {
        return {
            articles:{},
            tagList: [],
            selectArticleType: null,
            selectArticleTag: null,
            name: '',
            currentPage: 1,
            total: 0,
            size: 5,
        };
    },
    methods: {
        listArticles() {
            const _this = this;
            this.axios.get("/article/articles",{
                params : {
                    currentPage: this.currentPage,
                    pageSize: this.size
                }
            }
            ).then(res =>{
                console.log(res)
                _this.articles = res.data.data.records;
                _this.currentPage = res.data.data.current;
                _this.total = res.data.data.total;
                _this.pageSize = res.data.data.size;
            });
        },
        currentChange(current) {
            this.currentPage = current;
            this.listArticles();
        },
        sizeChange(size) {
            this.size = size;
            this.listArticles();
        },
        loadTags(){
            const _this = this;  
            this.axios.get(`/tag/tags`).then( res  => {
                _this.tagList = res.data.data;
                _this.$store.state.tagList = res.data.data
            });
        },
        
        // 删除操作
        handleDelete(rowId,title) {
            const _this = this;
            // 二次确认删除
            this.$confirm('确定要删除文章 \" '+title+' \" 吗？', '提示', {
                type: 'warning'
            }).then(() => {
                this.$axios.post('/article/del',rowId, {
                    headers: {
                        "Authorization": localStorage.getItem("token")
                    }
                }).then((res) => {
                    _this.$alert('操作成功', '提示', {
                        confirmButtonText: '确定',
                        callback: action => {
                            _this.$router.push("/article")
                        }
                    });
                });
            }).catch(() => {});
        },
        changeTop(article) {
            let param = new URLSearchParams();
            param.append("isTop", article.isTop);
            // this.axios.put("/articles/top/" + article.id, param);
        },
         
    },
    watch:{
        currentPage(now){
            this.currentPage = now;
            this.listArticles();
        }
    }
    
};
</script>

<style scoped>
.handle-box {
    margin-bottom: 20px;
}

.handle-select {
    width: 120px;
}

.handle-input {
    width: 300px;
    display: inline-block;
}
.table {
    width: 100%;
    font-size: 14px;
}
.red {
    color: #ff0000;
}
.mr10 {
    margin-right: 10px;
}
.table-td-thumb {
    display: block;
    margin: auto;
    width: 40px;
    height: 40px;
}
</style>
