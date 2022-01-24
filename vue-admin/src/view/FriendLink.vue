<template>
<el-card class="main-card">
    <div class="operation-container">
        <div class="crumbs">
        <el-breadcrumb separator="/">
            <el-breadcrumb-item>
                <i class="el-icon-lx-cascades"/> 友链管理
            </el-breadcrumb-item>
        </el-breadcrumb>
        </div>
        <el-button type="primary" size="small" icon="el-icon-plus" 
            @click="openModel(null)"> 新增
        </el-button>
        <el-button type="danger" size="small" icon="el-icon-delete" :disabled="linkIdList.length == 0" 
            @click="deleteFlag = true">批量删除
        </el-button>
      <!-- 条件筛选 -->
      <div style="margin-left:auto"  class="handle-input mr10" align="right">
        <el-input
          v-model="keywords"
          prefix-icon="el-icon-search"
          size="small"
          placeholder="请输入链接名昵称"
          style="width:200px"
          @keyup.enter.native="searchFriendLink"
        />
        <el-button
          type="primary"
          size="small"
          icon="el-icon-search"
          style="margin-left:1rem"
          @click="searchFriendLink"
        >
          搜索
        </el-button>
      </div>
        <br />
        <br />
        <!-- 表格展示 -->
        <el-table
            border fit highlight-current-row
            style="width: 100%"
            :data="linkList"
            @selection-change="selectionChange"
        >
        <!-- 表格列 -->
        <el-table-column type="selection" width="55" />
        <el-table-column
            prop="avatar"
            label="链接头像"
            align="center"
            width="180"
        >
        <template slot-scope="scope">
            <img :src="scope.row.avatar" width="40" height="40" />
        </template>
        </el-table-column>
        <el-table-column prop="linkName" label="链接名" align="center" />
        <el-table-column prop="linkUrl" label="链接地址" align="center" />
        <el-table-column prop="linkInfo" label="链接介绍" align="center" />
        <el-table-column
            prop="created"
            label="创建时间"
            width="140"
            align="center"
        >
            <template slot-scope="scope">
            <i class="el-icon-time" style="margin-right:5px" />
            {{ scope.row.created | date }}
            </template>
        </el-table-column>
        <!-- 列操作 -->
        <el-table-column label="操作" align="center" width="160">
        <template slot-scope="scope">
            <el-button type="primary" size="mini" @click="openModel(scope.row)">
            编辑
            </el-button>
            <el-popconfirm
                title="确定删除吗？"
                style="margin-left:1rem"
                @confirm="deleteLink(scope.row.id)"
            >
                <el-button size="mini" type="danger" slot="reference">删除</el-button>
            </el-popconfirm>
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
            :page-size="pageSize"
            :total="total"
            :page-sizes="[5,10]"
            layout="total, sizes, prev, pager, next, jumper"
        />
    </div>
    <!-- 批量删除对话框 -->
    <el-dialog :visible.sync="deleteFlag" width="30%">
      <div class="dialog-title-container" slot="title">
        <i class="el-icon-warning" style="color:#ff9900" />提示
      </div>
      <div style="font-size:1rem">是否删除选中项？</div>
      <div slot="footer">
        <el-button @click="deleteFlag = false">取 消</el-button>
        <el-button type="primary" @click="deleteLink(null)">
          确 定
        </el-button>
      </div>
    </el-dialog>
    <!-- 添加对话框 -->
    <el-dialog :visible.sync="addOrEdit" width="30%">
      <div class="dialog-title-container" slot="title" ref="linkTitle" />
      <el-form label-width="80px" size="medium" :model="linkForm">
        <el-form-item label="链接名">
          <el-input style="width:250px" v-model="linkForm.linkName" />
        </el-form-item>
        <el-form-item label="链接头像">
          <el-input style="width:250px" v-model="linkForm.avatar" />
        </el-form-item>
        <el-form-item label="链接地址">
          <el-input style="width:250px" v-model="linkForm.linkUrl" />
        </el-form-item>
        <el-form-item label="链接介绍">
          <el-input style="width:250px" v-model="linkForm.linkInfo" />
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button @click="addOrEdit = false">取 消</el-button>
        <el-button type="primary" @click="addOrEditCategory">
          确 定
        </el-button>
      </div>
    </el-dialog>
