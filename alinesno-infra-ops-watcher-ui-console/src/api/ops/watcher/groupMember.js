import request from '@/utils/request'
import { parseStrEmpty } from "@/utils/ruoyi";

/**
 * 数据库接口操作
 * 
 * @author luoxiaodong
 * @since 1.0.0
 */

// 接口配置项
var prefix = '/api/infra/ops/watcher/groupMember/' ;
var managerUrl = {
    datatables : prefix +"datatables" ,
    createUrl: prefix + 'add' ,
    saveUrl: prefix + 'save' ,
    updateUrl: prefix +"modify" ,
    statusUrl: prefix +"changeStatus" ,
    cleanUrl: prefix + "clean",
    detailUrl: prefix +"detail",
    removeUrl: prefix + "delete" ,
    exportUrl: prefix + "exportExcel",
    changeField: prefix + "changeField",
    downloadfile: prefix + "downloadfile",
    updateGroupMemberContent: prefix + "updateGroupMemberContent",
    catalogTreeSelect: prefix + "catalogTreeSelect",
    getGroupMemberContent: prefix + "getGroupMemberContent",
}

// 查询部门下拉树结构
export function catalogTreeSelect() {
  return request({
    url: managerUrl.catalogTreeSelect , 
    method: 'get'
  })
}

// 列新GroupMemberContent 
export function updateGroupMemberContent(data , postId) {
  return request({
    url: managerUrl.updateGroupMemberContent + '?postId=' + postId,
    method: 'post',
    data: data
  })
}

// 查询数据库列表
export function listGroupMember(query) {
  return request({
    url: managerUrl.datatables ,
    method: 'post',
    params: query
  })
}

// 获取GroupMemberContent内容  
export function getGroupMemberContent(postId) {
  return request({
    url: managerUrl.getGroupMemberContent+ '?postId=' + parseStrEmpty(postId),
    method: 'get'
  })
}

// 查询数据库详细
export function getGroupMember(databaseId) {
  return request({
    url: managerUrl.detailUrl + '/' + parseStrEmpty(databaseId),
    method: 'get'
  })
}

// 新增数据库
export function addGroupMember(data) {
  return request({
    url: managerUrl.saveUrl ,
    method: 'post',
    data: data
  })
}

// 修改数据库
export function updateGroupMember(data) {
  return request({
    url: managerUrl.updateUrl ,
    method: 'put',
    data: data
  })
}

// 删除数据库
export function delGroupMember(databaseId) {
  return request({
    url: managerUrl.removeUrl + '/' + parseStrEmpty(databaseId),
    method: 'delete'
  })
}
