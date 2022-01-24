import Vue from "vue";
import VueRouter from "vue-router";
import Home from "../views/home/Home.vue";
import Article from "../views/article/Article.vue";
import Archive from "../views/archive/Archive.vue";
import Tag from "../views/tag/Tag.vue";
import Link from "../views/link/Link.vue";
import About from "../views/about/About.vue";
import Message from "../views/message/Messsage.vue";
import ArticleList from "../components/ArticleList.vue";
import User from "../views/user/User.vue";
import OauthLogin from "../components/OauthLogin.vue";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    component: Home,
    meta: {
      title: "丶di的个人博客"
    }
  },
  {
    path: "/article/:id",
    component: Article
  },
  {
    path: "/archives",
    component: Archive,
    meta: {
      title: "归档"
    }
  },
  {
    path: "/tag",
    component: Tag,
    meta: {
      title: "标签"
    }
  },
  {
    path: "/links",
    component: Link,
    meta: {
      title: "友链列表"
    }
  },
  {
    path: "/about",
    component: About,
    meta: {
      title: "关于我"
    }
  },
  {
    path: "/message",
    component: Message,
    meta: {
      title: "留言板"
    }
  },
  {
    path: "/tag/*",
    component: ArticleList
  },
  {
    path: "/user",
    component: User,
    meta: {
      title: "个人中心"
    }
  },
  {
    path: "/login/qq",
    component: OauthLogin
  }
  // ,
  // {
  //   path: "/login/weibo",
  //   component: OauthLogin
  // }
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes
});

export default router;
