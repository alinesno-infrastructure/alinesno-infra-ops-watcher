package com.alinesno.infra.ops.watcher.scheduler.controller;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.alinesno.infra.common.core.constants.SpringInstanceScope;
import com.alinesno.infra.common.core.utils.DateUtils;
import com.alinesno.infra.common.extend.datasource.annotation.DataPermissionQuery;
import com.alinesno.infra.common.extend.datasource.annotation.DataPermissionSave;
import com.alinesno.infra.common.extend.datasource.annotation.DataPermissionScope;
import com.alinesno.infra.common.facade.datascope.PermissionQuery;
import com.alinesno.infra.common.facade.pageable.DatatablesPageBean;
import com.alinesno.infra.common.facade.pageable.TableDataInfo;
import com.alinesno.infra.common.facade.response.AjaxResult;
import com.alinesno.infra.common.web.adapter.rest.BaseController;
import com.alinesno.infra.ops.watcher.constants.PipeConstants;
import com.alinesno.infra.ops.watcher.scheduler.dto.ProcessContextDto;
import com.alinesno.infra.ops.watcher.scheduler.dto.ProcessDefinitionDto;
import com.alinesno.infra.ops.watcher.scheduler.dto.ProcessTaskDto;
import com.alinesno.infra.ops.watcher.scheduler.dto.ProcessTaskValidateDto;
import com.alinesno.infra.ops.watcher.scheduler.entity.ProcessDefinitionEntity;
import com.alinesno.infra.ops.watcher.scheduler.service.ICategoryService;
import com.alinesno.infra.ops.watcher.scheduler.service.IProcessDefinitionService;
import io.swagger.annotations.Api;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 处理与TransEntity相关的请求的Controller。
 * 继承自BaseController类并实现ITransService接口。
 *
 * @version 1.0.0
 * @author  luoxiaodong
 */
@Slf4j
@Api(tags = "Trans")
@RestController
@Scope(SpringInstanceScope.PROTOTYPE)
@RequestMapping("/api/infra/data/scheduler/processDefinition")
public class ProcessDefinitionController extends BaseController<ProcessDefinitionEntity, IProcessDefinitionService> {

    @Autowired
    private IProcessDefinitionService service;

    @Autowired
    private ICategoryService catalogService ;

    @Autowired
    private Scheduler scheduler ;

    /**
     * 获取TransEntity的DataTables数据。
     *
     * @param request HttpServletRequest对象。
     * @param model Model对象。
     * @param page DatatablesPageBean对象。
     * @return 包含DataTables数据的TableDataInfo对象。
     */
    @DataPermissionScope
    @ResponseBody
    @PostMapping("/datatables")
    public TableDataInfo datatables(HttpServletRequest request, Model model, DatatablesPageBean page) {
        log.debug("page = {}", ToStringBuilder.reflectionToString(page));

        // CurrentProjectSession.filterProject(page);

        return this.toPage(model, this.getFeign(), page);
    }



