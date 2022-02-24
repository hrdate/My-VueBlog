module.exports = {
    transpileDependencies: ["vuetify"],
    productionSourceMap: false,
    devServer: {
      port: 8083, //前端端口--随意，通过Nginx反向代理后都一样
      //host: 'localhost', //主机地址
      open: true,
      // proxy: {
      //   "/api": {
      //     target: "http://127.0.0.1:8081",
      //     pathRewrite: { "^/api": "/" },
      //     changeOrigin: true
      //   }
      // },
      // disableHostCheck: true
      disableHostCheck: true
    },
    chainWebpack: config => {
      config.resolve.alias.set("@", resolve("src"));
    }
};
  
const path = require("path");
function resolve(dir) {
  return path.join(__dirname, dir);
}
