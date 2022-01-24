import axios from 'axios'; //引入axios

//文章集合
export function ArticleList(params){
    return axios.get(`/article/articles`,{params:params})
}
//文章查看内容
export function ArticleById(params){
    return axios.get(`/article/${params}`)
}
//文章内容编辑
export function ArticleEditById(data,config){
    return axios.post(`/article/edit`,data,config)
}
//评论集合
export function CommentList(params){
    return axios.get(`/comments/index`,params)
}
//链接集合
export function FriendLinkList(params){
    return axios.get(`/friend/list`,params)
}

//链接修改
export function FriendLinkEidt(data,config) {
    return axios.post(`/FriendLink/edit`,data,config)
}
//日志查看集合
export function LogList(params) {
    return axios.get(`/log/list`,params)
}
//标签查看集合
export function TagList(params){
    return axios.get(`/tag/list`,params)
}
//类型查看集合
export function TypeList(params){
    return axios.get(`/type/list`,params)
}
//类型修改
export function TypeEidt(data,config) {
    return axios.post(`/type/edit`,data,config)
}
