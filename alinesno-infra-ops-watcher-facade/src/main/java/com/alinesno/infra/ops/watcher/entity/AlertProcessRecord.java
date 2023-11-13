package com.alinesno.infra.ops.watcher.entity;

import com.alinesno.infra.common.facade.mapper.entity.InfraBaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@TableName("alert_process_record")
@Data
public class AlertProcessRecord extends InfraBaseEntity {

    private Long alertId; // 关联的预警信息ID
    private Long processorId; // 处理人员ID
    private Date processTime; // 处理时间
    private String processResult; // 处理结果描述
    private String remark; // 处理备注
}