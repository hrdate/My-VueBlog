import Vue from "vue";
import Vuex from "vuex";
import createPersistedState from "vuex-persistedstate";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    token: null,
    searchFlag: false,
    loginFlag: false,
    registerFlag: false,
    forgetFlag: false,
    emailFlag: false,
    drawer: false,
    loginUrl: "",
    userId: null,
    avatar: null,
    userName: null,
    introduce: null,
    webSite: null,
    loginType: null,
    email: null,
    tagList: [],
    articleLikeSet: [],
    commentLikeSet: [],
    blogInfo: {
      viewsCount: null,
      articleCount: 0,
      tagCount: 0
    },
    websiteConfigForm: {
        websiteAvatar: "",
        websiteName: "",
        websiteAuthor: "",
        websiteIntro: "",
        websiteNotice: "",
        websiteCreateTime: null,
        websiteRecordNo: "",
        socialLoginList: [],
        socialUrlList: [],
        qq: "",
        github: "",
        gitee: "",
        userAvatar: "",
        touristAvatar: "",
        isReward: 1,
        weiXinQRCode: "",
        alipayQRCode: "",
        isChatRoom: 1,
        websocketUrl: "",
        isMusicPlayer: 1,
        isEmailNotice: 1,
        isCommentReview: 0,
        isMessageReview: 0
    }
  },
  mutations: {
    login(state, user) {
      state.userId = user.userId;
      state.avatar = user.avatar;
      state.userName = user.userName;
      state.introduceduce = user.introduce;
      state.webSite = user.webSite;
      state.articleLikeSet = user.articleLikeSet ? user.articleLikeSet : [];
      state.commentLikeSet = user.commentLikeSet ? user.commentLikeSet : [];
      state.email = user.email;
      state.loginType = user.loginType;
    },
    token(state,token){
      state.token = token
      sessionStorage.setItem("token",token)
    },
    logout(state) {
      state.token = null;
      sessionStorage.removeItem("token");
      state.userId = null;
      state.avatar = null;
      state.userName = null;
      state.introduce = null;
      state.webSite = null;
      state.articleLikeSet = [];
      state.commentLikeSet = [];
      state.email = null;
      state.loginType = null;
    },
    saveTags(state, tagList){
      state.tagList = tagList;
    },
    saveLoginUrl(state, url) {
      state.loginUrl = url;
    },
    saveEmail(state, email) {
      state.email = email;
    },
    saveTagList(state,tagList){
      state.tagList = tagList;
    },
    updateUserInfo(state, user) {
      state.userName = user.userName;
      state.introduce = user.introduce;
      state.webSite = user.webSite;
    },
    updateAvatar(state, avatar) {
      state.avatar = avatar;
    },
    checkBlogInfo(state, blogInfo) {
      state.blogInfo = blogInfo;
      state.websiteConfigForm = JSON.parse(blogInfo.config)
    },
    closeModel(state) {
      state.registerFlag = false;
      state.loginFlag = false;
      state.searchFlag = false;
      state.emailFlag = false;
    },
    articleLike(state, articleId) {
      var articleLikeSet = state.articleLikeSet;
      if (articleLikeSet.indexOf(articleId) != -1) {
        articleLikeSet.splice(articleLikeSet.indexOf(articleId), 1);
      } else {
        articleLikeSet.push(articleId);
      }
    },
    commentLike(state, commentId) {
      var commentLikeSet = state.commentLikeSet;
      if (commentLikeSet.indexOf(commentId) != -1) {
        commentLikeSet.splice(commentLikeSet.indexOf(commentId), 1);
      } else {
        commentLikeSet.push(commentId);
      }
    }
  },
  actions: {},
  modules: {},
  plugins: [
    createPersistedState({
      storage: window.sessionStorage
    })
  ]
});
