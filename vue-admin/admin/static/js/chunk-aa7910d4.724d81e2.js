(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-aa7910d4"],{"233d":function(t,e,r){"use strict";r.r(e);var n=function(){var t=this,e=t.$createElement,r=t._self._c||e;return r("div",[r("div",{staticClass:"crumbs"},[r("el-breadcrumb",{attrs:{separator:"/"}},[r("el-breadcrumb-item",[r("i",{staticClass:"el-icon-lx-calendar"}),t._v(" 文章")]),r("el-breadcrumb-item",[t._v("markdown编辑器")])],1)],1),r("div",{staticClass:"m-content"},[r("el-form",{ref:"editForm",attrs:{"status-icon":"",model:t.editForm,rules:t.rules,"label-width":"80px"}},[r("el-form-item",{attrs:{label:"标题",prop:"title"}},[r("el-input",{model:{value:t.editForm.title,callback:function(e){t.$set(t.editForm,"title",e)},expression:"editForm.title"}})],1),r("el-form-item",{attrs:{label:"摘要",prop:"description"}},[r("el-input",{attrs:{type:"textarea"},model:{value:t.editForm.description,callback:function(e){t.$set(t.editForm,"description",e)},expression:"editForm.description"}})],1),r("el-form-item",{attrs:{label:"作者",prop:"author"}},[r("el-input",{model:{value:t.editForm.author,callback:function(e){t.$set(t.editForm,"author",e)},expression:"editForm.author"}})],1),r("el-form-item",{attrs:{label:"内容",prop:"content"}},[r("mavon-editor",{model:{value:t.editForm.content,callback:function(e){t.$set(t.editForm,"content",e)},expression:"editForm.content"}})],1),r("el-form-item",[r("el-button",{attrs:{type:"primary"},on:{click:function(e){return t.submitForm()}}},[t._v("立即创建")]),r("el-button",[t._v("取消")])],1)],1)],1)])},i=[],o=r("365c"),a={name:"ArticleEdit",data:function(){return{editForm:{id:null,title:"",description:"",author:"",content:""},rules:{title:[{required:!0,message:"请输入标题",trigger:"blur"}],description:[{required:!0,message:"请输入摘要",trigger:"blur"}]}}},created:function(){var t=this.$route.params.articleId,e=this;console.log(t),t&&this.$axios.get("/article/"+t).then((function(t){var r=t.data.data;e.editForm.id=r.id,e.editForm.title=r.title,e.editForm.description=r.description,e.editForm.author=r.author,e.editForm.content=r.content}))},methods:{submitForm:function(){var t=this,e=this;this.$refs.editForm.validate((function(r){if(!r)return console.log("error submit!!"),!1;Object(o["b"])(t.editForm,{headers:{Authorization:localStorage.getItem("token")}}).then((function(t){e.$alert("操作成功","提示",{confirmButtonText:"确定",callback:function(t){e.$router.push("/article")}})}))}))}}},u=a,c=(r("e654"),r("2877")),l=Object(c["a"])(u,n,i,!1,null,"ead6971c",null);e["default"]=l.exports},"365c":function(t,e,r){"use strict";r.d(e,"l",(function(){return o})),r.d(e,"m",(function(){return a})),r.d(e,"c",(function(){return u})),r.d(e,"a",(function(){return c})),r.d(e,"b",(function(){return l})),r.d(e,"d",(function(){return s})),r.d(e,"f",(function(){return d})),r.d(e,"e",(function(){return m})),r.d(e,"g",(function(){return f})),r.d(e,"i",(function(){return p})),r.d(e,"h",(function(){return b})),r.d(e,"k",(function(){return h})),r.d(e,"j",(function(){return g}));var n=r("bc3a"),i=r.n(n);i.a.defaults.baseURL="http://localhost:8081";var o=function(t){return i.a.post("/login",t)},a=function(t){return i.a.get("/logout")};function u(t){return i.a.get("/article/list",{params:t})}function c(t){return i.a.get("/article/".concat(t))}function l(t,e){return i.a.post("/article/edit",t,e)}function s(t){return i.a.get("/comment/index",t)}function d(t){return i.a.get("/link/list",t)}function m(t,e){return i.a.post("/link/edit",t,e)}function f(t){return i.a.get("/log/list",t)}function p(t){return i.a.get("/tag/list",t)}function b(t,e){return i.a.post("/tag/edit",t,e)}function h(t){return i.a.get("/type/list",t)}function g(t,e){return i.a.post("/type/edit",t,e)}},"389c":function(t,e,r){},e654:function(t,e,r){"use strict";var n=r("389c"),i=r.n(n);i.a}}]);