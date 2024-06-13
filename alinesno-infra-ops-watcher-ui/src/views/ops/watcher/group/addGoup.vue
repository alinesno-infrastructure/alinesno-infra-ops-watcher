<template>groupName
    <div class="app-container">
        <!-- 添加或修改分类对话框 -->
        <el-form ref="deptRef" :model="form" :rules="rules" label-width="80px">
            <el-row>
                <el-col :span="24" v-if="form.parentId !== 0">
                    <el-form-item label="上级分类" prop="parentId">
                        <el-tree-select 
                            v-model="form.parentId" 
                            :data="deptOptions"
                            :props="{ value: 'id', label: 'groupName', children: 'children' }" 
                            value-key="id"
                            placeholder="选择上级分类" 
                            check-strictly />
                    </el-form-item>
                </el-col>
                <el-col :span="24">
                    <el-form-item label="分类名称" prop="groupName">
                        <el-input v-model="form.groupName" placeholder="请输入分类名称" />
                    </el-form-item>
                </el-col>
                <el-col :span="24">
                    <el-form-item label="类型描述" prop="groupDesc">
                        <el-input v-model="form.groupDesc" placeholder="请输入分类描述" />
                    </el-form-item>
                </el-col>
                <el-col :span="24">
                    <el-form-item label="显示排序" prop="orderNum">
                        <el-input-number v-model="form.orderNum" controls-position="right" :min="0" />
                    </el-form-item>
                </el-col>
                <el-col :span="24">
                    <el-form-item label="分类状态">
                        <el-radio-group v-model="form.hasStatus">
                            <el-radio v-for="dict in sys_normal_disable" :key="dict.value" :label="dict.value">{{
            dict.label }}</el-radio>
                        </el-radio-group>
                    </el-form-item>
                </el-col>
            </el-row>
        </el-form>
        <div class="dialog-footer">
            <el-button type="primary" @click="submitForm">确 定</el-button>
            <el-button @click="cancel">取 消</el-button>
        </div>
    </div>
</template>

<script setup groupName="Group">

import {
    listGroup,
    updateGroup,
    addGroup
} from "@/api/ops/watcher/group";

const { proxy } = getCurrentInstance();
const { sys_normal_disable } = proxy.useDict("sys_normal_disable");

const deptList = ref([]);
const open = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const title = ref("");
const deptOptions = ref([]);
const isExpandAll = ref(true);
const refreshTable = ref(true);

const data = reactive({
    form: {},
    queryParams: {
        pageNum: 1,
        pageSize: 10,
        groupName: undefined,
        hasStatus: undefined
    },
    rules: {
        parentId: [{ required: true, message: "上级分类不能为空", trigger: "blur" }],
        groupName: [{ required: true, message: "分类名称不能为空", trigger: "blur" }],
        groupDesc: [{ required: true, message: "分类描述不能为空", trigger: "blur" }],
        orderNum: [{ required: true, message: "显示排序不能为空", trigger: "blur" }]
    },
});

const { queryParams, form, rules } = toRefs(data);

/** 查询分类列表 */
function getList() {
    loading.value = true;
    listGroup(queryParams.value).then(response => {
        deptList.value = proxy.handleTree(response.rows, "id" , "parentId" , "children");
        deptOptions.value = proxy.handleTree(response.rows, "id" , "parentId" , "children");
        loading.value = false;
    });
}
/** 取消按钮 */
function cancel() {
    open.value = false;
    reset();
}
/** 表单重置 */
function reset() {
    form.value = {
        id: undefined,
        parentId: undefined,
        groupName: undefined,
        orderNum: 0,
        leader: undefined,
        phone: undefined,
        email: undefined,
        hasStatus: "0"
    };
    proxy.resetForm("deptRef");
}

/** 提交按钮 */
function submitForm() {
    proxy.$refs["deptRef"].validate(valid => {
        if (valid) {
            if (form.value.id != undefined) {
                updateGroup(form.value).then(response => {
                    proxy.$modal.msgSuccess("修改成功");
                    open.value = false;
                    getList();
                });
            } else {
                addGroup(form.value).then(response => {
                    proxy.$modal.msgSuccess("新增成功");
                    open.value = false;
                    getList();
                });
            }
        }
    });
}

getList();

</script>
