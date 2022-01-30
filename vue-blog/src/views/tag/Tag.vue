<template>
  <div>
    <!-- banner -->
    <div class="tag-banner banner">
      <h1 class="banner-title">标签</h1>
    </div>
    <!-- 标签列表 -->
    <v-card class="blog-container">
      <div class="tag-cloud-title">标签 - {{ this.count }}</div>
      <div class="tag-cloud">
        <router-link
          :style="{ 'font-size': Math.floor(Math.random() * 10) + 18 + 'px' }"
          v-for="item of this.tagList"
          :key="item.id"
          :to="'/tag/' + item.id"
        >
          {{ item.tagName }}
        </router-link>
      </div>
    </v-card>
  </div>
</template>

<script>
export default {
  created() {
    this.getTagList();
  },
  data: function() {
    return {
      tagList: null,
      count: 0
    };
  },
  methods: {
    getTagList(){
      this.tagList = this.$store.state.tagList;
      this.count = this.tagList.length
    }
  },
  computed(){
    
  },
};
</script>

<style scoped>
.tag-banner {
  background: url(../../assets/img/feng1.png) center center / cover no-repeat;
  background-color: #49b1f5;
}
.tag-cloud-title {
  line-height: 2;
  font-size: 36px;
  text-align: center;
}
@media (max-width: 759px) {
  .tag-cloud-title {
    font-size: 25px;
  }
}
.tag-cloud {
  text-align: center;
}
.tag-cloud a {
  color: #616161;
  display: inline-block;
  text-decoration: none;
  padding: 0 8px;
  line-height: 2;
  transition: all 0.3s;
}
.tag-cloud a:hover {
  color: #03a9f4 !important;
  transform: scale(1.1);
}
</style>
