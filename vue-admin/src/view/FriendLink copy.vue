<template>
<el-card class="main-card">
<div class="app-container">
    <div class="crumbs">
    <el-breadcrumb separator="/">
        <el-breadcrumb-item>
            <i class="el-icon-lx-cascades"/> 友链管理
        </el-breadcrumb-item>
    </el-breadcrumb>
    </div>
    <el-button class="filter-item" style="margin-left: 10px;" type="primary"
        icon="el-icon-edit" @click="handleCreate" >添加</el-button>
    <br />
    <br />
    <el-table
        fit
        border
        v-loading="listLoading"
        :data="links"
        highlight-current-row
        style="width: 100%"
    >
        <el-table-column prop="linkId" align="center" label="ID" width="100px">
            <template slot-scope="scope">
                <span v-model="scope.row.linkId">{{ scope.row.linkId }}</span>
            </template>
        </el-table-column>

        <el-table-column prop="linkName" width="150px" label="名称" align="center">
            <template slot-scope="{row}">
                <template v-if="row.edit">
                    <el-input v-model="row.linkName" class="edit-input" size="small" />
                    <el-button
                            class="cancel-btn"
                            size="small"
                            icon="el-icon-refresh"
                            type="warning"
                            @click="cancelEdit(row)"
                    >取消</el-button>
                </template>
                <span v-else v-model="row.linkName">{{ row.linkName }}</span>
            </template>
        </el-table-column>

        <el-table-column prop="linkUrl" width="300px" label="链接地址" align="center">
            <template slot-scope="{row}">
                <template v-if="row.edit">
                    <el-input v-model="row.linkUrl" class="edit-input" size="small" />
                    <el-button
                            class="cancel-btn"
                            size="small"
                            icon="el-icon-refresh"
                            type="warning"
                            @click="cancelEdit(row)"
                    >取消</el-button>
                </template>
                <a v-else v-model="row.linkUrl" :href="row.url" target="_blank">{{ row.linkUrl }}</a>
            </template>
        </el-table-column>

        <el-table-column prop="avatar" min-width="150px" label="图标" align="center" >
            <template slot-scope="{row}">
                <template v-if="row.edit">
                    <el-input v-model="row.avatar" class="edit-input" size="small" />
                    <el-button
                            class="cancel-btn"
                            size="small"
                            icon="el-icon-refresh"
                            type="warning"
                            @click="cancelEdit(row)"
                    >取消</el-button>
                </template>
                <v-avatar  v-else size="65"  target="_blank">
                    <img class="link-wrapper" v-model="row.avatar"  :src="row.avatar" />
                </v-avatar>
            </template>
        </el-table-column>

        <el-table-column prop="created" label="创建时间" min-width="100px" align="center">
            <template slot-scope="scope">
                <span v-model="scope.row.created">{{scope.row.created | date}}</span>
            </template>
        </el-table-column>

        <!-- <el-table-column prop="updateTime" label="最后修改时间" min-width="200px" align="center">
            <template slot-scope="scope">
                <span v-model="scope.row.updateTime">{{ scope.row.updateTime }}</span>
            </template>
        </el-table-column> -->

        <el-table-column align="center" label="操作" width="300">
            <template slot-scope="{row}">
                <el-button v-if="row.edit" type="success" size="small" icon="el-icon-circle-check-outline"
                    @click="confirmEdit(row)" >完成</el-button>
                <el-button v-else type="primary" size="small" icon="el-icon-edit"
                    @click="row.edit=!row.edit">修改</el-button>

                <el-button disabled size="small" type="danger" 
                    @click="deleted(row)">删除</el-button>
                
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
import {FriendLinkList,FriendLinkEidt,FriendLinks} from "../api";

export default {
    name: "FriendLink",
    data() {
        return {
            links: {},
            currentPage: 1,
            total: 0,
            pageSize: 10,
        };
    },
    created() {
        this.getFriendList();
    },
    methods: {
        getFriendList() {
            const _this = this;
            // FriendLinkList(currentPage).then(res=>{
            this.ajax().then(res=>{
                console.log(res)
                _this.links = res.data.data;
                _this.currentPage = res.data.data.current;
                _this.total = res.data.data.total;
                _this.pageSize = res.data.data.size;
            });
        },
        //新增数据表格
        handleCreate() {
            this.links.push({ linkId: null, linkName: "",linkUrl:"",avatar:"", edit: true });
        },
        /** *
         * 删除数据
         *
         **/
        deleted(row) {
            const id = row.id;
            this.$confirm('是否确认删除用户编号为"' + id + '"的数据项?', "警告", {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                type: "warning"
            }).then(function() {
                return deleteTagById(id);
            }).then(() => {
                this.$message.warning("删除已成功");
                this.getFriendList();
            }).catch(err =>{
                this.$message.error('修改失败');
            });
        },
        //取消修改
        cancelEdit(row) {
            this.$message.warning("修改已取消");
            this.getFriendList();
        },
        //提交修改
        confirmEdit(row) {
            const _this = this;
            FriendLinkEidt(row, {
                headers: {
                    "Authorization": localStorage.getItem("token"),
                    'Content-Type': 'application/json; charset=UTF-8'
                }
            }).then(res=>{
                _this.$message.success("修改成功");
                row.edit = false;
                _this.getFriendList();
            }).catch(err =>{
                this.$message.error('修改失败');
            });
        }

    }
};
</script>

<style scoped>
.edit-input {
    padding-right: 100px;
}
.cancel-btn {
    position: absolute;
    right: 15px;
    top: 10px;
}
.link-avatar {
    margin-top: 5px;
    margin-left: 10px;
    transition: all 0.5s;
}
@media (max-width: 759px) {
  .link-avatar {
    margin-left: 30px;
  }
}
.link-wrapper {
  width: 75px;
  height: 75px;
  position: relative;
  transition: all 0.3s;
  border-radius: 8px;
}
.link-wrapper:hover a {
  color: #fff;
}
.link-wrapper:hover .link-intro {
  color: #fff;
}
.link-wrapper:hover .link-avatar {
  transform: rotate(360deg);
}
.link-wrapper a {
  color: #333;
  text-decoration: none;
  display: flex;
  height: 100%;
  width: 100%;
}
.link-wrapper:hover {
  box-shadow: 0 2px 20px #49b1f5;
}
.link-wrapper:hover:before {
  transform: scale(1);
}
.link-wrapper:before {
  position: absolute;
  border-radius: 8px;
  top: 0;
  right: 0;
  bottom: 0;
  left: 0;
  background: #49b1f5 !important;
  content: "";
  transition-timing-function: ease-out;
  transition-duration: 0.3s;
  transition-property: transform;
  transform: scale(0);
}
</style>
