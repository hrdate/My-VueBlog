<template>
<el-card class="main-card">
    <div>
        <div class="crumbs">
        <el-breadcrumb separator="/">
            <el-breadcrumb-item>
                <i class="el-icon-lx-cascades"/> 评论管理
            </el-breadcrumb-item>
        </el-breadcrumb>
        </div>
        <el-table
            :data="commentList"
            border
            style="width: 100%">
            <el-table-column prop="id" label="id" width="200"/>
            <el-table-column prop="userName" label="姓名" width="200"/>
            <el-table-column prop="email" label="邮箱" width="200"/>
            <el-table-column prop="content" label="内容" width="300" ></el-table-column>
            <el-table-column prop="isDelete" label="删除" width="200">
                <template slot-scope="scope">
                    <el-tag  type="success" v-if="scope.row.isDelete===0 || scope.row.isDelete=== false ">否</el-tag>
                    <el-tag type="info" v-if="scope.row.isDelete===1 ||scope.row.isDelete=== true ">是</el-tag>
                </template>
            </el-table-column>
            <el-table-column prop="ipAddress" label="IP地址" width="200"/>
            <el-table-column prop="created" label="创建时间" width="200"/>
            <el-table-column prop="updated" label="更新时间" width="200"/>
            <el-table-column label="操作" width="100" >
                <template slot-scope="scope">
                    <el-button v-show="!scope.row.edit" type="primary" size="mini" icon="edit">编辑</el-button>
                    <el-button v-show="scope.row.edit"  type="success" size="mini"  icon="check">完成</el-button>
                </template>
            </el-table-column>
        </el-table>
        <div class="pagination">
            <el-pagination
                background
                layout="total, prev, pager, next"
                :current-page="currentPage"
                :page-size="pageSize"
                :total="total"
                @current-change="page"
            />
        </div>
    </div>
</el-card>
</template>

<script>
import {CommentList} from "../api";

export default {
    data() {
        return {
            commentList:{},
            currentPage: 1,
            total: 0,
            pageSize: 5,
        }
    },
    methods:{
        getCommentList(){
            const _this = this;
            this.axios.get("/comments/admin",{
                params:{
                    currentPage: this.currentPage,
                    pageSize: this.pageSize
                },headers: {
                    "Authorization": this.$store.state.token
                }
            }).then(res =>{
                _this.comments = res.data.data.records;
                _this.currentPage = res.data.data.current;
                _this.total = res.data.data.total;
                _this.pageSize = res.data.data.size;
            });
        },
    },
    mounted() {
        this.page(1);
    }
}
</script>
<style slot-scope>

</style>
