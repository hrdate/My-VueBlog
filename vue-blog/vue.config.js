module.exports = {
  transpileDependencies: ["vuetify"],
  devServer: {
    port: 8081, //前端端口--随意，通过Nginx反向代理后都一样
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
  }
};