</el-card>
</template>

<script>

export default {
    name: "FriendLink",
    data() {
        return {
            loading: true,
            deleteFlag: false,
            addOrEdit: false,
            keywords: null,
            linkForm: {
                linkId: null,
                linkName: "",
                avatar: "",
                linkInfo: "",
                linkUrl: "",
                created: "",
            },
            linkList: {},
            linkIdList:[],
            currentPage: 1,
            total: 0,
            pageSize: 5,
        };
    },
    created() {
        this.getFriendLinkList();
    },
    methods: {
        getFriendLinkList() {
            const _this = this;
            this.axios.get(`/friend/admin`,{
                params : {
                    currentPage: this.currentPage,
                    pageSize: this.pageSize
                }
            }).then(res=>{
                console.log(res)
                _this.linkList = res.data.data.records;
                _this.currentPage = res.data.data.current;
                _this.total = res.data.data.total;
                _this.pageSize = res.data.data.size;
            });
        },
        //弹窗
        openModel(link) {
            if (link != null) {
                this.linkForm = JSON.parse(JSON.stringify(link));
                this.$refs.linkTitle.innerHTML = "修改友链";
            } else {
                this.linkForm.linkId = null;
                this.linkForm.linkName = "";
                this.linkForm.avatar = "";
                this.linkForm.linkInfo = "";
                this.linkForm.linkUrl = "";
                this.$refs.linkTitle.innerHTML = "添加友链";
            }
            this.addOrEdit = true;
        },
        searchFriendLink() {
            this.current = 1;
            this.getFriendLinkList();
        },
        addOrEditCategory() {
            if (this.linkForm.linkName.trim() == "") {
                this.$message.error("友链名不能为空");
                return false;
            }
            if (this.linkForm.avatar.trim() == "") {
                this.$message.error("友链头像不能为空");
                return false;
            }
            if (this.linkForm.linkInfo.trim() == "") {
                this.$message.error("友链介绍不能为空");
                return false;
            }
            if (this.linkForm.linkUrl.trim() == "") {
                this.$message.error("友链地址不能为空");
                return false;
            }
            this.axios.post("/friend/edit",this.linkForm,{
                headers: {
                    "Authorization": sessionStorage.getItem("token"),
                    "token": sessionStorage.getItem("token")
                }}).then(res => {
                if (res.data.code == 200) {
                    this.$notify.success({
                        title: "成功",
                        message: res.data.msg
                    });
                    this.getFriendLinkList();
                } else {
                    this.$notify.error({
                        title: "失败",
                        message: res.data.msg
                    });
                }
                this.addOrEdit = false;
            });
        },
        currentChange(current) {
            this.currentPage = current;
            this.getFriendLinkList();
        },
        sizeChange(size) {
            this.pageSize = size;
            this.getFriendLinkList();
        },
        deleteLink(id) {
            var param = {};
            if (id == null) {
                param = { data: this.linkIdList };
            } else {
                param = { data: [id] };
            }
            this.axios.delete("/friend/del", param).then(({ data }) => {
                if (data.code == 200) {
                    this.$notify.success({
                        title: "成功",
                        message: data.msg
                    });
                    this.listLinks();
                } else {
                    this.$notify.error({
                        title: "失败",
                        message: data.msg
                    });
                }
                this.deleteFlag = false;
            });
        },
        //新增数据表格
        handleCreate() {
            this.linkList.push({ linkId: null, linkName: "",linkUrl:"",avatar:"", edit: true });
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
                this.page(1);
            }).catch(err =>{
                this.$message.error('修改失败');
            });
        },
        //取消修改
        cancelEdit(row) {
            this.$message.warning("修改已取消");
            this.page(1);
        },
        //提交修改
        confirmEdit(row) {
            const _this = this;
            this.axios.post(`/FriendLink/edit`,row, {
                headers: {
                    "Authorization": localStorage.getItem("token"),
                    'Content-Type': 'application/json; charset=UTF-8'
                }
            }).then(res=>{
                _this.$message.success("修改成功");
                row.edit = false;
                _this.page(1);
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
.handle-input {
    width: 300px;
    display: inline-block;
}
</style>
