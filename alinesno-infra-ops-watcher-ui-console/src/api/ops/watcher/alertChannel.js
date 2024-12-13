import request from '@/utils/request'
import { parseStrEmpty } from "@/utils/ruoyi";
import { parse } from '@vue/compiler-sfc';

/**
 * 数据库接口操作
 * 
 * @author luoxiaodong
 * @since 1.0.0
 */

// 接口配置项
var prefix = '/api/infra/ops/watcher/alert_channel/' ;
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
    getAlertChannelParams: prefix + "getChannelParams",
    downloadfile: prefix + "downloadfile",
    updateAlertChannelParams: prefix + "updateAlertChannelParams"
}

// 修改渠道参数
export function updateAlertChannelParams(data , channelId) {
  return request({
    url: managerUrl.updateAlertChannelParams + '?channelId=' + parseStrEmpty(channelId),
    method: 'put',
    data: data
  })
}

// 获取预警通知渠道参数
export function getAlertChannelParams(id , channelCode){
  return request({
    url: managerUrl.getAlertChannelParams+ '?id=' + parseStrEmpty(id) + '&channelCode=' + parseStrEmpty(channelCode),
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

// 查询数据库列表
export function listAlertChannel(query) {
  return request({
    url: managerUrl.datatables ,
    method: 'post',
    params: query
  })
}

// 查询数据库详细
export function getAlertChannel(databaseId) {
  return request({
    url: managerUrl.detailUrl + '/' + parseStrEmpty(databaseId),
    method: 'get'
  })
}

// 新增数据库
export function addAlertChannel(data) {
  return request({
    url: managerUrl.saveUrl ,
    method: 'post',
    data: data
  })
}

// 修改数据库
export function updateAlertChannel(data) {
  return request({
    url: managerUrl.updateUrl ,
    method: 'put',
    data: data
  })
}

// 删除数据库
export function delAlertChannel(databaseId) {
  return request({
    url: managerUrl.removeUrl + '/' + parseStrEmpty(databaseId),
    method: 'delete'
  })
}
