<template>
    <div id="app">
        <router-view></router-view>
    </div>
</template>

<script>

export default{
    created(){
        // 上传访客信息
        this.axios.post("/report");
    },
    methods: {
        
    }
}
</script>

<style scoped>
    @import "./assets/css/main.css";
    @import "./assets/css/color-dark.css";     /*深色主题*/
    /*@import "./assets/css/theme-green/color-green.css";   浅绿色主题*/

</style>

