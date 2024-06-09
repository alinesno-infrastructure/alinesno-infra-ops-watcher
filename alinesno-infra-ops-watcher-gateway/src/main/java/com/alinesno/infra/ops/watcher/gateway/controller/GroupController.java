package com.alinesno.infra.ops.watcher.gateway.controller;

import com.alinesno.infra.common.core.constants.SpringInstanceScope;
import com.alinesno.infra.common.facade.pageable.DatatablesPageBean;
import com.alinesno.infra.common.facade.pageable.TableDataInfo;
import com.alinesno.infra.common.web.adapter.login.account.CurrentAccountJwt;
import com.alinesno.infra.common.web.adapter.rest.BaseController;
import com.alinesno.infra.ops.watcher.entity.GroupEntity;
import com.alinesno.infra.ops.watcher.service.IGroupService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.annotations.Api;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 处理与AlertChannelEntity相关的请求的Controller。
 * 继承自BaseController类并实现IAlertChannelService接口。
 *
 * @author LuoXiaoDong
 * @version 1.0.0
 */
@Slf4j
@Api(tags = "AlertChannel")
@RestController
@Scope(SpringInstanceScope.PROTOTYPE)
@RequestMapping("/api/infra/ops/watcher/group")
public class GroupController extends BaseController<GroupEntity, IGroupService> {

    @Autowired
    private IGroupService service;

    /**
     * 获取AlertChannelEntity的DataTables数据。
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

        long userId = CurrentAccountJwt.getUserId();
        long countGitRepository = service.count(new LambdaQueryWrapper<GroupEntity>().eq(GroupEntity::getOperatorId , userId));

        // 初始化用户仓库
        if (countGitRepository == 0) {
            service.initGroup(CurrentAccountJwt.getUserId());
        }

        return this.toPage(model, this.getFeign(), page);
    }

    @Override
    public IGroupService getFeign() {
        return this.service;
    }
}
