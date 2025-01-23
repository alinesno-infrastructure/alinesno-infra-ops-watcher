package com.alinesno.infra.ops.watcher.collector.bean;

import com.alinesno.infra.common.facade.base.BaseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 巡检数据
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString
public class InspectionDataDto extends BaseDto {

	private String name;
	private String nameCode;
	private String jsonData;
	private String busType;
	private String runStatus;
	private String totalTime;

}
