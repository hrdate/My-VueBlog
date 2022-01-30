<template>
  <div>
    <!-- banner -->
    <div class="archive-banner banner">
      <h1 class="banner-title">归档</h1>
    </div>
    <!-- 归档列表 -->
    <v-card class="blog-container">
      <timeline>
        <timeline-title> 目前共计{{ count }}篇文章，继续加油 </timeline-title>
        <timeline-item v-for="item of archiveList" :key="item.id">
          <!-- 日期 -->
          <span class="time">{{ item.created | date }}</span>
          <!-- 文章标题 -->
          <router-link :to="'/article/' + item.id" style="color:#666;text-decoration: none">
             {{ item.title }} </router-link>
        </timeline-item>
      </timeline>
      <!-- 分页按钮 -->
      <v-pagination
        color="#00C4B6"
        v-model="current"
        :length="totalPage"
        total-visible="5"
      />
    </v-card>
  </div>
</template>

<script>
import { Timeline, TimelineItem, TimelineTitle } from "vue-cute-timeline";
export default {
  created() {
    this.listArchives();
  },
  components: {
    Timeline,
    TimelineItem,
    TimelineTitle
  },
  data: function() {
    return {
      current: 1,
      count: 0,
      totalPage: 1,
      archiveList: []
    };
  },
  methods: {
    listArchives() {
      this.axios
        .get("/article/archives", {
          params: { currentPage: this.current }
        })
        .then(({ data }) => {
          this.archiveList = data.data.records;
          this.current = data.data.current;
          this.count = data.data.total;
          this.totalPage = data.data.pages;
        });
    }
  },
  watch: {
    current(value) {
      this.current = value;
      this.listArchives();
    }
  }
};
</script>

<style scoped>
.archive-banner {
  background: url(../../assets/img/feng2.png)
    center center / cover no-repeat;
  /* background-color: #49b1f5; */
}
.time {
  font-size: 0.75rem;
  color: #555;
  margin-right: 1rem;
}
</style>
