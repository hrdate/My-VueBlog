<template>
    <div class="app-container">
        <el-button
                class="filter-item"
                style="margin-left: 10px;"
                type="primary"
                icon="el-icon-edit"
                @click="handleCreate"
        >Add</el-button>
        <br />
        <br />
        <el-table
                v-loading="listLoading"
                :data="types"
                border
                fit
                highlight-current-row
                style="width: 100%"
        >
            <el-table-column prop="id" align="center" label="ID" width="120">
                <template slot-scope="scope">
                    <span v-model="scope.row.id">{{ scope.row.id }}</span>
                </template>
            </el-table-column>

            <el-table-column prop="name" min-width="300px" label="名称" align="center">
                <template slot-scope="{row}">
                    <template v-if="row.edit">
                        <el-input v-model="row.name" class="edit-input" size="small" />
                        <el-button
                                class="cancel-btn"
                                size="small"
                                icon="el-icon-refresh"
                                type="warning"
                                @click="cancelEdit(row)"
                        >cancel</el-button>
                    </template>
                    <span v-else v-model="row.name">{{ row.name }}</span>
                </template>
            </el-table-column>

            <el-table-column prop="created" label="创建时间" min-width="200px" align="center">
                <template slot-scope="scope">
                    <span v-model="scope.row.createTime">{{scope.row.created | date}}</span>
                </template>
            </el-table-column>

            <!-- <el-table-column prop="updateTime" label="最后修改时间" min-width="200px" align="center">
                <template slot-scope="scope">
                    <span v-model="scope.row.updateTime">{{ scope.row.updateTime }}</span>
                </template>
            </el-table-column> -->

            <el-table-column align="center" label="操作" width="240">
                <template slot-scope="{row}">
                    <el-button
                            v-if="row.edit"
                            type="success"
                            size="small"
                            icon="el-icon-circle-check-outline"
                            @click="confirmEdit(row)"
                    >完成</el-button>
                    <el-button
                            v-else
                            type="primary"
                            size="small"
                            icon="el-icon-edit"
                            @click="row.edit=!row.edit"
                    >Edit</el-button>
                    <el-button size="small" type="danger" @click="deleted(row)">Delete</el-button>
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
</template>

<script>
import {TypeEidt} from "../api";
import {TypeList} from "../api";
export default {
    name: "Type",
    data() {
        return {
            types: {},
            currentPage: 1,
            total: 0,
            pageSize: 10,
        };
    },
    mounted() {
        this.page(1);
    },
    methods: {
        page(currentPage) {
            const _this = this;
            TypeList(currentPage).then(res =>{
                _this.types = res.data.data.records;
                _this.currentPage = res.data.data.current;
                _this.total = res.data.data.total;
                _this.pageSize = res.data.data.size;
            });
        },
        //新增数据表格
        handleCreate() {
            this.types.push({ id: null, name: "", edit: true });
        },
        /** *
         * 删除数据
         *
         *  */
        deleted(row) {
            const id = row.id;
            this.$confirm('是否确认删除用户编号为"' + id + '"的数据项?', "警告", {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                type: "warning"
            }).then(function() {
                return deleteTypeById(id);
            }).then(() => {
                this.$message({
                    message: "删除已成功",
                    type: "warning"
                });
                this.page(1);
            }).catch(function() {});
        },
        //取消修改
        cancelEdit(row) {
            this.$message({
                message: "修改已取消",
                type: "warning"
            });
            this.page(1);
        },
        //提交修改
        confirmEdit(row) {
            const _this = this;
            TypeEidt(row,{
                headers: {
                    "Authorization": localStorage.getItem("token"),
                    'Content-Type':'application/json; charset=UTF-8'
                }
            }).then((res)=>{
                _this.$message({
                    message: "修改成功",
                    type: "success"
                });
                row.edit = false;
                _this.page(1);
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
</style>
