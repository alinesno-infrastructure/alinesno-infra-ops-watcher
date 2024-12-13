package com.alinesno.infra.ops.watcher.gateway.controller;

import com.alinesno.infra.common.core.constants.SpringInstanceScope;
import com.alinesno.infra.common.facade.pageable.DatatablesPageBean;
import com.alinesno.infra.common.facade.pageable.TableDataInfo;
import com.alinesno.infra.common.facade.response.AjaxResult;
import com.alinesno.infra.common.web.adapter.login.account.CurrentAccountJwt;
import com.alinesno.infra.common.web.adapter.rest.BaseController;
import com.alinesno.infra.ops.watcher.dto.AlertChannelParamDto;
import com.alinesno.infra.ops.watcher.entity.AlertChannelEntity;
import com.alinesno.infra.ops.watcher.entity.AlertChannelParamEntity;
import com.alinesno.infra.ops.watcher.service.IAlertChannelParamService;
import com.alinesno.infra.ops.watcher.service.IAlertChannelService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.annotations.Api;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
@RequestMapping("/api/infra/ops/watcher/alert_channel")
public class AlertChannelController extends BaseController<AlertChannelEntity, IAlertChannelService> {

    /**
     * 报警渠道服务接口。
     * 用于处理报警渠道的增删改查等操作。
     */
    @Autowired
    private IAlertChannelService service;

    /**
     * 报警渠道参数服务接口。
     * 用于处理报警渠道参数的增删改查等操作。
     */
    @Autowired
    private IAlertChannelParamService alertChannelParamService ;

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
        long countGitRepository = service.count(new LambdaQueryWrapper<AlertChannelEntity>().eq(AlertChannelEntity::getOperatorId , userId));

        // 初始化用户仓库
        if (countGitRepository == 0) {
            service.initAlertChannel(CurrentAccountJwt.getUserId());
        }

        return this.toPage(model, this.getFeign(), page);
    }

    /**
     * 获取到渠道参数列表和参数值
     * @return
     */
    @GetMapping("/getChannelParams")
    public AjaxResult getChannelParams(long id , String channelCode){

        List<AlertChannelParamEntity> list =  alertChannelParamService.
                list(new LambdaQueryWrapper<AlertChannelParamEntity>()
                        .eq(AlertChannelParamEntity::getAlertChannelId , id)) ;

        return AjaxResult.success(list);
    }

    /**
     * 更新渠道参数
     * @param paramDtos
     * @return
     */
    @PutMapping("/updateAlertChannelParams")
    public AjaxResult updateAlertChannelParams(@RequestBody List<AlertChannelParamEntity> paramDtos ,
                                               @RequestParam long channelId){

        service.updateAlertChannelParams(paramDtos , channelId);

        return AjaxResult.success() ;
    }

    @Override
    public IAlertChannelService getFeign() {
        return this.service;
    }
}
