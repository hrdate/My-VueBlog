<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-lx-calendar"></i> 文章</el-breadcrumb-item>
                <el-breadcrumb-item>markdown编辑器</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="m-content">
            <el-form ref="editForm" status-icon :model="editForm" :rules="rules" label-width="80px">
                <el-form-item label="标题" prop="title">
                    <el-input v-model="editForm.title"/>
                </el-form-item>
                <el-form-item label="标签" prop="tagId">
                    <template>
                        <el-select v-model="tagList[editForm.tagId-1].tagName" placeholder="请选择">
                            <el-option
                                v-for="item in tagList"
                                :key="item.tagName"
                                :label="item.tagName"
                                :value="item.tagName">
                            </el-option>
                        </el-select>
                    </template>
                </el-form-item>
                <el-form-item label="分类" prop="type">
                    <el-select v-model="editForm.type" placeholder="类型" class="handle-select mr10">
                        <el-option key="原创" label="原创" value="原创"/>
                        <el-option key="转载" label="转载" value="转载"/>
                        <el-option key="转载" label="翻译" value="翻译"/>
                    </el-select>
                </el-form-item>

                <el-form-item label="内容" prop="content">
                    <mavon-editor v-model="editForm.content"/>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="submitForm()">立即创建</el-button>
                    <el-button ><router-link :to="{name: 'ArticleList'}">取消</router-link> </el-button>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>

<script>

export default {
    name: 'ArticleEdit',
    created() {
        this.loadArticle();
    },
    data() {
        return {
            tagList:[],
            editForm: {
                id: null,
                userId: null,
                title: null,
                description: null,
                content: null,
                tagId: null,
                type: null,
            },
            rules: {
                title: [
                    {required: true, message: '请输入标题', trigger: 'blur'},
                ],
                description: [
                    {required: true, message: '请输入摘要', trigger: 'blur'}
                ]
            },
        }
    },
    
    methods: {
    
    loadArticle(){
        const _this = this;
        const path = this.$route.path;
        if(path == "/article/add") {
                _this.editForm.tagId = 1;
                _this.editForm.type = "原创";
        }else{
            const articleId = this.$route.params.articleId;
            this.axios.get('/article/' + articleId).then((res) => {
                const article = res.data.data;
                _this.editForm.id = article.id;
                _this.editForm.userId = article.userId;
                _this.editForm.title = article.title;
                _this.editForm.tagId = article.tagId;
                _this.editForm.type = article.type;
                _this.editForm.description = article.description;
                _this.editForm.content = article.content;
            });
        }
        // this.axios.get(`/tag/tags`).then(res=>{
        //     _this.tagList = res.data.data;
        // });
        this.tagList = this.$store.state.tagList;
    },
    
    submitForm() {
        const _this = this;
        if(_this.editForm.userId==null){
            _this.editForm.userId=_this.$store.state.userId;
        }
        this.$refs.editForm.validate((valid) => {
            if (valid) {
                _this.axios.post(`/article/edit`,this.editForm,{
                    headers: {
                        "Authorization": sessionStorage.getItem("token")
                    }}).then((res) => {
                    // _this.$alert('操作成功', '提示', {
                    //     confirmButtonText: '确定',
                    //     callback: action => {
                    //         _this.$router.push("/article")
                    //     }
                    // });
                    this.$message.success('提交成功');
                    _this.$router.push("/article")
                }).catch(err => {
                    this.$message.error(err.response.data.data);
                });
            } else {
                this.$message.error('错误提交');
                return false;
            }
        })
    }
    },
}
</script>

<style scoped>
    .editor-btn{
        margin-top: 20px;
    }
</style>

