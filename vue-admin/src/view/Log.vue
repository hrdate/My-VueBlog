<template>
    <div class="app-container">

        <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
            <el-form-item label="请求者IP" prop="ipAddress">
                <!--v-model="queryParams.ipAddress"-->
                <el-input
                    placeholder="请输入IP地址"
                    clearable
                    style="width: 240px;"
                    size="small"
                    @keyup.enter.native="handleQuery"
                />
            </el-form-item>
            <el-form-item label="操作时间">
                <el-date-picker
                        v-model="dateRange"
                        size="small"
                        style="width: 240px"
                        value-format="yyyy-MM-dd"
                        type="daterange"
                        range-separator="-"
                        start-placeholder="开始日期"
                        end-placeholder="结束日期"
                ></el-date-picker>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
                <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
            </el-form-item>
        </el-form>

        <el-row gutter="10" class="mb8">
            <el-col :span="1.5">
                <el-button
                        type="danger"
                        icon="el-icon-delete"dd
                        size="mini"
                        :disabled="multiple"
                        @click="deleted"
                        v-hasPermi="['monitor:logininfor:remove']"
                >删除</el-button>
            </el-col>
        </el-row>
        <el-table
                v-loading="listLoading"
                :data="logs"
                border
                fit
                highlight-current-row
                style="width: 100%"
                @selection-change="handleSelectionChange"
        >
            <el-table-column type="selection" width="55" align="center" />
            <el-table-column prop="id" align="center" label="ID" width="120">
                <template slot-scope="scope">
                    <span v-model="scope.row.id">{{ scope.row.id }}</span>
                </template>
            </el-table-column>
            <el-table-column prop="ip" min-width="300px" label="IP地址" align="center"/>
            <el-table-column prop="method" min-width="300px" label="请求方法" align="center"/>
            <el-table-column prop="params" min-width="300px" label="请求参数" align="center"/>
            <el-table-column prop="time" min-width="300px" label="执行时间" align="center"/>
            <el-table-column prop="createDate" label="创建时间" min-width="200px" align="center">
                <template slot-scope="scope">
                    <span v-model="scope.row.createDate">{{scope.row.createDate}}</span>
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
    import {LogList} from "../api";

    export default {
        name: "Log",
        data() {
            return {
                logs: {},
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
                LogList(currentPage).then(res=>{
                    _this.logs = res.data.data.records;
                    _this.currentPage = res.data.data.current;
                    _this.total = res.data.data.total;
                    _this.pageSize = res.data.data.size;
                });
            },
            /** *
             * 删除数据
             *
             *  */
            deleted() {
                const id = row.id;
                this.$confirm('是否确认删除用户编号为"' + id + '"的数据项?', "警告", {
                    confirmButtonText: "确定",
                    cancelButtonText: "取消",
                    type: "warning"
                }).then(function() {
                    return deleteLogById(id);
                }).then(() => {
                    this.$message({
                        message: "删除已成功",
                        type: "warning"
                    });
                    this.page(1);
                }).catch(function() {});
            },

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
