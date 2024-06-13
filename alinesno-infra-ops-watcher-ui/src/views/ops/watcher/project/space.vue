<template>
  <div class="app-container">

     <el-page-header @back="goBack" content="告警事项"></el-page-header>
     <br/>

     <el-row :gutter="20">
        <!--应用数据-->
        <el-col :span="24" :xs="24">
           <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="100px">
              <el-form-item label="应用名称" prop="typeName">
                 <el-input v-model="queryParams.typeName" placeholder="请输入应用名称" clearable style="width: 240px" @keyup.enter="handleQuery" />
              </el-form-item>
              <el-form-item label="应用名称" prop="channelDesc">
                 <el-input v-model="queryParams['condition[channelDesc|like]']" placeholder="请输入应用名称" clearable style="width: 240px" @keyup.enter="handleQuery" />
              </el-form-item>
              <el-form-item>
                 <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
                 <el-button icon="Refresh" @click="resetQuery">重置</el-button>
              </el-form-item>
           </el-form>

           <el-row :gutter="10" class="mb8">
              <el-col :span="1.5">
                 <el-button type="success" plain icon="Edit" :disabled="single" @click="handleUpdate">修改</el-button>
              </el-col>
              <right-toolbar v-model:showSearch="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
           </el-row>

           <el-table v-loading="loading" :data="ChannelList" @selection-change="handleSelectionChange">
              <el-table-column type="index" width="50" align="center" />
              <el-table-column label="图标" align="center" width="70" key="icon" v-if="columns[5].visible">
                 <template #default="scope">
                    <span style="font-size:25px;color:#3b5998">
                       <i :class="scope.row.icon" />
                    </span>
                 </template>
              </el-table-column>

              <!-- 业务字段-->
              <el-table-column label="类型名称" align="left" width="150" key="channelName" prop="channelName" v-if="columns[0].visible" />
              <el-table-column label="类型描述" align="left" key="channelDesc" prop="channelDesc" v-if="columns[0].visible" />

              <el-table-column label="状态" align="center" key="isOpen" prop="isOpen" v-if="columns[1].visible" :show-overflow-tooltip="true" >
                 <template #default="scope">
                    <el-button v-if="scope.$index % 2 == 0" type="primary" bg text @click="handleConfigType(scope.row.id , scope.row.documentType)"> 
                        <i class="fa-solid fa-link"></i>&nbsp;正常
                    </el-button>
                    <el-button v-if="scope.$index % 2 == 1" type="danger" bg text @click="handleConfigType(scope.row.id , scope.row.documentType)"> 
                        <i class="fa-solid fa-link-slash"></i>&nbsp;异常
                    </el-button>
                 </template>
              </el-table-column>
              <el-table-column label="警告" align="center" key="documentType" prop="documentType" v-if="columns[1].visible" :show-overflow-tooltip="true" >
                 <template #default="scope">
                    <el-button type="warning" bg text @click="handleConfigType(scope.row.id , scope.row.documentType)"> 
                        <i class="fa-solid fa-car-on"></i>&nbsp;{{ (parseInt(Math.random()*10)+50 ) }}
                    </el-button>
                 </template>
              </el-table-column>
              <el-table-column label="紧急" align="center" key="documentType" prop="documentType" v-if="columns[1].visible" :show-overflow-tooltip="true" >
                 <template #default="scope">
                    <el-button type="danger" bg text @click="handleConfigType(scope.row.id , scope.row.documentType)"> 
                        <i class="fa-solid fa-truck-medical"></i>&nbsp;{{ (parseInt(Math.random()*10)+50) }}
                    </el-button>
                 </template>
              </el-table-column>
              <el-table-column label="告警数" align="center" key="requestCount" prop="requestCount" v-if="columns[2].visible" :show-overflow-tooltip="true">
                 <template #default="scope">
                    <el-button type="success" bg text @click="handleConfigType(scope.row.id , scope.row.documentType)"> 
                        <i class="fa-solid fa-bell"></i>&nbsp;{{ (parseInt(Math.random()*10)+50) }}
                    </el-button>
                 </template>
              </el-table-column>

              <!-- 操作字段  -->
              <el-table-column label="操作" align="center" width="100" class-name="small-padding fixed-width">
                 <template #default="scope">
                    <el-tooltip content="配置" placement="top" v-if="scope.row.id !== 1">
                       <el-button link type="primary" @click="handleUpdate(scope.row)" v-hasPermi="['system:Channel:edit']">
                        <i class="fa-solid fa-feather"></i>&nbsp;抑制
                       </el-button>
                    </el-tooltip>
                 </template>

              </el-table-column>
           </el-table>
           <pagination v-show="total > 0" :total="total" v-model:page="queryParams.pageNum" v-model:limit="queryParams.pageSize" @pagination="getList" />
        </el-col>
     </el-row>

  </div>
</template>

<script setup name="Channel">

import {
  listChannel,
  delChannel,
  getChannel,
  updateChannel,
  addChannel , 
  changStatusField 
} from "@/api/ops/watcher/channel";

const router = useRouter();
const { proxy } = getCurrentInstance();

// 定义变量
const ChannelList = ref([]);
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
     pageSize: 20,
     typeName: undefined,
     isOpen: undefined
  },
  rules: {
     typeName: [{ required: true, message: "名称不能为空", trigger: "blur" }] , 
     jdbcUrl: [{ required: true, message: "连接不能为空", trigger: "blur" }],
     isRateLimited: [{ required: true, message: "类型不能为空", trigger: "blur" }] , 
     dbUsername: [{ required: true , message: "用户名不能为空", trigger: "blur"}],
     dbPasswd: [{ required: true, message: "密码不能为空", trigger: "blur" }] , 
     isOpen: [{ required: true, message: "备注不能为空", trigger: "blur" }] 
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询应用列表 */
function getList() {
  loading.value = true;
  listChannel(proxy.addDateRange(queryParams.value, dateRange.value)).then(res => {
     loading.value = false;
     ChannelList.value = res.rows;
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
  const ChannelIds = row.id || ids.value;
  proxy.$modal.confirm('是否确认删除应用编号为"' + ChannelIds + '"的数据项？').then(function () {
     return delChannel(ChannelIds);
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
     ChannelName: undefined,
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
  reset();
};

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
  getChannel(id).then(response => {
     form.value = response.data;
     open.value = true;
     title.value = "修改应用";
  });
};

/** 提交按钮 */
function submitForm() {
  proxy.$refs["databaseRef"].validate(valid => {
     if (valid) {
        if (form.value.id != undefined) {
           updateChannel(form.value).then(response => {
              proxy.$modal.msgSuccess("修改成功");
              open.value = false;
              getList();
           });
        } else {
           addChannel(form.value).then(response => {
              proxy.$modal.msgSuccess("新增成功");
              open.value = false;
              getList();
           });
        }
     }
  });
};

const handleChangStatusField = async(field , value , id) => {
   // 判断tags值 这样就不会进页面时调用了
     const res = await changStatusField({
        field: field,
        value: value?1:0,
        id: id
     }).catch(() => { })
     if (res && res.code == 200) {
        // 刷新表格
        getList()
     }
}

/** 返回上一页 */
function goBack() {
  router.back();
}

getList();

</script>
