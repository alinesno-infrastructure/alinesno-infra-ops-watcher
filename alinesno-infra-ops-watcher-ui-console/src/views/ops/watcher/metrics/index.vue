<template>
   <div class="app-container">
      <el-row :gutter="20">

         <!--分类-->
         <el-col :span="4" :xs="24">

            <div class="head-container">
               <!-- 查询条件 -->
               <el-form :inline="true" :model="searchForm" class="demo-form-inline">
                  <el-row>
                     <el-col :span="19">
                        <el-form-item label="监控项目">
                           <el-input placeholder="请输入部门名称"></el-input>
                        </el-form-item>
                     </el-col>
                     <el-col :span="5">
                        <el-form-item>
                           <el-button type="primary" text bg @click="onSubmit">查询</el-button>
                        </el-form-item>
                     </el-col>
                  </el-row>
               </el-form>
            </div>

            <!-- 告警分类 -->
            <div class="type-card-box">
               <div v-for="(item, index) in operationMetricsTypes" :key="index" class="monitor-item-type-box">
                  <span style="color:#3b5998"><i :class="['fa-solid', item.icon]"></i></span>
                  <span>{{ item.title }}</span>
               </div>
            </div>

         </el-col>

         <!--应用数据-->
         <el-col :span="20" :xs="24">
            <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="100px">
               <el-form-item label="应用名称" prop="typeName">
                  <el-input v-model="queryParams.typeName" placeholder="请输入应用名称" clearable style="width: 240px"
                     @keyup.enter="handleQuery" />
               </el-form-item>
               <el-form-item label="应用名称" prop="channelDesc">
                  <el-input v-model="queryParams['condition[channelDesc|like]']" placeholder="请输入应用名称" clearable
                     style="width: 240px" @keyup.enter="handleQuery" />
               </el-form-item>
               <el-form-item>
                  <el-button type="primary" icon="Search" bg text @click="handleQuery">搜索</el-button>
                  <el-button icon="Refresh" @click="resetQuery">重置</el-button>
               </el-form-item>
            </el-form>

            <el-row :gutter="10" class="mb8">

               <!-- <el-col :span="1.5">
                   <el-button type="primary" plain icon="Plus" @click="handleAdd">新增</el-button>
                </el-col> -->

               <el-col :span="1.5">
                  <el-button type="success" plain icon="Edit" :disabled="single" @click="handleUpdate">修改</el-button>
               </el-col>

               <!-- <el-col :span="1.5">
                   <el-button type="danger" plain icon="Delete" :disabled="multiple" @click="handleDelete">删除</el-button>
                </el-col> -->

               <right-toolbar v-model:showSearch="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
            </el-row>

            <el-table v-loading="loading" :data="AlertChannelList" @selection-change="handleSelectionChange">
               <el-table-column type="index" width="50" align="center" />
               <el-table-column label="图标" align="center" width="70" key="icon" v-if="columns[5].visible">
                  <template #default="scope">
                     <span style="font-size:25px;color:#3b5998">
                        <i :class="scope.row.icon" />
                     </span>
                  </template>
               </el-table-column>

               <!-- 业务字段-->
               <el-table-column label="类型名称" align="left" width="250" key="channelName" prop="channelName"
                  v-if="columns[0].visible">
                  <template #default="scope">
                     {{ scope.row.channelName }} ({{ scope.row.channelCode }})
                  </template>
               </el-table-column>

               <el-table-column label="类型描述" align="left" key="channelDesc" prop="channelDesc"
                  v-if="columns[0].visible" />

               <el-table-column label="是否开启" align="center" width="200" key="isOpen" prop="isOpen"
                  v-if="columns[1].visible" :show-overflow-tooltip="true">
                  <template #default="scope">
                     <el-switch v-model="scope.row.isOpen" :active-value="1" :inactive-value="0"
                        @change="handleChangStatusField('isOpen', scope.row.isOpen, scope.row.id)" />
                  </template>
               </el-table-column>
               <el-table-column label="配置渠道" align="center" width="150" key="channelCode" prop="channelCode"
                  v-if="columns[1].visible" :show-overflow-tooltip="true">
                  <template #default="scope">
                     <el-button type="primary" bg text
                        @click="handleConfigParams(scope.row.id, scope.row.channelCode)">
                        <i class="fa-solid fa-code"></i> 配置
                     </el-button>
                  </template>
               </el-table-column>
               <el-table-column label="请求次数" align="center" width="300" key="requestCount" prop="requestCount"
                  v-if="columns[2].visible" :show-overflow-tooltip="true">
                  <template #default="scope">
                     <span v-if="scope.row.isRateLimited == 1">
                        <el-button type="primary" bg text> <i class="fa-solid fa-code"></i> {{ scope.row.requestCount
                           }}/分</el-button>
                     </span>
                     <span v-else>
                        <el-button type="danger" bg text> <i class="fa-solid fa-feather"></i> 不限流</el-button>
                     </span>
                  </template>
               </el-table-column>

               <el-table-column label="限流" align="center" width="100" key="isRateLimited" prop="isRateLimited"
                  v-if="columns[3].visible" :show-overflow-tooltip="true">
                  <template #default="scope">
                     <el-switch v-model="scope.row.isRateLimited" :active-value="1" :inactive-value="0"
                        @change="handleChangStatusField('isRateLimited', scope.row.isRateLimited, scope.row.id)" />
                  </template>
               </el-table-column>

               <!-- 操作字段  -->
               <el-table-column label="操作" align="center" width="100" class-name="small-padding fixed-width">
                  <template #default="scope">
                     <el-tooltip content="配置" placement="top" v-if="scope.row.id !== 1">
                        <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)"
                           v-hasPermi="['system:AlertChannel:edit']"></el-button>
                     </el-tooltip>
                  </template>

               </el-table-column>
            </el-table>
            <pagination v-show="total > 0" :total="total" v-model:page="queryParams.pageNum"
               v-model:limit="queryParams.pageSize" @pagination="getList" />
         </el-col>
      </el-row>

      <!-- 添加或修改应用配置对话框 -->
      <el-dialog :title="title" v-model="open" width="900px" append-to-body>
         <el-form :model="form" :rules="rules" ref="databaseRef" label-width="100px">
            <el-row>
               <el-col :span="24">
                  <el-form-item label="图标" prop="icon">
                     <el-input v-model="form.icon" placeholder="请输入图标icon" maxlength="128" />
                  </el-form-item>
               </el-col>
            </el-row>
            <el-row>
               <el-col :span="24">
                  <el-form-item label="类型名称" prop="typeName">
                     <el-input v-model="form.typeName" placeholder="请输入应用名称" maxlength="50" />
                  </el-form-item>
               </el-col>
            </el-row>
            <el-col :span="24">
               <el-form-item label="限流次数" prop="requestCount">
                  <el-input v-model="form.requestCount" placeholder="请输入每分钟限流" maxlength="50" />
               </el-form-item>
            </el-col>
            <el-row>
               <el-col :span="24">
                  <el-form-item label="类型描述" prop="typeDesc">
                     <el-input v-model="form.typeDesc" placeholder="请输入类型描述" maxlength="256" />
                  </el-form-item>
               </el-col>
            </el-row>
         </el-form>
         <template #footer>
            <div class="dialog-footer">
               <el-button type="primary" @click="submitForm">确 定</el-button>
               <el-button @click="cancel">取 消</el-button>
            </div>
         </template>
      </el-dialog>

      <el-dialog :title="formTitle" v-model="formDataOpen" width="900px" append-to-body>
         <div class="mb-6">
            <el-row v-for="(item, index) in state.editForm.header" :key="index" style="margin-bottom:10px;">
               <el-col :span="1">
               </el-col>
               <el-col :span="10">
                  <el-input v-model="item.paramKey" :placeholder="item.paramKey" :disabled="item.inner"></el-input>
               </el-col>
               <el-col :span="1">
               </el-col>
               <el-col :span="10">
                  <el-input v-model="item.paramValue" :placeholder="item.paramDesc"></el-input>
               </el-col>
               <el-col :span="2" v-if="!item.inner" style="text-align: right">
                  <el-button icon="Close" type="danger" plain circle @click="deleteHeaderRow(index)" />
               </el-col>
            </el-row>
            <br />
            <div class="dialog-footer" style="text-align: right;">
               <el-button type="primary" @click="submitEditForm">确 定</el-button>
               <el-button @click="cancel">取 消</el-button>
               <el-button icon="Plus" type="success" plain @click="addHeaderRow">添加参数</el-button>
            </div>
         </div>
      </el-dialog>


   </div>
