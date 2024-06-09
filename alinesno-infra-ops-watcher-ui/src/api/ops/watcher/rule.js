import request from '@/utils/request'
import { parseStrEmpty } from "@/utils/ruoyi";

// 接口配置项
var prefix = '/api/infra/ops/watcher/rule/' ;
var managerUrl = {
    datatables: prefix + "datatables",
    createUrl: prefix + 'add',
    saveUrl: prefix + 'save',
    updateUrl: prefix + "modify",
    statusUrl: prefix + "changeStatus",
    cleanUrl: prefix + "clean",
    detailUrl: prefix + "detail",
    removeUrl: prefix + "delete",
    choiceRule: prefix + "choiceRule",
    exportUrl: prefix + "exportExcel",
    changeField: prefix + "changeField",
    currentRule: prefix + "currentRule",
    downloadfile: prefix + "downloadfile" ,
    latestList: prefix + "latestList"
}

// 获取最新的应用列表
export function getLatestList(){
    return request({
        url: managerUrl.latestList ,
        method: 'get'
    })
}

// 修改字段
export function changStatusField(data){
    return request({
        url: managerUrl.changeField ,
        method: 'post',
        data: data
    })
}

// 选择当前应用
export function getCurrentRule(){
    return request({
        url: managerUrl.currentRule ,
        method: 'get'
    })
}

// 配置当前应用
export function choiceRule(id){
    return request({
        url: managerUrl.choiceRule + '?projectId=' + parseStrEmpty(id),
        method: 'get'
    })
}

// 查询用户列表
export function listRule(query) {
    return request({
        url: managerUrl.datatables,
        method: 'post',
        params: query
    })
}

// 查询用户详细
export function getRule(id) {
    return request({
        url: managerUrl.detailUrl + '/' + parseStrEmpty(id),
        method: 'get'
    })
}

// 新增用户
export function addRule(data) {
    return request({
        url: managerUrl.saveUrl,
        method: 'post',
        data: data
    })
}

// 修改用户
export function updateRule(data) {
    return request({
        url: managerUrl.updateUrl,
        method: 'put',
        data: data
    })
}

// 删除用户
export function delRule(id) {
    return request({
        url: managerUrl.removeUrl + '/' + parseStrEmpty(id),
        method: 'delete'
    })
}

// 用户密码重置
export function resetRulePwd(userId, password) {
    const data = {
        userId,
        password
    }
    return request({
        url: '/system/user/resetPwd',
        method: 'put',
        data: data
    })
}

// 用户状态修改
export function changeRuleStatus(userId, status) {
    const data = {
        userId,
        status
    }
    return request({
        url: '/system/user/changeStatus',
        method: 'put',
        data: data
    })
}

// 查询用户个人信息
export function getRuleProfile() {
    return request({
        url: '/system/user/profile',
        method: 'get'
    })
}

// 修改用户个人信息
export function updateRuleProfile(data) {
    return request({
        url: '/system/user/profile',
        method: 'put',
        data: data
    })
}

// 用户密码重置
export function updateRulePwd(oldPassword, newPassword) {
    const data = {
        oldPassword,
        newPassword
    }
    return request({
        url: '/system/user/profile/updatePwd',
        method: 'put',
        params: data
    })
}

// 用户头像上传
export function uploadAvatar(data) {
    return request({
        url: '/system/user/profile/avatar',
        method: 'post',
        data: data
    })
}

// 查询授权角色
export function getAuthRole(userId) {
    return request({
        url: '/system/user/authRole/' + userId,
        method: 'get'
    })
}

// 保存授权角色
export function updateAuthRole(data) {
    return request({
        url: '/system/user/authRole',
        method: 'put',
        params: data
    })
}
