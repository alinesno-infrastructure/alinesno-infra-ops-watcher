package com.alinesno.infra.ops.watcher.scheduler.controller;

import com.alinesno.infra.common.core.constants.SpringInstanceScope;
import com.alinesno.infra.common.extend.datasource.annotation.DataPermissionSave;
import com.alinesno.infra.common.extend.datasource.annotation.DataPermissionScope;
import com.alinesno.infra.common.facade.pageable.DatatablesPageBean;
import com.alinesno.infra.common.facade.pageable.TableDataInfo;
import com.alinesno.infra.common.facade.response.AjaxResult;
import com.alinesno.infra.common.web.adapter.rest.BaseController;
import com.alinesno.infra.ops.watcher.scheduler.dto.SecretsDto;
import com.alinesno.infra.ops.watcher.scheduler.entity.SecretsEntity;
import com.alinesno.infra.ops.watcher.scheduler.service.ISecretsService;
import io.swagger.annotations.Api;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * 处理与TransEntity相关的请求的Controller。
 * 继承自BaseController类并实现ITransService接口。
 *
 * @version 1.0.0
 * @author  luoxiaodong
 */
@Slf4j
@RestController
@Scope(SpringInstanceScope.PROTOTYPE)
@RequestMapping("/api/infra/data/scheduler/secrets")
public class SecretsController extends BaseController<SecretsEntity, ISecretsService> {

    @Autowired
    private ISecretsService service;

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
        return this.toPage(model, this.getFeign(), page);
    }

    /**
     * 保存认证
     * @return
     */
    @DataPermissionSave
    @PostMapping("/saveSec")
    public AjaxResult saveSec(@RequestBody @Validated SecretsDto dto) {

        SecretsEntity entity = new SecretsEntity() ;
        BeanUtils.copyProperties(dto, entity);

        entity.setUpdateTime(new Date());
        service.save(entity) ;

        return ok() ;
    }

    /**
     * 更新密钥信息
     */
    @PutMapping("/updateSec")
    public AjaxResult updateSec(@RequestBody @Validated SecretsDto dto) {

        SecretsEntity entity = service.getById(dto.getId()) ;

        entity.setSecName(dto.getSecName());
        if(dto.getSecValue() != null){
            entity.setSecValue(dto.getSecValue());
        }
        entity.setSecDesc(dto.getSecDesc());
        entity.setSecScope(dto.getSecScope());

        entity.setUpdateTime(new Date());
        service.updateById(entity) ;

        return ok() ;
    }

    /**
     * 通过id查询密钥信息
     * @return
     */
    @GetMapping("/findSecById")
    public AjaxResult findSecById(@RequestParam long id) {

        SecretsEntity entity = service.getById(id) ;
        entity.setSecValue("");

        return AjaxResult.success(entity) ;
    }

    @Override
    public ISecretsService getFeign() {
        return this.service;
    }
}