</template>

<script setup name="AlertChannel">

import {
   listAlertChannel,
   delAlertChannel,
   getAlertChannel,
   getAlertChannelParams,
   updateAlertChannel,
   updateAlertChannelParams,
   addAlertChannel,
   changStatusField
} from "@/api/ops/watcher/alertChannel";

const router = useRouter();
const { proxy } = getCurrentInstance();

// 定义变量
const AlertChannelList = ref([]);
const open = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref("");
const dateRange = ref([]);
const postOptions = ref([]);
const roleOptions = ref([]);

// 列显隐信息
const columns = ref([
   { key: 0, label: `应用名称`, visible: true },
   { key: 1, label: `应用描述`, visible: true },
   { key: 2, label: `表数据量`, visible: true },
   { key: 3, label: `类型`, visible: true },
   { key: 4, label: `应用地址`, visible: true },
   { key: 5, label: `状态`, visible: true },
   { key: 6, label: `更新时间`, visible: true }
]);

const data = reactive({
   form: {},
   queryParams: {
      pageNum: 1,
      pageSize: 10,
      typeName: undefined,
      isOpen: undefined
   },
   rules: {
      typeName: [{ required: true, message: "名称不能为空", trigger: "blur" }],
      jdbcUrl: [{ required: true, message: "连接不能为空", trigger: "blur" }],
      isRateLimited: [{ required: true, message: "类型不能为空", trigger: "blur" }],
      dbUsername: [{ required: true, message: "用户名不能为空", trigger: "blur" }],
      dbPasswd: [{ required: true, message: "密码不能为空", trigger: "blur" }],
      isOpen: [{ required: true, message: "备注不能为空", trigger: "blur" }]
   }
});

