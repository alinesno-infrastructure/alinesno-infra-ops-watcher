<template>
  <div class="app-container">
     <el-row :gutter="20">
        <!--模板数据-->
        <el-col :span="24" :xs="24">
           <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="100px">
              <el-form-item label="模板名称" prop="typeName">
                 <el-input v-model="queryParams.typeName" placeholder="请输入模板名称" clearable style="width: 240px" @keyup.enter="handleQuery" />
              </el-form-item>
              <el-form-item label="模板名称" prop="channelDesc">
                 <el-input v-model="queryParams['condition[channelDesc|like]']" placeholder="请输入模板名称" clearable style="width: 240px" @keyup.enter="handleQuery" />
              </el-form-item>
              <el-form-item>
                 <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
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

           <el-table v-loading="loading" :data="AlertTemplateList" @selection-change="handleSelectionChange">
              <el-table-column label="序号" type="index" width="50" align="center" />

              <el-table-column label="类型" align="center" width="80" key="icon" >
                  <template #default="scope">
                        <span style="font-size:25px;color:#3b5998">
                           <i v-if="scope.row.alertMethod == 'email'" class="fa-solid fa-envelope-open-text" />
                           <i v-if="scope.row.alertMethod == 'aliyun_sms'" class="fa-brands fa-weixin" />
                        </span>
                  </template>
              </el-table-column>

              <!-- 业务字段-->
              <el-table-column label="模板名称" align="left" key="channelName" prop="channelName" v-if="columns[0].visible">
                 <template #default="scope">
                  {{ scope.row.alertContentTemplateCn }} <br/>
                  {{ scope.row.alertContentTemplateEn}}
                 </template>
              </el-table-column>

              <el-table-column label="告警级别" align="center" key="alertLevel" width="100" prop="alertLevel" v-if="columns[0].visible" />

              <el-table-column label="是否开启" align="center" width="200" key="isOpen" prop="isOpen" v-if="columns[1].visible" :show-overflow-tooltip="true" >
                 <template #default="scope">
                    <el-switch
                       v-model="scope.row.hasStatus"
                       :active-value="1"
                       :inactive-value="0"
                       @change="handleChangStatusField('hasStatus' , scope.row.hasStatus, scope.row.id)"
                    />
                 </template>
              </el-table-column>

              <!-- 操作字段  -->
              <el-table-column label="操作" align="center" width="180" class-name="small-padding fixed-width">
                 <template #default="scope">
                   <el-button type="text" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['system:menu:edit']">修改</el-button>
                   <el-button type="text" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['system:menu:remove']">删除</el-button>
                 </template>
              </el-table-column>

           </el-table>
           <pagination v-show="total > 0" :total="total" v-model:page="queryParams.pageNum" v-model:limit="queryParams.pageSize" @pagination="getList" />
        </el-col>
     </el-row>

     <!-- 添加或修改模板配置对话框 -->
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
                    <el-input v-model="form.typeName" placeholder="请输入模板名称" maxlength="50" />
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

  </div>
</template>

<script setup name="AlertTemplate">

import {
  listAlertTemplate,
  delAlertTemplate,
  getAlertTemplate,
  updateAlertTemplate,
  addAlertTemplate , 
  changStatusField 
} from "@/api/ops/watcher/alertTemplate";

const router = useRouter();
const { proxy } = getCurrentInstance();

// 定义变量
const AlertTemplateList = ref([]);
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
  { key: 0, label: `模板名称`, visible: true },
  { key: 1, label: `模板描述`, visible: true },
  { key: 2, label: `表数据量`, visible: true },
  { key: 3, label: `类型`, visible: true },
  { key: 4, label: `模板地址`, visible: true },
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
     typeName: [{ required: true, message: "名称不能为空", trigger: "blur" }] , 
     jdbcUrl: [{ required: true, message: "连接不能为空", trigger: "blur" }],
     isRateLimited: [{ required: true, message: "类型不能为空", trigger: "blur" }] , 
     dbUsername: [{ required: true , message: "用户名不能为空", trigger: "blur"}],
     dbPasswd: [{ required: true, message: "密码不能为空", trigger: "blur" }] , 
     isOpen: [{ required: true, message: "备注不能为空", trigger: "blur" }] 
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询模板列表 */
function getList() {
  loading.value = true;
  listAlertTemplate(proxy.addDateRange(queryParams.value, dateRange.value)).then(res => {
     loading.value = false;
     AlertTemplateList.value = res.rows;
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
  const AlertTemplateIds = row.id || ids.value;
  proxy.$modal.confirm('是否确认删除模板编号为"' + AlertTemplateIds + '"的数据项？').then(function () {
     return delAlertTemplate(AlertTemplateIds);
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
     AlertTemplateName: undefined,
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
  title.value = "添加模板";
};

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const id = row.id || ids.value;
  getAlertTemplate(id).then(response => {
     form.value = response.data;
     open.value = true;
     title.value = "修改模板";
  });
};

/** 提交按钮 */
function submitForm() {
  proxy.$refs["databaseRef"].validate(valid => {
     if (valid) {
        if (form.value.id != undefined) {
           updateAlertTemplate(form.value).then(response => {
              proxy.$modal.msgSuccess("修改成功");
              open.value = false;
              getList();
           });
        } else {
           addAlertTemplate(form.value).then(response => {
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

getList();

</script>
