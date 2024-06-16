package com.alinesno.infra.ops.watcher.gateway.controller;

import com.alinesno.infra.common.core.constants.SpringInstanceScope;
import com.alinesno.infra.common.facade.pageable.DatatablesPageBean;
import com.alinesno.infra.common.facade.pageable.TableDataInfo;
import com.alinesno.infra.common.facade.response.AjaxResult;
import com.alinesno.infra.common.web.adapter.login.account.CurrentAccountJwt;
import com.alinesno.infra.common.web.adapter.rest.BaseController;
import com.alinesno.infra.ops.watcher.dto.ProjectChannelDto;
import com.alinesno.infra.ops.watcher.entity.ProjectChannelEntity;
import com.alinesno.infra.ops.watcher.service.IProjectChannelService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.annotations.Api;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * 处理与ApplicationEntity相关的请求的Controller。
 * 继承自BaseController类并实现IApplicationService接口。
 *
 * @author LuoXiaoDong
 * @version 1.0.0
 */
@Slf4j
@Api(tags = "Application")
@RestController
@Scope(SpringInstanceScope.PROTOTYPE)
@RequestMapping("/api/infra/ops/watcher/projectChannel")
public class ProjectChannelController extends BaseController<ProjectChannelEntity, IProjectChannelService> {

    @Autowired
    private IProjectChannelService service;

    /**
     * 获取ApplicationEntity的DataTables数据。
     *
     * @param request HttpServletRequest对象。
     * @param model   Model对象。
     * @param page    DatatablesPageBean对象。
     * @return 包含DataTables数据的TableDataInfo对象。
     */
    @ResponseBody
    @PostMapping("/datatables")
    public TableDataInfo datatables(HttpServletRequest request, Model model, DatatablesPageBean page) {
        log.debug("page = {}", ToStringBuilder.reflectionToString(page));

        long projectId = Long.parseLong(request.getParameter("projectId"));
        long count = service.count(new LambdaQueryWrapper<ProjectChannelEntity>().eq(ProjectChannelEntity::getProjectId, projectId));

        // 初始化默认应用
        if (count == 0) {
            service.initDefaultChannel(CurrentAccountJwt.getUserId() , projectId);
        }

        return this.toPage(model, this.getFeign(), page);
    }


    /**
     * 保存项目预警渠道
     * @param dto
     */
    @PostMapping("/addProviderChannel")
    public AjaxResult addProviderChannel(@RequestBody ProjectChannelDto dto) {
        service.addProviderChannel(dto);
        return AjaxResult.success("操作成功");
    }


    @Override
    public IProjectChannelService getFeign() {
        return this.service;
    }
}