const { queryParams, form, rules } = toRefs(data);

const formDataOpen = ref(false);
const formTitle = ref("");

/** 动态字段添加 */
const state = reactive({
   editForm: {
      header: [
         { paramKey: 'aliyun.sms.app_key', paramValue: '', paramDesc: 'AppKey从控制台获取', inner: true },
         { paramKey: 'aliyun.sms.app_secret', paramValue: '', paramDesc: 'AppSecret从控制台获取', inner: true },
         { paramKey: 'aliyun.sms.sign_name', paramValue: '', paramDesc: '签名名称从控制台获取，必须是审核通过的', inner: true },
         { paramKey: 'aliyun.sms.template_code', paramValue: '', paramDesc: '模板CODE从控制台获取，必须是审核通过的', inner: true },
         { paramKey: 'aliyun.sms.host', paramValue: '', paramDesc: 'API域名从控制台获取', inner: true },
      ]
   },
})

const operationMetricsTypes = ref([
   {
      title: '服务可用性',
      icon: 'fa-check-circle',
      desc: '衡量服务在规定时间内可访问的比例'
   },
   {
      title: '平均响应时间',
      icon: 'fa-stopwatch',
      desc: '请求从发出到接收到完整响应所需的时间'
   },
   {
      title: '交易成功率',
      icon: 'fa-handshake',
      desc: '成功完成的交易数量与总尝试次数的比例'
   },
   {
      title: '用户活跃度',
      icon: 'fa-users',
      desc: '一定时间内活跃用户的数量或比例'
   },
   {
      title: '错误率',
      icon: 'fa-exclamation-triangle',
      desc: '一段时间内发生的错误次数占总请求数的比例'
   },
   {
      title: '资源利用率',
      icon: 'fa-chart-line',
      desc: 'CPU、内存、磁盘等资源使用的效率'
   }
]);

