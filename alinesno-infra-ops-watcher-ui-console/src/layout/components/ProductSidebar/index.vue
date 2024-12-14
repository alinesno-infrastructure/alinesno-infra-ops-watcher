<template>
  <div class="siderbar">
    <el-menu default-active="1" class="el-menu-vertical" :collapse="isCollapse" @open="handleOpen" @close="handleClose">

      <el-tooltip effect="dark" :content="item.desc" v-for="item in menuItems" :key="item.id" placement="right">
        <el-menu-item :index="item.id" @click="openServiceList(item.link)">
          <i :class="item.icon"></i>
        </el-menu-item>
      </el-tooltip>
    </el-menu>

    <el-menu style="" class="el-menu-vertical acp-suggest" :collapse="isCollapse" @open="handleOpen" @close="handleClose">
      <el-tooltip effect="dark" content="优化建议" placement="right">
        <el-menu-item index="12" @click="dialogVisible = true">
          <i class="fa-solid fa-paper-plane"></i>
        </el-menu-item>
      </el-tooltip>
    </el-menu>

    <!-- 建议和反馈 -->
    <el-dialog v-model="dialogVisible" title="使用建议和反馈" width="30%" :append-to-body="true" :before-close="handleClose">

      <el-form ref="ruleFormRef" label-position="top" :model="ruleForm" :rules="rules" label-width="120px"
        class="demo-ruleForm" :size="formSize" status-icon>

        <el-form-item label="您对控制台首页满意吗？" prop="name">
          <el-rate v-model="value2" :colors="colors" />
        </el-form-item>

        <el-form-item label="您对控制台首页满意吗？" prop="name">
          <el-input type="textarea" />
        </el-form-item>

        <el-form-item style="margin-top:30px">
          <el-button type="primary" @click="submitForm(ruleFormRef)">保存</el-button>
          <el-button @click="resetForm(ruleFormRef)">重置</el-button>
        </el-form-item>

      </el-form>
    </el-dialog>

  </div>
</template>

<script setup>

const dialogVisible = ref(false)
const router = useRouter();

// 菜单列表
const menuItems = ref([
  { id: '1', icon: 'fa-solid fa-house-user', link: '/index', desc: '仪表盘' },
  { id: '2', icon: 'fa-solid fa-truck-medical', link: '/alerts', desc: '告警事件' },
  { id: '3', icon: 'fa-solid fa-industry', link: '/pipelines', desc: '自动化流水线' },
  { id: '4', icon: 'fa-solid fa-ship', link: '/infrastructure', desc: '基础设施' },
  { id: '5', icon: 'fa-solid fa-chart-line', link: '/metrics', desc: '运行指标' },
  { id: '6', icon: 'fa-solid fa-file-lines', link: '/logs', desc: '日志监控' },
  { id: '7', icon: 'fa-solid fa-code-pull-request', link: '/apm', desc: '应用性能监控' },
  { id: '9', icon: 'fa-solid fa-wand-magic-sparkles', link: '/inspection', desc: '巡检监控' },
  { id: '8', icon: 'fa-solid fa-user-check', link: '/userAccess', desc: '用户访问监控' },
  { id: '10', icon: 'fa-solid fa-puzzle-piece', link: '/integration', desc: '集成' },
]);

// 打开首页
function jumpTo() {
  router.push({ path: "/index" });
}

// 打开智能客服
function openSmartService() {
  router.push({ path: "/dashboard/smartService" });
}

// 打开服务市场
function openServiceList(_path) {
  router.push({ path: _path });
}

</script>

<style lang="scss" scoped>
.el-menu-vertical:not(.el-menu--collapse) {
  width: 65px;
}

.acp-suggest {
  bottom: 20px;
  position: absolute;
}

.siderbar {
  float: left;
  height: 100%;
  width: 64px;
  border-right: 1px solid #e6e6e6;
  padding-top: 40px;
  overflow: hidden;
  background-color: #fff;
  position: fixed;
}
</style>
