<template>
<el-card class="main-card">
  <div class="app-container">
    <div class="crumbs">
        <el-breadcrumb separator="/">
            <el-breadcrumb-item>
                <i class="el-icon-lx-cascades"/> 标签管理
            </el-breadcrumb-item>
        </el-breadcrumb>
    </div>
    <el-button icon="el-icon-edit-outline" type="primary" class="handle-del mr10" @click="openModel(null)">新增标签</el-button>
    <el-button icon="el-icon-delete" type="danger" class="handle-del mr10" @click="isDelete = true" disabled>批量删除</el-button>
    <br /> <br />
    <!-- 表格展示 -->
    <el-table
      border
      :data="tagList"
      @selection-change="selectionChange"
    >
      <!-- 表格列 -->
      <el-table-column type="selection" width="55" />
      <!-- 标签名 -->
      <el-table-column prop="tagName" label="标签名" align="center">
        <template slot-scope="scope">
          <el-tag>
            {{ scope.row.tagName }}
          </el-tag>
        </template>
      </el-table-column>
      <!-- 文章量 -->
      <el-table-column prop="articleCount" label="文章量" align="center" />
      <!-- 标签创建时间 -->
      <el-table-column prop="created" label="创建时间" align="center">
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
            @confirm="deleteTag(scope.row.id)"
          >
            <el-button size="mini" type="danger" slot="reference">
              删除
            </el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
  </div>
  <!-- 编辑对话框 -->
    <el-dialog :visible.sync="addOrEdit" width="30%">
      <div class="dialog-title-container" slot="title" ref="tagTitle" />
      <el-form label-width="80px" size="medium" :model="tagForm">
        <el-form-item label="标签名">
          <el-input style="width:220px" v-model="tagForm.tagName" />
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button @click="addOrEdit = false">取 消</el-button>
        <el-button type="primary" @click="addOrEditTag">
          确 定
        </el-button>
      </div>
    </el-dialog>
  <!-- 批量删除对话框 -->
    <el-dialog :visible.sync="isDelete" width="30%">
      <div class="dialog-title-container" slot="title">
        <i class="el-icon-warning" style="color:#ff9900" />提示
      </div>
      <div style="font-size:1rem">是否删除选中项？</div>
      <div slot="footer">
        <el-button @click="isDelete = false">取 消</el-button>
        <el-button type="primary" @click="deleteTag(null)">
          确 定
        </el-button>
      </div>
    </el-dialog>
</el-card>
</template>

<script>

export default {
  name: "Tag",
  data() {
    return {
        isDelete: false,
        loading: true,
        addOrEdit: false,
        tagList: {},
        tagIdList: {},
        tagForm: {
          id: null,
          tagName: "null",
          created: null
        },
        total: 0,
    };
  },
  mounted() {
      this.getTagList();
  },
  methods: {
      getTagList() {
          this.axios.get(`/tag/tags`).then(res =>{
              this.tagList = res.data.data;
              this.$store.state.tagList = this.tagList;
          });
      },
      selectionChange(tagList) {
        this.tagIdList = [];
        tagList.forEach(item => {
          this.tagIdList.push(item.id);
        });
      },
      openModel(tag) {
          if (tag != null) {
              this.tagForm = JSON.parse(JSON.stringify(tag));
              this.$refs.tagTitle.innerHTML = "修改标签";
          } else {
              this.tagForm.id = null;
              this.tagForm.tagName = "";
              this.$refs.tagTitle.innerHTML = "添加标签";
          }
          this.addOrEdit = true;
      },
      
      /** *
       * 删除数据
       *
       *  */
      deleteTag(tagId) {
        var param = {};
        if (tagId == null) {
          param = { data: this.tagIdList };
        } else {
          param = { tagId: [tagId] };
        }
        this.axios.delete("/tag/del",tagId,{
          headers: {
            "Authorization": sessionStorage.getItem("token"),
            "token": sessionStorage.getItem("token")
          }
        }).then(({ data }) => {
          console.log(dada)
          if (data.code == 200) {
            this.$notify.success({
              title: "成功",
              message: data.msg
            });
            this.getTagList();
          } else {
            this.$notify.error({
              title: "失败",
              message: data.msg
            });
          }
        });
        this.isDelete = false;
      },
      //提交修改
      addOrEditTag() {
      if (this.tagForm.tagName.trim() == "") {
        this.$message.error("标签名不能为空");
        return false;
      }
      this.axios.post("/tag/edit", this.tagForm,{
        headers: {
            "Authorization": sessionStorage.getItem("token"),
            "token": sessionStorage.getItem("token")
        }
      }).then(({ data }) => {
        if (data.code == 200) {
          this.$notify.success({
            title: "成功",
            message: data.msg
          });
          this.getTagList();
        } else {
          this.$notify.error({
            title: "失败",
            message: data.msg
          });
        }
      });
      this.addOrEdit = false;
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
