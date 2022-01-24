<template>
    <div class="article">
        <h2>{{ article.title }}</h2>
        <!--<el-link icon="el-icon-edit" v-if="ownArticle"><router-link :to="{name: 'ArticleEdit', params: {articleId: article.id}}">编辑</router-link></el-link>-->
        <el-divider></el-divider>
        <div class="content markdown-body" v-html="article.content"></div>
    </div>
</template>

<script>
    import 'github-markdown-css/github-markdown.css' // 然后添加样式markdown-body
    import {ArticleById} from '../api/index';
    export default {
        name: "ArticleDetail",
        data() {
            return {
                article: {
                    //userId: null,
                    title: "",
                    description: "",
                    content: ""
                },
                ownArticle: false
            }
        },
        methods: {
            getBlog() {
                const articleId = this.$route.params.articleId;
                const _this = this;
                ArticleById(articleId).then((res) => {
                    _this.article = res.data.data;
                    var MarkdownIt = require('markdown-it'),
                        md = new MarkdownIt();
                    var result = md.render(_this.article.content);
                    _this.article.content = result;
                    // 判断是否是自己的文章，能否编辑
                    //_this.ownBlog =  (_this.blog.userId === _this.$store.getters.getUser.id)
                });
            }
        },
        created() {
            this.getBlog()
        },
    }
</script>

<style scoped>

</style>
