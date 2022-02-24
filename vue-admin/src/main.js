import Vue from 'vue';
import App from './App.vue';
import store from './store'
import router from './router';
// 默认主题
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css'; 
Vue.use(ElementUI, {
    size: 'small'
});
//图形
import ECharts from "vue-echarts";
import echarts from 'echarts'
import "echarts/lib/chart/line";
import "echarts/lib/chart/pie";
import "echarts/lib/chart/bar";
import "echarts/lib/chart/map";
import "echarts/lib/component/tooltip";
import "echarts/lib/component/legend";
import "echarts/lib/component/title";
import Schart from 'vue-schart';
Vue.use(Schart)
// import VCharts from 'v-charts'
//引入基本模板
Vue.prototype.$echarts = echarts
Vue.component("v-chart", ECharts)
// Vue.use(VCharts)
//makerdown文本编辑器
import mavonEditor  from 'mavon-editor'
Vue.use(mavonEditor);
import 'mavon-editor/dist/css/index.css'
import './assets/css/icon.css';
import 'babel-polyfill';
//
import VueCompositionAPI from '@vue/composition-api'
Vue.use(VueCompositionAPI)
//请求
import axios from 'axios';
Vue.use(VueAxios, axios);
import VueAxios from "vue-axios";
// 定义全局时间戳过滤器
import moment from "moment";
//加载进度条
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'
NProgress.configure({
    easing: "ease", // 动画方式
    speed: 500, // 递增进度条的速度
    showSpinner: false, // 是否显示加载ico
    trickleSpeed: 200, // 自动递增间隔
    minimum: 0.3 // 初始化时的最小百分比
});
//首页标签展示
// import tagCloud from 'v-tag-cloud'
// Vue.use(tagCloud)
import tagCloud from "./components/tag-cloud";
Vue.use(tagCloud);

axios.defaults.baseURL = "http://127.0.0.1:6525";
axios.defaults.headers.post["Content-Type"] = "application/json";

// 阻止启动生产消息
Vue.config.productionTip = false;
// 定义全局时间戳过滤器
Vue.prototype.$moment = moment;
Vue.filter("date", function(value, formatStr = "YYYY-MM-DD") {
    return moment(value).format(formatStr);
});
Vue.filter("dateTime", function(value, formatStr = "YYYY-MM-DD hh:mm:ss") {
return moment(value).format(formatStr);
});
//前置拦截，加载进度条
router.beforeEach((to, from, next) => {
    NProgress.start();
    // const token = localStorage.getItem("token");
    // const userInfo=sessionStorage.getItem("userInfo");
    if (to.path == "/login") {
        next();
    } else if (!store.state.userId) {
        next({ path: "/login" });
    } else {
        next();
    }
});
router.afterEach(() => {
    NProgress.done();
});
//后置拦截
axios.interceptors.request.use(config => {
    // 可以统一设置请求头
    return config
})
axios.interceptors.response.use(response => {
    const res = response.data;
    // console.log("后置拦截")
    // 当结果的code是否为200的情况
    if (res.code == 200) {
        return response
    } else {
        // 弹窗异常信息
        Element.Message({
            message: response.data.msg,
            type: 'error',
            duration: 2 * 1000
        });
        // 直接拒绝往下面返回结果信息
        return Promise.reject(response.data.msg)
    }
},
error => {
    console.log('err' + error);// for debug
    if(error.response.data) {
        error.message = error.response.data.msg
    }
    // 根据请求状态觉得是否登录或者提示其他
    if (error.response.status === 401) {
        store.commit('REMOVE_INFO');
        router.push({
            path: '/login'
        });
        error.message = '请重新登录';
    }
    if (error.response.status === 403) {
        error.message = '权限不足，无法访问';
    }
    Element.Message({
        message: error.message,
        type: 'error',
        duration: 3 * 1000
    });
    return Promise.reject(error)
});



new Vue({
    router,
    store,
    render: h => h(App)
}).$mount('#app');
