import Vue from "vue";
import Vuex from "vuex";
import createPersistedState from "vuex-persistedstate";
Vue.use(Vuex);

export default new Vuex.Store({
    state: {
        token: null,
        userId: null,
        email: null,
        roleList: null,
        avatar: null,
        userName: null,
        introduce: null,
        webSite: null,
        userMenuList: [],
        userInfo: JSON.parse(sessionStorage.getItem("userInfo")),
        tagList:[],
        blogInfo: {}
    },
    mutations: {
        saveTab(state, tab) {
            if (state.tabList.findIndex(item => item.path === tab.path) == -1) {
                state.tabList.push({ name: tab.name, path: tab.path });
            }
        },
        removeTab(state, tab) {
            var index = state.tabList.findIndex(item => item.name === tab.name);
            state.tabList.splice(index, 1);
        },
        resetTab(state) {
            state.tabList = [{ name: "首页", path: "/" }];
        },
        trigger(state) {
            state.collapse = !state.collapse;
        },
        login(state, user) {
            state.userId = user.userId;
            state.email = user.email;
            state.roleList = user.roleList;
            state.avatar = user.avatar;
            state.userName = user.userName;
            state.introduce = user.introduce;
            state.webSite = user.webSite;
        },
        logout(state) {
            state.userId = null;
            state.roleList = null;
            state.avatar = null;
            state.userName = null;
            state.introduce = null;
            state.webSite = null;
            state.userMenuList = [];
        },
        checkBlogInfo(state, blogInfo) {
            state.blogInfo = blogInfo;
        },
        updateAvatar(state, avatar) {
            state.avatar = avatar;
          },
        updateUserInfo(state, user) {
            state.userName = user.userName;
            state.introduce = user.introduce;
            state.webSite = user.webSite;
        },
        SET_TOKEN: (state, token) => {
            state.token = token;
            // localStorage.setItem("token", token)
            sessionStorage.setItem("token", token)
        },
        SET_USERINFO: (state, userInfo) => {
            state.userInfo = userInfo;
            sessionStorage.setItem("userInfo", JSON.stringify(userInfo))
        },
        REMOVE_INFO: (state) => {
            sessionStorage.setItem("token", '');
            sessionStorage.setItem("userInfo", JSON.stringify(''));
            state.userInfo = {}
        }
    },
    getters: {
        getUser: state => {
            return state.userInfo
        }
    },
    actions: {},
    modules: {},
    plugins: [
        createPersistedState({
          storage: window.sessionStorage
        })
    ]
})