    /**
     * 查询详情getProcessDefinitionByDto
     */
    @GetMapping("/getProcessDefinitionByDto")
    public AjaxResult getProcessDefinitionByDto(long id){

        ProcessDefinitionEntity entity = service.getById(id) ;

        ProcessContextDto dto = new ProcessContextDto() ;

        dto.setId(entity.getId());
        dto.setIcon(entity.getIcon());
        dto.setDataCollectionTemplate(entity.getDataCollectionTemplate());
        dto.setTaskName(entity.getName());
        dto.setTaskDesc(entity.getDescription());
        dto.setGlobalParams(entity.getGlobalParamMap());
        dto.setProjectCode(entity.getProjectId());
        dto.setEnvId(entity.getEnvId());
        dto.setTimeout(entity.getTimeout());
        dto.setMonitorEmail(entity.getMonitorEmail());
        dto.setCronExpression(entity.getScheduleCron());
        dto.setCategoryId(entity.getCategoryId());

        if(entity.getStartTime() != null){
            dto.setStartTime(DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS , entity.getStartTime()));
        }
        if(entity.getEndTime() != null){
            dto.setEndTime(DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS , entity.getEndTime()));
        }

        return AjaxResult.success("success" , dto) ;
    }

    /**
     * 获取目录树
     * @return
     */
    @DataPermissionQuery
    @GetMapping("/catalogTreeSelect")
    public AjaxResult catalogTreeSelect(PermissionQuery query){
        long currentProject = 1 ; // CurrentProjectSession.get().getId() ;
        return AjaxResult.success("success" , catalogService.selectCatalogTreeList(query , currentProject)) ;
    }

    /**
     * 验证脚本任务
     * @param dto
     * @return
     */
    @PostMapping("/validateTask")
    public AjaxResult validateTask(@RequestBody @Validated ProcessTaskValidateDto dto){

        log.debug("dto = {}", JSONUtil.toJsonPrettyStr(JSONObject.toJSONString(dto)));

        // 运行任务验证
        service.runProcessTask(dto) ;

        return AjaxResult.success() ;
    }

    /**
     * 更新流程定义信息
     */
    @DataPermissionSave
    @PostMapping("/updateProcessDefinition")
    public AjaxResult updateProcessDefinition(@RequestBody ProcessDefinitionDto dto){
        log.debug("dto = {}", dto);

        if("node".equals(dto.getType())){
            dto.setContext(null); // 清空上下文信息，只更新节点
        }

        List<ProcessTaskDto> taskFlow = dto.getTaskFlow() ;
        Assert.isTrue(taskFlow.size() > 1 , "流程定义为空,请定义流程.");

        // dto.setProjectId(CurrentProjectSession.get().getId());

        service.updateProcessDefinition(dto) ;
        return AjaxResult.success() ;
    }

    /**
     * 保存流程定义信息
     * @return
     */
    @DataPermissionSave
    @PostMapping("/commitProcessDefinition")
    public AjaxResult commitProcessDefinition(@RequestBody ProcessDefinitionDto dto){
        log.debug("dto = {}", dto);

        List<ProcessTaskDto> taskFlow = dto.getTaskFlow() ;
        Assert.isTrue(taskFlow.size() > 1 , "流程定义为空,请定义流程.");

        // dto.setProjectId(CurrentProjectSession.get().getId());

        long processId = service.commitProcessDefinition(dto) ;
        return AjaxResult.success("success" , processId) ;
    }

    /**
     * 暂停触发器
     * @param jobId
     * @return
     */
    @PostMapping("pauseTrigger")
    public AjaxResult pauseTrigger(String jobId) throws SchedulerException {
        scheduler.pauseTrigger(TriggerKey.triggerKey(jobId , PipeConstants.TRIGGER_GROUP_NAME));
        return AjaxResult.success();
    }

    /**
     * 运行一次
     * @param jobId
     * @return
     */
    @PostMapping("runOneTime")
    public AjaxResult runOneTime(String jobId) throws SchedulerException {

        JobKey jobKey = JobKey.jobKey(jobId,PipeConstants.JOB_GROUP_NAME);
        scheduler.triggerJob(jobKey);

        return AjaxResult.success() ;
    }

    /**
     * 启动触发器
     * @param jobId
     * @return
     */
    @PostMapping("startJob")
    public AjaxResult startJob(String jobId) throws SchedulerException {
        scheduler.resumeTrigger(TriggerKey.triggerKey(jobId , PipeConstants.TRIGGER_GROUP_NAME));//恢复Trigger
        return AjaxResult.success();
    }

    /**
     * 移除触发器
     * @param jobId
     * @return
     */
    @PostMapping("unscheduleJob")
    public AjaxResult unscheduleJob(String jobId) throws SchedulerException {
        scheduler.unscheduleJob(TriggerKey.triggerKey(jobId , PipeConstants.TRIGGER_GROUP_NAME));//移除触发器
        return AjaxResult.success();
    }

    /**
     * 任务的恢复
     * @param jobId
     * @return
     */
    @PostMapping("resumeTrigger")
    public AjaxResult resumeTrigger(String jobId) throws SchedulerException {
        scheduler.resumeTrigger(TriggerKey.triggerKey(jobId , PipeConstants.TRIGGER_GROUP_NAME)) ;
        return AjaxResult.success();
    }

    @Override
    public IProcessDefinitionService getFeign() {
        return this.service;
    }
}