const { editForm } = toRefs(state);
const currentChannelId = ref(null);

// 点击加号:添加一行header
const addHeaderRow = () => {
   state.editForm.header.push({ paramKey: '', paramValue: '', paramDesc: '请自定义填写key值', inner: false });
};

// 点击减号:删除一行header
const deleteHeaderRow = (index) => {
   state.editForm.header.splice(index, 1);
};

/** 查询应用列表 */
function getList() {
   loading.value = true;
   listAlertChannel(proxy.addDateRange(queryParams.value, dateRange.value)).then(res => {
      loading.value = false;
      AlertChannelList.value = res.rows;
      total.value = res.total;
   });
};

/** 搜索按钮操作 */
function handleQuery() {
   queryParams.value.pageNum = 1;
   getList();
};

/** 重置按钮操作 */
function resetQuery() {
   dateRange.value = [];
   proxy.resetForm("queryRef");
   queryParams.value.deptId = undefined;
   proxy.$refs.deptTreeRef.setCurrentKey(null);
   handleQuery();
};
/** 删除按钮操作 */
function handleDelete(row) {
   const AlertChannelIds = row.id || ids.value;
   proxy.$modal.confirm('是否确认删除应用编号为"' + AlertChannelIds + '"的数据项？').then(function () {
      return delAlertChannel(AlertChannelIds);
   }).then(() => {
      getList();
      proxy.$modal.msgSuccess("删除成功");
   }).catch(() => { });
};

/** 选择条数  */
function handleSelectionChange(selection) {
   ids.value = selection.map(item => item.id);
   single.value = selection.length != 1;
   multiple.value = !selection.length;
};

/** 重置操作表单 */
function reset() {
   form.value = {
      id: undefined,
      deptId: undefined,
      AlertChannelName: undefined,
      requestCount: undefined,
      password: undefined,
      phonenumber: undefined,
      status: "0",
      remark: undefined,
   };
   proxy.resetForm("databaseRef");
};
/** 取消按钮 */
function cancel() {
   open.value = false;
   formDataOpen.value = false;
   reset();
};

/** 配置参数 */
function handleConfigParams(id, channelCode) {
   formDataOpen.value = true;
   console.log('id = ' + id + ' , channelCode = ' + channelCode);
   formTitle.value = '配置参数';
   currentChannelId.value = id;
   getAlertChannelParams(id, channelCode).then(res => {
      console.log('res = ' + res);
      editForm.value.header = res.data;
   })
}

/** 新增按钮操作 */
function handleAdd() {
   reset();
   open.value = true;
   title.value = "添加应用";
};

/** 修改按钮操作 */
function handleUpdate(row) {
   reset();
   const id = row.id || ids.value;
   getAlertChannel(id).then(response => {
      form.value = response.data;
      open.value = true;
      title.value = "修改应用";
   });
};

/** 提交动态参数表单数据 */
function submitEditForm() {
   updateAlertChannelParams(editForm.value.header, currentChannelId.value).then(res => {
      console.log(res);
      proxy.$modal.msgSuccess("修改成功");
   });
}

/** 提交按钮 */
function submitForm() {
   proxy.$refs["databaseRef"].validate(valid => {
      if (valid) {
         if (form.value.id != undefined) {
            updateAlertChannel(form.value).then(response => {
               proxy.$modal.msgSuccess("修改成功");
               open.value = false;
               getList();
            });
         } else {
            addAlertChannel(form.value).then(response => {
               proxy.$modal.msgSuccess("新增成功");
               open.value = false;
               getList();
            });
         }
      }
   });
};

const handleChangStatusField = async (field, value, id) => {
   // 判断tags值 这样就不会进页面时调用了
   const res = await changStatusField({
      field: field,
      value: value ? 1 : 0,
      id: id
   }).catch(() => { })
   if (res && res.code == 200) {
      // 刷新表格
      getList()
   }
}

getList();

</script>