<template>
    <div id="app">
        <router-view></router-view>
    </div>
</template>

<script>

export default{
    created(){

        this.loadTags();
    },
    methods: {
        loadTags(){
            const _this = this;  
            this.axios.get(`/tag/tags`).then( res  => {
                _this.$store.state.tagList = res.data.data
            });
        }
    }
}
</script>

<style scoped>
    @import "./assets/css/main.css";
    @import "./assets/css/color-dark.css";     /*深色主题*/
    /*@import "./assets/css/theme-green/color-green.css";   浅绿色主题*/

</style>

