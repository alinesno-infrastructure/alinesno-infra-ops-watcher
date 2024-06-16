package com.alinesno.infra.ops.watcher.gateway.controller;

import com.alinesno.infra.common.core.constants.SpringInstanceScope;
import com.alinesno.infra.common.facade.pageable.DatatablesPageBean;
import com.alinesno.infra.common.facade.pageable.TableDataInfo;
import com.alinesno.infra.common.web.adapter.rest.BaseController;
import com.alinesno.infra.ops.watcher.dto.AlertMessageDto;
import com.alinesno.infra.ops.watcher.entity.AlertMessageEntity;
import com.alinesno.infra.ops.watcher.entity.ProjectChannelEntity;
import com.alinesno.infra.ops.watcher.entity.ProjectEntity;
import com.alinesno.infra.ops.watcher.service.IAlertMessageService;
import com.alinesno.infra.ops.watcher.service.IProjectChannelService;
import com.alinesno.infra.ops.watcher.service.IProjectService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.annotations.Api;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * 处理与AlertMessageEntity相关的请求的Controller。
 * 继承自BaseController类并实现IAlertMessageService接口。
 *
 * @version 1.0.0
 * @since 1.0.0
 */
@Api(tags = "AlertMessage")
@RestController
@Scope(SpringInstanceScope.PROTOTYPE)
@RequestMapping("/api/infra/ops/watcher/alertMessage")
public class AlertMessageController extends BaseController<AlertMessageEntity, IAlertMessageService> {

    // 日志记录
    private static final Logger log = LoggerFactory.getLogger(AlertMessageController.class);

    @Autowired
    private IAlertMessageService service;

    @Autowired
    private IProjectService projectService ;

    @Autowired
    private IProjectChannelService projectChannelService ;

    /**
     * 获取AlertMessageEntity的DataTables数据。
     *
     * @param request HttpServletRequest对象。
     * @param model Model对象。
     * @param page DatatablesPageBean对象。
     * @return 包含DataTables数据的TableDataInfo对象。
     */
    @ResponseBody
    @PostMapping("/datatables")
    public TableDataInfo datatables(HttpServletRequest request, Model model, DatatablesPageBean page) {
        log.debug("page = {}", ToStringBuilder.reflectionToString(page));
        TableDataInfo tableDataInfo =  this.toPage(model, this.getFeign(), page);

        return getTableDataInfo(tableDataInfo);
    }

    /**
     * 根据查询结果填充提醒消息的详细信息。
     *
     * @param tableDataInfo 查询结果的包装类，包含分页信息和数据列表。
     * @return 填充了项目名称和项目渠道名称的提醒消息列表。
     */
    private TableDataInfo getTableDataInfo(TableDataInfo tableDataInfo) {
        // 初始化一个空的提醒消息DTO列表，用于存储处理后的数据
        List<AlertMessageDto> listDto = new ArrayList<>();
        // 将查询结果转换为提醒消息实体列表
        List<AlertMessageEntity> list = (List<AlertMessageEntity>) tableDataInfo.getRows();

        // 使用HashSet来去重并存储所有项目代码和项目渠道代码
        HashSet<String> projectCodeSet = new HashSet<>();
        HashSet<String> projectChannelCodeSet = new HashSet<>();

        // 遍历列表，收集所有不同的项目代码和项目渠道代码
        list.forEach(item -> {
            projectCodeSet.add(item.getProjectCode());
            projectChannelCodeSet.add(item.getCategory());
        });

        List<ProjectEntity> projectEntities;
        List<ProjectChannelEntity> projectChannelEntities;

        // 根据项目代码查询项目信息，以便后续填充项目名称
        if(!projectCodeSet.isEmpty()){
            projectEntities = projectService
                    .list(new LambdaQueryWrapper<ProjectEntity>()
                            .in(ProjectEntity::getProjectCode, projectCodeSet));
        } else {
            projectEntities = null;
        }

        // 根据项目渠道代码查询项目渠道信息，以便后续填充项目渠道名称
        if(!projectChannelCodeSet.isEmpty()) {
            projectChannelEntities = projectChannelService
                    .list(new LambdaQueryWrapper<ProjectChannelEntity>()
                            .in(ProjectChannelEntity::getChannelCode, projectChannelCodeSet));
        } else {
            projectChannelEntities = null;
        }

        // 遍历提醒消息列表，为每个消息填充项目名称和项目渠道名称
        list.forEach(item -> {
            AlertMessageDto dto = new AlertMessageDto();
            BeanUtils.copyProperties(item, dto);

            // 根据项目代码匹配项目名称
            if(!projectEntities.isEmpty()){
                projectEntities.forEach(projectEntity -> {
                    if (projectEntity.getProjectCode().equals(item.getProjectCode())) {
                        dto.setProjectName(projectEntity.getProjectName());
                    }
                });
            }

            // 根据项目渠道代码匹配项目渠道名称
            if(!projectChannelEntities.isEmpty()) {
                projectChannelEntities.forEach(projectChannelEntity -> {
                    if (projectChannelEntity.getChannelCode().equals(item.getCategory())) {
                        dto.setProjectChannelName(projectChannelEntity.getChannelDesc());
                    }
                });
            }

            // 将处理后的DTO添加到结果列表
            listDto.add(dto);
        });

        // 更新tableDataInfo中的数据列表为处理后的DTO列表
        tableDataInfo.setRows(listDto);

        // 返回填充了详细信息的查询结果
        return tableDataInfo;
    }

    @Override
    public IAlertMessageService getFeign() {
        return this.service;
    }
}
