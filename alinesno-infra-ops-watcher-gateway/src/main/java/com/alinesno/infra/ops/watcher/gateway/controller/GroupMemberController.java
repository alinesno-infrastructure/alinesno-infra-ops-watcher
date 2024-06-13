package com.alinesno.infra.ops.watcher.gateway.controller;

import com.alinesno.infra.common.core.constants.SpringInstanceScope;
import com.alinesno.infra.common.facade.pageable.DatatablesPageBean;
import com.alinesno.infra.common.facade.pageable.TableDataInfo;
import com.alinesno.infra.common.facade.response.AjaxResult;
import com.alinesno.infra.common.web.adapter.login.account.CurrentAccountJwt;
import com.alinesno.infra.common.web.adapter.rest.BaseController;
import com.alinesno.infra.ops.watcher.entity.GroupMemberEntity;
import com.alinesno.infra.ops.watcher.service.IGroupMemberService;
import com.alinesno.infra.ops.watcher.service.IGroupService;
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
@RequestMapping("/api/infra/ops/watcher/groupMember")
public class GroupMemberController extends BaseController<GroupMemberEntity, IGroupMemberService> {

    @Autowired
    private IGroupMemberService service;

    @Autowired
    private IGroupService groupService;

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
        long countGitRepository = service.count(new LambdaQueryWrapper<GroupMemberEntity>().eq(GroupMemberEntity::getOperatorId , userId));

        // 初始化用户仓库
        if (countGitRepository == 0) {
            groupService.initGroup(CurrentAccountJwt.getUserId());
        }

        return this.toPage(model, this.getFeign(), page);
    }

    @GetMapping("/catalogTreeSelect")
    public AjaxResult catalogTreeSelect(){

        groupService.initGroup(CurrentAccountJwt.getUserId());

        return AjaxResult.success("success" , groupService.selectCatalogTreeList()) ;
    }

    @Override
    public IGroupMemberService getFeign() {
        return this.service;
    }
}
