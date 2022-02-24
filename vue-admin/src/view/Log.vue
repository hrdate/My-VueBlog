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
        :data="LogList"
        border
        fit
        highlight-current-row
        style="width: 100%"
        @selection-change="handleSelectionChange"
    >
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column prop="id" align="center" label="ID" width="50px">
            <template slot-scope="scope">
                <span v-model="scope.row.id">{{ scope.row.id }}</span>
            </template>
        </el-table-column>
        <el-table-column prop="ipAddress" min-width="80px" label="IP地址" align="center"/>
        <el-table-column prop="optModule" min-width="80px" label="操作模块" align="center"/>
        <el-table-column prop="optModule" min-width="80px" label="操作类型" align="center"/>
        <el-table-column prop="optUrl" min-width="120px" label="请求地址" align="center"/>
        <el-table-column prop="optMethod" min-width="120px" label="请求方法" align="center"/>
        <el-table-column prop="requestParam" min-width="150px" label="请求参数" align="center"/>
        <el-table-column prop="responseData" min-width="120px" label="请求结果" align="center"/>
        <el-table-column prop="createTime" min-width="100px" label="执行时间" align="center">
            {{createTime | dateTime}}
        </el-table-column>
        </el-table-column>
    </el-table>
    <div class="pagination">
        <!-- 分页 -->
        <el-pagination align="right"
            background
            class="pagination-container"
            @current-change="currentChange"
            :current-page="currentPage"
            @size-change="sizeChange"
            :page-size="pageSize"
            :total="total"
            :page-sizes="[5,10]"
            layout="total, sizes, prev, pager, next, jumper"
        />
    </div>
</div>
</template>

<script>

export default {
    name: "Log",
    created(){
        this.getLogList();
    },
    data() {
        return {
            LogList: {},
            currentPage: 1,
            pageSize: 5,
            total: 0,
        };
    },
    mounted() {
    },
    methods: {
        getLogList() {
            const _this = this;
            this.axios.get("/admin/operation/logs",{
                params:{
                    currentPage: this.currentPage,
                    pageSize: this.pageSize,
                }
            }).then(res=>{
                _this.LogList = res.data.data.records
                _this.total = res.data.data.total
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
        currentChange(current) {
            this.currentPage = current;
            this.getFriendLinkList();
        },
        sizeChange(size) {
            this.pageSize = size;
            this.getFriendLinkList();
        },
    },
    
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
