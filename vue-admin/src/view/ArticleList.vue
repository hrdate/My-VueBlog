<template>
<el-card class="main-card">
<div>
    
    <div class="crumbs">
        <el-breadcrumb separator="/">
            <el-breadcrumb-item>
                <i class="el-icon-lx-cascades"/> 文章管理
            </el-breadcrumb-item>
        </el-breadcrumb>
        <div class="review-menu">
        <span>状态</span>
        <span
            @click="changeDelete(null)"
            :class="isDelete == null ? 'active-review' : 'review'"
        >
            全部
        </span>
        <span
            @click="changeDelete(1)"
            :class="isDelete == 1 ? 'active-review' : 'review'"
        >
            回收站
        </span>
        <span
            @click="changeDelete(0)"
            :class="isDelete == 0 ? 'active-review' : 'review'"
        >
            已审核
        </span>
        </div>
    </div>
    <div>
        <div class="handle-box">
            <el-button icon="el-icon-edit-outline" type="primary" class="handle-del mr10"><router-link :to="{name: 'ArticleAdd',params: {articleId: 0}}">新增文章</router-link></el-button>
            <!-- <el-select v-model="selectArticleType" placeholder="类型" class="handle-select mr10">
                <el-option key="1" label="原创" value="原创"/>
                <el-option key="2" label="转载" value="转载"/>
                <el-option key="3" label="翻译" value="翻译"/>
            </el-select> -->
            <el-select v-model="selectArticleTagId" placeholder="标签" class="handle-select mr10">
                <el-option
                    v-for="item in tagList"
                    :key="item.tagName"
                    :label="item.tagName"
                    :value="item.id">
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
            <el-table-column prop="articleLike" width="80px" label="点赞量"/>
            <!-- 文章置顶 -->
            <el-table-column prop="isTop" label="置顶" width="80px" align="center">
                <template slot-scope="scope">
                <el-switch
                    v-model="scope.row.isTop"
                    active-color="#13ce66"
                    inactive-color="#F4F4F5"
                    :active-value="1"
                    :inactive-value="0"
                    @change="changeTop(scope.row)"
                />
                <!-- :disabled="scope.row.isDelete == 1" -->
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
            <el-table-column prop="created" width="190px" align="center" label="创建时间">
                <template slot-scope="scope" >
                    {{ scope.row.created | dateTime}}
                </template>
            </el-table-column>
            <el-table-column label="操作" width="180px" align="center">
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
                        :disabled="scope.row.isDelete"
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
            // selectArticleType: null,
            selectArticleTagId: null,
            keywords: null,
            name: '',
            isDelete: null,
            status: null,
            currentPage: 1,
            total: 0,
            size: 5,
        };
    },
    methods: {
        listArticles() {
            const _this = this;
            this.axios.get("/article/admin/articles",{
                params : {
                    currentPage: this.currentPage,
                    pageSize: this.size,
                    tagId: this.selectArticleTagId,
                    // type: this.selectArticleType,
                    isDelete: this.isDelete
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
            this.tagList = this.$store.state.tagList;
        },
        
        // 删除操作
        handleDelete(articleId,title) {
            const _this = this;
            // 二次确认删除
            this.$confirm('确定要删除文章 \" '+title+' \" 吗？', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                _this.$axios.delete(`/article/del/`+articleId, {
                    headers: {
                        "token": sessionStorage.getItem("token"),
                        "Authorization": sessionStorage.getItem("token")
                    }
                }).then(res => {
                    if(res.data.code == 200){
                        _this.$message.success('文章删除操作成功');
                        _this.listArticles();
                    }else{
                        _this.$message.error(res.data.data);
                    }
                }).catch(() => {
                    _this.$message.error('文章删除操作失败!');
                });
            }).catch(() => {
                _this.$message.error('文章删除操作失败.');
            });
        },
        changeTop(article) {
            this.axios.put("/article/top/" + article.id).then(res => {
                if (res.data.code == 200) {
                    this.$notify.success({
                        title: "成功",
                        message: res.data.data
                    });
                    this.listArticles();
                } else {
                    this.$notify.error({
                        title: "失败",
                        message: res.data.msg
                    });
                }
            });
        },
        changeDelete(isDelete){
            this.isDelete = isDelete;
        }
    },
    watch:{
        currentPage(now){
            this.currentPage = now
            this.listArticles()
        },
        // selectArticleType(){
        //     this.currentPage = 1
        //     this.listArticles()
        // },
        selectArticleTagId() {
            this.currentPage = 1
            this.listArticles()
        },
        isDelete(){
            this.currentPage = 1
            this.listArticles()
        }
    },
    computed: {
        isActive() {
            return function(status) {
                return this.activeStatus == status ? "active-status" : "status";
            };
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

.article-status-menu {
  font-size: 14px;
  margin-top: 40px;
  color: #999;
}
.article-status-menu span {
  margin-right: 24px;
}
.status {
  cursor: pointer;
}
.active-status {
  cursor: pointer;
  color: #333;
  font-weight: bold;
}
.article-cover {
  position: relative;
  width: 100%;
  height: 90px;
  border-radius: 4px;
}
.article-cover::after {
  content: "";
  background: rgba(0, 0, 0, 0.3);
  position: absolute;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
}
.article-status-icon {
  color: #fff;
  font-size: 1.5rem;
  position: absolute;
  right: 1rem;
  bottom: 1.4rem;
}

.review-menu {
  font-size: 14px;
  margin-top: 15px;
  color: #999;
}
.review-menu span {
  margin-right: 24px;
}
.review {
  cursor: pointer;
}
.active-review {
  cursor: pointer;
  color: #333;
  font-weight: bold;
}
</style>
